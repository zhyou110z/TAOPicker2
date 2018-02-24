package com.rt.taopicker.main.printer.adapter;

import android.bluetooth.BluetoothDevice;

/**
 * Created by wangzhi on 2018/2/5.
 */

public class DeviceItem {
    private String name;
    private String address;
    private BluetoothDevice device;

    //是否是空白提示
    private Boolean isNull = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public Boolean getNull() {
        return isNull;
    }

    public void setNull(Boolean aNull) {
        isNull = aNull;
    }
}
