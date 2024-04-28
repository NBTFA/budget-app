<template>
    <el-container style="height: 100vh">
        <i class="fa-solid fa-circle-dollar-to-slot">预算保镖</i>
        <!-- 顶部导航 -->
        <el-header>
            <navigator></navigator>
        </el-header>

        <!-- 主内容区域，使用Flex布局 -->
        <el-container>
            <!-- 群组列表区，放在左侧 -->
            <el-aside width="200px">
                <div class="group-list">
                    <h3>所有群组</h3>
                    <ul>
                        <li v-for="group in groups" :key="group.id" @click="selectGroup(group)"
                            :class="{ 'active-group': group.id === currentGroupId }">
                            {{ group.name + "(" + group.peopleNum + ")" }}
                        </li>
                    </ul>
                </div>
            </el-aside>

            <!-- 聊天室内容区，使用Flex布局以保持发送区在底部 -->
            <el-main>
                <div class="chatroom">
                    <!-- 消息显示区 -->
                    <div class="scrollbar-wrapper">
                        <div class="message-container">
                            <div v-for="msg in messages" :key="msg.id" class="message"
                                :class="{ 'my-message': msg.isMine }">
                                <div class="message-info">{{ msg.user }} - {{ msg.time }}</div>
                                <div class="message-content">{{ msg.content }}</div>
                            </div>
                        </div>
                    </div>

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
import store from '@/store/index.js';

export default {
    components: {
        Navigator,
        SideBar,
    },
    data() {
        return {
            client: null,
            messages: [],
            newMessage: '',
            groups: [
                { id: 1, name: "Group One", peopleNum: 10 },
                { id: 2, name: "Group Two", peopleNum: 20 }
            ],
            currentGroupId: 0
        };
    },
    mounted() {
        this.connect();
        this.loadHistoryMessage();
        console.log("currentGroupId: ", this.currentGroupId);
    },
    methods: {
        loadHistoryMessage() {
            this.$http.get("/chat/messages/" + this.currentGroupId).then((res) => {
                console.log("loadHistoryMessage: ", res);
                if (res.data.code === 20000) {
                    this.messages = res.data.data.messages.map(msg => ({
                        ...msg,
                        isMine: msg.user_id === store.state.user_id // 如果消息的用户ID与当前用户ID相同，则将isMine设置为true
                    }));
                } else {
                    this.$message.error(res.data.message);
                }
            });
        },
        getGroups() {
            this.$http.get("/chat/groups").then((res) => {
                console.log("getGroups: ", res);
                if (res.data.code === 20000) {
                    this.groups = res.data.data.groups;
                    this.currentGroupId = this.groups[0].id;
                    this.selectGroup(this.groups[0]);
                } else {
                    this.$message.error(res.data.message);
                }
            });
        },
        connect() {
            this.client = new Client({
                brokerURL: 'ws://localhost:8088/ws',
                onConnect: () => {
                    this.client.subscribe('/topic/messages/' + this.currentGroupId, (message) => {
                        console.log("connect() - message: ", message.body);
                        console.log("connect() - currentGroupId: ", this.currentGroupId);
                        const msg = JSON.parse(message.body);
                        // 如果是自己发送的消息，则将ismine设置为true
                        msg.isMine = msg.user_id === store.state.user_id;
                        this.messages.push(msg);
                    });
                },
                onStompError: (frame) => {
                    console.error('Broker reported error: ' + frame.headers['message']);
                    console.error('Additional details: ' + frame.body);
                },
            });
            console.log("connect() - currentGroupId: ", this.currentGroupId);
            this.client.activate();
        },
        sendMessage() {
            if (!this.newMessage.trim()) return;
            const msg = {
                user_id: store.state.user_id,
                user: store.state.userName,
                content: this.newMessage,
                time: new Date().toLocaleTimeString(),
                isMine: true,
                group_id: this.currentGroupId
            };
            console.log("sendMessage() - currentGroupId: ", msg.group_id);
            this.client.publish({ destination: '/app/chat/' + this.currentGroupId, body: JSON.stringify(msg) });
            this.newMessage = '';
        },
        selectGroup(group) {
            this.currentGroupId = group.id;
            this.messages = [];
            if (this.client && this.client.connected) {
                this.client.unsubscribe('/topic/messages/' + this.currentGroupId);
                this.client.subscribe('/topic/messages/' + group.id, (message) => {
                    const msg = JSON.parse(message.body);
                    msg.isMine = msg.user_id === store.state.user_id;
                    this.messages.push(msg);
                });
            }
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
.el-container,
.el-main,
.chatroom {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.el-main {
    flex-grow: 1;
    overflow: hidden;
}

.scrollbar-wrapper {
    flex-grow: 1;
    overflow-y: auto;
    margin-bottom: 10px;
}

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

.send-message {
    display: flex;
    padding: 10px;
}

.message-input {
    flex: 1;
}

.group-list {
    padding: 10px;
}

.group-list h3 {
    margin-top: 0;
}

.group-list ul {
    list-style: none;
    padding: 0;
}

.group-list li {
    cursor: pointer;
    padding: 8px;
    border-radius: 4px;
    transition: background-color 0.3s;
}

.group-list li:hover,
.group-list li.active-group {
    background-color: #f0f0f0;
}
</style>
  