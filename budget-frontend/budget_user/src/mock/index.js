import Mock from 'mockjs'
//拦截/login post请求,返回模拟数据,指定httpstatus为200
// Mock.mock('http://localhost:8088/user/login', 'post', {
//     "status": 20000,
//     "data": {
//         "id": "@id",
//         "username": "@cname",
//         "email": "@email",
//         "phone": /^1[3456789]\d{9}$/,
//         "role": 0,
//         "createTime": "@datetime",
//         "updateTime": "@datetime",
//         "token" : "dmwklqd"
//     }
// })
// //拦截/register post请求,返回模拟数据,指定httpstatus为200
// Mock.mock('http://localhost:8088/user/register', 'post', {
//     "status": 20000,
//     "data": {
//         "id": "@id",
//         "username": "@cname",
//         "email": "@email",
//         "phone": /^1[3456789]\d{9}$/,
//         "role": 0,
//         "createTime": "@datetime",
//         "updateTime": "@datetime"
//     }
// })
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
        "notificationNum": "@integer(0,100)",
        "notifications": [
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
//拦截/user/rankList get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/rankList', 'get', {
    "status": 20000,
    "data": {
        "rankUsers|10": [
            {
                "username": "@cname",
                "rank": "@integer(1,5)",
                "continuousRecord": "@integer(0,100)",
            }
        ]
    }
})
//拦截/user/todoList get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/todoList', 'get', {
    "status": 20000,
    "data": {
        "todoList|5": [
            {
                "description": "@cname",
                "completedDate": "@Date"
            }
        ]
    }
})
//拦截/user/budget/progress get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/budget/progress', 'get', {
    "status": 20000,
    "data": {
        "progress": "@integer(0,100)",
    }
})
//拦截/user/budget?frame=year get请求,返回模拟数据,指定httpstatus为20000
//格式为{ date: '2020-01', name: '项目A', category: '分类1', amount: 1000, income: true }
Mock.mock('http://localhost:8088/user/budget?frame=year', 'get', {
    "status": 20000,
    "data": {
        "budgetList|10": [
            {
                "date": "@date('yyyy-MM')",
                "name": "@cname",
                "category": "@cname",
                "amount": "@integer(0,100)",
                "income": "@boolean"
            }
        ]
    }
})
//拦截/user/budget?frame=month get请求,返回模拟数据,指定httpstatus为20000
//格式为{ date: '2020-01-01', name: '项目A', category: '分类1', amount: 1000, income: true }
Mock.mock('http://localhost:8088/user/budget?frame=month', 'get', {
    "status": 20000,
    "data": {
        "budgetList|10": [
            {
                "date": "@date('yyyy-MM-dd')",
                "name": "@cname",
                "category": "@cname",
                "amount": "@integer(0,100)",
                "income": "@boolean"
            }
        ]
    }
})
//拦截/user/budget/pieChart get请求,返回模拟数据,指定httpstatus为20000
//{ name: "餐饮", value: 100 }
Mock.mock('http://localhost:8088/user/budget/pieChart', 'get', {
    "status": 20000,
    "data": {
        "pieChart|10": [
            {
                "name": "@cname",
                "value": "@integer(0,100)",
            }
        ]
    }
})
