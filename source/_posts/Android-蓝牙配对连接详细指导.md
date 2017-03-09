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
 1. 获取已配对的设备

 对于之前已经配对成功的设备，系统会把它的信息存储在本地。再去调用搜索的时候，系统是不会重新再次发现这个设备的。
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
### 连接
### 一长串完整代码
``` java 
public class BluetoothDeviceListActivity extends Activity {
    private static final String TAG = "DeviceListActivity";
    public static String EXTRA_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private static final int REQUEST_ENABLE_BT = 3;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothA2dp mA2dpService;
    private BluetoothHeadset mHeadsetService;

    private MapList<String, DeviceItem> mPairedDevicesMap;
    private MapList<String, DeviceItem> mNewDevicesMap;
    private BluetoothDeviceAdapter mPairedDevicesAdapter;
    private BluetoothDeviceAdapter mNewDevicesAdapter;

    private TextView mTitle;
    private boolean isInitReceiver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_device_list);
        setResult(Activity.RESULT_CANCELED);
        mTitle = (TextView) findViewById(R.id.devices_title);

        //开启蓝牙
        if (openBluetooth()) {
            //获取Profile实例
            getProfileProxy();
        }
    }

    protected void setThisTitle(int resid) {
        mTitle.setText(resid);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
        registerReceiver(mReceiver, intentFilter);
        isInitReceiver = true;
    }

    private boolean openBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            finish();
            return false;
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                return false;
            } else {
                return true;
            }
        }
    }

    private void getProfileProxy() {
        mBluetoothAdapter.getProfileProxy(this, new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int profile, BluetoothProfile proxy) {
                if (mA2dpService == null) {
                    mA2dpService = (BluetoothA2dp) proxy;
                    initView();
                }
            }

            @Override
            public void onServiceDisconnected(int profile) {
            }
        }, BluetoothProfile.A2DP);

        mBluetoothAdapter.getProfileProxy(this, new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int profile, BluetoothProfile proxy) {
                if (mHeadsetService == null) {
                    mHeadsetService = (BluetoothHeadset) proxy;
                    initView();
                }
            }

            @Override
            public void onServiceDisconnected(int profile) {
            }
        }, BluetoothProfile.HEADSET);
    }

    private void initView() {
        if (mA2dpService != null && mHeadsetService != null) {
            //注册广播接收器
            initReceiver();

            //实例化两个List
            initPairedView();
            initNewView();
        }
    }

    private void initPairedView() {
        mPairedDevicesMap = new MapList<>();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                if (checkName(device.getName())) {
                    mPairedDevicesMap.add(device.getAddress(), toDeviceItem(device));
                }
            }
        }

        mPairedDevicesAdapter = new BluetoothDeviceAdapter(this, mPairedDevicesMap);
        mPairedDevicesAdapter.setOnItemClickListener(mDeviceClickListener);
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesAdapter);
    }

    private void initNewView() {
        // Initialize the button to perform device discovery
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
                v.setVisibility(View.GONE);
            }
        });

        mNewDevicesMap = new MapList<>();

        mNewDevicesAdapter = new BluetoothDeviceAdapter(this, mNewDevicesMap);
        mNewDevicesAdapter.setOnItemClickListener(mDeviceClickListener);
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesAdapter);
    }

    private DeviceItem toDeviceItem(BluetoothDevice device) {
        DeviceItem item = new DeviceItem();
        item.setDevice(device);
        item.setName(device.getName());
        item.setAddress(device.getAddress());
        item.setBondState(device.getBondState());
        item.setA2dpProfileState(mA2dpService.getConnectionState(device));
        item.setHeadsetProfileState(mHeadsetService.getConnectionState(device));
        return item;
    }

    /**
     * 配对
     */
    private void pairDevice(DeviceItem item) {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        //如果设备没有绑定
        if (item.getBondState() == BluetoothDevice.BOND_NONE) {
            BluetoothDevice device = item.getDevice();
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
        } else if (item.getBondState() == BluetoothDevice.BOND_BONDED) {
            connectDevice(item);
        }
    }

    /**
     * 连接
     * 最主要连接A2dp协议，Headset附带
     */
    private void connectDevice(DeviceItem item) {
        if (item.getBondState() == BluetoothDevice.BOND_BONDED) {
            //如果A2dp Profile 没有连接的情况
            if (item.getA2dpProfileState() == BluetoothProfile.STATE_DISCONNECTED) {
                //API 不开放连接 Profile 的接口，利用反射调用连接方法
                try {
                    mA2dpService.getClass().getMethod("connect", BluetoothDevice.class)
                            .invoke(mA2dpService, item.getDevice());

                    mHeadsetService.getClass().getMethod("connect", BluetoothDevice.class)
                            .invoke(mHeadsetService, item.getDevice());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (item.getA2dpProfileState() == BluetoothProfile.STATE_CONNECTED) {
                returnTheMac(item);
            }
        }
    }

    private void returnTheMac(DeviceItem item) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DEVICE_ADDRESS, item.getAddress());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    //获取Profile实例
                    getProfileProxy();
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (isInitReceiver) {
            unregisterReceiver(mReceiver);
        }

        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();

            mBluetoothAdapter.closeProfileProxy(BluetoothProfile.A2DP, mA2dpService);
            mBluetoothAdapter.closeProfileProxy(BluetoothProfile.HEADSET, mHeadsetService);
            mA2dpService = null;
            mHeadsetService = null;
        }
        super.onDestroy();
    }

    /**
     * 开始搜索设备
     */
    private void doDiscovery() {
        setThisTitle(R.string.scanning);

        // Turn on sub-title for new devices
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
    }

    private boolean checkName(String name) {
        return null != name && name.startsWith("T100");
    }

    /**
     * The on-click listener for all devices in the ListViews
     */
    private BluetoothDeviceAdapter.OnItemClickListener mDeviceClickListener =
            new BluetoothDeviceAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DeviceItem item) {
                    pairDevice(item);
                }
            };


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //发现新设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (checkName(device.getName())) {
                        mNewDevicesAdapter.add(device.getAddress(), toDeviceItem(device));
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //搜索结束
                if (mNewDevicesMap.getSize() == 0) {
                    setThisTitle(R.string.none_found);
                } else {
                    setThisTitle(R.string.select_device);
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                //设备绑定状态改变
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                DeviceItem item = null;

                if (mPairedDevicesMap.getItem(device.getAddress()) != null) {
                    item = mPairedDevicesMap.getItem(device.getAddress());
                    item.setBondState(bondState);
                    mPairedDevicesAdapter.notifyDataSetChanged();
                } else if (mNewDevicesMap.getItem(device.getAddress()) != null) {
                    item = mNewDevicesMap.getItem(device.getAddress());
                    item.setBondState(bondState);
                    mNewDevicesAdapter.notifyDataSetChanged();
                }

                //收到绑定成功的通知后自动连接
                if (item != null && bondState == BluetoothDevice.BOND_BONDED) {
                    connectDevice(item);
                }
            } else if (BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                //A2dp连接状态改变
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int profileState = intent.getIntExtra(BluetoothProfile.EXTRA_STATE, BluetoothProfile.STATE_DISCONNECTED);
                DeviceItem item = null;

                if (mPairedDevicesMap.getItem(device.getAddress()) != null) {
                    item = mPairedDevicesMap.getItem(device.getAddress());
                    item.setA2dpProfileState(profileState);
                    mPairedDevicesAdapter.notifyDataSetChanged();
                } else if (mNewDevicesMap.getItem(device.getAddress()) != null) {
                    item = mNewDevicesMap.getItem(device.getAddress());
                    item.setA2dpProfileState(profileState);
                    mNewDevicesAdapter.notifyDataSetChanged();
                }

                //收到连接成功的通知
                if (item != null && profileState == BluetoothProfile.STATE_CONNECTED) {
                    returnTheMac(item);
                }
            }
        }
    };
}
```