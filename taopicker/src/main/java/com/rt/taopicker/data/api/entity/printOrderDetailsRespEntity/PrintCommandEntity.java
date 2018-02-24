package com.rt.taopicker.data.api.entity.printOrderDetailsRespEntity;

import java.util.List;

public class PrintCommandEntity {

	// 指令类型:0-普通text,graphic,barcode指令,1-image指令
	private Integer commandType;

	// text,graphic,barcode指令,如果是image则为空
	private List<String> command;

	// image的Base64编码
	private String base64Data;

	// zebra打印机指令
	private String zebraText;

	public Integer getCommandType() {
		return commandType;
	}

	public void setCommandType(Integer commandType) {
		this.commandType = commandType;
	}

	public List<String> getCommand() {
		return command;
	}

	public void setCommand(List<String> command) {
		this.command = command;
	}

	public String getBase64Data() {
		return base64Data;
	}

	public void setBase64Data(String base64Data) {
		this.base64Data = base64Data;
	}

	public String getZebraText() {
		return zebraText;
	}

	public void setZebraText(String zebraText) {
		this.zebraText = zebraText;
	}
}
