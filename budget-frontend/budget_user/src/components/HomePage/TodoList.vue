<template>
  <div class="todo-list">
    <el-row>
      <el-col :span="24">
        <h2>待办事项</h2>
        <div class="todo-items">
          <todo-item
            v-for="(todo, index) in todos"
            :key="index"
            :description="todo.title"
            :date="todo.time"
            :isCompleted="todo.completed"
            :id="todo.id"
          ></todo-item>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
  
  <script>
import TodoItem from "./TodoItem.vue";

export default {
  name: "TodoList",
  components: {
    TodoItem,
  },
  data() {
    return {
      todos: [],
    };
  },
  created: function() {
    this.$http.get("/user/todo").then((res) => {
        console.log("获取用户排名：", res);
        if (res.data.code === 20000) {
          this.todos = res.data.data.todos;
        } else {
          this.$message.error(res.data.message);
        }
      })
  }
  // 可以添加方法用于从后端获取待办事项列表等
};
</script>
  
  <style scoped>
.todo-list {
  padding: 0;
  margin: 0;
}

.todo-items {
  margin-bottom: 10px;
}
</style>
  