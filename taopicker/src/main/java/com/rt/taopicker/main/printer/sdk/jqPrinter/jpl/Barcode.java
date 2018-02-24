package com.rt.taopicker.main.printer.sdk.jqPrinter.jpl;

import com.rt.taopicker.main.printer.sdk.jqPrinter.JQPrinter;
import com.rt.taopicker.main.printer.sdk.jqPrinter.common.Code128;

public class Barcode extends BaseJPL
{
	/*
	 * 枚举类型：JPL所用条码类型
	 */
    public static enum BAR_1D_TYPE
    {
        UPCA_AUTO(0x41),
        UPCE_AUTO(0x42),
        EAN8_AUTO(0x43),
        EAN13_AUTO(0x44),
        CODE39_AUTO(0x45),
        ITF25_AUTO(0x46),
        CODABAR_AUTO(0x47),
        CODE93_AUTO(0x48),
        CODE128_AUTO(0x49);
        private int _value;
		private BAR_1D_TYPE(int id)
		{
			_value = id;
		}		
		public int value()
		{
			return _value;
		}	
    }
    /*
	 * 枚举类型：条码单元,JPL所用
	 */
    public enum BAR_UNIT
    {
        x1(1),
        x2(2),
        x3(3),
        x4(4),
        x5(5),
        x6(6),
        x7(7);
        private int _value;
		private BAR_UNIT(int id)
		{
			_value = id;
		}		
		public int value()
		{
			return _value;
		}
    }
    // <summary>
    /// 打印对象旋转角度
    /// </summary>
    public enum BAR_ROTATE
    {
        ANGLE_0,
        ANGLE_90,
        ANGLE_180,
        ANGLE_270
    } 
    /*
     * 构造函数
     */
	public Barcode(JPL_Param param) {
		super(param);
	}

	/*
	 *一维条码绘制
	 */
	private boolean _1D_barcode(int x, int y, BAR_1D_TYPE type, int height, BAR_UNIT unit_width, BAR_ROTATE rotate, String text)
    {
        byte[] cmd = { 0x1A, 0x30, 0x00 };
        port.write(cmd);
        port.write((short)x);
        port.write((short)y);
        port.write((byte)type.value());
        port.write((short)height);
        port.write((byte)unit_width.value());
        port.write((byte)rotate.ordinal());
        return port.write(text);
    }
	/*
	 * code128
	 */
	public boolean code128(int x,int y, int bar_height, BAR_UNIT unit_width,BAR_ROTATE rotate,String text)
	{
		return _1D_barcode(x,y, BAR_1D_TYPE.CODE128_AUTO,bar_height, unit_width, rotate, text);
	}
	/*
	 * Code128
	 */
	public boolean code128(JQPrinter.ALIGN align, int y, int bar_height, BAR_UNIT unit_width, BAR_ROTATE rotate, String text)
	{
		int x = 0;
		Code128 code128 = new Code128(text);
		if (code128.encode_data == null)
			return false;
		if (!code128.decode(code128.encode_data))
			return false;
		int bar_width = code128.decode_string.length();
		if (align == JQPrinter.ALIGN.CENTER)
			x = (param.pageWidth - bar_width* unit_width.value())/2;
		else if(align == JQPrinter.ALIGN.RIGHT)
			x = param.pageWidth - bar_width* unit_width.value();
		else
			x = 0;
		return _1D_barcode(x,y, BAR_1D_TYPE.CODE128_AUTO,bar_height, unit_width, rotate, text);
	}
	
	public enum QRCODE_ECC
	{
		LEVEL_L,//可纠错7%
		LEVEL_M,//可纠错15%
		LEVEL_Q,//可纠错25%
		LEVEL_H,//可纠错30%	
	}
	/*
	 * QRCode
	 * int version:版本号，如果为0，将自动计算版本号。
	 *             每个版本号容纳的字节数目是一定的。如果内容不足，将自动填充空白。通过定义一个大的版本号来固定QRCode大小。
	 * int ecc：纠错方式,取值0, 1，2，3，纠错级别越高，有效字符越少，识别率越高。缺省为2
	 * int unit_width：基本单元大小
	 */
	public boolean QRCode(int x, int y, int version, QRCODE_ECC ecc, BAR_UNIT unit_width, JPL.ROTATE rotate, String text)
	{
		byte[] cmd = {0x1A, 0x31, 0x00};
		port.write(cmd);
		port.write((byte)version);
		port.write((byte)ecc.ordinal());
		port.write((short)x);
		port.write((short)y);
		port.write((byte)unit_width.value());
		port.write((byte)rotate.value());
		return port.write(text);
	}
	/*
	 * PDF417
	 */
	public boolean PDF417(int x, int y, int col_num, int ecc, int LW_ratio, BAR_UNIT unit_width, JPL.ROTATE rotate, String text)
	{
		byte[] cmd = {0x1A, 0x31, 0x01};
		port.write(cmd);
		port.write((byte)col_num);
		port.write((byte)ecc);
		port.write((byte)LW_ratio);
		port.write((short)x);
		port.write((short)y);
		port.write((byte)unit_width.value());
		port.write((byte)rotate.value());
		return port.write(text);
	}
	
	public boolean DataMatrix(int x, int y, BAR_UNIT unit_width, JPL.ROTATE rotate, String text)
	{
		byte[] cmd = {0x1A, 0x31, 0x02};
		port.write(cmd);
		port.write((short)x);
		port.write((short)y);
		port.write((byte)unit_width.value());
		port.write((byte)rotate.value());
		return port.write(text);		
	}
	
	public boolean GridMatrix(int x, int y, byte ecc, BAR_UNIT unit_width, JPL.ROTATE rotate, String text)
	{
		byte[] cmd = {0x1A, 0x31, 0x03};
		port.write(cmd);
		port.write((byte)ecc);
		port.write((short)x);
		port.write((short)y);
		port.write((byte)unit_width.value());
		port.write((byte)rotate.value());
		return port.write(text);		
	}
}
