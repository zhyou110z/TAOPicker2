package com.rt.taopicker.main.printer.sdk.jqPrinter.jpl;

import android.graphics.Point;

public class Graphic extends BaseJPL{

	public Graphic(JPL_Param param) {
		super(param);
	}
	/*
	 * 在页面内绘制线段 
	 */
    public boolean line(Point start, Point end, int width, JPL.COLOR color)
    {
    	byte[] cmd = { 0x1A, 0x5C, 0x01};
    	port.write(cmd);
    	port.write((short)start.x);
    	port.write((short)start.y);
    	port.write((short)end.x);
    	port.write((short)end.y);
    	port.write((short)width);
    	return port.write((byte)color.ordinal());
    }
    
    public boolean line(Point start, Point end, int width)
    {
        return line(start,end,width, JPL.COLOR.Black);
    }
    
    public boolean line(Point start, Point end)
	{
    	byte[] cmd = { 0x1A, 0x5C, 0x00};
    	port.write(cmd);
    	port.write((short)start.x);
    	port.write((short)start.y);
    	port.write((short)end.x);
    	return port.write((short)end.y);
	}

	public boolean rect(int left, int top, int right, int bottom)
	{
		byte[] cmd = {0x1A, 0x26, 0x00};
		port.write(cmd);
		port.write((short)left);
		port.write((short)top);
		port.write((short)right);
		return port.write((short)bottom);
	}
	
	public boolean rect(int left, int top, int right, int bottom, int width, JPL.COLOR color)
	{
		byte[] cmd = {0x1A, 0x26, 0x01};
		port.write(cmd);
		port.write((short)left);
		port.write((short)top);
		port.write((short)right);
		port.write((short)bottom);
		port.write((short)width);
		return port.write((byte)color.ordinal());
	}
	
	public boolean rectFill(int left, int top, int right, int bottom, JPL.COLOR color)
	{
		byte[] cmd = new byte[]{0x1A, 0x2A, 0x00};
		port.write(cmd);
		port.write((short)left);
		port.write((short)top);
		port.write((short)right);
		port.write((short)bottom);
		return port.write((byte)color.ordinal());
	}
}
