package com.rt.taopicker.main.printer.sdk.jqPrinter.esc;

import com.rt.taopicker.main.printer.sdk.jqPrinter.JQPrinter;
import com.rt.taopicker.main.printer.sdk.jqPrinter.Port;

public class ESC 
{
	 public enum CARD_TYPE_MAIN
     {
         CDT_AT24Cxx(0x01),
         CDT_SLE44xx(0x11),
         CDT_CPU(0x21);
        private int _value;
 		private CARD_TYPE_MAIN(int type)
 		{
 			_value = type;
 		}		
 		public int value()
 		{
 			return _value;
 		}
     };
     
	public static enum BAR_TEXT_POS
	{
		NONE,
		TOP,
		BOTTOM,				
	}
	public static enum BAR_TEXT_SIZE
	{
		ASCII_12x24,
		ASCII_8x16,						
	}
	
	public static enum BAR_UNIT
	{
		x1(1),
		x2(2),
		x3(3),
		x4(4);
		private int _value;
		private BAR_UNIT(int dots)
		{
			_value = dots;
		}		
		public int value()
		{
			return _value;
		}
	}
	public static class LINE_POINT
	{
		public int startPoint;
		public int endPoint;
		public LINE_POINT(){};
		public LINE_POINT(int start_point, int end_point)
		{
			startPoint = (short)start_point;
			endPoint = (short)end_point;
		}
	}
	/*
	 * 枚举类型：文本放大方式
	 */
	public static enum 	TEXT_ENLARGE
	{
		NORMAL(0x00),                        //正常字符 
        HEIGHT_DOUBLE(0x01),                 //倍高字符
        WIDTH_DOUBLE(0x10),                  //倍宽字符
        HEIGHT_WIDTH_DOUBLE (0x11);           //倍高倍宽字符
        
        private int _value;
		private TEXT_ENLARGE(int mode)
		{
			_value = mode;
		}		
		public int value()
		{
			return _value;
		}
	}
	/*
	 * 枚举类型：字体高度
	 */
	public static enum FONT_HEIGHT
	{
		x24,                     
        x16,
        x32,
        x48,
        x64,        
	}
	
	private byte[] cmd ={0,0,0,0,0,0,0,0};
	private Port port;
	public Text text;  
	public Image image;
	public Graphic graphic;
	public Barcode barcode;
	public CardReader card_reader;
	public ESC(Port port,JQPrinter.PRINTER_TYPE printer_type)
	{
		if (port== null)
			return;
		this.port = port;
		text = new Text(port,printer_type);
		image = new Image(port,printer_type);
		graphic = new Graphic(port,printer_type);
		barcode = new Barcode(port,printer_type);
		card_reader = new CardReader(port,printer_type);
	}

	public static enum IMAGE_MODE
    {
        SINGLE_WIDTH_8_HEIGHT(0x01),        //单倍宽8点高
        DOUBLE_WIDTH_8_HEIGHT(0x00),        //倍宽8点高
        SINGLE_WIDTH_24_HEIGHT(0x21),       //单倍宽24点高
        DOUBLE_WIDTH_24_HEIGHT(0x20);       //倍宽24点高
        private int _value;
		private IMAGE_MODE(final int mode)
		{
			_value = mode;
		}
		public int value()
		{
			return _value;
		}
    }
	public static enum IMAGE_ENLARGE
	{
		NORMAL,//正常
		HEIGHT_DOUBLE,//倍高 
		WIDTH_DOUBLE,//倍宽
		HEIGHT_WIDTH_DOUBLE	//倍高倍宽	
	}
	
	public boolean init()
	{
		cmd[0] = 0x1B;	cmd[1] = 0x40;
		return port.write(cmd,0,2);		
	}
	
	/*
	 * 唤醒打印机，并初始化
	 */
	public boolean wakeUp() 
	{
		if(!port.writeNULL())
			return false;
		try {Thread.sleep(50);} catch (InterruptedException e) {}
		return init();
	}
	
	public boolean getState(byte []ret,int timerout_read)
    {
		port.flushReadBuffer();
		cmd[0] = 0x10;
		cmd[1] = 0x04;
		cmd[2] = 0x05;
		if (!port.write(cmd,0,3))
			return false;
		if (!port.read(ret, 2,timerout_read))
			return false;
		return true;
    }
	/*
	 * 换行回车
	 */
	public boolean feedEnter()
	{
		byte []cmd={0x0D,0x0A};
		return port.write(cmd);
	}
	/*
	 * 走纸几行
	 * 输入参数:
	 * --int lines:几行
	 */
	public boolean feedLines(int lines)
	{
		byte []cmd={0x1B,0x64,00};
		cmd[2] = (byte)lines;
		return port.write(cmd);
	}
	/*
	 * 走纸几点
	 * 输入参数:
	 * --int dots:多少个点
	 */
	public boolean feedDots(int dots)
	{
		byte []cmd={0x1B,0x4A,00};
		cmd[2] = (byte)dots;
		return port.write(cmd);
	}
}


