<template>
  <div ref="chart" class="chart-container"></div>
</template>
  
  <script>
import * as echarts from "echarts";

export default {
  props: {
    data: Array, // 预期为 { date, name, category, amount, income } 格式的对象数组
  },
  data() {
    return {
      chartInstance: null,
      sortedData: [],
    };
  },
  mounted() {
    this.chartInstance = echarts.init(this.$refs.chart);
    this.initChart();
  },
  beforeDestroy() {
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  },
  methods: {
    initChart() {
      this.sortedData = [...this.data].sort((a, b) => {
        return new Date(a.record_date) - new Date(b.record_date);
      });
      const option = {
        tooltip: {
          trigger: "item",
        },
        xAxis: {
          type: "category",
          data: this.sortedData.map((item) => item.record_date),
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: this.sortedData.map((item) => {
              return {
                value: item.gain ? item.amount : -item.amount,
                itemStyle: {
                  color: item.gain ? "green" : "red",
                },
              };
            }),
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 10,
          },
        ],
      };

      this.chartInstance.setOption(option);
      this.chartInstance.on("click", this.onChartClick);
    },
    onChartClick(params) {
      if (params.componentType === "series") {
        const dataItem = this.sortedData[params.dataIndex];
        this.$emit("point-clicked", dataItem);
      }
    },
  },
};
</script>
  
  <style>
.chart-container {
  height: 400px;
}
</style>
  