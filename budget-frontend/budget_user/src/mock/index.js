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
// Mock.mock('http://localhost:8088/user/avatar', 'get', {
//     "status": 20000,
//     "data": {
//         "url": "@image('200x200','red','#fff','avatar')"
//     }
// })
//拦截/user/mention get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/notification', 'get', {
    "code": 20000,
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
    "code": 20000,
    "data": {
        "continue": "@integer(0,100)",
    }
})
//拦截/user/rankList get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/rankList', 'get', {
    "code": 20000,
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
    "code": 20000,
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
    "code": 20000,
    "data": {
        "progress": "@integer(0,100)",
    }
})
//拦截/user/budget?frame=year get请求,返回模拟数据,指定httpstatus为20000
//格式为{ date: '2020-01', name: '项目A', category: '分类1', amount: 1000, income: true }
Mock.mock('http://localhost:8088/user/budget?frame=year', 'get', {
    "code": 20000,
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
//格式为{ date: '2020-01', name: '项目A', category: '分类1', amount: 1000, income: true }
Mock.mock('http://localhost:8088/user/budget?frame=month', 'get', {
    "code": 20000,
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
//拦截/user/budget/allget请求,返回模拟数据,指定httpstatus为20000
//格式为{ date: '2020-01-01', name: '项目A', category: '分类1', amount: 1000, income: true }
Mock.mock('http://localhost:8088/user/budget/all', 'get', {
    "code": 20000,
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
    "code": 20000,
    "data": {
        "pieChart|10": [
            {
                "name": "@cname",
                "value": "@integer(0,100)",
            }
        ]
    }
})
//拦截/user/todo/add post请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/todo', 'post', {
    "code": 20000,
    "id": "@id"
})
//拦截/user/todo get请求,返回模拟数据,指定httpstatus为20000
//格式为{ time: '2020-01-01', title: '项目A', content: '分类1', completed: true, id: 1}
Mock.mock('http://localhost:8088/user/todo', 'get', {
    "code": 20000,
    "data": {
        "todos": [
            {
                "id": "1",
                "time": "@date('yyyy-MM-dd')",
                "title": "@cname",
                "content": "@cname",
                "completed": "@boolean"
            },
            {
                "id": "2",
                "time": "@date('yyyy-MM-dd')",
                "title": "@cname",
                "content": "@cname",
                "completed": "@boolean"
            },
        ]
    }
})
//拦截/user/todo delete请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/todo', 'delete', {
    "code": 20000,
})
//拦截/user/todo/{id} patch请求,返回模拟数据,指定httpstatus为20000
//id为todo的id，使用正则表达式匹配
Mock.mock(/http:\/\/localhost:8088\/user\/todo\/\d+/, 'patch', {
    "code": 20000,
})
//拦截/user/budget?id=111 delete请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/budget', 'delete', {
    "code": 20000,
})
//拦截/user/budget/add post请求,返回模拟数据,指定httpstatus为20000
//格式为{ id, date: '2020-01-01', name: '项目A', category: '分类1', amount: 1000, income: true }
Mock.mock('http://localhost:8088/user/budget/add', 'post', {
    "code": 20000,
    "budget": {
        "id": "@id",
        "date": "@date('yyyy-MM-dd')",
        "name": "新加的",
        "category": "@cname",
        "amount": "@integer(0,100)",
        "income": "@boolean"
    }
})
//拦截/user/budget/health get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/budget/health', 'get', {
    "code": 20000,
    "data": {
        "health": "@integer(0,100)",
    }
})
//拦截/user/budget/remainPercentage get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/budget/remainPercentage', 'get', {
    "code": 20000,
    "data": {
        "remainPercentage": "@integer(0,100)",
    }
})
//拦截/user/budget/categories get请求,返回模拟数据,指定httpstatus为20000
Mock.mock('http://localhost:8088/user/budget/categories', 'get', {
    "code": 20000,
    "data": {
        "categories|10": [
            {
                "name": "@cname",
                "percentage": "@integer(0,100)",
            }
        ]
    }
})
//拦截/user/budget/gpt get请求,返回模拟数据,指定httpstatus为20000
//gpt是一个段落
Mock.mock('http://localhost:8088/user/budget/gpt', 'get', {
    "code": 20000,
    "data": {
        "gpt": "@cparagraph",
    }
})
