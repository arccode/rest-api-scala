## 背景

1. 使用纯`Rest`风格定义接口
2. 像搭积木一样开发分布式业务
3. 实战响应式架构
4. 异步非阻塞
5. 分布式/集群

## 名词解释

* swagger: 文档工具,  可生成规范的Http接口调用文档.
* flyway: 数据迁移工具, 可管理sql脚本的版本及升级, 同时支持`JDBC`.

## 快速运行项目代码

### 运行前准备

1. JDK >= 1.8
2. Scala >= 2.12.2
3. SBT = 0.13.16

### 新建数据库

```
# 命令行连接db
mysql -h 127.0.0.1 -u root -p
# 新建utf8字符集的database
create database `rest_api_scala` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

### 启动项目

目前项目的`sql`脚本使用`flyway`进行管理和维护, 当前项目中内置了建表语句, 因此只需要修改项目配置中DB连接的用户名和密码即可.

1. 修改`application.conf`, 将`flyway`和`mysql`节点中的用户名密码修改为本地DB的用户名和密码
2. 命令行切换进入项目根目录, 执行指令`sbt run`; 待出现`启动成功`字样时项目即启动成功

### 访问swagger接口API

```
# 在浏览器中键入如下url
http://127.0.0.1:9100/rest_api_scala/swagger
```

### 针对各种环境打包

```
# 测试环境
sbt testPackage/universal:packageBin
```
在项目目录下`build/test/target/universal`可找到打包完成的`api-rest_test_0.0.2_build20180419.zip`包.


预发布环境/生产环境, 按照build.sbt中 testPackage 子模块规则进行编写即可.

### 运行生成包

#### 前台运行

```
# 解压
unzip api-rest_test_0.0.2_build20180419.zip
cd api-rest_test_0.0.2_build20180419
chmod +x ./bin/testpackage
./bin/testpackage
```

#### 后台运行

```
# 解压
unzip api-rest_test_0.0.2_build20180419.zip
cd api-rest_test_0.0.2_build20180419
chmod +x ./bin/testpackage
nohup ./bin/testpackage&
```

## 版本发布历史

### v0.0.1

初始化项目, 提供完整的基础架构和编码规范.

* 路由分散-聚合模式
* 解决CORS问题
* 接口采用纯`restful`架构
* 自定义各种`restful`异常类, 统一处理异常并返回优雅的响应码和消息
* 使用`swagger`描述接口文档, 并在文档中可对接口进行调用测试
* 使用`flyway`管理`sql`脚本

### v0.0.2

增加通用业务, 获取区域信息接口.

### v0.0.3

* 支持分布式调用, 同时提供scala和java版的客户端
	* akka-remote-client-scala: 
	* akka-remote-client-java: 
* 支持`tell`和`ask`两种调用模式, 推荐使用`tell`模式
* 弃用默认的`Java Serializer`序列化, 使用twitter `chill-akka`进行序列化和反序列化


	