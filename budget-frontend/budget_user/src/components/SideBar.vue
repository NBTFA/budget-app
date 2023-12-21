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
        rankUsers: [
            {
                "username": "user1",
                "rank": 1,
                "continuousRecord": 2
            },
            {
                "username": "user1",
                "rank": 2,
                "continuousRecord": 2
            },
            {
                "username": "user1",
                "rank": 3,
                "continuousRecord": 2
            },
            {
                "username": "user1",
                "rank": 4,
                "continuousRecord": 2
            },
            {
                "username": "user1",
                "rank": 5,
                "continuousRecord": 2
            },
            {
                "username": "user1",
                "rank": 6,
                "continuousRecord": 2
            }
          // ... your users data here ...
        ],
      };
    },
    computed: {
      limitedRankUsers() {
        // Returns only the first 5 users
        return this.rankUsers.slice(0, 5);
      }
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
  