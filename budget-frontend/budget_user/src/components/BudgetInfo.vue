<template>
  <div class="budget-info">
    <div class="progress-container">
      <h3 class="progress-title">预算进度</h3>
      <el-progress
        :percentage="percentage"
        :text-inside="true"
        :stroke-width="20"
      ></el-progress>
    </div>
    <div class="diagram">
      <div class="timeline-container">
        <time-line-section></time-line-section>
      </div>
      <div class="pie-chart-container">
        <pie-chart chart-title="预算使用情况" :data="pieChartData"></pie-chart>
      </div>
    </div>
  </div>
</template>
  
  <script>
import TimeLineSection from "./HomePage/TimeLineSection.vue";
import PieChart from "./HomePage/PieChart.vue";

export default {
  data() {
    return {
      percentage: 70, // 示例进度百分比
      pieChartData: [],
    };
  },
  components: {
    TimeLineSection,
    PieChart,
  },
  watch: {
    "$store.state.progress": function (newVal, oldVal) {
      this.percentage = newVal;
    },
    "$store.state.pieChartData": function (newVal, oldVal) {
      this.pieChartData = newVal;
    },
  },
  created() {
    // 获取预算进度百分比
    this.percentage = this.$store.state.progress;
  
    // 获取饼图数据
    this.pieChartData = this.$store.state.pieChartData;
  },
};
</script>
  
  <style scoped>
.pie-chart-container {
  flex: 1;
}
.timeline-container {
    flex: 1;
}

.progress-container {
  display: flex;
  align-items: center; /* 垂直居中 */
  margin-top: 0%;
  width: 50%;
}

.progress-title {
  margin-right: 10px; /* 标题和进度条之间的距离 */
  font-size: 15px;
}

.el-progress {
  flex-grow: 1; /* 进度条占据剩余空间 */
}

.diagram {
  display: flex;
  flex-direction: row;
  justify-content: space-around; /* 两个图表之间的距离 */
}
</style>
  