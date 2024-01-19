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
import MentionItem from "../HomePage/MentionItem.vue";
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
  //this.mentionNum监视store.state.notificationNum的变化
  watch: {
    "$store.state.notificationNum": function (newVal, oldVal) {
      this.mentionNum = newVal;
    },
    "$store.state.notifications": function (newVal, oldVal) {
      this.mention = newVal;
    },
  },
  methods: {
    getMention() {
      this.mentionNum = this.$store.state.notificationNum;
      this.mention = this.$store.state.notifications;
    },
    clearMention() {
        this.mentionNum = 0;
        this.$store.dispatch('clearNotificationNum');
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