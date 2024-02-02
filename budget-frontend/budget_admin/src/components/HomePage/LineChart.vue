<template>
    <div ref="lineChart" style="width: 100%; height: 400px;"></div>
  </template>
  
  <script>
  import * as echarts from 'echarts';
  
  export default {
    name: 'LineChart',
    props: {
      data: {
        type: Array,
        required: true,
      },
    },
    data() {
      return {
        chart: null,
      };
    },
    mounted() {
      this.initChart();
    },
    methods: {
      initChart() {
        this.chart = echarts.init(this.$refs.lineChart);
        const option = {
          xAxis: {
            type: 'category',
            data: this.data.map(item => item.date),
          },
          yAxis: {
            type: 'value',
          },
          series: [
            {
              data: this.data.map(item => item.value),
              type: 'line',
            },
          ],
        };
        this.chart.setOption(option);
      },
    },
    watch: {
      data: 'initChart',
    },
  };
  </script>
  