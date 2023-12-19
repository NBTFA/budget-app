<template>
    <el-container style="height: 100vh;">
        <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
      <!-- 顶部导航 -->
      <el-header>
        <el-menu mode="horizontal">
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2">待办事项</el-menu-item>
            <el-menu-item index="3">消费记录</el-menu-item>
            <el-menu-item index="4">排行榜</el-menu-item>
            <el-menu-item index="5">个人报告</el-menu-item>
          <!-- 其他菜单项 -->
            <div class="menu-right">
                <i class="fa-regular fa-bell fa-2x"></i>
                <i class="fa-solid fa-fire fa-2x"></i>
                <el-avatar :size="50" :src="circleUrl"></el-avatar>
            </div>
        </el-menu>
      </el-header>
  
      <!-- 侧边栏和内容区域 -->
      <el-container>
        <!-- 侧边栏 -->
        <el-aside width="200px">
          <el-container>
            <el-header style="height: 300px; padding: 0;">
                <el-card>
                <el-table :data="tableData">
                  <!-- 表格列 -->
                </el-table>
              </el-card>
            </el-header>
            <el-main style="padding: 0;">
                <el-card>
                <div v-for="(todo, index) in todos" :key="index">
                  <el-checkbox v-model="todo.done">{{ todo.text }}</el-checkbox>
                </div>
              </el-card>
            </el-main>
          </el-container>
        </el-aside>
  
        <!-- 主内容区 -->
        <el-main>
          <el-row :gutter="20">
            <!-- 图表卡片 -->
            <el-col :span="12">
              <el-card>
                <!-- 在这里插入饼状图 -->
              </el-card>
              <el-card>
                <!-- 在这里插入柱状图 -->
              </el-card>
            </el-col>
  
            <!-- 表格和Todo列表 -->
            <el-col :span="12">
              <el-card>
                <el-table :data="tableData">
                  <!-- 表格列 -->
                </el-table>
              </el-card>
              <el-card>
                <div v-for="(todo, index) in todos" :key="index">
                  <el-checkbox v-model="todo.done">{{ todo.text }}</el-checkbox>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        tableData: [
          // 表格数据
        ],
        todos: [
          // Todo列表数据
        ]
      };
    }
  };
  </script>
  
  <style>
  .menu-right {
    float: right;
    display: flex; /* 使用flex布局可以更容易地控制间距 */
    align-items: center; /* 垂直居中图标 */
  }
  
  .menu-right > *:not(:last-child) { /* 为每个子元素（除了最后一个）添加右边距 */
    margin-right: 15px; /* 或者您希望的任何间距 */
  }

  .el-menu-item i {
  font-size: 20px; /* 调整图标大小 */
  vertical-align: middle;
}
.el-menu-item:not(:last-child) {
  margin-right: 30px;
}

  </style>
  