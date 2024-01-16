<template>
  <el-container>
    <el-header>
      <navigator></navigator>
    </el-header>
    <el-main>
      <el-form>
        <!-- 用户名显示 -->
        <div class="username">
          <h1>你好，{{ username }}！</h1>
        </div>

        <!-- 头像显示和上传 -->
        <el-form-item>
          <img :src="avatar" alt="头像" class="avatar" />
          <!-- 强制换行 -->
          <br />
          <br />
          <br />
          <el-button type="primary" @click="uploadAvatar">上传头像</el-button>
          <el-button type="primary" @click="changePasswordDialog = true"
            >更改密码</el-button
          >
        </el-form-item>
      </el-form>
    </el-main>

    <!-- 更改密码对话框 -->
    <el-dialog title="更改密码" :visible.sync="changePasswordDialog">
      <el-form>
        <el-form-item label="新密码">
          <el-input v-model="newPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码">
          <el-input v-model="confirmPassword" type="password"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="changePasswordDialog = false">取消</el-button>
        <el-button type="primary" @click="changePassword">确定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>
  
  <script>
import Navigator from "@/components/Navigator.vue";
export default {
  data() {
    return {
      username: "当前用户名",
      avatar: "当前头像的URL",
      changePasswordDialog: false,
      newPassword: "",
      confirmPassword: "",
    };
  },
  components: { Navigator },
  methods: {
    uploadAvatar() {
      // 实现上传头像的逻辑
      // 通常包括打开文件选择对话框，选择文件，然后上传到服务器
    },
    changePassword() {
      if (this.newPassword !== this.confirmPassword) {
        this.$message.error("两次输入的密码不一致");
        this.newPassword = "";
        this.confirmPassword = "";
        return;
      }
      // 实现更改密码的逻辑
      // 将新密码发送到服务器进行更新
      this.changePasswordDialog = false;
      this.$message.success("密码更改成功");
      this.newPassword = "";
      this.confirmPassword = "";
    },
  },
};
</script>

<style>
.username {
  text-align: left;
}
.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
}
</style>  