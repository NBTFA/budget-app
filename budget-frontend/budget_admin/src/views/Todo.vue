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
              placeholder="选择创建日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              @change="filterByDate"
              class="date-picker"
            ></el-date-picker>
          </el-col>
        </el-row>

        <el-table :data="filteredData" style="width: 100%">
          <!-- 数据列 -->
          <el-table-column prop="id" label="ID" width="100"></el-table-column>
          <el-table-column
            prop="user_id"
            label="用户ID"
            width="100"
          ></el-table-column>
          <el-table-column
            prop="title"
            label="标题"
            width="120"
          ></el-table-column>
          <el-table-column prop="description" label="描述" width="150">
          </el-table-column>
          <el-table-column prop="completed" label="完成状态" width="100">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.completed" type="success">已完成</el-tag>
              <el-tag v-else>未完成</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="created_Date"
            label="创建时间"
            width="100"
          ></el-table-column>
          <el-table-column
            prop="completed_Date"
            label="完成时间"
            width="100"
          ></el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <el-button
                type="danger"
                size="small"
                @click="confirmDelete(scope.$index, scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-dialog
          title="确认删除"
          :visible.sync="dialogVisible"
          width="30%"
          @close="resetDeleteConfirmation"
        >
          <span>确定要删除这项记录吗？</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handleDelete">确定</el-button>
          </span>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import SideBar from "@/components/SideBar.vue";
import Avatar from "@/components/Avatar.vue";
export default {
  name: "Todo",
  components: {
    SideBar,
    Avatar,
  },
  data() {
    return {
      currentIndex: "4-2",
      tableData: [
        {
          id: 1,
          user_id: 2,
          title: "111",
          description: "232323",
          completed: true,
          created_Date: "2023-12-25",
          completed_Date: "2023-12-25",
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
    this.$http.get("/admin/todoRequest").then((res) => {
      console.log("todoRequest: ", res);
      if (res.data.code === 20000) {
        this.tableData = res.data.data.todoLists;
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
            item.title.toLowerCase().includes(this.searchText.toLowerCase()) ||
            item.user_id.toString().includes(this.searchText.toLowerCase()) ||
            item.id.toString().includes(this.searchText) ||
            item.description
              .toLowerCase()
              .includes(this.searchText.toLowerCase())
        );
      }
      return data;
    },
  },
  methods: {
    filterByDate() {
      if (this.selectedDate) {
        this.filteredByDateData = this.tableData.filter(
          (item) => item.created_Date === this.selectedDate
        );
      } else {
        this.filteredByDateData = this.tableData;
      }
    },
    confirmDelete(index, row) {
      this.dialogVisible = true;
      this.deleteIndex = index;
      this.deleteRow = row;
    },

    // 处理删除操作
    handleDelete() {
      this.tableData.splice(this.deleteIndex, 1);
        this.$http
          .delete("/admin/todo", {
            data: {
              id: this.deleteRow.id,
            },
          })
          .then((res) => {
            console.log("删除待办：", res);
            if (res.data.code === 20000) {
              this.$message.success("删除成功");
            } else {
              this.$message.error(res.data.message);
            }
          });
      console.log(`删除了行: ${this.deleteRow.name}`);
      this.resetDeleteConfirmation();
      //   this.$store.commit("setBudgets", this.tableData);
    },

    // 重置删除确认
    resetDeleteConfirmation() {
      this.dialogVisible = false;
      this.deleteIndex = null;
      this.deleteRow = null;
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
