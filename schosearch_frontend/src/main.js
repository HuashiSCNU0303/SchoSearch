import { createApp } from "vue";
import App from "./App.vue";
import router from "@/router/index.js";

import api from "@/api/api.js";
import antdIcons from "@/libs/antd-icons.js";
import antd from "@/libs/antd.js"

// import Antd from "ant-design-vue";
// import "ant-design-vue/dist/antd.css";

import AnimateCss from "animate.css";

const app = createApp(App);

app
  // .use(Antd)
  .use(router)
  .use(AnimateCss)
  .mount("#app");

antd.install(app);
antdIcons.install(app);

app.config.globalProperties.$api = api;
