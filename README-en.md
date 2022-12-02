<div align=center>
    <h1>Translator for Multiplatform Compose</h1>
</div>

[![Kotlin Multiplatform](https://img.shields.io/static/v1?logo=Kotlin&&logoColor=FF9C27B0&label=&message=Kotlin%20Multiplatform&color=555)](https://kotlinlang.org/docs/reference/multiplatform.html)
[![Kotlin Multiplatform](https://img.shields.io/static/v1?logo=Jetbrains&&logoColor=4FC3F7&label=&message=Compose%20Multiplatform&color=555)](https://www.jetbrains.com/lp/compose-mpp/)
![](https://img.shields.io/hexpm/l/plug.svg)

### [中文](README.md) | English

The address of the Gitee repository：https://gitee.com/liang_dh/Translator-Compose-Multiplatform

#### This is a simple cross-platform translation software, built in Baidu translation and YouDao translation interface, in addition to the simple translation and weight reduction function.
- **Extreme startup speed🚀** No ads, no background services, out of the box, no need to discreetly click "skip AD" and wait half a day for the main interface card to be used.
- **The paper reduces the repetition rate function😊** Just a click of the button, you can get another way of writing a paragraph of text, reduce the weight of the translation of the number of times up to you! Check rate down to 10% per minute!
- **Streamlined interface**, cutting-edge technology, make Material Design style is no longer just a special feature of Android.

### Technology
This is a Kotlin cross-platform declarative UI front-end technology development project, this project includes the target platform is Android and Desktop.
The front-end pages are primarily implemented by Jetpack Compose and Compose for Desktop.
The network request related module is implemented by the Ktor client and KotlinSerialize.
The software architecture consists of Jetpack Compose's officially recommended MVI architecture.
- Compose Multiplatform - [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/)
- Android - [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Desktop - [Compose for Desktop](https://www.jetbrains.com/lp/compose-desktop/)
- Network - [Ktor Client](https://ktor.io/)


## The development environment
- The development tools： IntelliJ IDEA (2022.2.1) / Android Studio (Chipmunk)
- Gradle JVM Version: 11
- Project SDK: Java 17 (Oracle OpenJdk version 17.0.4)

## Known issues
- There is a problem with the Android release package, the debug package is fine, to be solved.
- When you package the computer side program, you need to change the gradle jvm version to 15 or higher and delete org.gradle.jvmargs=... This change to change is very troublesome, to be improved.
- If the Program is installed in the default C:/Program Files/, the file system will fail. You can copy the file to another directory and click exe to start it.


## Download
- Android  [Click here](release/android-debug.apk)
- Windows x64 Unzip it and run it straight away [Click here](release/win-x64%20解压即用.zip)
- Windows x64 Setup program (not recommended because it can cause problems when you install the file system on drive C) [Click here](release/win-x64%20安装程序，建议去下另一个.msi)

## Demo

### Android Platform

|         Click to translate          |        Use translation to reduce the repetition rate         |
|:-----------------------:|:-----------------------:|
| ![](images/安卓录制_翻译.gif) | ![](images/安卓录制_降重.gif) |

|          Switch translation engine/language         |       Related Settings and rich animations       |
|:---------------------------:|:-----------------------:|
| ![](images/安卓录制_切换翻译引擎.gif) | ![](images/安卓录制_设置.gif) |

#### Landscape display on Android devices
![img.png](images/安卓截屏_横屏.png)

### Desktop Platform
![img.png](images/电脑截图.png)

## Use to remind
1.For the first time, you need to configure "AppId" and "AppSecret" of the translation interface. If you do not know, you can register and create an application on the official website. The common translation interface is free. Click here to [YouDao ZhiYun](https://ai.youdao.com/) and [Baidu Translate Open Platform](http://api.fanyi.baidu.com/).

2.The "一件降重" function, which translates a piece of text into several other languages and then back to Chinese, may not work well, so you need to double check.

3.The computer will automatically adjust whether to use landscape or portrait based on the window size, and the Android will automatically adjust whether to use landscape or portrait. If you don't want to automatically adjust, you can specify which screen layout to use in the Settings.

### Finally, a small talk
kotlin is awesome, a set of languages can do almost anything, a ktor solution backend + client.
Now compose cross-platform technology is enabling kotlin to make client development outside of Android and on other platforms as well.
And the front-end and logic are also in the same language, no longer need to use java and xml to cooperate with traditional Android.
<br/>
<br/>
As a jvm language, kotlin can be said to be fully compatible with java, and the java ecosystem that has been built up over the past few decades is still alive and well in the kotlin era.
<br/>
<br/>
compose is a lot like flutter, both of which are frameworks developed by Google, and even compose is a framework developed by the flutter team and the Android team. The two front-end frameworks have their own features, and I'm really excited about kotlin because it seems to be a versatile language.
<br/>
<br/>
In the process of development also write a little diary, boring words can be [clicked in to see](docs/diary.md)

[//]: # (## Sponsored by the author)

[//]: # (If you think I have helped you, you can give me a little encouragement, thank the boss! &#40;You can include your name and Github address when you tip&#41;, any amount, to show your support.)

[//]: # ()
[//]: # (![]&#40;images/pay.jpg&#41;)

[//]: # ()
[//]: # (| Sponsors | Home                        |)

[//]: # (|----------|-----------------------------|)

[//]: # (| ldh      | https://github.com/ldh-star |)


## Contact information

Email: liang.dh@outlook.com

QQ: 2637614077


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