<template>
  <el-container style="height: 100vh">
    <!-- 顶部导航 -->
    <el-header>
      <i class="fa-solid fa-circle-dollar-to-slot">预算保镖 - 管理员界面</i>
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
          <el-table-column prop="id" label="Id" width="120"></el-table-column>
          <el-table-column
            prop="username"
            label="用户名"
            width="150"
          ></el-table-column>
          <el-table-column
            prop="email"
            label="Email"
            width="120"
          ></el-table-column>
          <el-table-column prop="created_at" label="创建于" width="100">
          </el-table-column>
          <el-table-column label="管理员类型" width="150">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.root" type="warning">超级管理员</el-tag>
              <el-tag v-else type="success">普通管理员</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <el-button
                type="primary"
                size="small"
                @click="handleEdit(scope.row)"
                :disabled="!currentRoot"
                >修改</el-button
              >

              <el-button
                type="danger"
                size="small"
                @click="confirmDelete(scope.$index, scope.row)"
                :disabled="!currentRoot"
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
        <el-dialog
          title="编辑用户"
          :visible.sync="editDialogVisible"
          width="30%"
          @close="resetEditForm"
        >
          <el-form :model="editFormData">
            <el-form-item label="Id">
              <el-input v-model="editFormData.id" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="用户名">
              <el-input v-model="editFormData.username"></el-input>
            </el-form-item>
            <el-form-item label="Email">
              <el-input v-model="editFormData.email"></el-input>
            </el-form-item>
            <el-form-item label="创建于">
              <el-date-picker
                v-model="editFormData.created_at"
                type="date"
                placeholder="选择日期"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                :disabled="true"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="管理员类型">
              <el-switch
                v-model="editFormData.root"
                active-color="#13ce66"
              >
              </el-switch>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handleUpdate">完成</el-button>
          </span>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import SideBar from "@/components/SideBar.vue";
export default {
  name: "Admin",
  components: {
    SideBar,
  },
  data() {
    return {
      currentIndex: "2-2",
      tableData: [
        {
          id: 1,
          username: "user1",
          email: "111",
          created_at: "2023-12-25",
          root: true,
        },
      ],
      editDialogVisible: false,
      editFormData: {
        id: "",
        username: "",
        email: "",
        created_at: "",
        root: false,
      },
      searchText: "",
      selectedDate: "",
      filteredByDateData: [],
      dialogVisible: false, // 控制对话框显示
      deleteIndex: null, // 要删除的行的索引
      deleteRow: null, // 要删除的行的数据

      currentRoot: false, //当前用户是否为root
    };
  },
  computed: {
    filteredData() {
      let data = this.selectedDate ? this.filteredByDateData : this.tableData;
      if (this.searchText) {
        return data.filter(
          (item) =>
            item.username
              .toLowerCase()
              .includes(this.searchText.toLowerCase()) ||
            item.email.toLowerCase().includes(this.searchText.toLowerCase()) ||
            item.id.toString().includes(this.searchText)
        );
      }
      return data;
    },
  },
  methods: {
    filterByDate() {
      if (this.selectedDate) {
        this.filteredByDateData = this.tableData.filter(
          (item) => item.created_at === this.selectedDate
        );
      } else {
        this.filteredByDateData = this.tableData;
      }
    },
    handleEdit(row) {
      console.log("编辑行", row);
    },
    confirmDelete(index, row) {
      this.dialogVisible = true;
      this.deleteIndex = index;
      this.deleteRow = row;
    },

    // 处理删除操作
    handleDelete() {
      this.tableData.splice(this.deleteIndex, 1);
      // 这里可以添加与服务器的交互逻辑
      //   this.$http
      //     .delete("/user/budget", {
      //       data: {
      //         id: this.deleteRow.id,
      //       },
      //     })
      //     .then((res) => {
      //       console.log("删除预算：", res);
      //       if (res.data.code === 20000) {
      //         this.$message.success("删除成功");
      //       } else {
      //         this.$message.error(res.data.message);
      //       }
      //     });
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
    handleEdit(row) {
      this.editFormData = Object.assign({}, row); // 浅拷贝行数据到表单
      this.editDialogVisible = true; // 显示编辑对话框
    },

    handleUpdate() {
      // 处理更新逻辑，例如发送请求到服务器
      // 更新 tableData 中的数据
      const index = this.tableData.findIndex(
        (item) => item.id === this.editFormData.id
      );
      if (index !== -1) {
        this.tableData.splice(index, 1, this.editFormData);
      }
      this.editDialogVisible = false; // 关闭对话框
      console.log("更新数据", this.editFormData);
      // 可选：重置表单或发送更新到服务器
      this.resetEditForm();
    },

    resetEditForm() {
      this.editDialogVisible = false;
      this.editFormData = {
        id: "",
        username: "",
        email: "",
        created_at: "",
        root: false,
      }; // 重置表单数据
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
