<template>
  <el-container style="height: 100vh">
    <!-- 顶部导航 -->
    <el-header>
      <i class="fa-solid fa-circle-dollar-to-slot">预算保镖 - 管理员界面</i>
      <avatar></avatar>
    </el-header>

    <!-- 侧边栏和内容区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <side-bar :activeIndex="currentIndex"></side-bar>
      <!-- 主内容区 -->
      <el-main>
        <el-row :gutter="40" class="panel-group">
          <el-col :xs="12" :sm="12" :lg="6">
            <el-card
              class="cursor-pointer"
              @click.native="changeData('newUsers')"
            >
              <div slot="header">
                <i class="fa-solid fa-user-plus"></i>
              </div>
              <el-statistic
                group-separator=","
                :precision="1"
                decimal-separator="."
                :value="100000"
                :title="title"
              >
                <template slot="prefix">
                  <i class="el-icon-s-flag" style="color: red"></i>
                </template>
                <template slot="suffix">
                  <i class="el-icon-s-flag" style="color: blue"></i>
                </template>
              </el-statistic>
            </el-card>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6">
            <el-card
              class="cursor-pointer"
              @click.native="changeData('totalUsers')"
            >
              <div slot="header">
                <i class="fa-solid fa-users"></i>
              </div>
              <el-statistic
                group-separator=","
                :precision="1"
                decimal-separator="."
                :value="100000"
                :title="title1"
              >
                <template slot="prefix">
                  <i class="el-icon-s-flag" style="color: red"></i>
                </template>
                <template slot="suffix">
                  <i class="el-icon-s-flag" style="color: blue"></i>
                </template>
              </el-statistic>
            </el-card>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6">
            <el-card
              class="cursor-pointer"
              @click.native="changeData('budgetRecords')"
            >
              <div slot="header">
                <i class="fa-solid fa-money-check-dollar"></i>
              </div>
              <el-statistic
                group-separator=","
                :precision="1"
                decimal-separator="."
                :value="100000"
                :title="title2"
              >
                <template slot="prefix">
                  <i class="el-icon-s-flag" style="color: red"></i>
                </template>
                <template slot="suffix">
                  <i class="el-icon-s-flag" style="color: blue"></i>
                </template>
              </el-statistic>
            </el-card>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="24">
            <el-card @click.native="changeData('newUsers')">
              <line-chart :data="chartData"></line-chart>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>
    
    <script>
import "@fortawesome/fontawesome-free/css/all.min.css";
import LineChart from "@/components/HomePage/LineChart.vue";
import SideBar from "@/components/SideBar.vue";
import Avatar from "@/components/Avatar.vue"; 

export default {
  name: "Home",
  components: {
    SideBar,
    LineChart,
    Avatar,
  },
  data() {
    return {
      currentIndex: "1",
      title: "昨日新增",
      title1: "总用户数",
      title2: "预算记录总数",
      currentData: "newUsers",
      chartData: [],
      allData: {
        newUsers: [
          { date: "2022-01-01", value: 120 },
          // 更多数据...
        ],
        totalUsers: [
          { date: "2022-01-01", value: 1000 },
          // 更多数据...
        ],
        budgetRecords: [
          { date: "2022-01-01", value: 200 },
          // 更多数据...
        ],
      },
    };
  },
  mounted() {
    this.changeData(this.currentData);
  },
  methods: {
    changeData(type) {
      this.chartData = this.allData[type];
    },
  },
};
</script>
    
    <style>
.cursor-pointer {
  cursor: pointer;
}

</style>
    