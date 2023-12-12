<template>
  <el-time-select
  v-model="value"
  :picker-options="{
    start: '08:30',
    step: '00:15',
    end: '18:30'
  }"
  placeholder="选择时间">
  </el-time-select>
</template>


<script>
  export default {
    data() {
      return {
        value: ''
      };
    }
  }
</script>