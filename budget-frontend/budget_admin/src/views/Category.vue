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
          <el-col :span="20">
            <el-input
              v-model="searchText"
              placeholder="请输入搜索内容"
              class="table-search"
            ></el-input>
          </el-col>
        </el-row>

        <el-table :data="filteredData" style="width: 100%">
          <!-- 数据列 -->
          <el-table-column prop="id" label="ID" width="300"></el-table-column>
          <el-table-column
            prop="user_id"
            label="用户ID"
            width="300"
          ></el-table-column>
          <el-table-column
            prop="category"
            label="类别"
            width="300"
          ></el-table-column>
        </el-table>
        
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import SideBar from "@/components/SideBar.vue";
import Avatar from "@/components/Avatar.vue";
export default {
  name: "Category",
  components: {
    SideBar,
    Avatar,
  },
  data() {
    return {
      currentIndex: "4-3",
      tableData: [
        {
          id: 1,
          user_id: 2,
          category: "111",
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
    this.$http.get("/admin/categoryRequest").then((res) => {
      console.log("categoryRequest: ", res);
      if (res.data.code === 20000) {
        this.tableData = res.data.data.categories;
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
            item.category
              .toLowerCase()
              .includes(this.searchText.toLowerCase()) ||
            item.user_id.toString().includes(this.searchText.toLowerCase()) ||
            item.id.toString().includes(this.searchText)
        );
      }
      return data;
    },
  },
  methods: {
    
  },
};
</script>
<style>
.table-search,
.date-picker {
  max-width: 100%;
}
</style>
