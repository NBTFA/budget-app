<template>
  <div class="menu-right">
    <el-dropdown @command="handleCommand">
      <span class="avatar-wrapper">
        <el-avatar icon="el-icon-user-solid" :size="50" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Avatar",
  data() {
    return {
      url: "",
    };
  },
  methods: {
    handleCommand(command) {
      if (command === "logout") {
        // 执行退出操作
        console.log("退出操作");
        // 比如清除本地存储，重定向到登录页面等
        this.$router.push("/login");
        //清除本地存储
        this.$store.commit("deleteToken");
        localStorage.removeItem("token");
      }
    },
  },
};
</script>
<style>
.menu-right {
  float: right;
  margin-right: 20px;
}
.avatar-wrapper {
    cursor: pointer;
  }
</style>