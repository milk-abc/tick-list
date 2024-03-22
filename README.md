## 前言

tick-list项目想要打造一个完整的清单管理系统，用户在上面创建自己待办的清单并进行管理。旨在让大家从冗杂的事情中脱离出来，用更少的时间去达成自己的目标。

## 项目文档

### 预览地址

项目已经部署在阿里云ECS上，可以在上面尝试使用，没有限制任何功能，大家随意就行（手机上显示不佳，建议大家使用电脑访问）。**第一次进入可能为有点慢，请大家耐心等待**。大家觉的还不错的话，欢迎给个star!

[腾讯云预览地址](http://1.117.235.168:3000)

## 项目介绍

tick-list项目是一个清单管理系统，包括前台系统使用及后台管理系统，基于SpringBoot+Vue实现。该系统前台包括登录页、首页、清单管理、反馈等模块。下面是每个模块具体的功能介绍：

### 登录页

- 登录页提供用户登录、注册以及修改密码的功能；
- 用户需提供帐号和密码，以及页面上显示的验证码即可成功登录，登录成功后页面跳转到首页；
- 为了保障用户密码的安全性，使用非对称加密对密码进行加密传输；

### 首页

- 首页主要以图表的形式，来展示用户最近一段时间对tick-list的使用情况。前端图表生成使用v-charts生成；
- 另外，首页也可以修改用户头像，用户可以上传本地图片来对头像进行修改；

### 添加清单

- 添加清单是tick-list的主要功能，用户输入清单名称和清单描述，选择清单所属分类和标签即可添加一个新的清单；
- 用户也可以选择页面上的+号，来对清单进行批量添加；

### 列表

- 列表里面主要包括了三个子模块：清单列表、分类列表和标签列表；
- 清单列表以分页的形式，来对当前用户未完成的清单进行展示。另外也提供了搜索功能，用户可以加入查询条件，来对清单进行搜索。
- 右键点击清单列表可以选择专注和计时，选择专注会进入倒计时25分钟。
- 分类列表主要展示了用户目前创建的分类，用户可以对分类进行添加和删除操作；
- 标签列表主要展示了用户目前创建的标签，用户可以对标签进行添加和删除操作；

### 反馈

- 反馈功能主要用来搜集用户的建议信息，用户输入反馈后，反馈信息会存储到数据库中，并以邮件的形式，提醒管理员对该反馈信息进行关注。

## 技术选型

### 前端技术

|    技术    |        说明        |                         官网                          |
| :--------: | :----------------: | :---------------------------------------------------: |
|    Vue     |      前端框架      |                  https://vuejs.org/                   |
| Vue-router |      路由框架      |               https://router.vuejs.org/               |
|  Element   |     前端UI框架     | [https://element.eleme.io](https://element.eleme.io/) |
|   Axios    |    前端HTTP框架    |            https://github.com/axios/axios             |
|  v-charts  |    前端图表生成    |              https://v-charts.js.org/#/               |
|  Iconfont  | 阿里巴巴矢量图标库 |               https://www.iconfont.cn/                |
