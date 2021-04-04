module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
  plugins: [
    [
      "import",
      {
        libraryName: "ant-design-vue",
        libraryDirectory: "es",
        style: 'css' // 把true改成'css' 可以解决
      }
    ],
    ["lodash"],
  ]
}
