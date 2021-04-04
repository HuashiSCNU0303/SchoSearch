// 按需引入Ant Design，但配置不了babel，先不管了
// 而且Select下的组件也不能帮我注册是咋回事……

import { Button, Collapse, AutoComplete, Input, InputNumber, Popover, Select, Col, Row, Tag, Card, Typography, Divider, List, Empty, Badge, Spin } from "ant-design-vue";

const nameComponents = [Button, Collapse, AutoComplete, Input, InputNumber, Popover, Select, Col, Row, Tag, Card, Typography, Divider, List, Badge, Spin, Collapse.Panel, List.Item];
const displayNameComponents = [Select.Option, Typography.Title, List.Item.Meta, Empty]

export default {
  install (app) {
    for (let compo of nameComponents) {
      app.component(compo.name, compo);
    }

    for (let compo of displayNameComponents) {
      app.component(compo.displayName, compo);
    }
  }
}