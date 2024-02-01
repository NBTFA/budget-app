<template>
  <el-aside width="200px">
    <el-menu default-active="1">
      <el-menu-item index="1">
        <span slot="title">统计数据</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>用户管理</span>
        </template>
        <el-menu-item index="1-1">用户操作</el-menu-item>
        <el-menu-item index="1-2">管理员操作</el-menu-item>
      </el-submenu>
      <el-menu-item index="3">
        <i class="el-icon-document"></i>
        <span slot="title">通知系统</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-setting"></i>
        <span slot="title">数据管理</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>
  
  <script>
export default {
  name: "Sidebar",
  components: {},
  data() {
    return {
      rankUsers: [],
    };
  },
};
</script>
  
<style scoped>
/* 所有文字靠左 */
.el-menu{
  text-align: left;
}

</style>
  