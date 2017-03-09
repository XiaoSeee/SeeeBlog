---
title: Android 蓝牙配对连接详细指导
date: 2017-03-09 16:10:53
tags: Android 蓝牙 配对 连接
---
公司的项目中需要连接一个蓝牙耳机的功能。Android 并没有开放配对连接耳机的接口，而且网上大部分资料都是讲解如何连接蓝牙4.0的，很少有资料详细介绍蓝牙2.0相关的。自己先是花了一些时间看系统的蓝牙设置代码，期间还是踩了不少坑才摸索出解决办法。所有把我自己摸索总结出来的经验梳理记录下，以便备份。
<!--more-->

### 本文适用的范围
Android蓝牙部分是很复杂的，也涉及很多名词和功能。本文介绍的配对连接方法适用于一般的蓝牙耳机、音响等，并不是连接蓝牙 BLE 或者想用蓝牙来进行 Socket 通信的。

先来介绍几种名称：
 1. Profile：
 > Bluetooth 的一个很重要特性，就是所有的 Bluetooth 产品都无须实现全部的 Bluetooth 规范。为了更容易的保持 Bluetooth 设备之间的兼容，Bluetooth 规范中定义了 Profile。Profile 定义了设备如何实现一种连接或者应用，你可以把 Profile 理解为连接层或者应用层协。我们标题中的说的连接其实就是去连接各种 Profile。下面介绍的几种都是Android 实现了的 Profile。
 2. A2dp：
 > 表示蓝牙立体声，和蓝牙耳机听歌有关那些，另还有个`Avrcp`音频/视频远程控制配置文件，是用来听歌时暂停，上下歌曲选择的。
 3. Handset、Handfree：
 > 和电话相关，蓝牙接听、挂断电话。
 4. 其他：
 > `btservice`关于蓝牙基本操作的目录，一切由此开始； `hdp`蓝牙关于医疗方面的应用；`hid`：人机交互接口，蓝牙鼠标键盘什么的就是这个了 ；`pbap`：电话号码簿访问协议(Phonebook Access Profile) ...

### 准备
 1. 在 AndroidManifest.xml 添加所需的权限
 ``` xml
 <uses-permission android:name="android.permission.BLUETOOTH" />
 <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
 ```
 2. 打开蓝牙
 ``` java
 mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
 if (!mBluetoothAdapter.isEnabled()) {
    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
 }
 ```
 3. 注册广播

 由于蓝牙的搜索、配对和连接状态的改变都是系统通过广播的方式发出来的，所以需要注册这些广播来获取状态的改变。
 ``` java
 IntentFilter intentFilter = new IntentFilter();
 intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
 intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
 intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
 intentFilter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);
 intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
 registerReceiver(mReceiver, intentFilter);
 ```

### 搜索
 1. 获取已配对的设备。对于之前已经配对成功的设备，系统会把它的信息存储在本地。再去调用搜索的时候，系统是不会重新再次发现这个设备的。
 ``` java
 Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
 ```
 2. 搜索设备
 ``` java
 mBluetoothAdapter.startDiscovery();
 ```
 系统发现新的蓝牙设备了之后，会通过广播把这个设备的信息发送出来。所以我们要通过截获 Action 为`BluetoothDevice.ACTION_FOUND`的 Intent，并得到设备信息。
 ``` java
 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
 ```

### 配对
重点来来了，做了一系列准备工作，拿到了`BluetoothDevice`下面就要开始配对连接了。但是坑的地方也在这里，首先蓝牙设备必须要先配对成功了再去连接各个不同的 Profile，如果直接去连接有的机型确实也可以连上，但是大部分的都没反应。然后就是 Android 4.4 API 19 以上才开放配对接口，对于之前的系统我们只能通过反射的方式去获取接口。

 1. 配对
