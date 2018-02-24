package com.rt.taopicker.main.printer.sdk.jqPrinter.jpl;

import com.rt.taopicker.main.printer.sdk.jqPrinter.Port;

public class JPL_Param {
	public Port port;
	public int pageWidth;
	public int pageHeight;
	public JPL_Param(Port port)
	{
		this.port = port;
		pageWidth = -1;
		pageHeight = -1;
	}
}
