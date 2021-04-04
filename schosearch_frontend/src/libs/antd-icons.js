import { UpOutlined, DownOutlined, SearchOutlined } from "@ant-design/icons-vue";

const components = [UpOutlined, DownOutlined, SearchOutlined]

export default {
  install (app) {
    for (let compo of components) {
      app.component(compo.displayName, compo);
    }
  }
}