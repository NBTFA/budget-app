<template>
    <el-container style="height: 100vh">
        <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
        <!-- 顶部导航 -->
        <el-header>
            <navigator></navigator>
        </el-header>

        <!-- 侧边栏和内容区域 -->
        <el-container>
            <!-- 侧边栏 -->
            <!-- 主内容区 -->
            <el-main>
                <div class="chatroom">
                    <!-- 消息显示区 -->
                    <el-scrollbar wrap-class="scrollbar-wrapper">
                        <div class="message-container">
                            <div v-for="msg in messages" :key="msg.id" class="message"
                                :class="{ 'my-message': msg.isMine }">
                                <div class="message-info">{{ msg.user }} - {{ msg.time }}</div>
                                <div class="message-content">{{ msg.content }}</div>
                            </div>
                        </div>
                    </el-scrollbar>

                    <!-- 消息发送区 -->
                    <div class="send-message">
                        <el-input v-model="newMessage" class="message-input" placeholder="请输入消息..."
                            @keyup.enter="sendMessage"></el-input>
                        <el-button type="primary" @click="sendMessage">发送</el-button>
                    </div>
                </div>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import { Client } from '@stomp/stompjs';
import Navigator from "@/components/Navigator.vue";
import SideBar from "@/components/SideBar.vue";
import store from '@/store/index.js'

export default {
    components: {
        Navigator,
        SideBar,
    },
    data() {
        return {
            client: null,
            messages: [],
            newMessage: ''
        };
    },
    mounted() {
        this.connect();
    },
    methods: {
        connect() {
            this.client = new Client({
                brokerURL: 'ws://localhost:8088/ws', // 修改为你的WebSocket端点
                onConnect: () => {
                    this.client.subscribe('/topic/messages', (message) => {
                        const msg = JSON.parse(message.body);
                        // 打印msg
                        console.log(msg);
                        msg.isMine = false;
                        if (msg.user === store.state.userName) {
                            
                        }
                        else {
                            msg.isMine = false;
                            this.messages.push(msg);
                        }
                    });
                },
                onStompError: (frame) => {
                    console.error('Broker reported error: ' + frame.headers['message']);
                    console.error('Additional details: ' + frame.body);
                },
            });
            this.client.activate();
        },
        sendMessage() {
            if (!this.newMessage.trim()) return;
            const msg = {
                user: store.state.userName, // 假设的用户名称
                content: this.newMessage,
                time: new Date().toLocaleTimeString(),
                isMine: true
            };
            // 打印msg
            console.log(msg);
            this.messages.push(msg);
            this.client.publish({ destination: '/app/chat', body: JSON.stringify(msg) });
            this.newMessage = '';
        }
    },
    beforeDestroy() {
        if (this.client) {
            this.client.deactivate();
        }
    }
};
</script>

<style scoped>
/* 最外层容器设置为Flex布局，方向为垂直 */
.el-container {
    display: flex;
    flex-direction: column;
    height: 100vh;
    /* 全屏高度 */
}

/* 主内容区也应该是Flex容器，确保消息区和发送区正确布局 */
.el-main {
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    /* 填充剩余空间 */
    overflow: hidden;
    /* 避免内容溢出导致滚动 */
}

/* 主内容区域设置为Flex容器 */
.chatroom {
    display: flex;
    flex-direction: column;
    height: 100%;
    /* 确保聊天室占满整个主内容区 */
}

/* 消息显示区域自动填充剩余空间 */
.scrollbar-wrapper {
    flex-grow: 1;
    overflow-y: auto;
    margin-bottom: 10px;
    /* 为发送消息区域留出空间 */
}

/* 消息容器和消息样式保持不变 */
.message-container {
    margin: 10px;
}

.message {
    margin: 5px 0;
    text-align: left;
}

.message.my-message {
    text-align: right;
}

.message-content {
    padding: 10px;
    border-radius: 4px;
    display: inline-block;
}

.my-message .message-content {
    background-color: #409EFF;
    color: white;
}

.message:not(.my-message) .message-content {
    background-color: #67C23A;
    color: white;
}

.message-info {
    font-size: 12px;
    color: #888;
    margin-bottom: 5px;
}

/* 发送消息区域 */
.send-message {
    display: flex;
    padding: 10px;
}

.message-input {
    flex: 1;
}
</style>