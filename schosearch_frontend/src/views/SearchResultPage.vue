<template>
  <div class="page">
    <div class="animate__animated animate__fadeInDown animate__fast">
      <a-row>
        <a-col>
          <img class="logo" src="@/assets/SchoSearch_Logo.png" />
        </a-col>
        <a-col>
          <search-box
            class="search-box"
            :justify="'start'"
            :initK="k"
            :dropDownTagCnt="10"
            :initR="r"
            :initTags="queryAttrStrs"
            :handleSearch="handleSearch"
          />
        </a-col>
      </a-row>
      <a-divider style="height: 1px; background-color: #d9d9d9" />
    </div>
    <div>
      <router-view v-slot="{ Component }" v-if="isRouterAlive">
        <transition
          appear
          appear-active-class="animate__animated animate__fadeIn animate__slow"
        >
          <component :is="Component" />
        </transition>
      </router-view>
    </div>
  </div>
</template>

<script>
import SearchBox from "../components/SearchBox.vue";
export default {
  components: { SearchBox },
  data() {
    return {
      isRouterAlive: true,
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
  },
  methods: {
    handleSearch(k, r, selectedTags) {
      this.$route.params = {
        k: k,
        r: r,
        queryAttrStrs: selectedTags,
      };
      this.isRouterAlive = false;
      this.$nextTick(function () {
        this.isRouterAlive = true;
      });
    },
  },
};
</script>

<style scoped>
.logo {
  max-width: 200px;
  margin: 28px 16px 8px 0px;
}

.search-box {
  margin: 28px 0 8px 0;
}

.page {
  padding: 0 32px 32px 32px;
}
</style>
