package schosearch.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import schosearch.backend.bean.HttpProps;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/get")
public class SearchController {

    @Autowired
    private HttpProps httpProps;

    // final static String BASE_URL = "http://192.168.193.154", USERNAME = "neo4j", PASSWORD = "123456";


    @PostMapping("/communities")
    @ResponseBody
    public String getCommunities(@RequestBody String json) {
        RestTemplate restTemplate = new RestTemplate();
        // String url = BASE_URL + ":8082/search";
        String url = httpProps.getCommunitiesUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return response.getBody();
    }

    @PostMapping("/papers")
    @ResponseBody
    public String getPapers(@RequestBody String json) {
        // 找paper，要组装一个json

        JSONObject reqObj = JSON.parseObject(json);
        String src = reqObj.getString("source"), dst = reqObj.getString("target");

        JSONObject paramObj = new JSONObject();
        paramObj.put("xName", src);
        paramObj.put("yName", dst);

        JSONObject statObj = new JSONObject();
        statObj.put("statement", "MATCH (x:author) - [co:COOPERATE] -> (y:author) WHERE x.name = $xName AND y.name = $yName RETURN co.papers");
        statObj.put("parameters", paramObj);

        JSONArray statArray = new JSONArray();
        statArray.add(statObj);

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("statements", statArray);

        String bodyStr = bodyObj.toJSONString();

        RestTemplate restTemplate = new RestTemplate();
        // String url = BASE_URL + ":7474/db/data/transaction/commit";
        String url = httpProps.getPapersUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.setBasicAuth(USERNAME, PASSWORD);
        headers.setBasicAuth(httpProps.getPapersAuthName(), httpProps.getPapersAuthPass());
        HttpEntity<String> requestEntity = new HttpEntity<>(bodyStr, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String responseString = response.getBody();

        JSONObject respObj = JSON.parseObject(responseString);
        JSONArray resultArr = respObj.getJSONArray("results");
        JSONArray dataArr = resultArr.getJSONObject(0).getJSONArray("data");
        JSONArray rowArr = dataArr.getJSONObject(0).getJSONArray("row");
        List<String> paperList = rowArr.getJSONArray(0).toJavaList(String.class);

        // 要整理一下再返回
        return JSON.toJSONString(paperList);
    }

    @GetMapping("/keywords")
    @ResponseBody
    public String getKeywords(@RequestParam("keyword") String word) {
        // 模糊搜索，要组装一个json

        JSONObject matchObj = new JSONObject();
        if (word != null && !"".equals(word)) {
            matchObj.put("name", word);
        }

        JSONObject queryObj = new JSONObject();
        if (word != null && !"".equals(word)) {
            queryObj.put("match", matchObj);
        } else {
            queryObj.put("match_all", matchObj);
        }

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("query", queryObj);

        String bodyStr = bodyObj.toJSONString();

        RestTemplate restTemplate = new RestTemplate();
        // String url = BASE_URL + ":9200/attrs/attr/_search?filter_path=hits.hits._source";
        String url = httpProps.getKeywordsUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(bodyStr, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String responseString = response.getBody();
        JSONObject respObj = JSON.parseObject(responseString);
        JSONObject hitsObj = respObj.getJSONObject("hits");
        if (hitsObj == null) {
            return "[]";
        }
        JSONArray hitsArr = hitsObj.getJSONArray("hits");

        List<String> keywordList = new ArrayList<>();
        for (int i = 0; i < hitsArr.size(); i++) {
            JSONObject srcObj = hitsArr.getJSONObject(i);
            JSONObject _srcObj = srcObj.getJSONObject("_source");
            String name = _srcObj.getString("name");
            keywordList.add(name);
        }

        return JSON.toJSONString(keywordList);
    }
}
