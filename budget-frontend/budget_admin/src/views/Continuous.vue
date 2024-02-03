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
        <el-row :gutter="20">
          <el-col :span="12">
            <el-input
              v-model="searchText"
              placeholder="请输入搜索内容"
              class="table-search"
            ></el-input>
          </el-col>
          <el-col :span="12">
            <el-date-picker
              v-model="selectedDate"
              type="date"
              placeholder="选择日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              @change="filterByDate"
              class="date-picker"
            ></el-date-picker>
          </el-col>
        </el-row>

        <el-table :data="filteredData" style="width: 100%">
          <!-- 数据列 -->
          <el-table-column prop="id" label="ID" width="200"></el-table-column>
          <el-table-column
            prop="user_id"
            label="用户ID"
            width="200"
          ></el-table-column>
          <el-table-column
            prop="count"
            label="连续记录天数"
            width="200"
          ></el-table-column>
          <el-table-column prop="record_date" label="上次登录" width="200">
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import SideBar from "@/components/SideBar.vue";
import Avatar from "@/components/Avatar.vue";
export default {
  name: "Continuous",
  components: {
    SideBar,
    Avatar,
  },
  data() {
    return {
      currentIndex: "4-4",
      tableData: [
        {
          id: 1,
          user_id: 2,
          count: 111,
          record_date: "2023-12-25",
        },
      ],

      searchText: "",
      selectedDate: "",
      filteredByDateData: [],
      dialogVisible: false, // 控制对话框显示
      deleteIndex: null, // 要删除的行的索引
      deleteRow: null, // 要删除的行的数据
    };
  },
  created() {
    this.$http.get("/admin/continuousRequest").then((res) => {
      console.log("continuousRequest: ", res);
      if (res.data.code === 20000) {
        this.tableData = res.data.data.continuousRecords;
      } else {
        this.$message.error(res.data.message);
      }
    });
  },
  computed: {
    filteredData() {
      let data = this.selectedDate ? this.filteredByDateData : this.tableData;
      if (this.searchText) {
        return data.filter(
          (item) =>
            item.user_id.toString().includes(this.searchText) ||
            item.id.toString().includes(this.searchText) ||
            item.count.toString().includes(this.searchText)
        );
      }
      return data;
    },
  },
  methods: {
    filterByDate() {
      if (this.selectedDate) {
        this.filteredByDateData = this.tableData.filter(
          (item) => item.record_date === this.selectedDate
        );
      } else {
        this.filteredByDateData = this.tableData;
      }
    },
  },
};
</script>
<style>
.table-search,
.date-picker {
  max-width: 100%;
}
</style>
