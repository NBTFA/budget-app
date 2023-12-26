<template>
    <el-container style="height: 100vh;">
        <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
      <!-- 顶部导航 -->
      <el-header>
        <navigator></navigator>
      </el-header>
      <time-line-section></time-line-section>
    </el-container>
</template>

<script>
import Navigator from "../components/Navigator.vue";
import TimeLineSection from "@/components/HomePage/TimeLineSection.vue";
export default {
    name: "Report",
    components: { Navigator, TimeLineSection },
}
</script>