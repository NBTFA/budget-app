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
  .chartSection {
    overflow-y: auto;
    max-height: 70%;
  }
  
  .tableSection {
    overflow-y: auto;
    flex-grow: 1;
  }
  
  .menu-right {
    float: right;
    display: flex; /* 使用flex布局可以更容易地控制间距 */
    align-items: center; /* 垂直居中图标 */
  }
  
  .menu-right > *:not(:last-child) {
    /* 为每个子元素（除了最后一个）添加右边距 */
    margin-right: 15px; /* 或者您希望的任何间距 */
  }
  
  .el-menu-item i {
    font-size: 20px; /* 调整图标大小 */
    vertical-align: middle;
  }
  .el-menu-item:not(:last-child) {
    margin-right: 30px;
  }
  .budget-step {
    margin-top: 20px;
    text-align: center;
  }
  
  .budget-step span {
    display: block;
    margin-bottom: 15px;
    font-size: 16px;
  }
  
  .budget-step .el-input {
    margin-bottom: 20px;
  }
  
  .dialog-footer {
    text-align: right;
  }
  .budget-category {
      display: flex;
      align-items: center;
      margin-bottom: 10px;
    }
  
    .budget-category .el-input {
      flex-grow: 1;
      margin-right: 10px;
    }
  </style>
    