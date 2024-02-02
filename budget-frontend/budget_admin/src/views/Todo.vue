<template>
    <el-container style="height: 100vh">
      <!-- 顶部导航 -->
      <el-header>
        <i class="fa-solid fa-circle-dollar-to-slot">预算保镖 - 管理员界面</i>
      </el-header>
  
      <!-- 侧边栏和内容区域 -->
      <el-container>
        <!-- 侧边栏 -->
        <side-bar :activeIndex="currentIndex"></side-bar>
        <!-- 主内容区 -->
        <el-main> </el-main>
      </el-container>
    </el-container>
  </template>
  
  <script>
  import SideBar from "@/components/SideBar.vue";
  export default {
    name: "Todo",
    components: {
      SideBar,
    },
    data() {
      return {
          currentIndex: "4-2",
      };
    },
  };
  </script>
  