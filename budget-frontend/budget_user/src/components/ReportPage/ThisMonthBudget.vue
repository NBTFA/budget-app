<template>
  <div class="category-progress">
    <div class="title">
      <h2>本月预算报告</h2>
    </div>
    <div class="percentage">
      <h4>预算健康度：{{ percentage }}%</h4>
      <h4>总预算剩余：{{ remain }}%</h4>
    </div>
    <el-row
      v-for="(category, index) in categories"
      :key="index"
      class="category-row"
    >
      <el-col :span="12">
        <span class="category-label">{{ category.name }}</span>
      </el-col>
      <el-col :span="12">
        <el-progress :percentage="category.percentage"></el-progress>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      categories: [
        { name: "Category I", percentage: 50 },
        { name: "Category II", percentage: 20 },
        { name: "Category III", percentage: 30 },
        // ...更多类别
      ],
      percentage: 0,
      remain: 0, 
    };
  },
  created() {
    this.getPercentage();
    this.getRemain();
    this.getCategories();
  },
  methods: {
    getPercentage() {
      this.$http.get("/user/budget/health").then((res) => {
        console.log("预算健康度：", res);
        if (res.data.code === 20000) {
          this.percentage = res.data.data.health;
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    getRemain() {
      this.$http.get("/user/budget/remainPercentage").then((res) => {
        console.log("预算剩余：", res);
        if (res.data.code === 20000) {
          this.remain = res.data.data.remainPercentage;
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    getCategories() {
      this.$http.get("/user/budget/categories").then((res) => {
        console.log("预算类别：", res);
        if (res.data.code === 20000) {
          this.categories = res.data.data.categories;
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
  },
};
</script>

<style scoped>
.percentage {
  text-align: left;
}
.category-progress {
  /* 靠左 */
  margin: 0;
}
.category-label {
  font-size: 16px;
  font-weight: 500;
}
.category-row {
  margin-bottom: 10px;
  text-align: left;
}
</style>
