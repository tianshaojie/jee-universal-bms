# jee-universal-bms
 * J2EE通用后台管理系统

## 功能清单

* spring boot
* javaConfig，零配置
* yml替代properties
* mybatis-generator:generate，自动生产entity，mapper，xml
* 数据库设计，用户、角色、功能管理
* shiro，认证、授权，基于redis的session共享和授权cache信息共享
* static html + restful，前后端分离，html+ajax开发方式
* index,403,404,500配置
* 日志存储
* 自动化脚本：打包、上传、启动，切换环境
* 优化登录页


## jquery-easyui 优化UI和体验

* easyui-顶部区，增加当前用户和换肤展示
* easyui-导航栏，增加树结构，支持多级菜单导航，可设置图标
* easyui-工作区，增加tab标签，可设置图标
* easyui-导航栏，根据当前用户获取权限菜单，ajax获取菜单权限，js点击导航菜单到工作区，js工作区tab打开、刷新、关闭
* easyui-搜索框+表单，统一样式处理，filedset，form, table
* easyui-datagrid，自动高度


## 写更少的代码

* bean && map转换工具类
* 扩展easyui validateBox
* mybatis mapper自动生成
* mybatis分页查询
* DataGrid分页映射
* search表单样式
* save表单样式
* 使用from 序列化
* 使用validatebox，很少代码完成表单输入验证
* 优化easyui默认项，减少初始化代码
* common.js
* common.css

## 默认规则

* 10以下user id为初始化
* Constants定义
* 字段status等标识意义的值从1开始


## 启动

1. 配置数据库，初始化service/resources/sql/application.sql
2. 本地启动redis
3. 修改main/deploy/application.yml
4. 把main/deplay，main/deploy/config加到工程的dependendies
5. run main BmsApplication.java main方法即可启动




