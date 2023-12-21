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
              :completedDate="todo.completedDate"
            ></todo-item>
          </div>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  import TodoItem from './TodoItem.vue';
  
  export default {
    name: 'TodoList',
    components: {
      TodoItem
    },
    data() {
      return {
        todos: [
          // 示例待办事项数据
          { description: '学习 Vue.js', completedDate: null },
          { description: '阅读 Element UI 文档', completedDate: null },
          { description: '写一个新的 Vue 组件', completedDate: null }
        ]
      };
    },
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
  