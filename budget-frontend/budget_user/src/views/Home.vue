<template>
  <el-container style="height: 100vh">
    <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
    <!-- 顶部导航 -->
    <el-header>
      <navigator></navigator>
    </el-header>

    <!-- 侧边栏和内容区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <side-bar></side-bar>
      <!-- 主内容区 -->
      <el-main>
        <div class="chartSection">
          <budget-info></budget-info>
        </div>
        <div class="tableSection">
          <budget-table></budget-table>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>
  
  <script>
import Navigator from "@/components/Navigator.vue";
import SideBar from "@/components/SideBar.vue";
import BudgetTable from "@/components/BudgetTable.vue";
import BudgetInfo from "@/components/BudgetInfo.vue";

export default {
  data() {
    return {
      // 在这里添加数据
    };
  },
  components: { Navigator, SideBar, BudgetTable, BudgetInfo },
  methods: {
    // 在这里添加方法
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
</style>
  