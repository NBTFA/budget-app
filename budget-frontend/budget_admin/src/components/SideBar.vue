<template>
  <el-aside width="200px">
    <el-menu :default-active="activeIndex">
      <el-menu-item index="1" @click="goToHome">
        <i class="fa-solid fa-chart-simple">   统计数据</i>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="fa-solid fa-users-gear">   用户管理</i>
        </template>
        <el-menu-item index="2-1" @click="goToUserManagement">
          <i class="fa-solid fa-user">   用户操作</i>
        </el-menu-item>
        <el-menu-item index="2-2" @click="goToAdminManagement">
          <i class="fa-solid fa-user-tie">   管理员操作</i>
        </el-menu-item>
      </el-submenu>
      <el-menu-item index="3" @click="goToNotificationManagement">
        <i class="fa-solid fa-envelope">   通知系统</i>
      </el-menu-item>
      <el-submenu index="4">
        <template slot="title">
          <i class="fa-solid fa-gear">   数据管理</i>
        </template>
        <el-menu-item index="4-1" @click="goToBudgetRecord">
          <i class="fa-solid fa-money-check-dollar">   预算记录</i>
        </el-menu-item>
        <el-menu-item index="4-2" @click="goToTodoRecord">
          <i class="fa-solid fa-list">   待办记录</i>
        </el-menu-item>
        <el-menu-item index="4-3" @click="goToCategoryRecord">
          <i class="fa-solid fa-bars">   类别记录</i>
        </el-menu-item>
        <el-menu-item index="4-4" @click="goToContinuousRecord">
          <i class="fa-solid fa-fire">   连续记录</i>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </el-aside>
</template>
  
  <script>
export default {
  name: "Sidebar",
  props: {
    activeIndex: {
      type: String,
      default: "1",
    },
  },
  components: {},
  data() {
    return {
      rankUsers: [],
    };
  },
  methods: {
    goToHome() {
      console.log("跳转首页");
      if (this.activeIndex === "1") return;
      this.$router.push("/home");
    },
    goToUserManagement() {
      console.log("跳转用户管理页面");
      if (this.activeIndex === "2-1") return;
      this.$router.push("/user");
    },
    goToAdminManagement() {
      console.log("跳转管理员管理页面");
      if (this.activeIndex === "2-2") return;
      this.$router.push("/admin");
    },
    goToNotificationManagement() {
      console.log("跳转通知系统页面");
      if (this.activeIndex === "3") return;
      this.$router.push("/notification");
    },
    goToBudgetRecord() {
      console.log("跳转预算记录页面");
      if (this.activeIndex === "4-1") return;
      this.$router.push("/budget");
    },
    goToTodoRecord() {
      console.log("跳转待办记录页面");
      if (this.activeIndex === "4-2") return;
      this.$router.push("/todo");
    },
    goToCategoryRecord() {
      console.log("跳转类别记录页面");
      if (this.activeIndex === "4-3") return;
      this.$router.push("/category");
    },
    goToContinuousRecord() {
      console.log("跳转连续记录页面");
      if (this.activeIndex === "4-4") return;
      this.$router.push("/continuous");
    },
  },
};
</script>
  
<style scoped>
/* 所有文字靠左 */
.el-menu{
  text-align: left;
}

</style>
  