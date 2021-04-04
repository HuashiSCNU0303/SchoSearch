<template>
  <div>
    <a-card class="card">
      <a-row>
        <a-col :span="13">
          <!--关系图-->
          <div class="graph" ref="graph"></div>
        </a-col>
        <a-col :span="11" style="padding: 0 16px; display: flex; flex-direction: column">
          <a-row style="margin-bottom: 8px">
            <a-typography-title :level="5">主要研究方向</a-typography-title>
          </a-row>
          <div>
            <span v-for="(tag, index) in tags" :key="index" class="tags-input-badge">
              <a-tag class="tag" :color="judgeTag(tag) ? '#f50' : 'default'">{{
                tag
              }}</a-tag>
            </span>
          </div>
          <a-divider style="height: 1px; margin: 16px 0; background-color: #d9d9d9" />
          <a-typography-title :level="5">学者信息</a-typography-title>
          <a-list
            item-layout="horizontal"
            :data-source="authorInfo"
            v-if="updateKey != 0"
            :key="updateKey"
          >
            <template #renderItem="{ item }">
              <a-list-item v-if="authorInfo.length > 0">
                <a-list-item-meta :title="item.name">
                  <template #avatar>
                    <img src="@/assets/author.png" style="width: 48px; height: 48px" />
                  </template>
                  <template #description>
                    <p v-if="item.org != 'unknown'">{{ item.org }}</p>
                    <p>
                      主要研究方向：<span v-for="(tag, index) in item.attrs" :key="index"
                        ><a-tag
                          :color="judgeTag(tag) ? '#f50' : 'default'"
                          style="margin-bottom: 0.25em"
                          >{{ tag }}</a-tag
                        ></span
                      >
                    </p>
                  </template>
                </a-list-item-meta>
              </a-list-item>
            </template>
          </a-list>
          <div v-else class="no-author-block">
            <a-empty description="点击左边网络中的结点，可查看学者具体信息哦~" />
          </div>
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>

<script>
import * as echarts from "echarts/core";
import { GraphChart } from "echarts/charts";
import { TitleComponent, TooltipComponent } from "echarts/components";
import { CanvasRenderer } from "echarts/renderers";
echarts.use([TitleComponent, TooltipComponent, GraphChart, CanvasRenderer]);

import papers from "../store/papers";
import { toRefs } from "vue";

