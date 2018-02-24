package com.rt.taopicker.main.printer.sdk.jqPrinter.jpl;

import com.rt.taopicker.main.printer.sdk.jqPrinter.Port;

public class BaseJPL 
{
	protected Port port;
	protected JPL_Param param;

	/*
	 * 构造函数
	 */
	public BaseJPL(JPL_Param param) {
		if (param.port == null)
			return;
		this.param = param;
		this.port = param.port;
	}
}
