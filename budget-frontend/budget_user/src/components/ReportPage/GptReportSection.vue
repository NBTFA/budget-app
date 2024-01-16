<template>
  <div class="gpt">
    <div class="title">
      <i class="fa-solid fa-robot">AI分析</i>
    </div>
    <div class="ResponseSection">
      <p>{{ gptResponse }}</p>
    </div>
    <el-button type="primary" @click="getGptResponse">点击分析</el-button>
  </div>
</template>

<script>
export default {
  name: "GptReportSection",
  data() {
    return {
      gptResponse: "暂无分析",
    };
  },
  created() {
  },
  methods: {
    getGptResponse() {
      this.gptResponse = "暂无分析";
      this.$http.get("/user/budget/gpt").then((res) => {
        console.log("gpt分析：", res);
        if (res.data.code === 20000) {
          this.gptResponse = res.data.data.gpt;
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
  },
};
</script>

<style>
.title {
  text-align: left;
  font-size: 20px;
  font-weight: bold;
  margin-left: 60px;
  margin-top: 29px;
}
.ResponseSection {
  margin-left: 70px;
  text-align: left;
  word-wrap: break-word;
  white-space: normal;
  overflow-wrap: break-word;
}
</style>