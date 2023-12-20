<template>
    <el-badge :value="continueNum" class="item">
        <i :class="[iconColorClass, 'fa-solid', 'fa-fire', 'fa-2x']"></i>
    </el-badge>
</template>

<script>
export default {
    name: "ContinueIcon",
    data() {
        return {
            continueNum: "",
        };
    },
    computed: {
        iconColorClass() {
            // 将 continueNum 转换为数字
            const num = +this.continueNum;
            // 如果 continueNum 为 0，返回 'black-icon' 类，否则返回 'red-icon' 类
            return num === 0 ? 'black-icon' : 'red-icon';
        }
    },
    methods: {
        getContinue() {
            this.$http.get("/user/continue").then((res) => {
                console.log("获取连续打卡天数：", res);
                if (res.data.status === 20000) {
                    this.continueNum = res.data.data.continue;
                } else {
                    this.$message.error(res.data.message);
                }
            });
        },
    },
    created() {
        this.getContinue();
    },
}
</script>

<style>
/* 定义红色和黑色图标的样式 */
.red-icon {
    color: red;
}

.black-icon {
    color: black;
}
</style>
