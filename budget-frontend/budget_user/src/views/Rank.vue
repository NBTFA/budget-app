<template>
    <el-container style="height: 100vh;">
        <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
      <!-- 顶部导航 -->
      <el-header>
        <navigator></navigator>
      </el-header>
    </el-container>
</template>

<script>
import Navigator from "../components/Navigator.vue";
export default {
    name: "Rank",
    components: { Navigator },
}
</script>