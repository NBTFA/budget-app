<template>
    <el-aside width="200px">

    </el-aside>
  </template>
  
  <script>
  
  export default {
    name: 'Sidebar',
    components: {

    },
    data() {
      return {
        rankUsers: [],
      };
    },
  }
  </script>
  
  <style scoped>
  .rank-title {
    font-size: 24px;
  }
  .el-aside {
    display: flex;
    flex-direction: column;
  }
  
  .rank-section {
    overflow-y: auto;
    max-height: 30%; /* You can adjust this value as needed */
  }
  
  .todo-section {
    overflow-y: auto;
    flex-grow: 1; /* The rest of the space is for the todo list */
  }
  </style>
  