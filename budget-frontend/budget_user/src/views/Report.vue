<template>
  <el-container style="height: 100vh">
    <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
    <!-- 顶部导航 -->
    <el-header>
      <navigator></navigator>
    </el-header>
    <el-main>
      <el-row class="topSection">
        <el-col :span="8" class="thisMonthBudget">
          <this-month-budget-vue></this-month-budget-vue>
        </el-col>

        <el-col :span="16" class="gptReport">
          <gpt-report-section></gpt-report-section>
        </el-col>
      </el-row>

      <div class="timeLineSection">
        <time-line-section></time-line-section>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import Navigator from "../components/Navigator.vue";
import TimeLineSection from "@/components/HomePage/TimeLineSection.vue";
import ThisMonthBudgetVue from "@/components/ReportPage/ThisMonthBudget.vue";
import GptReportSection from "@/components/ReportPage/GptReportSection.vue";
import store from '@/store/index.js'
export default {
  name: "Report",
  components: {
    Navigator,
    TimeLineSection,
    ThisMonthBudgetVue,
    GptReportSection,
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
};
</script>

<style>
.topSection {
  overflow-y: auto;
  max-height: 70%;
}
.timeLineSection {
  overflow-y: auto;
  flex-grow: 1;
}
.thisMonthBudget,
.gptReport {
  overflow-x: auto;
}
</style>