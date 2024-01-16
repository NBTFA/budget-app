<template>
  <el-menu mode="horizontal" :default-active="activeIndex">
      <el-menu-item index="1" @click="onClickMainMenu">首页</el-menu-item>
      <el-menu-item index="2" @click="onClickTodoList">待办事项</el-menu-item>
      <el-menu-item index="3" @click="onClickHistory">消费记录</el-menu-item>
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
import MentionIcon from "./HomePage/MentionIcon.vue";
import ContinueIcon from "./HomePage/ContinueIcon.vue";
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
    onClickReport() {
      if (this.$route.path === "/report") return;
      this.$router.push("/report");
    },
  },
  computed: {
    activeIndex() {
      switch (this.$route.path) {
        case "/home":
          return "1";
        case "/todo":
          return "2";
        case "/history":
          return "3";
        case "/rank":
          return "4";
        case "/report":
          return "5";
        default:
          return "1";
      }
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