<template>
  <div>
    <el-button-group>
      <el-button :type="timeframe === 'year' ? 'primary' : 'default'" @click="setTimeFrame('year')">按年显示</el-button>
      <el-button :type="timeframe === 'month' ? 'primary' : 'default'" @click="setTimeFrame('month')">按月显示</el-button>
    </el-button-group>
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
  methods: {
    setTimeFrame(frame) {
      this.timeframe = frame;
      this.dataLoaded = false; // 在加载数据前设置为 false
      this.processData();
    },
    processData() {
      this.$http.get('/user/budget', { params: { frame: this.timeframe } }).then(res => {
        if (res.data.code === 20000) {
          this.data = res.data.data.budgetList; // 直接替换 data 对象
          this.dataLoaded = true; // 数据加载完成
        } else {
          this.$message.error(res.data.message);
        }
      }).catch(err => {
        this.$message.error('获取预算信息失败');
        console.error(err);
      });
    },
    showDetails(point) {
      this.$message.info(`${point.name}：${point.value}元`);
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
