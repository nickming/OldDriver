# OldDriver
##整体结构
项目为总体结构为MVC模式,包名为**com.wennuan.olddriver**,其主体结构为一下几个包:
![项目结构](./screenshoot/struct.png)
* adapter:主要是列表的适配器存放位置
* base:主要是基类的包,包括项目的BaseActivity、BaseApplication等
* entity:POJO类,对应项目的实体
* ui:项目的主要ui界面、存放activity和fragment,根据模块可分为login、main、map、widget四个模块,各自对应
  显示的activity
* util:工具库,包括一些常用的工具类

##UI结构
###ui.main
为主要界面的包,其包括一下几个主要ui的界面
* chat:为聊天界面
* contact:通讯录界面
* discover:朋友圈界面
* mime:我的界面,包括设置、工资统计、收藏等界面
###ui.login
为登陆和注册界面,包括LoginFragment和SignUpFragment
###ui.map
为地图显示界面
###widget
为自定义控件包

##核心类库
* 登陆注册模块:运用了[LeanCloud的数据存储SDK](https://leancloud.cn/docs/leanstorage_guide-android.html#用户),使用了其用户模块的登陆与注册功能,详情
  直接参考文档说明.
* IM模块:主要运用了[LeanCloud的实时通讯SDK](https://leancloud.cn/docs/realtime_guide-android.html),基本上用到了单聊、会话、聊天记录几个模块的功能.
* 通讯录模块和朋友圈:运用了应用[LeanCloud应用内社交SDK](https://leancloud.cn/docs/status_system.html#Android_SDK),主要实现了好友关系,即通讯录模块,状态发送,即类似微博朋友圈的功能,详情参考文档。
* 地图模块:主要运用了[高德地图2D地图以及定位SDK](http://lbs.amap.com/api/android-sdk/summary/),可参考开发文档。
* 列表展示:运用了RecyclerView,以及开源项目[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)的支持
* 对话框:运用了[material-dialogs](https://github.com/afollestad/material-dialogs)开源项目的自持
* 事件通信:运用了[EventBus](https://github.com/greenrobot/EventBus)开源项目的支持,包括信息的交流、不同fragment和activity之间的通信以及总线事务管理。
* 线程事件:异步事务处理借助了RxJava和RxAndroid开源项目





