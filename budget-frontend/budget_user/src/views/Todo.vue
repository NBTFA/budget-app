<template>
  <el-container style="height: 100vh">
    <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
    <!-- 顶部导航 -->
    <el-header>
      <navigator></navigator>
    </el-header>
    <el-main>
      <todo-list-time-line></todo-list-time-line>
    </el-main>
  </el-container>
</template>

<script>
import TodoListTimeLine from "@/components/ToDoPage/TodoListTimeLine.vue";
import Navigator from "../components/Navigator.vue";
import store from '@/store/index.js'
export default {
  name: "Todo",
  components: { Navigator, TodoListTimeLine },
  data: function () {
    return {
      dialogVisible: false,
      todoItem: {
        title: "",
        content: "",
        date: "",
      },
    };
  },
  created() {
    this.$http.get("/user/todoRequest").then((res) => {
      console.log("todoRequest: ", res);
      if (res.data.code === 20000) {
        store.dispatch("setAvatar", res.data.data.avatar);
        store.dispatch("setNotificationNum", res.data.data.notificationNum);
        store.dispatch("setNotifications", res.data.data.notifications);
        store.dispatch("setContinueNum", res.data.data.continueNum);
        store.dispatch("setTodoLists", res.data.data.todoList);
      } else {
        this.$message.error(res.data.message);
      }
    });
  },
  methods: {
    onConfirm() {
      this.dialogVisible = false;
      this.$store.commit("addTodoItem", this.todoItem);
      this.todoItem = {
        title: "",
        content: "",
        date: "",
      };
    },
  },
};
</script>

<style>

</style>