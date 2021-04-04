const LodashModuleReplacementPlugin = require("lodash-webpack-plugin");

module.exports = {
  /** 区分打包环境与开发环境
   * process.env.NODE_ENV==='production'  (打包环境)
   * process.env.NODE_ENV==='development' (开发环境)
   * baseUrl: process.env.NODE_ENV==='production'?"https://cdn.didabisai.com/front/":'front/',
   */

  // 项目部署的基础路径
  // 我们默认假设你的应用将会部署在域名的根部,
  // 例如 https://www.my-app.com/
  // 如果你的应用部署在一个子路径下，那么你需要在这里
  // 指定子路径。比如将你的应用部署在
  // https://www.foobar.com/my-app/
  // 那么将这个值改为 '/my-app/'

  publicPath: "./",
  outputDir: "dist",
  lintOnSave: false,
  runtimeCompiler: false,
  transpileDependencies: [],
  productionSourceMap: false,

  chainWebpack: (config) => {
    config.plugin("loadshReplace").use(new LodashModuleReplacementPlugin());
  },

  devServer: {
    open: process.platform === "darwin",
    disableHostCheck: false,
    host: "127.0.0.1",
    port: 8082,
    https: false,
    hotOnly: false,
    proxy: null,
  },
};
