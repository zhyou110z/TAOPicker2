package com.rt.taopicker.data.api.entity.printOrderDetailsRespEntity;

import com.rt.taopicker.base.BaseEntity;

import java.util.List;

/**
 * Created by wangzhi on 2018/2/2.
 */

public class PrintOrderDetailsRespEntity extends BaseEntity {
    /**
     * 蓝牙打印机打印指令，是字节数组，需要将String转成byte
     */
    private List<PrintCommandEntity> command;

    /**
     * 打印机类型
     */
    private String printerType;

    public List<PrintCommandEntity> getCommand() {
        return command;
    }

    public void setCommand(List<PrintCommandEntity> command) {
        this.command = command;
    }

    public String getPrinterType() {
        return printerType;
    }

    public void setPrinterType(String printerType) {
        this.printerType = printerType;
    }
}
