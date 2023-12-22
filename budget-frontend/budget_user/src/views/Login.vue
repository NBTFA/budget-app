<template>
    <el-container class="login-container">
      <el-form ref="loginForm" :model="loginForm" class="login-form">
        <h1 class="login-title">登录</h1>
        <el-form-item>
          <el-input v-model="loginForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item class="login-form-buttons">
          <el-button type="primary" @click="onLogin">登录</el-button>
          <el-button  type="default" @click="onRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </el-container>
  </template>
  
  <script>
  export default {
    name: 'Login',
    data() {
      return {
        loginForm: {
          username: '',
          password: '',
        },
      };
    },
    methods: {
      onLogin() {
        console.log('登录信息：', this.loginForm);
        this.$http.post('/login', this.loginForm).then((res) => {
          console.log('登录结果：', res);
          if (res.data.status === 20000) {
            this.$router.push('/home');
            if(res.data.data.token) {
              localStorage.setItem('token', res.data.data.token);
              this.$store.commit('setToken', res.data.data.token);
            }
          } else {
            this.$message.error(res.data.message);
          }
        });
      },
      onRegister() {
        console.log('跳转注册页面');
        this.$router.push('/register');
      }
    },
  };
  </script>
  
  <style>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 50vh;
  }
  
  .login-form {
    width: 300px;
  }
  
  .login-title {
    text-align: center;
    margin-bottom: 20px;
  }
  </style>
  