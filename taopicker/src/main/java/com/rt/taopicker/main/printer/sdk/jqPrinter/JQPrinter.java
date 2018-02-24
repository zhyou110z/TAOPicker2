package com.rt.taopicker.main.printer.sdk.jqPrinter;

import android.bluetooth.BluetoothAdapter;

import com.rt.taopicker.main.printer.sdk.jqPrinter.esc.ESC;
import com.rt.taopicker.main.printer.sdk.jqPrinter.jpl.JPL;

public class JQPrinter {
	/*
	 * 枚举类型：打印机型号
	 */
	public static enum PRINTER_TYPE
	{
		VMP02,
		VMP02_P,
		JLP351,
		JLP351_IC,
		ULT113x,
		ULT1131_IC,
	}
	/*
	 * 枚举类型：对齐方式
	 */
	public static enum ALIGN
	{
		LEFT,
		CENTER,
		RIGHT;
	}

	public JQPrinterInfo printerInfo = new JQPrinterInfo();
	private PRINTER_TYPE printerType;
	private Port port = null;
	public ESC esc = null;
	public JPL jpl = null;
	public boolean isOpen = false;
	private boolean isInit = false;
	private byte[] state={0,0};
	
	/*
	 * 构造函数
	 */
	public JQPrinter()
	{
	
	}
	/*
	 * 构造函数
	 */
	public JQPrinter(BluetoothAdapter btAdapter,String btDeviceString)
	{
		if (btAdapter == null || btDeviceString == null)
		{
			isInit = false;
			return;
		}
		port = new Port(btAdapter,btDeviceString);
		isInit = true;
	}
	
	/*
	 * 构造函数
	 */
	public JQPrinter(String btDeviceString) 
	{
		if (btDeviceString == null)
		{
			isInit = false;
			return;
		}
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		if ( btAdapter == null )
		{
			isInit = false;
			return ;
		}
		port = new Port(btAdapter, btDeviceString);
		isInit = true;
	}
	
	//打开端口
	//注意：最好不要将此函数放在Activity的onCreate函数中，因为bluetooth connect时会有定的延时，有时会造成页面显示很慢，而误认为没有点击按钮
	public boolean open(PRINTER_TYPE printer_type)
	{
		if (!isInit)
			return false;
		printerType = printer_type;
		if (isOpen)
			return true;
		
		if (!port.open(3000))
			return false;
		
		esc = new ESC(port,printer_type);
		jpl = new JPL(port,printer_type);
		isOpen =  true;
		return true;
	}
		
	public boolean open(PRINTER_TYPE printer_type,int timeout)
	{
		if (!isInit)
			return false;
		printerType = printer_type;
		if (isOpen)
			return true;
		
		if (!port.open(timeout))
			return false;
		
		esc = new ESC(port,printer_type);
		jpl = new JPL(port,printer_type);
		isOpen =  true;
		return true;
	}
	
	public boolean open(String btDeviceString,PRINTER_TYPE printer_type)
	{
		if (btDeviceString == null)
			return false;
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		if ( btAdapter == null )
			return false;
		port = new Port(btAdapter, btDeviceString);
		if (port == null)
			return false;
		isInit = true;
			
		return open(printer_type,3000);
	}
	
	public boolean close()
	{
		if (!isOpen)
			return false;

		isOpen = false;
		return port.close();
	}
	
	public Port.PORT_STATE getPortState()
	{
		return port.getState();
	}	

	/*
	 * 唤醒打印机
	 * 注意:部分手持蓝牙连接第一次不稳定，会造成开头字符乱码，可以通常这个方法来避免此问题
	 */
	public boolean wakeUp() 
	{
		if (!isInit)
			return false;
		
		if(!port.writeNULL())
			return false;
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return esc.text.init();
	}
	/*
	 * 走纸到右黑标
	 */
	public boolean feedRightMark()
	{
		if (!isInit)
			return false;
		
		byte []cmd={0x00,0x00};		
		switch(this.printerType)
		{
			case JLP351: //JLP351系列没有右黑标传感器，只能使用中间的标签缝传感器代替有右黑标传感器
			case JLP351_IC:
				cmd[0] = 0x1D;
				cmd[1] = 0x0C;
				return port.write(cmd,2);//指令0x1D 0x01C 等效于 0x0E
			default:
				cmd[0] = 0x0C;
				return port.write(cmd,1);
		}		
	}
	/*
	 * 走纸到左黑标
	 */
	public boolean feedLeftMark()
	{
		if (!isInit)
			return false;
		
		switch(this.printerType)
		{
			case JLP351: //JLP351系列没有右黑标传感器，只能使用中间的标签缝传感器代替有右黑标传感器
			case JLP351_IC:
				return port.write((byte)0x0c);//0x0C 
			default:
				return port.write((byte)0x0e);
		}
	}
	
	/*
	 * 获取打印机状态
	 */
	public boolean getPrinterState(int timeout_read) {
		if (!isInit)
			return false;
		
		printerInfo.stateReset();
		if (!esc.getState(state,timeout_read))
			return false;
		if ((state[0] & JQPrinterInfo.STATE_NOPAPER_UNMASK) != 0 )
		{
			printerInfo.isNoPaper = true;
		}
		if ((state[0] & JQPrinterInfo.STATE_BATTERYLOW_UNMASK) != 0)
		{
			printerInfo.isBatteryLow = true;
		}	
		if ((state[0] & JQPrinterInfo.STATE_COVEROPEN_UNMASK) != 0)
		{
			printerInfo.isCoverOpen = true;
		}
		if ((state[0] & JQPrinterInfo.STATE_OVERHEAT_UNMASK) != 0)
		{
			printerInfo.isOverHeat = true;
		}
		if ((state[0] & JQPrinterInfo.STATE_PRINTING_UNMASK) != 0)
		{
			printerInfo.isPrinting = true;
		}
		return true;  
	}

	public Port getPort() {
		return port;
	}
}
