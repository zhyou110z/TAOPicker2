package com.rt.taopicker.main.printer.sdk.jqPrinter.jpl;

import com.rt.taopicker.main.printer.sdk.jqPrinter.JQPrinter;
import com.rt.taopicker.main.printer.sdk.jqPrinter.Port;

public class JPL {
	/*
	 * 枚举类型：对象旋转方式
	 */
	public static enum ROTATE
	{
		ROTATE_0(0x00),
		ROTATE_90(0x01),
		ROTATE_180(0x10),
		ROTATE_270(0x11);
		private int _value;
		private ROTATE(int mode)
		{
			_value = mode;
		}
		public int value()
		{
			return _value;
		}
	}
	public static enum COLOR
	{
		White,
		Black,		
	}	
	private Port port;
	public Page page;
	public Barcode barcode;
	public Text text;
	public JPL_Param param;
	public Graphic graphic;
	public Image image;
	public JPL(Port port,JQPrinter.PRINTER_TYPE printer_type)
	{
		if (port== null)
			return;
		this.port = port;
		this.param = new JPL_Param(port);
		page = new Page(param);
		barcode = new Barcode(param);
		text = new Text(param);
		graphic = new Graphic(param);
		image = new Image(param);
	}
	
	/*
	 * 走纸到下一张标签开始
	 */
	public boolean feedNextLabelBegin()
	{
		byte[] cmd = {0x1A, 0x0C, 0x00};
		return port.write(cmd);
	}
	public static enum FEED_TYPE
	{
		MARK_OR_GAP,
		LABEL_END,
		MARK_BEGIN,
		MARK_END,
		BACK, //后退
	}
	private boolean feed(FEED_TYPE feed_type, int offset)
	{
		byte[] cmd = {0x1A, 0x0C, 0x01};
		port.write(cmd);
		port.write((byte)feed_type.ordinal());
		return port.write((short)offset);
	}
	/*
	 * 打印纸后退
	 * 注意:1.需要打印机JLP351的固件版本3.0.0.0及以上
	 *      2.需要设置软件设置打印机，使能FeedBack状态
	 */
	public boolean feedBack(int dots)
	{
		return feed(FEED_TYPE.BACK,dots);
	}
	
	public boolean feedNextLabelEnd(int dots)
	{
		return feed(FEED_TYPE.LABEL_END,dots);
	}
	
	public boolean feedMarkOrGap(int dots)
	{
		return feed(FEED_TYPE.MARK_OR_GAP,dots);
	}
	
	public boolean feedMarkEnd(int dots)
	{
		return feed(FEED_TYPE.MARK_END,dots);
	}
	
	public boolean feedMarkBegin(int dots)
	{
		return feed(FEED_TYPE.MARK_BEGIN,dots);
	}
}
