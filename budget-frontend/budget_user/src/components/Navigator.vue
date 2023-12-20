<template>
  <el-menu mode="horizontal">
    <el-menu-item index="1" @click="onClickMainMenu">首页</el-menu-item>
    <el-menu-item index="2" @click="onClickTodoList">待办事项</el-menu-item>
    <el-menu-item index="3" @click="onClickHistory">消费记录</el-menu-item>
    <el-menu-item index="4" @click="onClickRank">排行榜</el-menu-item>
    <el-menu-item index="5" @click="onClickReport">个人报告</el-menu-item>
    <!-- 其他菜单项 -->
    <div class="menu-right">
      <MentionIcon></MentionIcon>
      <ContinueIcon></ContinueIcon>
      <el-avatar icon="el-icon-user-solid" :size="50" :src="url"></el-avatar>
    </div>
  </el-menu>
</template>

<script>
import MentionIcon from "./MentionIcon.vue";
import ContinueIcon from "./ContinueIcon.vue";
export default {
  name: "Navigator",
  components: { MentionIcon, ContinueIcon },
  data() {
    return {
      url: "",
      mention: [],
    };
  },
  created() {
    this.getUserAvatar();
  },
  methods: {
    getUserAvatar() {
      this.$http.get("/user/avatar").then((res) => {
        console.log("获取用户头像：", res);
        if (res.data.status === 20000) {
          this.url = res.data.data.url;
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    onClickMainMenu() {
      //如果是首页，就不跳转
      if (this.$route.path === "/home") return;
      this.$router.push("/home");
    },
    onClickTodoList() {
      if (this.$route.path === "/todo") return;
      this.$router.push("/todo");
    },
    onClickHistory() {
      if (this.$route.path === "/history") return;
      this.$router.push("/history");
    },
    onClickRank() {
      if (this.$route.path === "/rank") return;
      this.$router.push("/rank");
    },
    onClickReport() {
      if (this.$route.path === "/report") return;
      this.$router.push("/report");
    },
  },
};
</script>

<style>
/* 当移动到fa-bell时 */
.fa-bell:hover {
  color: #409eff;
}
</style>