package com.rt.taopicker.main.printer.sdk.jqPrinter.esc;

import com.rt.taopicker.main.printer.sdk.jqPrinter.JQPrinter;
import com.rt.taopicker.main.printer.sdk.jqPrinter.Port;
import com.rt.taopicker.main.printer.sdk.jqPrinter.common.Code128;

public class Barcode extends BaseESC
{
	
	/*
	 * 构造函数
	 */
	public Barcode(Port port, JQPrinter.PRINTER_TYPE printer_type) {
		super(port, printer_type);
	}
	/*
	 * 设置1维条码高度
	 */
	public boolean set1DHeight(int height)
	{
		byte []cmd = { 0x1D, 0x68, 0x00};
		cmd[2] = (byte)height;
		return port.write(cmd);
	}
	/*
	 * 设置1维，2维条码基本单元大小
	 */
	public boolean setUnit(ESC.BAR_UNIT unit)
	{
		byte []cmd = { 0x1D, 0x77, 0x00};
		cmd[2] = (byte)unit.value();
		return port.write(cmd);
	}
	/*
	 * 设置条码文字位置
	 */
	public boolean setTextPosition(ESC.BAR_TEXT_POS pos)
	{
		byte []cmd = { 0x1D, 0x48, 0x00};
		cmd[2] = (byte)pos.ordinal();
		return port.write(cmd);
	}
	/*
	 * 设置条码文字大小
	 */
	public boolean setTextSize(ESC.BAR_TEXT_SIZE size)
	{
		byte []cmd = { 0x1D, 0x66, 0x00};
		cmd[2] = (byte)size.ordinal();
		return port.write(cmd);
	}
	/*
	 * EAN校验和
	 */
	private byte EAN_checksum(byte []str,int str_len)
	{	
		int i;
		int check_sum = 0;
		
		if ( str_len%2 == 1) //奇数个
		{
			for( i = 0; i < str_len; i++)
			{
				if(i%2==0)	
					check_sum += (str[i]-'0')*3;
				else	
					check_sum += (str[i]-'0');
			}
		}
		else //偶数个
		{
			for( i = 0; i < str_len; i++)
			{
				if(i%2==0)	
					check_sum += (str[i]-'0');
				else	
					check_sum += (str[i]-'0')*3;
			}
		}
		check_sum = check_sum%10;
		if(check_sum != 0)
			check_sum = 10 - check_sum;
		check_sum = '0'+check_sum;
		return (byte)check_sum;
	}
	/*
	 * 获取UPCE的校验和
	 */
	private byte UPCE_getChecksum(byte []src,int length)
	{
		byte []buf =new byte[12];	
		int i;
		int j=0;
	    if (src[6] == '0' || src[6] == '1' || src[6] == '2')
	    {                
			buf[j++] = src[0];
	        buf[j++] = src[1];
			buf[j++] = src[2];
	        buf[j++] = src[6];
			for (i = 0; i < 4; i++) 
				buf[j++] = '0';
			buf[j++] = src[3];
			buf[j++] = src[4];
			buf[j++] = src[5];
	    }
	    else if (src[6] == '3')
	    {
	    	buf[j++] = src[0];
	    	buf[j++] = src[1];
	    	buf[j++] = src[2];
	    	buf[j++] = src[3];
	        for (i = 0; i < 5; i++) 
	        	buf[j++] = '0';
	        buf[j++] = src[4];
	        buf[j++] = src[5];
	    }
	    else if (src[6] == '4')
	    {
	    	buf[j++] = src[0];
	    	buf[j++] = src[1];
	    	buf[j++] = src[2];
	    	buf[j++] = src[3];
	    	buf[j++] = src[4];
	        for (i = 0; i < 5; i++)	
	        	buf[j++] = '0';
	        buf[j++] = src[5];
	    }
	    else 
	    {
	    	buf[j++] = src[0];
	    	buf[j++] = src[1];
	    	buf[j++] = src[2];
	    	buf[j++] = src[3];
	    	buf[j++] = src[4];
	    	buf[j++] = src[5];
	        for (i = 0; i < 4; i++)	
	        	buf[j++] = '0';
	        buf[j++] = src[6];
	     }
		return EAN_checksum(buf,11);
	}
	/*
	 * UPCE基本方式，此函数不会计算校验和。需要你自己在data中算好校验和
	 */
	private  boolean UPCE_base(byte []data)
	{
		byte []cmd ={ 0x1D ,0x6B, 0x01};
		port.write(cmd);
		return port.write(data);		
	}
	/*
	 * UPCE自动计算校验和
	 * --String str：必须7个字符
	 */
	public boolean UPCE_auto(String text)
	{
		byte []buf =new byte[9];
		if(text.length() != 7) //检查长度
			return false;	
		for(int i = 0; i < text.length(); i++) //检查是否为数字
		{
			if(text.charAt(i) <'0'||text.charAt(i) >'9') 
				return false;
		}
		if(text.charAt(0)!='0' && text.charAt(1)!='1') 
			return false;
		for(int i = 0;i < 7;i++)
		{
			buf[i] = (byte)text.charAt(i);
		}
		buf[7] = UPCE_getChecksum(buf,7);
		buf[8] = 0;
		return UPCE_base(buf);
	}
	/*
	 * UPCA基本方式，此函数不会计算校验和。需要你自己在data中算好校验和
	 */
	private boolean UPCA_base(byte []data)
	{
		byte []cmd ={ 0x1D ,0x6B, 0x00};
		port.write(cmd);
		return port.write(data);		
	}
	/*
	 * UPCA自动计算校验和
	 * --String str：必须11个字符
	 */
	public boolean UPCA_auto(String str)
	{
		byte []buf = new byte[13];
		if(str.length() != 11) 
			return false;
		for(int i = 0; i < str.length(); i++) //检查是否为数字
		{
			if(str.charAt(i) <'0'||str.charAt(i) >'9') 
				return false;
		}
		for(int i = 0; i < str.length(); i++)
		{
			buf[i] = (byte)str.charAt(i);
		}
		buf[11] = EAN_checksum(buf,11);	
		buf[12] = 0; 
		return UPCA_base(buf);
	}
	/*
	 * EAN13基本方式，此函数不会计算校验和。需要你自己在data中算好校验和
	 */
	private boolean EAN13_base(byte []data)
	{
		byte []cmd ={ 0x1D ,0x6B, 0x03};
		port.write(cmd);
		return port.write(data);		
	}
	/*
	 * EAN13自动计算校验和
	 * 输入参数：
	 * --String str：必须12个字符
	 */
	public boolean EAN13_auto(String str)
	{
		byte []buf = new byte[14];
		if(str.length() != 12) 
			return false;
		for(int i = 0; i < str.length(); i++) //检查是否为数字
		{
			if(str.charAt(i) <'0'||str.charAt(i) >'9') 
				return false;
		}
		for(int i = 0; i < str.length(); i++)
		{
			buf[i] = (byte)str.charAt(i);
		}
		buf[12] = EAN_checksum(buf,12);
		buf[13] = 0;
		return	EAN13_base(buf);
	}
	/*
	 * EAN8基本方式，此函数不会计算校验和。需要你自己在data中算好校验和
	 */
	private boolean EAN8_base(byte []data)
	{
		byte []cmd ={ 0x1D ,0x6B, 0x02};
		port.write(cmd);
		return port.write(data);		
	}
	/*
	 * EAN8自动计算校验和
	 * 输入参数：
	 * --String str：必须7个字符
	 */
	public boolean EAN8_auto(String str)
	{
		if(str.length() != 7) 
			return false;
		for(int i = 0; i < str.length(); i++) //检查是否为数字
		{
			if(str.charAt(i) <'0'||str.charAt(i) >'9') 
				return false;
		}
		byte []buf = new byte[9];
		for(int i = 0; i < str.length(); i++)
		{
			buf[i] = (byte)str.charAt(i);
		}
		buf[7] = EAN_checksum(buf,7);
		buf[8] = 0;
		return EAN8_base(buf);
	}
	/*
	 * CODE128基本方式，此函数不会计算校验和。需要你自己在data中算好校验和
	 */
	private boolean CODE128_base(byte []data)
	{
		byte []cmd ={ 0x1D ,0x6B, 0x08};
		port.write(cmd);
		return port.write(data);		
	}	
	/*
	 * Code128自动计算校验和
	 * 输入参数：
	 * --String str：
	 */
	public boolean CODE128_auto(String str)
	{
		Code128 code128 = new Code128(str);
		byte []buf = code128.encode_data;
		if (buf == null)
			return false;
		return CODE128_base(buf);
	}
	/*
	 * Code128自动计算校验和
	 */
	public boolean code128_auto_drawOut(JQPrinter.ALIGN align, ESC.BAR_UNIT unit, int height, ESC.BAR_TEXT_POS pos, ESC.BAR_TEXT_SIZE size, String str)
	{
		Code128 code128 = new Code128(str);		
		byte []buf = code128.encode_data;
		if (buf == null)
			return false;
		if (!setAlign(align))
			return false;
		setUnit(unit);
		if (!set1DHeight(height))
			return false;
		setTextPosition(pos);
		setTextSize(size);
		return CODE128_base(buf);
	}
	
	 public boolean code128_auto_printOut(JQPrinter.ALIGN align, ESC.BAR_UNIT unit, int height, ESC.BAR_TEXT_POS pos, ESC.BAR_TEXT_SIZE size, String str)
     {
         if (!code128_auto_drawOut(align,unit,height,pos,size,str))
             return false;
         enter();
         if (!setAlign(JQPrinter.ALIGN.LEFT))
             return false;
         return true;
     }
}
