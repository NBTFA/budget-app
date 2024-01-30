<template>
  <div class="todo-item">
    <el-checkbox
      v-model="checked"
      :disabled="checked"
      @change="onCheckboxChange"
    ></el-checkbox>
    <span :class="['description', { 'is-complete': checked }]">{{
      description
    }}</span>
    <span class="date">{{ formatDate(date) }}</span>
    <el-dialog
      title="是否确定选择完成？"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <p>完成后不可更改！</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitCompleted">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
  
  <script>
export default {
  name: "TodoItem",
  props: {
    description: {
      type: String,
      required: true,
    },
    // 从父组件接收日期
    date: {
      type: String,
      required: true,
    },
    // 从父组件接受是否完成
    isCompleted: {
      type: Boolean,
      default: false,
    },
    // 从父组件接收待办事项的id
    id: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      checked: this.isCompleted == false,
      dialogVisible: false,
      localCompletedDate: this.completedDate, // 本地状态
    };
  },
  watch: {
    completedDate(newVal) {
      this.localCompletedDate = newVal;
    },
  },
  methods: {
    onCheckboxChange(value) {
      if (value) {
        this.dialogVisible = true;
      }
    },
    formatDate(date) {
      if (!date) return ""; // 如果日期不存在，则返回空字符串
      const d = new Date(date);
      return `${d.getFullYear()}|${d.getMonth() + 1}|${d.getDate()}`;
    },
    submitCompleted() {
      this.$http
        .patch(`/user/todo/${this.id}`, {
          completed: true,
          completedDate: new Date().toISOString(),
        }).then((res) => {
          console.log("添加待办事项：", res);
          if (res.data.code === 20000) {
            this.$message.success("添加成功");
          } else {
            this.$message.error(res.data.message);
          }
        });
      this.checked = true; // 确保勾选框被选中
      this.dialogVisible = false;
    },
  },
};
</script>
  
  <style scoped>
.todo-item {
  display: flex;
  align-items: center;
}

.is-complete {
  text-decoration: line-through;
}

.date {
  font-size: x-small;
}

.description {
  font-size: x-small;
  margin-right: 10px;
  margin-left: 5px;
}
</style>
  