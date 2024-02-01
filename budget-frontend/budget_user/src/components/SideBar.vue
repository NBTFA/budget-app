<template>
    <el-aside width="200px">
      <div class="rank-section">
        <div class="rank-title">
          <h1>排行榜</h1>
        </div>
        <rank-item
          v-for="(user, index) in limitedRankUsers"
          :key="index"
          :user="user"
        ></rank-item>
      </div>
      <div class="todo-section">
        <todo-list></todo-list>
      </div>
    </el-aside>
  </template>
  
  <script>
  import RankItem from './HomePage/RankItem.vue';
  import TodoList from './HomePage/TodoList.vue';
  
  export default {
    name: 'Sidebar',
    components: {
      RankItem,
      TodoList
    },
    data() {
      return {
        rankUsers: [],
      };
    },
    computed: {
      limitedRankUsers() {
        // Returns only the first 5 users
        return this.rankUsers.slice(0, 5);
      }
    },
    watch: {
      "$store.state.rankUsers": function (newVal, oldVal) {
        this.rankUsers = newVal;
      },
    },
    created: function() {
      this.rankUsers = this.$store.state.rankUsers;
    }
  };
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
  