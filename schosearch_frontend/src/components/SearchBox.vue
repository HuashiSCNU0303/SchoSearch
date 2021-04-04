<template>
  <a-row type="flex" :justify="justify">
    <a-col style="margin-right: 16px">
      <div style="position: relative; width: 600px">
        <div
          :class="{
            'tags-input-wrapper-default': true,
            active: isActive,
          }"
        >
          <div class="tags-input-badge">
            <span v-for="(tag, index) in selectedTags" :key="tag"
              ><a-tag closable class="tag" @close="removeTag(index)">{{
                tag
              }}</a-tag></span
            >
          </div>
          <a-auto-complete
            :allowClear="true"
            :defaultActiveFirstOption="false"
            style="width: 100%"
            not-found-content="未找到匹配的关键词"
            @search="getCandidate"
            @select="selectTag"
            @keydown.enter.prevent="checkTag()"
            @focus="setActive(true)"
            @blur="setActive(false)"
            @click="setActive(true)"
          >
            <template #dataSource>
              <a-select-option v-for="str in candidates" :key="str" />
            </template>
            <template #default>
              <a-input placeholder="添加搜索关键词吧~" v-model:value="newTagInput" />
            </template>
          </a-auto-complete>
        </div>
      </div>
    </a-col>
    <a-col style="margin-right: 16px">
      <a-popover
        v-model:visible="isVisible"
        class="search-button"
        placement="bottom"
        trigger="hover"
      >
        <template #content>
          <span
            >团队至少应有几人？&nbsp;&nbsp;<a-input-number :min="2" v-model:value="k"
          /></span>
          <a-divider style="margin: 8px 0" />
          <span
            >共返回多少个团队？&nbsp;&nbsp;<a-input-number :min="1" v-model:value="r"
          /></span>
        </template>
        <a-button> 搜索参数设置 <DownOutlined /></a-button>
      </a-popover>
    </a-col>
    <a-col>
      <a-button class="search-button" type="primary" @click="search">
        <template #icon><SearchOutlined /></template>
        搜索
      </a-button>
    </a-col>
  </a-row>
</template>

<script>
import { debounce } from "lodash";
// var debounce = require("lodash/debounce");
import { message } from "ant-design-vue";
export default {
  name: "SearchBox",
  data() {
    return {
      selectedTags: this.initTags,
      k: this.initK,
      r: this.initR,
      isActive: false,
      isVisible: false,
      newTagInput: "",
      selected: false,
      candidates: [],
    };
  },
  props: {
    justify: {
      type: String,
    },
    initK: {
      type: Number,
    },
    initR: {
      type: Number,
    },
    initTags: {
      type: Array,
    },
    handleSearch: {
      type: Function,
    },
    dropDownTagCnt: {
      type: Number,
    },
  },
  mounted() {
    // 先调接口请求几个比较火的关键词
    this.$api.getKeywords("").then((res) => {
      this.candidates = res.data.slice(0, this.dropDownTagCnt);
    });
  },
  methods: {
    checkTag() {
      if (!this.selected) {
        this.$api.getKeywords(this.newTagInput).then((res) => {
          var flag = false;
          for (var i = 0; i < res.data.length; i++) {
            if (res.data[i] == this.newTagInput) {
              flag = true;
              break;
            }
          }
          if (flag) {
            this.addTag(this.newTagInput);
          } else {
            message.error("尚未收录具有该研究方向的学者哦~", 1);
          }
        });
      } else {
        this.selected = false;
      }
    },

    selectTag(newTag) {
      this.selected = true;
      this.addTag(newTag);
    },

    addTag(newTag) {
      var flag = false;
      for (var i = 0; i < this.selectedTags.length; i++) {
        var tag = this.selectedTags[i];
        if (tag == newTag) {
          message.error("该关键词已经被添加~", 1);
          flag = true;
          break;
        }
      }
      if (!flag) {
        this.selectedTags.push(newTag);
      }
    },

    removeTag(index) {
      this.selectedTags.splice(index, 1);
    },

    setActive(active) {
      this.isActive = active;
    },

    getCandidate: debounce(function (value) {
      // 调接口获取模糊搜索的匹配，然后再赋值给candidates即可
      this.$api.getKeywords(value).then((res) => {
        this.candidates = res.data.slice(0, this.dropDownTagCnt);
      });
    }, 500),

    search() {
      this.candidates = [];
      if (this.selectedTags.length == 0) {
        message.error("请至少选择一个关键词进行搜索~", 1);
      } else {
        this.handleSearch(this.k, this.r, this.selectedTags);
      }
    },
  },
};
</script>

<style scoped>
.tag {
  font-size: 14px;
  line-height: 26px;
}

.search-button {
  margin-top: 0.5em;
}

.tags-input-wrapper-default {
  padding: 0.5em 0.25em;
  background: #fff;
  border: 1px solid transparent;
  border-radius: 0.25em;
  border-color: #dbdbdb;
}

.tags-input-wrapper-default.active {
  border: 1px solid #8bbafe;
  box-shadow: 0 0 0 0.2em rgba(13, 110, 253, 0.25);
  outline: 0 none;
}

.tags-input-badge {
  position: relative;
  display: inline-block;
  padding-bottom: 0.4em;
  font-size: 100%;
  font-weight: 500;
  line-height: 1;
  text-align: center;
  white-space: nowrap;
  vertical-align: baseline;
  border-radius: 0.25em;
  overflow: hidden;
  text-overflow: ellipsis;
}

.ant-input {
  display: inline;
  border-color: transparent !important;
  box-shadow: 0 0 0 0px transparent !important;
}

.ant-input:focus {
  border-color: transparent !important;
  box-shadow: 0 0 0 0px transparent !important;
}
</style>
