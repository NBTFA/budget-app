# 预算保镖

这个项目是一个具有前后端分离架构的应用，它包括一个面向普通用户的前端界面和一个面向管理员的后台管理系统。在用户界面中，用户可以进行预算记录和待办事项管理，包括添加、查看、编辑和删除这些数据。此外，还有一个排行榜功能，展示了连续登录天数最多的前五名用户。在管理员后台，管理员有权查看用户数据，修改用户账户信息及管理员自身的账户信息。管理员还可以访问服务器的数据统计视图，包括新增用户数量、所有用户概览以及所有预算记录的可视化展示。

## 技术栈

### 前端技术栈

- **Vue.js (`^2.6.14`)**: Vue的核心库，用于构建用户界面的渐进式JavaScript框架。
- **Vue Router (`^3.6.5`)**: Vue的路由管理器，用于构建单页面应用（SPA）。
- **Vuex (`^3.6.2`)**: 状态管理模式和库，用于多组件之间的信息交互。
- **Axios (`^1.6.2`)**: 基于Promise的HTTP客户端，用于向后端发送HTTP请求。
- **Element UI (`^2.15.14`)**: 组件库。
- **ECharts (`^4.9.0`)**: 开源可视化库，主要用于数据可视化。
- **V-Charts (`^1.19.0`)**: 基于Vue2.0和ECharts封装的图表组件，简化了ECharts的配置过程。
- **MockJS (`^1.1.0`)**: 用于生成随机数据和拦截Ajax请求的模拟库。
- **Font Awesome (`^4.7.0` & `^6.5.1`)**: 图标集合。
- **Core-js (`^3.8.3`)**: 为旧浏览器提供最新的JavaScript特性的polyfill库。

### 后端技术栈

- **Spring Boot (`spring-boot-starter-web`)**: Spring Boot核心框架。
- **Spring Boot Test (`spring-boot-starter-test`)**: 提供测试支持，包括单元测试和集成测试。
- **Springfox (`3.0.0`)**: 自动生成Swagger API文档的库。
- **JSON Web Tokens (JWT) (`jjwt:0.9.1`)**: 用户认证和授权的Java实现。
- **MyBatis Plus (`3.4.2`)**: 基于MyBatis的增强工具。
- **MySQL Connector/J (`mysql-connector-java`)**: MySQL的JDBC驱动，用于Java应用与MySQL数据库之间的连接。
- **Alibaba Druid (`1.1.20`)**: 数据库连接池、监控和扩展组件。
- **Java Persistence API (JPA) (`javax.persistence-api:2.2`)**: 定义了Java持久化相关的方法和类。
- **Hibernate Core (`5.5.6.Final`)**: 对象关系映射(ORM)框架，用于将Java对象映射到数据库表。

## 如何启动

### 前端

### 使用npm安装依赖

```
npm install
```

### 开发环境编译和热部署

```
npm run serve
```

### 打包和运行生产环境

```
npm run build
```

### 后端

### 环境要求

- Java Development Kit (JDK) 1.8 或更高版本
- Maven 3.2 或更高版本

### 使用maven安装依赖

```markdown
mvn clean install
```

### 启动

```java
mvn spring-boot:run
```

### 打包和运行生产环境

```java
mvn clean package
```

## 前端结构

### budget_admin

```java
src
├── components
│   ├── HomePage
│   │   └── LineChart.vue
│   ├── Avatar.vue
│   ├── HelloWorld.vue
│   └── SideBar.vue
├── routers
│   └── index.js
├── store
│   └── index.js
├── views
│   ├── Admin.vue
│   ├── Budget.vue
│   ├── Category.vue
│   ├── Continuous.vue
│   ├── Home.vue
│   ├── Login.vue
│   ├── Notification.vue
│   ├── Register.vue
│   ├── Statistics.vue
│   ├── Todo.vue
│   └── User.vue
└── App.vue
```

### budget_user

```java
src
├── components
│   ├── HomePage
│   │   ├── ContinueIcon.vue
│   │   ├── MentionIcon.vue
│   │   ├── MentionItem.vue
│   │   ├── PieChart.vue
│   │   ├── RankItem.vue
│   │   ├── TimelineChart.vue
│   │   ├── TimelineSection.vue
│   │   ├── Todotem.vue
│   │   └── TodoList.vue
│   ├── ReportPage
│   │   └── GetReportSection.vue
│   ├── ToDoPage
│   │   └── TodoListTimeLine.vue
│   ├── BudgetInfo.vue
│   ├── BudgetTable.vue
│   ├── HelloWorld.vue
│   ├── Navigator.vue
│   └── SideBar.vue
├── mock
│   └── index.js
├── router
│   └── index.js
├── store
│   └── index.js
├── views
│   ├── History.vue
│   ├── Home.vue
│   ├── Login.vue
│   ├── Rank.vue
│   ├── Register.vue
│   ├── Report.vue
│   ├── Setting.vue
│   └── Todo.vue
└── App.vue
```

## API文档

### AdministratorController

