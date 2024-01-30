<template>
  <div class="todo-list">
    <el-row>
      <el-col :span="24">
        <h2>待办事项</h2>
        <div class="todo-items">
          <todo-item
            v-for="(todo, index) in todos"
            :key="index"
            :description="todo.description"
            :title="todo.title"
            :date="todo.created_Date"
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
  watch: {
    "$store.state.todoLists": function (newVal, oldVal) {
      this.todos = newVal;
    },
  },
  created: function() {
    this.todos = this.$store.state.todoLists;
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
  