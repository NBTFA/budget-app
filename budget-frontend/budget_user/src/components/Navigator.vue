<template>
  <el-menu mode="horizontal" :default-active="activeIndex">
      <el-menu-item index="1" @click="onClickMainMenu">首页</el-menu-item>
      <el-menu-item index="2" @click="onClickTodoList">待办事项</el-menu-item>
      <el-menu-item index="3" @click="onClickHistory">消费记录</el-menu-item>
      <!-- <el-menu-item index="5" @click="onClickReport">个人报告</el-menu-item> -->
    <!-- 其他菜单项 -->
    <div class="menu-right">
      <MentionIcon></MentionIcon>
      <ContinueIcon></ContinueIcon>
      <el-dropdown @command="handleCommand">
      <span class="avatar-wrapper">
        <el-avatar icon="el-icon-user-solid" :size="50" :src="url"></el-avatar>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="setting">设置</el-dropdown-item>
        <el-dropdown-item divided command="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
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
  //this.url监视store.state.avatar的变化
  watch: {
    "$store.state.avatar": function (newVal, oldVal) {
      this.url = newVal;
    },
  },
  methods: {
    getUserAvatar() {
      this.url = this.$store.state.avatar;
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
    handleCommand(command) {
      if (command === 'logout') {
        // 执行退出操作
        console.log('退出操作');
        // 比如清除本地存储，重定向到登录页面等
        this.$router.push('/login');
        //清除本地存储
        this.$store.commit('deleteToken');
        localStorage.removeItem('token');
      }
      else if(command === 'setting') {
        // 执行设置操作
        console.log('设置操作');
        this.$router.push('/setting');
      }
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
.avatar-wrapper {
    cursor: pointer; /* 显示一个手型指针表示这是一个可点击的项 */
  }

</style>