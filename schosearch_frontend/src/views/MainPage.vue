<template>
  <div class="block">
    <div class="logo-text">
      <div
        :class="{
          'animate__animated animate__fadeInDown animate__fast': show,
          'animate__animated animate__fadeOutUp animate__fast': !show,
        }"
      >
        <img class="logo" src="@/assets/SchoSearch_Logo.png" />
        <p class="intro-text">一个基于关键词的研究团队搜索系统</p>
      </div>
    </div>
    <div style="flex: 1">
      <search-box
        :class="{
          'animate__animated animate__fadeInUp animate__fast': show,
          'animate__animated animate__fadeOutDown animate__fast': !show,
        }"
        style="margin-top: 20px"
        :initK="6"
        :initR="1"
        :dropDownTagCnt="5"
        :justify="'center'"
        :initTags="[]"
        :handleSearch="handleSearch"
      />
    </div>
  </div>
</template>

<script>
import SearchBox from "../components/SearchBox.vue";
export default {
  components: { SearchBox },
  data() {
    return {
      show: true,
    };
  },
  methods: {
    handleSearch(k, r, selectedTags) {
      var queryAttrs = [];
      for (var i = 0; i < selectedTags.length; i++) {
        queryAttrs.push(selectedTags[i]);
      }
      this.show = false;
      // 1s后跳转
      setTimeout(() => {
        this.$router.push({
          name: "Results",
          params: {
            k: k,
            r: r,
            queryAttrStrs: queryAttrs,
          },
        });
      }, 800);
    },
  },
};
</script>

<style scoped>
.logo {
  display: block;
  max-width: 400px;
  margin: 0 auto;
}

.intro-text {
  font-size: 16px;
  text-align: center;
  color: rgba(0, 0, 0, 0.45);
}

.block {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.logo-text {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}
</style>
