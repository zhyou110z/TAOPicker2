package com.rt.taopicker.main.printer.sdk.jqPrinter;

public class JQPrinterInfo {
	
	public final static int STATE_NOPAPER_UNMASK = 0x01;
	public final static int STATE_OVERHEAT_UNMASK = 0x02;
	public final static int STATE_BATTERYLOW_UNMASK = 0x04;
	public final static int STATE_PRINTING_UNMASK = 0x08;
	public final static int STATE_COVEROPEN_UNMASK = 0x10;
	/*
	 * 缺省构造函数
	 */
	public JQPrinterInfo()
	{
	}

	public void stateReset()
	{
		isNoPaper = false;
		isOverHeat = false;
		isBatteryLow = false;
		isPrinting = false; 
		isCoverOpen = false;
	}
	/*
	 * 打印机缺纸
	 * 0x01
	 */
	public boolean isNoPaper;

	/*
	 * 打印机打印头过热
	 * 0x02
	 */
	public boolean isOverHeat;
	
	/*
	 * 打印机电池电压过低
	 * 0x04
	 */
	public boolean isBatteryLow;
	
	/*
	 * 打印机正在打印
	 * 0x08
	 */
	public boolean isPrinting;
	
	/*
	 * 打印机纸仓盖未关闭
	 * 0x10
	 */
	public boolean isCoverOpen;
}