export default {
  data() {
    return {
      authorInfo: {},
      updateKey: 0,
    };
  },
  props: {
    originalNodes: Array, // 图结点，包含学者原始信息，要整理一下
    links: Array, // 图的边
    order: Number, // 排序
    tags: Array, // 团队主要研究方向
    queryAttrs: Array,
  },
  mounted() {
    this.drawGraph();
  },
  computed: {
    graphNodes() {
      var nodes = [];
      let authorIcon =
        '<?xml version="1.0" encoding="utf-8"?> \
<!-- Generator: Adobe Illustrator 23.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  --> \
<svg version="1.2" baseProfile="tiny" id="图层_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 200 200" xml:space="preserve"> \
<path fill="#FAAD14" d="M0,100c0,55.23,44.77,100,100,100s100-44.77,100-100S155.23,0,100,0S0,44.77,0,100z"/> \
<path fill="#FFFFFF" d="M64.44,80v3.51H62.2V85h-4.4v-1.49h-2.24V80h2.24c0-5.84-0.01-11.67,0-17.51h4.4V80H64.44z"/> \
<path fill="#FFFFFF" d="M153.33,144.22c-0.05,2.23-0.59,4.43-1.57,6.43c-3.77,1.52-7.7,2.73-11.66,3.83 \
	c-12.97,3.51-26.34,5.36-39.78,5.51c5.55-4.43,11.08-8.87,16.65-13.28c-2.17-4.55-4.44-9.06-6.64-13.6l-8-16.31l4.12-5.5 \
	c-1.83,1.13-4.05,1.74-6.27,1.71c-2.45-0.05-4.87-0.64-7.08-1.71c1.25,1.92,2.57,3.8,3.86,5.7c0.14,0.18-0.07,0.39-0.12,0.56 \
	c-4.54,9.82-9.05,19.63-13.57,29.45l17.02,12.98c-18.29,0.22-36.65-2.91-53.49-9.34c-0.27-2.65-0.1-5.33,0.48-7.93 \
	c1.15-5.2,3.55-10.04,6.98-14.11c3.18-3.78,6.95-7.03,11.16-9.63c6.19-3.9,13.17-6.69,20.35-8.79c-3.9-3.12-7.28-6.83-10.02-11.01 \
	c-2.72-4.09-4.81-8.56-6.18-13.27c-2.08-7.32-2.45-15.03-1.07-22.52c-5.89-2.46-11.8-4.88-17.7-7.35 \
	c16.16-5.29,32.33-10.57,48.49-15.86c0.29-0.07,0.58-0.27,0.88-0.13h0.01l51.53,15.98c-1.78,0.71-3.61,1.3-5.41,1.97l-14.47,5.18 \
	c0.57,6.46,0.39,12.96-0.55,19.38c-0.93,6.03-2.57,12.06-5.68,17.5c-2.3,3.99-5.52,7.63-9.71,10.13c5.17,2.14,10.19,4.63,15.01,7.46 \
	c4.7,2.76,9.2,5.84,13.13,9.46c2.88,2.65,5.48,5.62,7.24,9C152.6,138.63,153.39,141.41,153.33,144.22z"/></svg>';
      authorIcon = encodeURIComponent(authorIcon);
      authorIcon = "data:image/svg+xml;utf8," + authorIcon;
      authorIcon = "image://" + authorIcon;
      for (let node of this.originalNodes) {
        nodes.push({
          name: node.name,
          symbol: authorIcon,
        });
      }
      return nodes;
    },
    ...toRefs(papers.papers),
  },
  methods: {
    judgeTag(tag) {
      return this.queryAttrs.findIndex((item) => item == tag) != -1;
    },

    drawGraph() {
      var graphRef = this.$refs.graph;
      var myChart = echarts.init(graphRef);
      var _this = this;
      var option = {
        title: {
          text: "学者协作网络",
          padding: [16, 0, 0, 16],
        },
        tooltip: {
          confine: true,
          enterable: true,
          appendToBody: true,
          formatter: function (params, ticket, callback) {
            if (params.seriesType == "graph") {
              if (params.dataType == "node") {
                return params.name;
              } else if (params.dataType == "edge") {
                var authorPapers = papers.paperMethods.findPaper(
                  params.data.source,
                  params.data.target
                );
                if (authorPapers.length == 0) {
                  _this.$api.getPapers(params.data).then((res) => {
                    var newPapers = res.data;
                    var paperStr =
                      params.data.source +
                      "和" +
                      params.data.target +
                      "共同发表过的论文有：<br/>";
                    for (let str of newPapers) {
                      paperStr += "· " + str + "<br/>";
                    }
                    papers.paperMethods.addPaper(
                      params.data.source,
                      params.data.target,
                      newPapers
                    );
                    callback(ticket, paperStr);
                  });
                  return "加载中...";
                } else {
                  var paperStr =
                    params.data.source +
                    "和" +
                    params.data.target +
                    "共同发表过的论文有：<br/>";
                  for (let str of authorPapers) {
                    paperStr += "· " + str + "<br/>";
                  }
                  return paperStr;
                }
              }
            }
          },
          extraCssText:
            "max-width: 800px; overflow: hidden; white-space: normal; word-break:break;",
        },
        animationDurationUpdate: 1500,
        animationEasingUpdate: "quinticInOut",
        series: [
          {
            type: "graph",
            layout: "circular",
            symbolSize: 40,
            roam: true,
            label: {
              show: true,
              position: "top",
            },
            data: this.graphNodes,
            links: this.links,
            lineStyle: {
              opacity: 0.9,
              width: 3,
              curveness: 0,
              color: "#91d5ff",
            },
            emphasis: {
              lineStyle: {
                opacity: 0.9,
                width: 9,
                curveness: 0,
                color: "#1890ff",
              },
            },
          },
        ],
      };

      option && myChart.setOption(option);
      myChart.on("click", function (params) {
        if (params.seriesType == "graph") {
          if (params.dataType == "node") {
            var name = params.data.name;
            _this.authorInfo = [_this.originalNodes.find((item) => item.name == name)];
            _this.updateKey++;
          }
        }
      });
    },
  },
};
</script>

<style scoped>
.card {
  margin: 0px;
  background-color: transparent;
  border-width: 0px;
}

.graph {
  height: 500px;
  background: transparent;
  border-radius: 8px;
  border-width: 1px;
  border-style: solid;
  border-color: #d9d9d9;
}

.tags-input-badge {
  position: relative;
  display: inline-block;
  font-size: 100%;
  font-weight: 500;
  line-height: 1;
  text-align: center;
  white-space: nowrap;
  vertical-align: baseline;
  border-radius: 0.25em;
  overflow: hidden;
  text-overflow: ellipsis;
}

.tag {
  font-size: 14px;
  line-height: 26px;
}

.rank {
  color: rgba(0, 0, 0, 0.85);
  font-weight: 600;
  font-size: 38px;
  line-height: 1.23;
  margin-left: 12px;
}

.no-author-block {
  flex-grow: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
