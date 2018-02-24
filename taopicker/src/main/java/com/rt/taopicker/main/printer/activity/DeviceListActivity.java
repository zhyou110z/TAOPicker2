package com.rt.taopicker.main.printer.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.main.printer.adapter.DeviceAdapter;
import com.rt.taopicker.main.printer.adapter.DeviceItem;
import com.rt.taopicker.main.printer.PrinterHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 蓝牙设备列表
 */
public class DeviceListActivity extends BaseActivity {
    private BluetoothAdapter mBtAdapter;

    private DeviceAdapter mPairedDevicesArrayAdapter;
    private DeviceAdapter mNewDevicesArrayAdapter;

    private List<DeviceItem> mPairedDevicesList = new ArrayList<>();
    private List<DeviceItem> mNewDevicesList = new ArrayList<>();

    private Button scanButton;
    private boolean isScaning = false;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    public static String EXTRA_DEVICE_NAME = "device_name";

    @Override
    public int getRootViewResId() {
        return R.layout.sys_bluetooth_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.lv_bonded_devices);
        mPairedDevicesArrayAdapter = new DeviceAdapter(this, true);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListView for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.lv_unbonded_devices);
        mNewDevicesArrayAdapter = new DeviceAdapter(this, false);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceConnectClickListener);

        scanButton = (Button) findViewById(R.id.btn_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isScaning){
                    isScaning = true;
                    doDiscovery(); //扫描设备

                    scanButton.setText("正在扫描中......");
                }
            }
        });

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        this.registerReceiver(mReceiver, filter);

        initPairedDevicesList();
    }

    private void initPairedDevicesList(){
        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            mPairedDevicesList.clear();
            for (BluetoothDevice device : pairedDevices) {
                DeviceItem item = new DeviceItem();
                item.setName(device.getName());
                item.setAddress(device.getAddress());
                item.setDevice(device);
                mPairedDevicesList.add(item);
            }
            mPairedDevicesArrayAdapter.setData(mPairedDevicesList);
            mPairedDevicesArrayAdapter.notifyDataSetChanged();
        } else {
            mPairedDevicesList.clear();
            DeviceItem item = new DeviceItem();
            item.setName("没有配对");
            item.setAddress("");
            item.setNull(true);
            mPairedDevicesList.add(item);
            mPairedDevicesArrayAdapter.setData(mPairedDevicesList);
            mPairedDevicesArrayAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        ToastUtil.toast("正在扫描中......");

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    @Override
    public IBasePresenter getPresenter() {
        return null;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DeviceListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * 连接
     */
    private OnItemClickListener mDeviceConnectClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mBtAdapter.cancelDiscovery();

            DeviceItem item = (DeviceItem) adapterView.getItemAtPosition(i);
            BluetoothDevice device = item.getDevice();
            if(device != null){
                Method createBondMethod;
                try {
                    createBondMethod = BluetoothDevice.class
                            .getMethod("createBond");
                    createBondMethod.invoke(device);

                    ToastUtil.toast("正在连接中......");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    // The on-click listener for all devices in the ListViews
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        @Override
		public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // Cancel discovery because it's costly and we're about to connect
            mBtAdapter.cancelDiscovery();

            DeviceItem item = (DeviceItem) adapterView.getItemAtPosition(i);
            BluetoothDevice device = item.getDevice();
            if(device != null){
                PrinterHelper.getInstance().setSelectDeviceItem(device);
                ToastUtil.toast("连接成功");
//                // Create the result Intent and include the MAC address
//                Intent intent = new Intent();
//                intent.putExtra(EXTRA_DEVICE_ADDRESS, item.getAddress());
//                intent.putExtra(EXTRA_DEVICE_NAME, item.getName());
//
//                // Set result and finish this Activity
//                setResult(Activity.RESULT_OK, intent);
                finish();
            }

        }
    };

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if(StringUtil.isNotBlank(device.getName())){
                        if(mNewDevicesList.size() == 1 && mNewDevicesList.get(0).getNull()){
                            mNewDevicesList.clear();
                        }

                        DeviceItem item = new DeviceItem();
                        item.setName(device.getName());
                        item.setAddress(device.getAddress());
                        item.setDevice(device);
                        mNewDevicesList.add(item);
                        mNewDevicesArrayAdapter.setData(mNewDevicesList);
                        mNewDevicesArrayAdapter.notifyDataSetChanged();
                    }
                }
            // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (mNewDevicesList.size() == 0) {
                    DeviceItem item = new DeviceItem();
                    item.setName("没有找到设备");
                    item.setAddress("");
                    item.setNull(true);
                    mNewDevicesList.add(item);
                    mNewDevicesArrayAdapter.setData(mNewDevicesList);
                    mNewDevicesArrayAdapter.notifyDataSetChanged();
                }
                scanButton.setText("扫描");
                isScaning = false;
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){ // 远程设备的连接状态改变
                BluetoothDevice stateChangeDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.e(Constant.PRINT_TAG, "蓝牙设备的状态" + stateChangeDevice.getBondState());
                switch (stateChangeDevice.getBondState()) {
                    case BluetoothDevice.BOND_BONDING:
                        ToastUtil.toast("正在配对......");
                        Log.e(Constant.PRINT_TAG, "正在配对......");
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        ToastUtil.toast("完成配对");
                        Log.e(Constant.PRINT_TAG, "完成配对");
                        initPairedDevicesList();
                        break;
                    case BluetoothDevice.BOND_NONE:
                        ToastUtil.toast("取消配对");
                        Log.e(Constant.PRINT_TAG, "取消配对");
                        break;
                    default:
                        break;
                }
            }
        }
    };

}
