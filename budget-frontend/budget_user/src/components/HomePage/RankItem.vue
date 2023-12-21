<template>
    <div class="rank-item">
      <span class="rank">{{ user.rank }}</span>
      <img :src="user.avatar" alt="Avatar" class="avatar" />
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
    font-size: 1em;
    margin-right: 10px; /* 保持与头像的间距 */
    font-weight: bold;
  }
  
  .avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
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
  