| 接口名称 | 请求类型 | 请求路径 | 请求参数 | 返回数据 | 描述 |
| --- | --- | --- | --- | --- | --- |
| 管理员登录 | POST | /admin/login | Administrator对象的JSON | Result对象 | 管理员登录，成功返回token |
| 管理员注册 | POST | /admin/register | Administrator对象的JSON | Result对象 | 管理员注册，成功返回token |
| 服务器统计信息 | GET | /admin/serverStat | Authorization头信息 | Result对象 | 获取新用户、总用户和总记录的统计信息 |
| 获取用户信息 | GET | /admin/user | Authorization头信息 | Result对象 | 获取用户列表 |
| 删除用户 | DELETE | /admin/user | Authorization头信息和User对象的JSON | Result对象 | 删除指定用户 |
| 更新用户 | POST | /admin/user | Authorization头信息和User对象的JSON | Result对象 | 更新用户信息 |
| 处理管理员请求 | GET | /admin/adminRequest | Authorization头信息 | Result对象 | 获取管理员列表和当前管理员权限 |
| 删除管理员 | DELETE | /admin/admin | Authorization头信息和Administrator对象的JSON | Result对象 | 删除指定管理员 |
| 更新管理员 | POST | /admin/admin | Authorization头信息和Administrator对象的JSON | Result对象 | 更新管理员信息 |
| 向用户发送信息 | POST | /admin/sendToUser | Authorization头信息和SendToUserRequest对象的JSON | Result对象 | 向指定用户发送信息 |
| 向所有用户发送信息 | POST | /admin/sendToAll | Authorization头信息和消息内容的字符串 | Result对象 | 向所有用户发送信息 |
| 删除通知 | DELETE | /admin/notification | Authorization头信息和Notification对象的JSON | Result对象 | 删除指定通知 |
| 获取通知 | GET | /admin/notification | Authorization头信息 | Result对象 | 获取通知列表和用户列表 |
| 获取分类请求 | GET | /admin/categoryRequest | Authorization头信息 | Result对象 | 获取分类信息 |
| 获取连续登陆请求 | GET | /admin/continuousRequest | Authorization头信息 | Result对象 | 获取连续登陆统计信息 |
| 获取预算请求 | GET | /admin/budgetRequest | Authorization头信息 | Result对象 | 获取预算记录 |
| 删除预算 | DELETE | /admin/budget | Authorization头信息和BudgetRecord对象的JSON | Result对象 | 删除指定预算记录 |
| 获取待办事项请求 | GET | /admin/todoRequest | Authorization头信息 | Result对象 | 获取待办事项列表 |
| 删除待办事项 | DELETE | /admin/todo | Authorization头信息和TodoListRecord对象的JSON | Result对象 | 删除指定待办事项 |

### BudgetController

| 接口名称 | 请求类型 | 请求路径 | 请求参数 | 返回数据 | 描述 |
| --- | --- | --- | --- | --- | --- |
| 获取预算进度 | GET | /user/budget/progress | Authorization头信息 | Result对象 | 根据token获取用户的预算进度信息 |
| 获取所有预算 | GET | /user/budget/all | Authorization头信息 | Result对象 | 根据token获取用户的所有预算信息 |
| 获取饼图数据 | GET | /user/budget/pieChart | Authorization头信息 | Result对象 | 根据token获取预算的饼图数据 |

### UserController

| 接口名称 | 请求类型 | 请求路径 | 请求参数 | 返回数据 | 描述 |
| --- | --- | --- | --- | --- | --- |
| 用户登录 | POST | /user/login | User对象的JSON | Result对象 | 用户登录，成功返回token |
| 用户注册 | POST | /user/register | User对象的JSON | Result对象 | 用户注册，成功返回token |
| 初始化用户预算 | POST | /user/initialize | Authorization头信息，InitializeBudgetRequest对象的JSON | Result对象 | 设置用户预算信息 |
| 首页请求处理 | GET | /user/homeRequest | Authorization头信息 | Result对象 | 处理首页请求，返回所需的各类信息 |
| 历史记录请求处理 | GET | /user/historyRequest | Authorization头信息 | Result对象 | 处理历史记录请求，返回所需的各类信息 |
| 待办事项请求处理 | GET | /user/todoRequest | Authorization头信息 | Result对象 | 处理待办事项请求，返回所需的各类信息 |
| 添加待办事项 | POST | /user/todo | TodoListRecord对象的JSON，Authorization头信息 | Result对象 | 添加待办事项 |
| 删除待办事项 | DELETE | /user/todo | TodoListRecord对象的JSON，Authorization头信息 | Result对象 | 删除指定的待办事项 |
| 完成待办事项 | POST | /user/todo/complete | TodoListRecord对象的JSON，Authorization头信息 | Result对象 | 标记待办事项为完成状态，并返回完成日期 |
| 添加预算 | POST | /user/budget | BudgetRecord对象的JSON，Authorization头信息 | Result对象 | 添加预算记录 |
| 删除预算 | DELETE | /user/budget | BudgetRecord对象的JSON，Authorization头信息 | Result对象 | 删除指定的预算记录 |
| 分页查询用户 | GET | /user/findByPage | 无需请求参数 | IPage | 分页查询用户信息，返回分页结果 |
