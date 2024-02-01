<template>
    <div class="rank-item">
      <span class="rank">{{ user.rank }}</span>
      <el-avatar src = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
      <!-- <img :src="user.avatar" alt="Avatar" class="avatar" /> -->
      <span class="username">{{ user.username }}</span>
      <i class="fa-solid fa-fire fire-icon"></i>
      <span class="record">{{ user.continuousRecord }}</span>
    </div>
  </template>
  
  <script>
  export default {
    name: "RankItem",
    props: {
      user: {
        type: Object,
        required: true
      }
    }
  }
  </script>
  
  <style>
  .rank-item {
    display: flex;
    align-items: center;
    padding: 10px;
    border-radius: 8px;
  }
  
  .rank {
    font-size: 20px;
    margin-right: 10px; /* 保持与头像的间距 */
    font-weight: bold;
  }
  
  
  .username {
    margin-right: auto;
  }
  
  .fire-icon {
    color: red; /* 设置图标颜色为红色 */
  }
  
  .record {
    margin-left: 5px;
    font-weight: bold;
  }
  </style>
  