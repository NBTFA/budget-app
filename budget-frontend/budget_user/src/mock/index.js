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