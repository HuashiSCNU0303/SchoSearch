const URL = "http://39.106.97.180:8280/get/";

const header = {
  'Access-Control-Allow-Origin': '*',
}

import axios from "axios"

export default {
  getCommunities (data) {
    return axios({
      url: URL + "communities",
      method: "post",
      data: data,
      headers: header,
    })
  },

  getPapers (data) {
    return axios({
      url: URL + "papers",
      method: "post",
      data: data,
      headers: header,
    })
  },

  getKeywords (word) {
    return axios({
      url: URL + "keywords",
      method: "get",
      params: {
        keyword: word,
      },
      headers: header,
    })
  },
};