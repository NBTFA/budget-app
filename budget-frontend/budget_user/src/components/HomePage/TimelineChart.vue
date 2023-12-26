<template>
    <div ref="chart" class="chart-container"></div>
  </template>
  
  <script>
  import * as echarts from 'echarts';
  
  export default {
    props: {
      data: Array, // 预期为 { date, name, category, amount, income } 格式的对象数组
    },
    data() {
      return {
        chartInstance: null,
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
        const option = {
          tooltip: {
            trigger: 'item',
          },
          xAxis: {
            type: 'category',
            data: this.data.map(item => item.date),
          },
          yAxis: {
            type: 'value',
          },
          series: [
            {
              data: this.data.map(item => {
                return {
                  value: item.income ? item.amount : -item.amount,
                  itemStyle: {
                    color: item.income ? 'green' : 'red',
                  },
                };
              }),
              type: 'line',
              smooth: true,
              symbol: 'circle',
              symbolSize: 10,
            },
          ],
        };
  
        this.chartInstance.setOption(option);
        this.chartInstance.on('click', this.onChartClick);
      },
      onChartClick(params) {
        if (params.componentType === 'series') {
          this.$emit('point-clicked', params.dataIndex);
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
  