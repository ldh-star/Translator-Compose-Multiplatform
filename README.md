<div align=center>
    <h1>Translator for Multiplatform Compose</h1>
</div>

[![Kotlin Multiplatform](https://img.shields.io/static/v1?logo=Kotlin&&logoColor=FF9C27B0&label=&message=Kotlin%20Multiplatform&color=555)](https://kotlinlang.org/docs/reference/multiplatform.html)
[![Kotlin Multiplatform](https://img.shields.io/static/v1?logo=Jetbrains&&logoColor=4FC3F7&label=&message=Compose%20Multiplatform&color=555)](https://www.jetbrains.com/lp/compose-mpp/)
![](https://img.shields.io/hexpm/l/plug.svg)

### 中文 | [English](README-en.md)

国内Gitee地址：https://gitee.com/liang_dh/Translator-Compose-Multiplatform

#### 一个简简单单的跨平台翻译小软件，内置了百度翻译和有道翻译的接口，除简单的翻译之外还有降重的功能。
- **极致的启动速度🚀** 没有任何广告，没有任何后台服务，即开即用，不再需要小心翼翼的点击”跳过广告“然后还要等主界面卡半天才能用。
- **大学牲落泪的一键降重功能😊** 只需按钮轻轻一点，就能得到一段文字的另一种写法，降重时翻译几次由您自己决定！查重率分分钟降至10%！
- **精简的界面**，前沿的技术，让Material Design风格不再只是安卓的专项。

### 技术相关
这是一个Kotlin跨平台声明式UI前端技术开发的项目，这个项目包含的目标平台是Android和Desktop。
前端页面主要是由Jetpack Compose 和 Compose for Desktop 实现。
网络请求相关模块由Ktor客户端 和 KotlinSerialize实现。
软件架构上采用 Jetpack Compose 官方推荐的MVI架构。
- Compose跨平台 - [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/)
- Android - [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Desktop - [Compose for Desktop](https://www.jetbrains.com/lp/compose-desktop/)
- 网络请求 - [Ktor Client](https://ktor.io/)


## 开发环境
- 开发工具： IntelliJ IDEA (2022.2.1) / Android Studio (Chipmunk)
- Gradle JVM Version: 11
- 项目SDK: Java 17 (Oracle OpenJdk version 17.0.4)

## 已知的问题
- 打包电脑端程序时，需要把 gradle jvm 的版本更改到15以上，并且在 gradle.properties 文件中删除 org.gradle.jvmargs=...，这样换来换去很麻烦，待改进。
- 程序在电脑端运行时，如果安装在默认的 C:/Program Files/ 中，文件系统会失效，你可以把文件复制到其他目录中然后点击exe启动。

## 下载体验
- Android  [点击此处下载](release/android-debug.apk)
- Windows x64 解压即用 [点击此处下载](release/win-x64%20解压即用.zip)
- Windows x64 安装程序（不建议用这个，因为安装到C盘下文件系统会出问题） [点击此处下载](release/win-x64%20安装程序，建议去下另一个.msi)

## 演示

### Android 平台

|         点击进行翻译          |        利用翻译一键降重         |
|:-----------------------:|:-----------------------:|
| ![](images/安卓录制_翻译.gif) | ![](images/安卓录制_降重.gif) |

|          切换翻译引擎/语言          |       相关设置以及丰富的动画       |
|:---------------------------:|:-----------------------:|
| ![](images/安卓录制_切换翻译引擎.gif) | ![](images/安卓录制_设置.gif) |

#### Android设备上的横屏显示
![img.png](images/安卓截屏_横屏.png)

### 电脑平台
![img.png](images/电脑截图.png)

## 使用提醒
1.首次使用需要先配置翻译接口的”AppId“和”AppSecret“，如果不知道可以去官网注册并创建应用，普通翻译接口都是免费的。点击此处 [有道智云](https://ai.youdao.com/) 和 [百度翻译开放平台](http://api.fanyi.baidu.com/) 。

2.“一键降重”功能其实就是把一段文字翻译成其他几种语言最后再翻译回中文，结果可能狗屁不通顺，自己得多检查一下。

3.电脑端会根据窗口大小、安卓端会根据横竖屏来自动调整使用横向布局还是竖向布局，如果不想要自动调整，可以在设置里指定使用哪一种屏幕布局。

### 最后闲谈吹牛瞎扯淡一下
kotlin太棒了，一套语言几乎可以完成任何事情，一个ktor解决后端+客户端。
现在的compose跨平台技术正在让kotlin走出安卓，在其他的平台上也能做客户端的开发。
并且前端和逻辑也是同一种语言，不再像传统安卓需要用java和xml相互配合。
<br/>
<br/>
作为一门jvm语言，kotlin可以说和java完全兼容，过去几十年来人们建立起的一套java生态在当今kotlin的时代来临之后依旧没有废弃。
<br/>
<br/>
compose和flutter很像，这两者都是谷歌研发出来的框架，甚至compose是由flutter研发团队和Android团队共同研发出来的，这两种前端框架各有特色，我还是很看好kotlin，似乎这门语言是万能的一样。
<br/>
<br/>
开发的过程中还一边写了点日记，如下：

<div align=center>
    <h3>本项目开发过程中的日记</h3>
</div>

## 2022年10月24日
该项目的前身是一个 Compose for Desktop 的项目，专门方便我在电脑上做翻译。
现在决定新开一个坑，把这个项目做成跨平台的，现在的目标是先做到 Desktop 和 Android 共用同一套代码。

#### 混合编译的时候遇到了好多问题啊
- 图片资源 在android中，读取图片资源是通过R.xxx.xxx来定位， painterResource(Int)传入的是图片资源的id，而desktop项目中所有资源是放在resource文件夹下的，定位直接用路径字符串，这两个会冲突。我暂时没有管怎么去解决冲突，而是导入了包icons-extended，用官方的包里的ImageVector。
- Desktop组件不能编译到安卓中 有一些组件是desktop特有的，比如Tooltip:鼠标放上去才会显示的组件。比如滑动条等等。这种问题可以只能安卓和desktop各写一个组件了，比如在这个组件中安卓是直接显示内容，而desktop端是显示内容加滑动条。
- 不知道的引用 有的模块里写不会自动导包进去（比如 common/androidMain 模块），不知道是bug还是什么，所以编译的时候会报错： The following declaration is incompatible because parameter types are different... 解决方式：手动把包导进去。
- 乱导包导致报错 在安卓编译时，发现如下报错：Duplicate class androidx.compose.ui.graphics.BlendMode found in modules ui-graphics-1.2.1-runtime (androidx.compose.ui:ui-graphics:1.2.1) and ui-graphics-desktop-1.2.0 (org.jetbrains.compose.ui:ui-graphics-desktop:1.2.0)，经过仔细检查后发现，原来是误将desktop端的依赖导入进整个项目的公共依赖里边去了，把desktop的依赖单独放在desktop的以来模块里即可。


总之，混合之后才发现需要解决的问题太多了，要包容不同平台的差异性，不过我认为这一项技术也有他自己的优点：
1. 前端和逻辑全部使用kotlin代码，真正实现一套代码多端复用。
2. 相比于flutter使用的dart语言，kotlin作为jvm语言有更好的生态，再java的基础上kotlin的语法和功能真的强大太多了。

电脑承受了它这个年纪不该承受的压力

![renwuguanliqi.png](images/renwuguanliqi.png)

## 2022年10月25日
这个项目一直报错
Caused by: java.lang.OutOfMemoryError: Java heap space
初步原因已经找到了，是导入了material-icons-extended这个库后会导致无法打包安卓。


17点31分： 嗨害嗨，终于解决啦。报错的原因是在导入图标拓展包时，编译安卓端会导致java堆内存不足，编译就会失败。

解决办法：在gradle.properties文件中加入以下代码修改内存大小
```
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=2048m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
```

一个一个依赖去排查，搞了一天多才搞出来，各种方法都试完了，终于弄出来了！！！呜呜呜~

最后这个项目的配置如下：
- 安卓sdk 33
- 项目jdk 17.0.4
- gradle(默认) jvm 11.0.16

## 2022年10月26日

为了解决安卓和desktop上的ui差异问题，我将组件进行了细分。并且在数据状态中新增了isLandscape变量，由这个变量来决定使用什么布局。

#### 19点51分
    之前很头疼的一个问题终于解决了！！~
- 如何显示资源文件中的图片？
  参考了官方的代码，将需要跨平台的资源文件用一个类似于安卓中R.java文件的写法定义出来:
```
object Res {
    object drawable {
        val icon_baidu = "drawable-nodpi/icon_baidu.png"
        val icon_youdao = "drawable-nodpi/icon_youdao.png"
    }
}
```
在电脑端的资源文件中直接按目录文件位置加进去，在安卓端如何获取呢？参考了官方的写法：
```
private fun drawableId(res: String): Int {
    val imageName = res.substringAfterLast("/").substringBeforeLast(".")
    val drawableClass = com.liangguo.common.R.drawable::class.java
    val field = drawableClass.getDeclaredField(imageName)
    return field.get(drawableClass) as Int
}
```
通过对资源名字反射的方法，把文件对应成对应id，把int值找出来。

太妙了，简直了！！！

安卓端基本也通了，并且也包含了电脑端的布局，当安卓手机切换到横屏的时候就会显示。这里我用了我自己开源的一个方向库[EasyingOrientation](https://github.com/ldh-star/EasyingOrientation)。

现在呢，我准备做本地数据持久化了，预计用File来实现，打印了手机端和电脑端的文件路径发现，安卓端的文件相对位置是从根目录开始算起，而电脑端的是
D:\Code Project\IdeaProject\Compose Multiplatform\Translator_Multiplatform\desktop\
从这个目录开始算起，有点奇怪。。。
这个问题还得想办法解决一下。
然后还得想一想做一套用户偏好系统，就用File去做，我得想想该怎么实现，现在已经是22点40分了，这些问题留到明天再说吧，再去力扣刷一道题就回去睡觉咯。


## 2022年10月27日

本地数据持久化算是做出来了，并且还开源了做成了一个依赖[KtPref](https://gitee.com/liang_dh/KtPref)。
基于java的文件系统来实现的。

电脑端打包的时候遇到了异常，要求gradle的jre环境版本在15以上，于是我将gradle的jre环境设置到了17，结果一运行又发现出问题了。
之前配置的修改gradle运行时jvm内存大小的配置跑不通了，于是又只能注释掉那行。

所以现在情况就是：在跑安卓的时候设置gradle的jre环境11，加上改内存的配置代码。在打包电脑程序的时候，gradle设置回17，注释掉加内存那行代码。

#### 讲一讲又新加了哪些东西。
- 新加了一个侧边栏菜单，在横屏和竖屏上有两种显示，竖屏的显示是侧滑栏来实现的。
- 主题切换已经实现了，并且改进了对其本地化储存的方式，以密封类作为设置项的枚举，以类名作为储存的内容和具体文件。
- 新增了控制按钮，用于方便调试。

今天就到这吧，现在是22点44分了，再写一道力扣就走咯。

## 2022年10月28日
今天本来想把几个模块的分页写了，但是compose multiplatform好像不支持安卓里的navigation。
哎，只能用状态来切换页面了呗。

今天又犯了一个低级错误，在编写ApiConfig.kt文件时，对于flow的collect我居然把几个collect函数放在了同一个协程launch块中，
collect函数会持续的收集flow的数据，所以会在协程中一直阻塞，这样一来就会导致只有第一个collect函数库能够响应，后面的几个都会失效。
我居然因为这个低级错误排查了半天是哪里的问题，差点还以为是我写的KtPref库有问题，害。

翻译接口配置功能已经写好了，23点45分了，提交一下代码就收工！

## 2022年10月29日
今天打包release包的时候又出问题了,不知道为啥。

现在又遇到了一个问题，Setting中的screenOrientation，在本类里边可以collect到，但是在ViewModel里面collect不到，感觉很奇怪，今天就到这里吧，明天再看看。

## 2022年10月30日
今天把这个屏幕切换的模块做好了，电脑端遇到了一个问题，在compose界面构造完成之前，如果对MutableState进行修改的时候，就会报错，这里也没太管，加了个try就完事了。
安卓的屏幕旋转重新弄好了，现在不是根据屏幕方向的重力响应来确定横屏还是竖屏，而是直接在Activity的onCreate的时候去设置，因为当进行横竖屏切换的时候Activity会经历onDestroy到重新onCreate。

## 2022年10月31日
今天在YouTube上看了大神的compose视频，又学到了一个新技巧：创建LocalProvider，像使用LocalContext.current一样。我们可以用这种方式去自定义我们主题里的一些内容，比如间距，然后可以用这个实现在跨平台上的一些尺寸的差异性。

今天还了解了一下compose性能优化相关的问题，
由于 ScopeUpdateScope 取决于我们对 State 的读取位置，因此，这就决定了我们可以使用 Kotlin 函数式编程当中的 Laziness 思想，对 Compose 进行「性能优化」。也就是让: 状态读取与使用位置一致，尽可能缩小「重组作用域」，尽可能避免「重组」发生。

#### 到此，这个项目也算是开发的差不多啦，功能基本都能用了，跨平台差不多撮合了一周。似乎也是感受到了compose这门技术似乎正在慢慢变成另一个flutter，不过相比于flutter，我更喜欢compose，kotlin这门语言真的很顶！！！

## 老板 vivo 50 支持一下

如果你觉得煮啵帮助到了你，可以给煮啵一点鼓励，感谢老板！（打赏时可以附上自己的大名和Github地址），金额随意，以表支持。

![](images/pay.png)

| 赞助者    | 主页                          |
|--------|-----------------------------|
| Hesley | https://github.com/ldh-star |

## 联系方式

Email: liang.dh@outlook.com

![](images/WechatIMG81.jpg)

## License

    Copyright 2021 Clément Beffa
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.