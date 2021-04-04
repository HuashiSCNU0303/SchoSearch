// 怎么用babel-loader配置按需加载啊……

import UpOutlined from "@ant-design/icons-vue/UpOutlined";
import DownOutlined from "@ant-design/icons-vue/DownOutlined";
import SearchOutlined from "@ant-design/icons-vue/SearchOutlined";

const components = [UpOutlined, DownOutlined, SearchOutlined]

export default {
  install (app) {
    for (let compo of components) {
      app.component(compo.displayName, compo);
    }
  }
}