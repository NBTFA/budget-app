<template>
  <div>
    <div class="buttonAdd">
      <el-button type="primary" @click="dialogVisible = true"
        >添加待办事项</el-button
      >
    </div>
    <el-dialog title="添加待办事项" :visible.sync="dialogVisible">
      <el-form label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="todoItem.title"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="todoItem.content"></el-input>
        </el-form-item>
        <el-form-item label="时间">
          <el-date-picker
            v-model="todoItem.date"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            :picker-options="{
              disabledDate: disabledBeforeToday,
            }"
          ></el-date-picker>
        </el-form-item>
        <el-button type="primary" @click="onConfirm">确定</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </el-form>
    </el-dialog>
    <div class="todoListSection">
      <el-timeline>
        <el-empty
          v-if="todoItems.length === 0"
          description="暂无待办事项"
        ></el-empty>
        <el-timeline-item
          v-for="(item, index) in todoItems"
          :key="item.id"
          :timestamp="item.time"
          placement="top"
          :color="item.completed ? 'green' : 'gray'"
          :icon="
            item.completed ? 'fas fa-check' : 'fa-solid fa-clock-rotate-left'
          "
        >
          <el-card>
            <div class="content">
              <h4>{{ item.title }}</h4>
              <p>{{ item.content }}</p>
            </div>
            <div class="completeButton">
              <el-button
                type="success"
                v-if="!item.completed"
                @click="prepareCompletion(index)"
                >完成</el-button
              >
            </div>
            <el-dialog
              title="是否确定选择完成？"
              :visible.sync="completeDialogVisible"
              width="30%"
            >
              <span slot="footer" class="dialog-footer">
                <el-button @click="completeDialogVisible = false"
                  >取消</el-button
                >
                <el-button type="primary" @click="onConfirmComplete()"
                  >确定</el-button
                >
              </span>
            </el-dialog>
            <el-button
              type="text"
              class="delete-button"
              @click="confirmDeletion(index)"
            >
              <i class="fas fa-trash-alt fa-2x"></i>
            </el-button>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>
  
  <script>
export default {
  name: "TodoListTimeline",
  data() {
    return {
      todoItems: [],
      dialogVisible: false,
      completeDialogVisible: false,
      todoItem: {
        title: "",
        content: "",
        date: "",
        id: "",
      },
      activeItemIndex: null,
    };
  },
  watch: {
    "$store.state.todoLists": function (newVal, oldVal) {
      this.todoItems = newVal;
    },
  },
  created() {
    this.todoItems = this.$store.state.todoLists;
  },
  methods: {
    prepareCompletion(index) {
      this.activeItemIndex = index;
      this.completeDialogVisible = true;
    },
    onConfirmComplete() {
      this.completeDialogVisible = false;
      if (this.activeItemIndex !== null) {
        this.todoItems[this.activeItemIndex].completed = true;
        console.log("选择id是：" + this.todoItems[this.activeItemIndex].id);
        this.$http
          .patch(`/user/todo/${this.todoItems[this.activeItemIndex].id}`, {
            completed: true,
            completedDate: new Date().toISOString(),
          })
          .then((res) => {
            console.log("完成待办事项：", res);
            if (res.data.code === 20000) {
              this.$message.success("添加成功");
            } else {
              this.$message.error(res.data.message);
            }
          });
      }
    },
    disabledBeforeToday(date) {
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      return date.getTime() < today.getTime();
    },
    onConfirm() {
      this.dialogVisible = false;
      this.$http
        .post("/user/todo", {
          title: this.todoItem.title,
          content: this.todoItem.content,
          time: this.todoItem.date,
          completed: false,
        })
        .then((res) => {
          console.log("添加待办事项：", res);
          if (res.data.code === 20000) {
            this.$message.success("添加成功");
            this.todoItem.id = res.data.id;
            this.todoItems.push({
              id: this.todoItem.id,
              title: this.todoItem.title,
              content: this.todoItem.content,
              time: this.todoItem.date,
              completed: false,
            });
            this.todoItem = {
              title: "",
              content: "",
              date: "",
            };
          } else {
            this.$message.error(res.data.message);
          }
        });
    },
    confirmDeletion(index) {
      this.$confirm("你确定要删除该待办事项吗？", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$http
            .delete("/user/todo", {
              data: {
                title: this.todoItems[index].title,
                content: this.todoItems[index].content,
                time: this.todoItems[index].time,
                completed: this.todoItems[index].completed,
              },
            })
            .then((res) => {
              console.log("删除待办事项：", res);
              if (res.data.code === 20000) {
              } else {
                this.$message.error(res.data.message);
              }
            });
          this.todoItems.splice(index, 1);
          this.$message({
            type: "success",
            message: "删除成功",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "删除取消",
          });
        });
    },
  },
};
</script>
  
  <style>
.content {
  text-align: left;
}
.el-timeline-item__timestamp {
  text-align: left;
}
.el-timeline-item {
  width: 50%;
}
.delete-button {
  position: absolute;
  right: 10px;
  top: 20px;
}
.buttonAdd {
  text-align: right;
}
</style>
  