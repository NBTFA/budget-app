<template>
  <el-container style="height: 100vh">
    <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
    <!-- 顶部导航 -->
    <el-header>
      <navigator></navigator>
    </el-header>
    <el-main>
      <div class="addButton">
        <el-button type="primary" @click="dialogVisible = true"
          >添加预算</el-button
        >
      </div>
      <budget-table-vue></budget-table-vue>
    </el-main>
    <el-dialog title="添加预算" :visible.sync="dialogVisible" width="30%">
      <el-form ref="budgetForm" :model="budgetForm">
        <el-form-item label="日期">
          <el-date-picker
            v-model="budgetForm.date"
            type="date"
            placeholder="选择日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="budgetForm.name"></el-input>
        </el-form-item>
        <el-form-item label="类别">
          <el-input v-model="budgetForm.category"></el-input>
        </el-form-item>
        <el-form-item label="金额">
          <el-input-number
            v-model="budgetForm.amount"
            :min="0"
            :max="100"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="收入">
          <el-switch v-model="budgetForm.income"></el-switch>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitBudget">确定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>
import Navigator from "../components/Navigator.vue";
import BudgetTableVue from "@/components/BudgetTable.vue";
import store from '@/store/index.js'
export default {
  name: "History",
  components: { Navigator, BudgetTableVue },
  data() {
    return {
      dialogVisible: false,
      budgetForm: {
        date: "",
        name: "",
        category: "",
        amount: 0,
        income: false,
      },
    };
  },
  created() {
    this.$http.get("/user/historyRequest").then((res) => {
      console.log("historyRequest: ", res);
      if (res.data.code === 20000) {
        store.dispatch("setAvatar", res.data.data.avatar);
        store.dispatch("setNotificationNum", res.data.data.notificationNum);
        store.dispatch("setNotifications", res.data.data.notifications);
        store.dispatch("setContinueNum", res.data.data.continueNum);
        store.dispatch("setBudgets", res.data.data.budgetList);
      } else {
        this.$message.error(res.data.message);
      }
    });
  },
  methods: {
    submitBudget() {
      console.log("预算信息：", this.budgetForm);
      // 这里可以添加预算逻辑
      this.$http.post("/user/budget/add", this.budgetForm).then((res) => {
        console.log("预算结果：", res);
        if (res.data.code === 20000) {
          this.$message.success("添加成功");
          this.dialogVisible = false;
          this.$refs.budgetForm.resetFields();
          this.$store.dispatch("addBudget", res.data.budget);
        } else {
          this.$message.error("添加失败");
        }
      });
    },
  },
};
</script>

<style>
.addButton {
  text-align: right;
}
</style>