package com.rt.taopicker.main.printer.sdk.jqPrinter.jpl;

public class Page  extends BaseJPL
{
	public static enum PAGE_ROTATE
	{
		x0,
		x90,
	}
	/*
	 * 构造函数
	 */
	public Page(JPL_Param param) {
		super(param);
	}

	/*
	 * 打印页面开始 使用打印机缺省参数绘制打印机页面 缺省页面宽576dots(72mm),高640dots（80mm）
	 */
	public boolean start() {
		param.pageWidth = 576;
		param.pageHeight = 640;
		byte[] cmd = new byte[] { 0x1A, 0x5B, 0x00 };
		return port.write(cmd);
	}

	/*
	 * 打印页面开始
	 */
	public boolean start(int originX, int originY, int pageWidth, int pageHeight, PAGE_ROTATE rotate) 
	{
		if (originX < 0 || originX > 575)
			return false;
		if (originY < 0)
			return false;
		if (pageWidth < 0 || pageWidth > 576)
			return false;
		if (pageHeight < 0 )
			return false;
		param.pageWidth = pageWidth;
		param.pageHeight = pageHeight;
		byte[] cmd = { 0x1A, 0x5B, 0x01 };
		if (!port.write(cmd))
			return false;
		if (!port.write((short) originX))
			return false;
		if (!port.write((short) originY))
			return false;
		if (!port.write((short) pageWidth))
			return false;
		if (!port.write((short) pageHeight))
			return false;
		return port.write((byte) rotate.ordinal());
	}

	/*
	 * 绘制打印页面结束
	 */
	public boolean end() {
		byte[] cmd = new byte[] { 0x1A, 0x5D, 0x00 };
		return port.write(cmd);
	}

	/*
	 * 打印页面内容 之前做的页面处理，只是把打印对象画到内存中，必须要通过这个方法把内容打印出来
	 */
	public boolean print() {
		byte[] cmd = new byte[] { 0x1A, 0x4F, 0x00 };
		return port.write(cmd);
	}
	
	/*
	 * 当前页面重复打印几次
	 */
	public boolean print(int count)
	{
		byte[] cmd = {0x1A, 0x4F, 0x01, 0x00};
		cmd[3] = (byte)count;
		return port.write(cmd);
	}
	
}
