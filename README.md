# Someget-admin

[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

简体中文 | [English](./README-en-US.md)

一个开箱即用管理后台

基于Layui+Spring Boot完成的开箱即用后台应用(前后端一体)，你只需要做少量改动就可完成基本的数据增删改查，方便小团队/组织快速搭建数据展示后台程序。

本仓库包含以下内容：

1. 基于Layui2.5.6+Freemarker前端页面。
2. 基于Spring Boot2.6.7搭建的后端程序。
3. 手把手教你如何使用的Readme

## 内容列表

- [背景](#背景)
- [在线演示地址](#在线演示地址)
- [演示介绍](#演示介绍)
- [安装](#安装)
- [使用说明](#使用说明)
- [如何启动](#如何启动)
- [如何定制](#如何定制开发)
- [维护者](#维护者)
- [如何贡献](#如何贡献)
  - [贡献者](#贡献者)

- [使用许可](#使用许可)

## 背景

`someget-admin` 最开始因为原来在工作中遇到过有数据要展示给运营看，运营每次只能发消息问研发，需要看xxx数据，然后我们查询给运营。所以有一个后台给运营看修改一些配置信息或者展示一些数据，就可以解放研发每天做查数菇的烦恼，但是因为当时就职的公司资源比较有限，没办法给到前端人力。但是就打算自己开(co)发(py)一个，纵观Github上有非常多后台库，可是都非常的重，当然哈，都非常优秀。可是功能太多了，对于我的使用场景来说我只需要简单的增删改查，最多用到鉴权有点大材小用，毕竟虽然是copy还是要在基础上进行修改，意味着需要熟悉代码，代码越多可能功能更加丰富但是上手成本也会增加。

当时就想开发一个开箱即用，功能就是只有增删改查一些数据的后台，**一方面我觉得可能会有和我一样同样需求的人，另外一方面有了简单的功能为基础后面如果需要实现复杂的功能都还是可以快速迭(co)代(py)的**，所以我觉得创建这样一个库还是蛮有意义的。

在即便是Vue大行其道的如今，我实在不愿意启动两个项目开发，分成两个项目部署，因为本库首要目标是开箱即用，所以我当时选型直接Layui+Freemarker，说来惭愧本来计划21十一放假就打算开发，辣个时候正好贤心正好宣布Layui官网在2021年10月13日下线迁移到GitHub，本来想趁机再打一波感情牌...结果直接拖到了22五一....

这个仓库的目标是：

1. 开箱即用：简单但是不简陋，欢迎您提 Issue 讨论其中的变化。
2. 轻松迭代：代码SonarLint告警率低于1%，代码遵循P3C，注释翔实。
3. 与时俱进：先进但是稳定，虽然采用前后端一体开发，但是所依赖的包都是最新release。

## 在线演示地址

待更新



## 演示介绍

待更新



## 安装

这个项目使用 [java8](https://java.com/en/download/manual.jsp)  请检查

```sh
$ java -version
```

然后把项目download下来

```sh
$ git clone git@github.com:oreoft/layui-someget-admin.git
```



## 使用说明

1. 使用的技术栈
   1. 前端：Layui 2.5.6+Freemarker
   2. 后端：SpringBoot 2.6.7
   3. 鉴权：shiro 1.8
   4. ORM：Mybatis Plus 3.5.1
   5. 其他：中间件redis+mysql+
2. 目录结构

<img src="https://mypicgogo.oss-cn-hangzhou.aliyuncs.com/tuchuang20220503134330.png" alt="image-20220503134329983" style="zoom: 33%;" />

```sh
├── common # 这包下面都是通用
│   ├── base
│   ├── config
│   ├── exception
│   ├── realm
│   ├── support
│   ├── sys
│   │   ├── dal # 系统的dal层
│   │   │   ├── entity
│   │   │   │   └── vo
│   │   │   └── mapper
│   │   ├── facade # controller
│   │   ├── freemark 
│   │   └── service # 后台系统的业务实现(账号、菜单、鉴权相关)
│   │       └── impl
│   └── util
├── dal # 你自己数据的dal
│   ├── entity
│   └── mapper
├── facade # 你自己数据的controoler
│   └── veiw
├── model # 你自己数据的pojo
│   ├── constant
│   ├── dto
│   ├── enums
│   └── vo
└── service # 你自己数据的业务
    └── impl
```



## 如何启动

1. 请更换配置信息，配置采用yml，并且默认自带两个环境，dev和prod。因为比较简单所以基本所有的配置都在通用配置application.yml里面，只有少数中间件连接信息在-dev和-prod文件中。

   ![](https://mypicgogo.oss-cn-hangzhou.aliyuncs.com/tuchuang20220503140229.png)

先把下面的配置信息都更换成自己的，因为我开发都没有用localhost所以我把我自己的信息都脱敏了上传到git了，你们要自行替换一下

<img src="https://mypicgogo.oss-cn-hangzhou.aliyuncs.com/tuchuang20220503140302.png" alt="image-20220503140302494" style="zoom: 33%;" />

2. 创建数据库和对应初始化sql

<img src="https://mypicgogo.oss-cn-hangzhou.aliyuncs.com/tuchuang20220503140435.png" alt="image-20220503140435075" style="zoom: 25%;" />

找到resources下面sql文件夹，然后把里面的admin所有sql都执行一下(按顺序)，里面有DDL和DML。这是初始化数据，里面含有管理员角色信息和相关系统菜单，有了这个初始化数据就可以在GUI界面进行操控数据了。

**注意，**数据表默认要创建在admin库下面，如果你不想创建admin库，请记得修改下图位置的链接信息

![image-20220503140914991](https://mypicgogo.oss-cn-hangzhou.aliyuncs.com/tuchuang20220503140915.png)

**初始化管理员账户/密码是root/123456，have fun**

## 如何定制开发

等有空再更新

## 维护者

[@Oreoft](https://github.com/oreoft)

## 如何贡献

非常欢迎你的加入！[提一个 Issue](https://github.com/oreoft/layui-someget-admin/issues/new) 或者提交一个 Pull Request。


标准 Readme 遵循 [Contributor Covenant](http://contributor-covenant.org/version/1/3/0/) 行为规范。

### 贡献者

等有了100个start才能显示


## 使用许可

[MIT](LICENSE) © Oreoft
