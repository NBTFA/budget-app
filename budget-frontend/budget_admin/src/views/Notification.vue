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
          <el-col :span="7">
            <el-input
              v-model="searchText"
              placeholder="请输入搜索内容"
              class="table-search"
            ></el-input>
          </el-col>
          <el-col :span="7">
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
          <el-col :span="6">
            <el-button type="primary" @click="showSendMessageDialog"
              >发送消息</el-button
            >
            <el-button type="success" @click="showBroadcastDialog"
              >群发消息</el-button
            >
          </el-col>
        </el-row>

        <el-table :data="filteredData" style="width: 100%">
          <!-- 数据列 -->
          <el-table-column prop="id" label="ID" width="120"></el-table-column>
          <el-table-column
            prop="user_id"
            label="用户ID"
            width="150"
          ></el-table-column>
          <el-table-column
            prop="message"
            label="消息内容"
            width="120"
          ></el-table-column>
          <el-table-column prop="is_read" label="消息状态" width="100">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.is_read" type="success">已读</el-tag>
              <el-tag v-else>未读</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="created_at"
            label="创建于"
            width="100"
          ></el-table-column>
          <el-table-column
            prop="from_user_name"
            label="发送者"
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
        <el-dialog
          title="发送消息"
          :visible.sync="sendMessageDialogVisible"
          width="30%"
        >
          <el-form>
            <el-form-item label="消息内容" :error="sendMessageError">
              <el-input
                v-model="sendMessageFormData.message"
                type="textarea"
              ></el-input>
            </el-form-item>
            <el-form-item label="选择用户" :error="user_idError">
              <el-select
                v-model="sendMessageFormData.user_id"
                placeholder="请选择用户"
              >
                <el-option
                  v-for="user in users"
                  :key="user.id"
                  :label="user.username"
                  :value="user.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="sendMessageDialogVisible = false"
              >取消</el-button
            >
            <el-button type="primary" @click="handleSendMessage" :disabled="!sendMessageFormData.message || !sendMessageFormData.user_id"
              >发送</el-button
            >
          </span>
        </el-dialog>

        <el-dialog
          title="群发消息"
          :visible.sync="broadcastMessageDialogVisible"
          width="30%"
        >
          <el-form :error="sendMessageError">
            <el-form-item label="消息内容" :error="broadcastMessageError">
              <el-input v-model="broadcastMessage" type="textarea"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="broadcastMessageDialogVisible = false"
              >取消</el-button
            >
            <el-button type="primary" @click="handleBroadcastMessage" :disabled="!broadcastMessage"
              >发送</el-button
            >
          </span>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import SideBar from "@/components/SideBar.vue";
export default {
  name: "Notification",
  components: {
    SideBar,
  },
  data() {
    return {
      currentIndex: "3",
      tableData: [
        {
          id: 1,
          user_id: 2,
          message: "111",
          is_read: false,
          created_at: "2023-12-25",
          from_user_name: "admin1",
        },
      ],
      searchText: "",
      selectedDate: "",
      filteredByDateData: [],
      dialogVisible: false, // 控制对话框显示
      deleteIndex: null, // 要删除的行的索引
      deleteRow: null, // 要删除的行的数据
      //发送消息数据
      sendMessageDialogVisible: false,
      broadcastMessageDialogVisible: false,
      sendMessageFormData: {
        message: "",
        user_id: "",
      },
      broadcastMessage: "",
      users: [
        // 示例数据，实际情况下应从服务器获取
        { id: 1, username: "user1" },
        { id: 2, username: "user2" },
        // 添加更多用户...
      ],
    };
  },
  computed: {
    user_idError() {
      if (!this.sendMessageFormData.user_id) {
        return "请选择用户";
      }
      return "";
    },
    sendMessageError() {
      if (!this.sendMessageFormData.message) {
        return "消息内容不能为空";
      }
      return "";
    },
    broadcastMessageError() {
      if (!this.broadcastMessage) {
        return "消息内容不能为空";
      }
      return "";
    },
    filteredData() {
      let data = this.selectedDate ? this.filteredByDateData : this.tableData;
      if (this.searchText) {
        return data.filter(
          (item) =>
            item.message
              .toLowerCase()
              .includes(this.searchText.toLowerCase()) ||
            item.user_id.toString().includes(this.searchText) ||
            item.id.toString().includes(this.searchText) ||
            item.from_user_name
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
          (item) => item.created_at === this.selectedDate
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
    // 发送消息方法
    showSendMessageDialog() {
      this.sendMessageDialogVisible = true;
    },

    showBroadcastDialog() {
      this.broadcastMessageDialogVisible = true;
    },

    handleSendMessage() {
      // 处理发送消息逻辑
      console.log("发送消息", this.sendMessageFormData);
      // 发送消息到服务器...
      this.$message.success("发送成功");
      this.sendMessageDialogVisible = false;
    },

    handleBroadcastMessage() {
      // 处理群发消息逻辑
      console.log("群发消息", this.broadcastMessage);
      // 发送消息到服务器...
      this.$message.success("发送成功");
      this.broadcastMessageDialogVisible = false;
    },

    // 重置对话框和表单数据
    resetSendMessageForm() {
      this.sendMessageFormData = { message: "", user_id: "" };
    },

    resetBroadcastMessageForm() {
      this.broadcastMessage = "";
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
