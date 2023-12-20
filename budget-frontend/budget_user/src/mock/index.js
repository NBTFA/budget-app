import Mock from 'mockjs'
//拦截/login post请求,返回模拟数据,指定httpstatus为200
Mock.mock('http://localhost:8088/login', 'post', {
    "status": 20000,
    "data": {
        "id": "@id",
        "username": "@cname",
        "email": "@email",
        "phone": /^1[3456789]\d{9}$/,
        "role": 0,
        "createTime": "@datetime",
        "updateTime": "@datetime"
    }
})
//拦截/register post请求,返回模拟数据,指定httpstatus为200
Mock.mock('http://localhost:8088/register', 'post', {
    "status": 20000,
    "data": {
        "id": "@id",
        "username": "@cname",
        "email": "@email",
        "phone": /^1[3456789]\d{9}$/,
        "role": 0,
        "createTime": "@datetime",
        "updateTime": "@datetime"
    }
})
//拦截/user/avatar get请求,返回模拟url,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/avatar', 'get', {
    "status": 20000,
    "data": {
        "url": "@image('200x200','red','#fff','avatar')"
    }
})
//拦截/user/mention get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/mention', 'get', {
    "status": 20000,
    "data": {
        "mention": "@integer(0,100)",
        "mentionList": [
            {
                "title": "大便",
                "content": "你有一份没吃的大便23232323",
                "createTime": "123",
            },
            {
                "title": "大便",
                "content": "你有一份没吃的大便",
                "createTime": "@datetime",
            },
            {
                "title": "大便",
                "content": "你有一份没吃的大便",
                "createTime": "@datetime",
            },
            {
                "title": "大便",
                "content": "你有一份没吃的大便",
                "createTime": "@datetime",
            },
            {
                "title": "大便",
                "content": "你有一份没吃的大便",
                "createTime": "@datetime",
            },
        ]
    }
})
//拦截/user/continue get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/continue', 'get', {
    "status": 20000,
    "data": {
        "continue": "@integer(0,100)",
    }
})