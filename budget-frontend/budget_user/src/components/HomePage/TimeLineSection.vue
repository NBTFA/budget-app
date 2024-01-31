<template>
  <div>
    <!-- <el-button-group>
      <el-button :type="timeframe === 'year' ? 'primary' : 'default'" @click="setTimeFrame('year')">按年显示</el-button>
      <el-button :type="timeframe === 'month' ? 'primary' : 'default'" @click="setTimeFrame('month')">按月显示</el-button>
    </el-button-group> -->
    <timeline-chart v-if="dataLoaded" :data="data" @point-clicked="showDetails" />
  </div>
</template>

<script>
import TimelineChart from './TimelineChart.vue';

export default {
  components: {
    TimelineChart,
  },
  data() {
    return {
      timeframe: 'year',
      dataLoaded: false, // 新增状态表示数据是否加载
      data: [],
    };
  },
  watch: {
    "$store.state.budgets"(newVal) {
      this.processData();
      this.dataLoaded = true; // 数据加载完成后设置为 true
    },
  },
  methods: {
    setTimeFrame(frame) {
      this.timeframe = frame;
      this.dataLoaded = false; // 在加载数据前设置为 false
      this.processData();
    },
    processData() {
      this.data = this.$store.state.budgets; // 从 store 中获取数据
    },
    showDetails(point) {
      const gainString = point.gain ? '收入' : '支出';
      this.$message.info(`在${point.name}上${gainString}：${point.amount}元`);
    },
  },
  created() {
    this.processData();
  },
};
</script>

<style>
/* Your styles here */
</style>