``` java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    //Android 4.4 API 19 以上才开放Bond接口
    device.createBond();
} else {
    //API 19 以下用反射调用Bond接口
    try {
        device.getClass().getMethod("connect").invoke(device);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

 2. 配对成功会发送广播`BluetoothDevice.ACTION_BOND_STATE_CHANGED`
``` java
//设备绑定状态改变
BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
//收到绑定成功的通知后自动连接
if (item != null && bondState == BluetoothDevice.BOND_BONDED) {
    connectDevice(item);
}
```

 3. 配对的几种状态
``` java
    public static final int BOND_NONE = 10;
    public static final int BOND_BONDING = 11;
    public static final int BOND_BONDED = 12;
```
### 连接
配对（绑定）和连接是两个不同的过程，配对是指两个设备发现了对方的存在，可以获取到对方的名称、地址等信息，有能力建立起连接。连接是指两个设备共享了一个 RFCOMM 通道，有能力进行数据互传。确认绑定上了之后，才能开始连接，连接其实就是连接这个蓝牙设备支持的 Profile 。

可以观察一下设置里面蓝牙连接的过程过程，就是先开始配对，配对成功后才开始连接所有支持的 Profile。这一步也是比较坑的地方，网上都没有详细的对这一块说明。我也是在 Setting 的源码里面翻了半天才找到这块的逻辑。但是系统应用可以直接调用连接的方法，却不外开放...

 1. 首先我们要提前获取 Profile，这里拿A2dp来举例，其他的原理是一样的。
``` java
        mBluetoothAdapter.getProfileProxy(this, new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int profile, BluetoothProfile proxy) {
                if (mA2dpService == null) {
                    mA2dpService = (BluetoothA2dp) proxy;
                }
            }

            @Override
            public void onServiceDisconnected(int profile) {
            }
        }, BluetoothProfile.A2DP);
```
 2. 当我们收到配对成功的广播或者确定设备已经配对成功后，我们就要调用 Profile 的`connect`方法来连接。但是这个方法被 Google 给`@hide`了。像上面一样用反射...
``` java
    try {
        mA2dpService.getClass().getMethod("connect", BluetoothDevice.class)
                .invoke(mA2dpService, item.getDevice());
    } catch (Exception e) {
        e.printStackTrace();
    }
```

 3. 连接成功系统会发送广播`BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED`
``` java
BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
int profileState = intent.getIntExtra(BluetoothProfile.EXTRA_STATE, BluetoothProfile.STATE_DISCONNECTED);
```

 4. 连接的几种状态
``` java
    /** The profile is in disconnected state */
    public static final int STATE_DISCONNECTED  = 0;
    /** The profile is in connecting state */
    public static final int STATE_CONNECTING    = 1;
    /** The profile is in connected state */
    public static final int STATE_CONNECTED     = 2;
    /** The profile is in disconnecting state */
    public static final int STATE_DISCONNECTING = 3;
```

### 坑坑坑
哈哈，你以为连接上了就完事了吗？！这里面还有几个坑容我给你说说。

 1. 不要忘记关闭 Profile。我们为了连接不是获取了 Profile 吗，在连接完成之后一定要关闭掉他，一直不关闭的话会报错。
 ``` java
 mBluetoothAdapter.closeProfileProxy(BluetoothProfile.A2DP, mA2dpService);
 mA2dpService = null;
 ```
 2. 蓝牙连接成功了之后系统会通知手机状态发生了改变，就像切换了横竖屏一样，需要重新调用这个 Activity 的生命周期。当时我发现只要我一连接成功，我的界面就闪一下回到初始状态。就纳闷了半天，然后我一直以为是用反射连接导致程序异常重启了...几经摸索我发现是没有设置`android:configChanges`的缘故。
``` xml
android:configChanges="keyboard|keyboardHidden|navigation"
```
 其实前面两个属性我也早想到了，唯独最后一个，在官方文档里面写的`This should never normally happen.`我天真的相信了，一直没试它。最后实在没办法了把所有的属性都写上去，然后一个个减，最终发现了这三少一个都不行。

| 值              | 说明   |
| --------        | :-----  |
| "keyboard"        |键盘类型发生了变化 — 例如，用户插入了一个外置键盘。|
| "keyboardHidden"  |键盘无障碍功能发生了变化 — 例如，用户显示了硬件键盘。|
| "navigation"      |导航类型（轨迹球/方向键）发生了变化。（这种情况通常永远不会发生。）|