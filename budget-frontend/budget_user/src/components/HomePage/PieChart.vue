<template>
    <div>
      <h2>{{ chartTitle }}</h2>
      <div ref="pieChart" class="chart-container"></div>
    </div>
  </template>
  
  <script>
  import * as echarts from 'echarts';
  
  export default {
    props: {
      chartTitle: {
        type: String,
        required: true,
      },
      data: {
        type: Array,
        required: true,
      },
    },
    data() {
      return {
        chartInstance: null,
      };
    },
    mounted() {
      this.chartInstance = echarts.init(this.$refs.pieChart);
      this.initChart();
    },
    methods: {
      initChart() {
        const option = {
          tooltip: {
            trigger: 'item',
          },
          legend: {
            orient: 'vertical',
            left: 'left',
          },
          series: [
            {
              type: 'pie',
              radius: '50%',
              data: this.data.map(item => ({
                value: item.value,
                name: item.name,
              })),
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)',
                },
              },
            },
          ],
        };
        this.chartInstance.setOption(option);
      },
    },
    watch: {
      data: {
        handler(newVal) {
          this.chartInstance.setOption({
            series: [
              {
                data: newVal.map(item => ({
                  value: item.value,
                  name: item.name,
                })),
              },
            ],
          });
        },
        deep: true,
      },
    },
  };
  </script>
  
  <style>
  .chart-container {
    height: 400px;
  }
  </style>
  