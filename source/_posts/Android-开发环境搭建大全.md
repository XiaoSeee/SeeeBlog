---
title: Android 开发环境搭建大全
date: 2020-07-20 17:07:49
tags: Android 环境
---
记录一下Android开发环境的配置，免得每次Google。
<!--more-->

### Java
1.下载安装JDK：[下载](https://www.oracle.com/cn/java/technologies/javase/javase-jdk8-downloads.html)
2.设置环境变量`JAVA_HOME`：
```
C:\Program Files\Java\jdk1.8.0_201
```

### Android Studio
Android Studio的配置文件默认在 C 盘会占用相当大的空间，我们需要把它迁移走（下文中所有的 X 均代表盘符）。
1.新建文件夹`X:\Android\setting`
2.然后把 C 盘目录里的`idea.properties`文件拷贝到这里，并且添加两行配置文件：
```
idea.config.path=X:/Android/setting/.AndroidStudio/config
idea.system.path=X:/Android/setting/.AndroidStudio/system
```
3.设置环境变量`STUDIO_PROPERTIES`：
```
X:\Android\setting\idea.properties
```

### SDK
新建一个目录`F:\Android\sdk`并在Android Studio里面把SDK的目录设置到这里就可以了，比较简单（注意目录不要有空格和中文字符）。

### AVD
模拟器默认也在 C 盘，同理也需要迁移。
1.新建文件夹`X:\Android\setting`
2.设置环境变量`ANDROID_SDK_HOME`：
```
X:\Android\setting
```

### Gradle
Gradle 的缓存文件也在 C 盘，同理也需要迁移。
1.新建文件夹`X:\Android\setting\.gradle`
2.Android Studio 设置 Settings->Build->Gradle->Gradle user home:
```
X:/Android/setting/.gradle
```
2.设置环境变量`GRADLE_USER_HOME`
```
X:\Android\setting\.gradle
```