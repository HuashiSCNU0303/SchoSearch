<template>
  <div>
    <transition
      enter-active-class="animate__animated animate__fadeIn"
      leave-active-class="animate__animated animate__fadeOut"
      mode="out-in"
    >
      <div class="bottom" v-if="isLoading">
        <a-spin tip="加载中..." size="large" />
      </div>
      <a-collapse
        :bordered="false"
        style="padding-bottom: 32px; background-color: transparent"
        v-model:activeKey="activeKey"
        v-else-if="communities.length > 0"
      >
        <a-collapse-panel
          style="background-color: transparent"
          v-for="(item, index) in communities"
          :key="index.toString()"
          :showArrow="false"
        >
          <community-card
            :order="item.order"
            :tags="item.tags"
            :links="item.edges"
            :originalNodes="item.nodes"
            :queryAttrs="queryAttrStrs"
          />

          <template #header>
            <span class="header">
              <span>
                <a-badge color="#f50" text="排行" /><span class="rank">{{
                  item.order
                }}</span>
              </span>
              <span style="margin-left: auto">
                <span v-if="activeKey != index.toString()"
                  ><DownOutlined />&nbsp; 展开</span
                >
                <span v-else><UpOutlined />&nbsp; 收起</span>
              </span>
            </span>
          </template>
        </a-collapse-panel>
      </a-collapse>
      <div class="bottom" v-else>
        <a-empty
          image="https://gw.alipayobjects.com/mdn/miniapp_social/afts/img/A*pevERLJC9v0AAAAAAAAAAABjAQAAAQ/original"
          :image-style="{
            height: '60px',
          }"
        >
          <template #description>
            没找到结果哦~<br />试试换一些关键词，或者调整一下搜索参数吧~
          </template>
        </a-empty>
      </div>
    </transition>
  </div>
</template>

<script>
import CommunityCard from "../components/CommunityCard.vue";
export default {
  components: { CommunityCard },
  data() {
    return {
      communities: [],
      isLoading: true,
      activeKey: "0",
    };
  },
  computed: {
    k() {
      return Number(this.$route.params.k);
    },
    r() {
      return Number(this.$route.params.r);
    },
    queryAttrStrs() {
      return this.$route.params.queryAttrStrs;
    },
    showAnimate() {
      return this.$route.params.showAnimate;
    },
  },
  mounted() {
    // 向服务器发请求
    this.$api
      .getCommunities({
        k: this.k,
        r: this.r,
        queryAttrStrs: this.queryAttrStrs,
      })
      .then((res) => {
        this.communities = res.data;
        this.isLoading = false;
      });
  },
  methods: {
    handleSearch(k, r, selectedTags) {
      this.$router.push({
        name: "Main",
        params: {
          k: k,
          r: r,
          showAnimate: false,
          queryAttrStrs: selectedTags,
        },
      });
    },
  },
};
</script>

<style scoped>
.bottom {
  height: 400px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.header {
  padding-left: 16px;
  width: 100%;
  display: flex;
  align-items: center;
}

.rank {
  color: rgba(0, 0, 0, 0.85);
  font-weight: 600;
  font-size: 38px;
  line-height: 1.23;
  margin-left: 12px;
}
</style>
