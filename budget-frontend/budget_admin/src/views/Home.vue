<template>
    <el-container style="height: 100vh">
      <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
      <!-- 顶部导航 -->
      <el-header>

      </el-header>
  
      <!-- 侧边栏和内容区域 -->
      <el-container>
        <!-- 侧边栏 -->
        <side-bar></side-bar>
        <!-- 主内容区 -->
        <el-main>

        </el-main>
  
        
      </el-container>
    </el-container>
  </template>
    
    <script>
    import SideBar from "@/components/SideBar.vue";
    export default {
      name: "Home",
      components: {
        SideBar,
      },
      data() {
        return {

        };
      },
    };
  </script>
    
    <style>

  </style>
    