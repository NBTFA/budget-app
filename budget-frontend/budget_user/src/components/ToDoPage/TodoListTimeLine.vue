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
          :key="index"
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
            <el-button
              type="text"
              class="delete-button"
              @click="confirmDeletion(index)"
            >
              <i class="fas fa-trash-alt"></i>
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
      todoItem: {
        title: "",
        content: "",
        date: "",
      },
    };
  },
  created() {
    this.$http.get("/user/todo").then((res) => {
      console.log("获取待办事项：", res);
      if (res.data.status === 20000) {
        this.todoItems = res.data.data.todos;
      } else {
        this.$message.error(res.data.message);
      }
    });
  },
  methods: {
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
          if (res.data.status === 20000) {
            this.$message.success("添加成功");
          } else {
            this.$message.error(res.data.message);
          }
        });
      this.todoItems.push({
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
              if (res.data.status === 20000) {
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
  