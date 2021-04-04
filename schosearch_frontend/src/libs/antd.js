import { Button, Collapse, AutoComplete, Input, InputNumber, Popover, Select, Col, Row, Tag, Card, Typography, Divider, List, Empty, Badge, Spin } from "ant-design-vue";

// 需要用name属性注册的组件
const nameComponents = [Button, Collapse, AutoComplete, Input, InputNumber, Popover, Select, Col, Row, Tag, Card, Typography, Divider, List, Badge, Spin, Collapse.Panel, List.Item];

// 需要用displayName属性注册的组件
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