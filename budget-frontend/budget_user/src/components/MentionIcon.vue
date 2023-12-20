<template>
  <el-badge :value="mentionNum" :max="99" class="item">
    <el-popover placement="bottom" trigger="click" @show="clearMention">
      <ul v-if="mention.length">
        <li v-for="(item, index) in mention" :key="index">
          <mention-item :mention="item"></mention-item>
        </li>
      </ul>
      <div v-else>No mentions</div>
      <i class="fa-regular fa-bell fa-2x" slot="reference"></i>
    </el-popover>
  </el-badge>
</template>

<script>
import MentionItem from "./MentionItem.vue";
export default {
  name: "MentionIcon",
  components: { MentionItem },
  data() {
    return {
      mention: [],
      mentionNum: "",
    };
  },
  created() {
    this.getMention();
  },
  methods: {
    getMention() {
      this.$http.get("/user/mention").then((res) => {
        console.log("获取提醒：", res);
        if (res.data.status === 20000) {
          this.mentionNum = res.data.data.mention;
          this.mention = res.data.data.mentionList;
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    clearMention() {
        this.mentionNum = 0;
    }
  },
};
</script>

<style>
/* 当鼠标移动到物体上变色 */
.item:hover {
  color: #409eff;
}
</style>