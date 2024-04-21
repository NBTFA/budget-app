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
        title="设置预算"
        :visible.sync="showBudgetDialog"
        :before-close="handleClose"
        width="50%"
      >
        <el-steps :active="activeStep" finish-status="success" align-center>
          <el-step title="设置总预算"></el-step>
          <el-step title="设置预算分类"></el-step>
        </el-steps>

        <!-- 总预算输入 -->
        <div v-if="activeStep === 0" class="budget-step">
          <span>请输入您的总预算</span>
          <el-input v-model="inputBudget" placeholder="请输入总预算"></el-input>
          <span class="dialog-footer">
            <el-button type="primary" @click="nextStep">下一步</el-button>
          </span>
        </div>
        <!-- 预算分类设置 -->
        <div v-if="activeStep === 1">
          <div
            v-for="(category, index) in budgetCategories"
            :key="index"
            class="budget-category"
          >
            <el-input
              v-model="category.name"
              placeholder="预算分类名称"
            ></el-input>
            <el-button type="text" @click="removeCategory(index)"
              >删除</el-button
            >
          </div>
          <el-button type="primary" @click="addCategory">添加分类</el-button>

          <!-- Footer -->
          <span class="dialog-footer">
            <el-button @click="backStep">上一步</el-button>
            <el-button @click="finishSetting">完成</el-button>
          </span>
        </div>
      </el-dialog>
    </el-container>
  </el-container>
</template>
  
  <script>
import Navigator from "@/components/Navigator.vue";
import SideBar from "@/components/SideBar.vue";
import BudgetTable from "@/components/BudgetTable.vue";
import BudgetInfo from "@/components/BudgetInfo.vue";
import store from '@/store/index.js'

export default {
  data() {
    return {
      totalBudget: 0,
      showBudgetDialog: false,
      inputBudget: 0,
      activeStep: 0, // 控制步骤的变量
      budgetCategories: [{ name: '预算1' }],
    };
  },
  components: { Navigator, SideBar, BudgetTable, BudgetInfo },
  methods: {
    backStep() {
      this.activeStep--;
    },
    nextStep() {
      const budget = Number(this.inputBudget);

      // 检查输入是否为有效的整数
      if (!Number.isInteger(budget) || budget <= 0) {
        this.$message.error("请输入有效的整数作为总预算");
        return;
      }

      // 如果输入有效，则进入下一个步骤
      this.activeStep++;
    },
    addCategory() {
      this.budgetCategories.push({ name: '' });
    },
    removeCategory(index) {
      this.budgetCategories.splice(index, 1);
    },
    finishSetting() {
      // 完成预算设置的逻辑
      // 例如：发送请求到服务器更新 totalBudget 和预算分类
      // 检查预算分类是否为空
      if (this.budgetCategories.length === 0) {
        this.$message.error("请至少添加一个预算分类");
        return;
      }
      else {
        this.showBudgetDialog = false;
        this.$http.post("/user/initialize", { totalBudget: this.inputBudget, budgetCategories: this.budgetCategories }).then((res) => {
          console.log("设置预算结果：", res);
          if (res.data.code === 20000) {
            this.$message.success("设置预算成功");
            window.location.reload();
          } else {
            this.$message.error(res.data.message);
          }
        });
      }
    },
    handleClose(done) {
      this.$message({
        type: "info",
        message: "请完成预算设置",
      });
    },
  },
  created() {
    this.$http.get("/user/homeRequest").then((res) => {
      console.log("homeRequest: ", res);
      if (res.data.code === 20000) {
        store.dispatch("setAvatar", res.data.data.avatar);
        store.dispatch("setNotificationNum", res.data.data.notificationNum);
        store.dispatch("setNotifications", res.data.data.notifications);
        store.dispatch("setContinueNum", res.data.data.continueNum);
        store.dispatch("setRankUsers", res.data.data.rankUsers);
        store.dispatch("setTodoLists", res.data.data.todoList);
        store.dispatch("setTotalBudget", res.data.data.totalBudget);
        store.dispatch("setPieChartData", res.data.data.pieChartData);
        store.dispatch("setBudgets", res.data.data.budgetList);
        store.dispatch("setProgress", res.data.data.progress);
        store.dispatch("setUserName", res.data.data.username);
        console.log("store.state.username: ", store.state.userName);
        if (store.state.totalBudget === -1) {
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
  