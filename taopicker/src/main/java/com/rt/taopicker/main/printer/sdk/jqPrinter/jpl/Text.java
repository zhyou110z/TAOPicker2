package com.rt.taopicker.main.printer.sdk.jqPrinter.jpl;

import com.rt.taopicker.main.printer.sdk.jqPrinter.JQPrinter;

public class Text extends BaseJPL{
	public static enum TEXT_ENLARGE
	{
		x1,
		x2,
		x3,
		x4,
	}	
	/*
	 * 构造函数
	 */
	public Text(JPL_Param param) {
		super(param);
	}
	
	public boolean drawOut(int x, int y, String text)
	{
		byte[] cmd = {0x1A, 0x54, 0x00};		
		port.write(cmd);
		port.write((short)x);
		port.write((short)y);
		port.write(text);
		return port.writeNULL();
	}
	public boolean drawOut(int x, int y, String text, 
			int fontHeight, boolean bold, boolean reverse, boolean underLine,boolean deleteLine, TEXT_ENLARGE enlargeX, TEXT_ENLARGE enlargeY, JPL.ROTATE rotateAngle)
	{
		if(x<0 || y < 0)
			return false;
		if(x>=param.pageWidth || y < 0)
			return false;
		
		byte[] cmd = new byte[] { 0x1A, 0x54, 0x01 };
		int font_type = 0;
		if (bold)
			font_type |= 0x0001;
		if (underLine)
			font_type |= 0x0002;
		if (reverse)
			font_type |= 0x0004;
		if (deleteLine)
			font_type |= 0x0008;
		switch (rotateAngle) 
		{
			case ROTATE_90:
				font_type |= 0x0010;
				break;
			case ROTATE_180:
				font_type |= 0x0020;
				break;
			case ROTATE_270:
				font_type |= 0x0030;
				break;
			default:
				break;
		}
		int ex = enlargeX.ordinal();
		int ey = enlargeY.ordinal();
		ex &= 0x000F;
		ey &= 0x000F;
		font_type |= (ex << 8);
		font_type |= (ey << 12);

		port.write(cmd);
		port.write((short)x);
		port.write((short)y);
		port.write((short)fontHeight);
		port.write((short)font_type);
		port.write(text);
		return port.writeNULL();
	}

	private boolean isChinese(char c) 
	{
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
             || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
	
	private int calcTextWidth(int font_width,String text)
	{
		int hz_count = 0;
		int ascii_count = 0;
		for(int i= 0;i < text.length();i++)
		{
			if (isChinese(text.charAt(i)))
			{
				hz_count++;
			}
			else
			{
				ascii_count++;
			}
		}		
		return (hz_count*font_width + ascii_count*font_width/2);
	}
	
	private int calcFontWidth(int font_height)
	{
		if (font_height< 20)
		{
			return 16;
		}
		else if (font_height< 28)
		{
			return 24;
		}
		else if (font_height< 40)
		{
			return 32;
		}
		else if (font_height< 56)
		{
			return 48;
		}
		else 
		{
			return 64;
		}
	}
	private int calcTextStartPosition(JQPrinter.ALIGN align, int font_height, int enlargeX, String text)
	{
		if (align == JQPrinter.ALIGN.LEFT)
			return 0;
		
		int x = 0;		
		int font_width = calcFontWidth(font_height);
		enlargeX++;
		int font_total_width = calcTextWidth(font_width,text)*enlargeX;
		switch(align)
		{
			case CENTER:
				x = (param.pageWidth - font_total_width)/2; 
				break;
			case RIGHT:
				x = param.pageWidth - font_total_width;
				break;
			default:
				break;
		}
		return x;	
	}
	
	public boolean drawOut(JQPrinter.ALIGN align, int y, String text,
						   int fontHeight, boolean bold, boolean reverse, boolean underLine, boolean deleteLine, TEXT_ENLARGE enlargeX, TEXT_ENLARGE enlargeY, JPL.ROTATE rotateAngle)
	{
		int x = calcTextStartPosition(align,fontHeight,enlargeX.ordinal(),text);		
		return drawOut(x, y,text,fontHeight,bold,reverse,underLine,deleteLine,enlargeX,enlargeY,rotateAngle);
	}
}
