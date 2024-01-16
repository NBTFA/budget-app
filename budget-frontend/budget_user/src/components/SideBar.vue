<template>
    <el-aside width="200px">
      <div class="rank-section">
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
    created: function() {
      this.$http.get("/user/rankList").then((res) => {
        console.log("获取用户排名：", res);
        if (res.data.code === 20000) {
          this.rankUsers = res.data.data.rankUsers;
        } else {
          this.$message.error(res.data.message);
        }
      })
    }
  };
  </script>
  
  <style scoped>
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
  