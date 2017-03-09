---
title: Java int 转 String
date: 2017-03-08 17:39:41
tags: Java int String
---

### int -> String
``` java
int i = 12345;
String s = "";
```
 1. 第一种方法：`s = i + "";`

 2. 第二种方法：`s = String.valueOf(i);`

> 第一种方法：`s = i + "";` 会产生两个 String 对象。
> 第二种方法：`s = String.valueOf(i);` 直接使用 String 类的静态方法，只产生一个对象。

<!--more-->
### String -> int
``` java
s = "12345";
int i;
```

 1. 第一种方法：`i = Integer.parseInt(s);`

 2. 第二种方法：`i = Integer.valueOf(s).intValue();`

> 第一种方法：`i = Integer.parseInt(s);`直接使用静态方法，不会产生多余的对象，但会抛出异常。
> 第二种方法：`i = Integer.valueOf(s).intValue();`，`Integer.valueOf(s)` 相当于 `new Integer(Integer.parseInt(s))`，也会抛异常，但会多产生一个对象。