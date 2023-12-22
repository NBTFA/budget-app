<template>
  <el-container class="register-container">
    <el-form ref="registerForm" :model="registerForm" class="register-form">
      <h1 class="register-title">注册</h1>
      <el-form-item label="用户名" :error="usernameError">
        <el-input
          v-model="registerForm.username"
          placeholder="请输入用户名"
        ></el-input>
      </el-form-item>
      <el-form-item label="邮箱" :error="emailError">
        <el-input
          v-model="registerForm.email"
          placeholder="请输入邮箱"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input
          v-model="registerForm.password"
          type="password"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item label="确认密码" :error="passwordError">
        <el-input
          v-model="registerForm.confirmPassword"
          type="password"
          placeholder="请再次输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onRegister">注册</el-button>
        <el-button type="default" @click="onBack">返回登陆</el-button>
      </el-form-item>
    </el-form>
  </el-container>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      registerForm: {
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
      },
    };
  },
  methods: {
    onRegister() {
      console.log("注册信息：", this.registerForm);
      // 这里可以添加注册逻辑
      this.$http.post("/user/register", this.registerForm).then((res) => {
        console.log("注册结果：", res);
        if (res.data.code === 20000) {
          this.$message.success("注册成功");
          this.$router.push("/home");
          if (res.data.data.token) {
            localStorage.setItem("token", res.data.data.token);
            this.$store.commit("setToken", res.data.data.token);
          }
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    onBack() {
      console.log("跳转登陆页面");
      this.$router.push("/login");
    },
  },
  computed: {
    // 确认密码的校验方法
    passwordError() {
      const { password, confirmPassword } = this.registerForm;
      if (password !== confirmPassword) {
        return "两次输入密码不一致";
      }
      return "";
    },
    //判断用户名是否为空或者是否为英文和数字的组合
    usernameError() {
      const { username } = this.registerForm;
      if (username === "") {
        return "用户名不能为空";
      }
      if (!/^[A-Za-z0-9]+$/.test(username)) {
        return "用户名只能为英文和数字的组合";
      }
      return "";
    },
    //判断邮箱是否为空或者是否为邮箱格式
    emailError() {
      const { email } = this.registerForm;
      if (email === "") {
        return "邮箱不能为空";
      }
      if (!/^\w+@[a-z0-9]+\.[a-z]{2,4}$/.test(email)) {
        return "邮箱格式不正确";
      }
      return "";
    },
  },
};
</script>

<style>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.register-form {
  width: 400px;
}

.register-title {
  text-align: center;
  margin-bottom: 20px;
}
</style>
