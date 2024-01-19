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

      <el-dialog
        title="设置总预算"
        :visible.sync="showBudgetDialog"
        :before-close="handleClose"
        width="30%"
      >
        <span>请设定您的总预算</span>
        <el-input v-model="inputBudget" placeholder="请输入预算"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="setTotalBudget">设定</el-button>
        </span>
      </el-dialog>
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
      totalBudget: 0,
      showBudgetDialog: false, // 控制弹窗显示的变量
      inputBudget: 0,
    };
  },
  components: { Navigator, SideBar, BudgetTable, BudgetInfo },
  methods: {
    // 在这里添加方法
    setTotalBudget() {
      // 设定总预算的逻辑
      // 可以在这里发送请求到服务器更新 totalBudget
      this.totalBudget = this.inputBudget;
      this.showBudgetDialog = false;
      // 更新操作后的逻辑，例如更新视图或通知用户
    },
    handleClose(done) {
      // 在这里可以添加逻辑，例如提示用户必须设置预算
      // 如果不希望在未设置预算时关闭对话框，可以不调用 done()
      this.$message({
        type: 'info',
        message: '请先设置总预算'
      });
    }
  },
  created() {
    this.$http.get("/user/totalBudget").then((res) => {
      console.log("totalBudget: ", res);
      if (res.data.code === 20000) {
        this.totalBudget = res.data.data.totalBudget;
        if (this.totalBudget === 0) {
          this.showBudgetDialog = true;
        }
      } else {
        this.$message.error(res.data.message);
      }
    });
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
  