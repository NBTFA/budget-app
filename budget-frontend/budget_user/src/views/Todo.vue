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