package com.rt.taopicker.main.printer.sdk.qrPrinter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.qr.printer.*;
import com.qr.printer.BluetoothPort;
import com.rt.taopicker.config.Constant;

import java.io.UnsupportedEncodingException;

public class QRPrinter
{
    private BluetoothPort Port = null;
    private int PortTimeOut = 3000;

    private int Family = 24;
    private int defaultFamily = 55;
    private int offsetdots = 40;
    private boolean isValidPrinter = false;
    private int PrinterName = 0;
    public static int pkgs;
    public static final int STATE_NOPAPER_UNMASK = 1;
    public static final int STATE_OVERHEAT_UNMASK = 2;
    public static final int STATE_BATTERYLOW_UNMASK = 4;
    public static final int STATE_PRINTING_UNMASK = 8;
    public static final int STATE_COVEROPEN_UNMASK = 16;

    private String CPCLCmd;

    public QRPrinter()
    {
        this.Port = new BluetoothPort();
    }

    public boolean connect(String name, String address)
    {
        if ((!(isEmpty(address))) && (!(isEmpty(name)))) {
            if (this.Port.isOpen) {
                this.Port.close();
            }
            this.Port.open(address, this.PortTimeOut);
            if (QRSDK.CheckPrinter(name)) {
                if (checkValidPrinter(address)) {
                    this.isValidPrinter = true;
                    return true;
                }
                this.isValidPrinter = false;
                this.Port.close();
                return false;
            }

            this.isValidPrinter = false;
            this.Port.close();
            return false;
        }

        this.isValidPrinter = false;
        return false;
    }

    public static boolean isEmpty(CharSequence str)
    {
        return ((str == null) || (str.length() == 0));
    }

    public void disconnect() {
        if (!(this.Port.isOpen)) return;
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.Port.close();
    }

    public boolean isConnected()
    {
        return this.Port.isOpen;
    }

    public Bitmap GetBitmap() {
        return null;
    }

    boolean portSendCmd(String cmd) {
        if (cmd != null) {
            byte[] data1;
            try {
                data1 = cmd.getBytes("GB2312");
            } catch (UnsupportedEncodingException e) {
                return false;
            }
            return portSendCmd(data1);
        }

        return false;
    }

    public boolean portSendCmd(byte[] data1)
    {
        String temp = "";
        for(int i=0; i<data1.length; i++){
            temp += data1[i]+",";
        }
        Log.e(Constant.PRINT_TAG, temp);

        if (this.Port.isOpen) {
            int Len = data1.length;
            while (true)
            {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (Len <= 10) {
                    return this.Port.write(data1, data1.length - Len, Len);
                }

                if (!(this.Port.write(data1, data1.length - Len, 10))) {
                    return false;
                }

                Len -= 10;
            }
        }
        return false;
    }

    public String print(int horizontal, int skip)
    {
        if (!(this.isValidPrinter))
            return "Invalid Device";
        String cmd;
        if (skip > 0) {
            cmd = "GAP-SENSE\r\nFORM\r\n";
            portSendCmd(cmd);
        }

        if (horizontal == 0) {
            cmd = "PRINT\r\n";

            portSendCmd(cmd);
        } else {
            cmd = "POPRINT\r\n";
            portSendCmd(cmd);
        }

        return "Ok";
    }

    public void pageSetup(int pageWidth, int pageHeight)
    {
        String cmd = "! 0 200 200 " + String.valueOf(pageHeight) + " " + "1" + "\r\n" + "PAGE-WIDTH" + " " +
                String.valueOf(pageWidth) + "\r\n";
        portSendCmd(cmd);
    }

    public void drawBox(int lineWidth, int top_left_x, int top_left_y, int bottom_right_x, int bottom_right_y) {
        if (top_left_x > 575) {
            top_left_x = 575;
        }

        if (bottom_right_x > 575) {
            bottom_right_x = 575;
        }
        String CPCLCmd = "BOX " + String.valueOf(top_left_x) + " " + String.valueOf(top_left_y) + " " +
                String.valueOf(bottom_right_x) + " " + String.valueOf(bottom_right_y) + " " +
                String.valueOf(lineWidth) + "\r\n";

        portSendCmd(CPCLCmd);
    }

    public void drawLine(int lineWidth, int start_x, int start_y, int end_x, int end_y, boolean fullline) {
        if (start_x > 575) {
            start_x = 575;
        }

        if (end_x > 575)
            end_x = 575;
        String CPCLCmd;
        if (fullline)
            CPCLCmd = "LINE " + String.valueOf(start_x) + " " + String.valueOf(start_y) + " " +
                    String.valueOf(end_x) + " " + String.valueOf(end_y) + " " + String.valueOf(lineWidth) + "\r\n";
        else {
            CPCLCmd = "LPLINE " + String.valueOf(start_x) + " " + String.valueOf(start_y) + " " +
                    String.valueOf(end_x) + " " + String.valueOf(end_y) + " " + String.valueOf(lineWidth) + "\r\n";
        }

        portSendCmd(CPCLCmd);
    }

    public void drawText(int text_x, int text_y, String text, int fontSize, int rotate, int bold, boolean reverse, boolean underline)
    {
        if (underline)
            CPCLCmd = "UNDERLINE ON\r\n";
        else {
            CPCLCmd = "UNDERLINE OFF\r\n";
        }
        portSendCmd(CPCLCmd);

        String CPCLCmd = "SETBOLD " + bold + "\r\n";
        portSendCmd(CPCLCmd);

        int family = 0;
        int size = 0;
        int ex = 1;
        int ey = 1;
        switch (fontSize)
        {
            case 1:
                family = 55;
                break;
            case 2:
                family = this.Family;
                break;
            case 3:
                family = 4;
                break;
            case 4:
                family = this.Family;
                ex = 2;
                ey = 2;
                break;
            case 5:
                family = 4;
                ex = 2;
                ey = 2;
                break;
            case 6:
                family = this.Family;
                ex = 3;
                ey = 3;
                break;
            case 7:
                family = 4;
                ex = 3;
                ey = 3;
                break;
            case 8:
                family = 3;
                size = 1;
                ex = 1;
                ey = 1;
                break;
            case 9:
                family = 4;
                size = 3;
                ex = 1;
                ey = 1;
                break;
            default:
                family = this.defaultFamily;
        }
        CPCLCmd = "SETMAG " + ex + " " + ey + "\r\n";
        portSendCmd(CPCLCmd);

        switch (rotate)
        {
            case 1:
                CPCLCmd = "TEXT90 ";

                break;
            case 2:
                CPCLCmd = "TEXT180 ";

                break;
            case 3:
                CPCLCmd = "TEXT270 ";

                break;
            default:
                CPCLCmd = "TEXT ";
        }

        CPCLCmd = CPCLCmd + family + " " + size + " " + text_x + " " + text_y + " " + text + "\r\n";
        portSendCmd(CPCLCmd);

        if (reverse) {
            int textLength = 0;
            try {
                textLength = text.getBytes("GB2312").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (fontSize == 1)
            {
                CPCLCmd = "INVERSE-LINE " + String.valueOf(text_x) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(text_x + textLength * 8) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(16) + "\r\n";
            }
            if (fontSize == 2)
            {
                CPCLCmd = "INVERSE-LINE " + String.valueOf(text_x) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(text_x + textLength * 12) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(24) + "\r\n";
            }
            if (fontSize == 3)
            {
                CPCLCmd = "INVERSE-LINE " + String.valueOf(text_x) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(text_x + textLength * 16) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(32) + "\r\n";
            }
            if (fontSize == 4)
            {
                CPCLCmd = "INVERSE-LINE " + String.valueOf(text_x) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(text_x + textLength * 24) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(48) + "\r\n";
            }
            if (fontSize == 5)
            {
                CPCLCmd = "INVERSE-LINE " + String.valueOf(text_x) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(text_x + textLength * 32) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(64) + "\r\n";
            }
            if (fontSize == 6)
            {
                CPCLCmd = "INVERSE-LINE " + String.valueOf(text_x) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(text_x + textLength * 48) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(96) + "\r\n";
            }
            if (fontSize == 7)
            {
                CPCLCmd = "INVERSE-LINE " + String.valueOf(text_x) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(text_x + textLength * 64) + " " + String.valueOf(text_y) + " " +
                        String.valueOf(128) + "\r\n";
            }
        }

        portSendCmd(CPCLCmd);
    }

    public void drawText(int text_x, int text_y, int width, int height, String text, int fontSize, int rotate, int bold, boolean underline, boolean reverse)
    {
        if (underline)
            CPCLCmd = "UNDERLINE ON\r\n";
        else {
            CPCLCmd = "UNDERLINE OFF\r\n";
        }
        portSendCmd(CPCLCmd);

        String CPCLCmd = "SETBOLD " + bold + "\r\n";
        portSendCmd(CPCLCmd);

        int family = 0;
        int size = 0;
        int ex = 1;
        int ey = 1;
        int Height = 0;
        int Width = 0;
        switch (fontSize)
        {
            case 1:
                family = 55;
                Height = 16;
                break;
            case 2:
                family = this.Family;
                Height = 24;
                break;
            case 3:
                family = 4;
                Height = 32;
                break;
            case 4:
                family = this.Family;
                Height = 48;
                ex = 2;
                ey = 2;
                break;
            case 5:
                family = 4;
                Height = 64;
                ex = 2;
                ey = 2;
                break;
            case 6:
                family = this.Family;
                Height = 72;
                ex = 3;
                ey = 3;
                break;
            case 7:
                family = 4;
                Height = 96;
                ex = 3;
                ey = 3;
                break;
            case 8:
                family = 3;
                Height = 20;
                size = 1;
                ex = 1;
                ey = 1;
                break;
            case 9:
                family = 4;
                Height = 56;
                size = 3;
                ex = 1;
                ey = 1;
                break;
            default:
                family = this.defaultFamily;
                Height = 16;
                size = 0;
                ex = 1;
                ey = 1;
        }
        CPCLCmd = "SETMAG " + ex + " " + ey + "\r\n";
        portSendCmd(CPCLCmd);

        switch (rotate)
        {
            case 1:
                CPCLCmd = "TEXT90 ";

                break;
            case 2:
                CPCLCmd = "TEXT180 ";

                break;
            case 3:
                CPCLCmd = "TEXT270 ";

                break;
            default:
                CPCLCmd = "TEXT ";
        }

        char[] array = text.toCharArray();
        String str = "";
        String SendCmd = "";
        for (int i = 0; i < array.length; ++i) {
            if ((char)(byte)array[i] != array[i]) {
                Width += Height;
                if (Width > width) {
                    SendCmd = CPCLCmd + family + " " + size + " " + text_x + " " + text_y + " " + str + "\r\n";
                    portSendCmd(SendCmd);
                    text_y += Height + 6;
                    Width = Height;
                    str = String.valueOf(array[i]);
                } else {
                    str = str + String.valueOf(array[i]);
                }
            } else {
                Width += Height / 2;
                if (Width > width) {
                    SendCmd = CPCLCmd + family + " " + size + " " + text_x + " " + text_y + " " + str + "\r\n";
                    portSendCmd(SendCmd);
                    text_y += Height + 6;
                    Width = Height;
                    str = String.valueOf(array[i]);
                } else {
                    str = str + String.valueOf(array[i]);
                }
            }
        }
        SendCmd = CPCLCmd + family + " " + size + " " + text_x + " " + text_y + " " + str + "\r\n";
        portSendCmd(SendCmd);

        if (reverse) {
            CPCLCmd = "INVERSE-LINE " + String.valueOf(text_x) + " " + String.valueOf(text_y) + " " +
                    String.valueOf(text_x + width) + " " + String.valueOf(text_y + Height) + " " +
                    String.valueOf(height) + "\r\n";
            portSendCmd(CPCLCmd);
        }
    }

    public void drawBarCode(int start_x, int start_y, String text, int type, int rotate, int linewidth, int height)
    {
        String command = "";
        String bartype = "";
        if ((rotate == 0) || (rotate == 2))
            command = "B";
        else if ((rotate == 1) || (rotate == 3))
            command = "VB";
        else {
            command = "B";
        }
        switch (type)
        {
            case 0:
                bartype = "39";
                break;
            case 1:
                bartype = "128";
                break;
            case 2:
                bartype = "93";
                break;
            case 3:
                bartype = "CODABAR";
                break;
            case 4:
                bartype = "EAN8";
                break;
            case 5:
                bartype = "EAN13";
                break;
            case 6:
                bartype = "UPCA";
                break;
            case 7:
                bartype = "UPCE";
                break;
            case 8:
                bartype = "I2OF5";
                break;
            default:
                bartype = "128";
        }

        String stringData = command + " " + bartype + " " + String.valueOf(linewidth - 1) + " " + "2" + " " +
                String.valueOf(height) + " " + String.valueOf(start_x) + " " + String.valueOf(start_y) + " " + text +
                "\r\n";

        portSendCmd(stringData);
    }

    public void drawQrCode(int start_x, int start_y, String text, int rotate, int ver, int lel)
    {
        String command = "";
        String levelText = "";
        if ((rotate == 0) || (rotate == 2))
            command = "B";
        else if ((rotate == 1) || (rotate == 3))
            command = "VB";
        else {
            command = "B";
        }
        if (ver < 2) {
            ver = 2;
        }
        if (ver > 6) {
            ver = 6;
        }
        switch (lel)
        {
            case 0:
                levelText = "L";
                break;
            case 1:
                levelText = "M";
                break;
            case 2:
                levelText = "Q";
                break;
            default:
                levelText = "H";
        }

        String stringData = command + " " + "QR" + " " + String.valueOf(start_x) + " " + String.valueOf(start_y) + " " +
                "M" + " " + "2" + " " + "U" + " " + String.valueOf(ver) + " " + levelText + "A," + " " + text + "\r\n" +
                "ENDQR\r\n\r\n";

        portSendCmd(stringData);
    }

    private String printHexString(byte[] b) {
        String a = "";

        for (int i = 0; i < b.length; ++i) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            a = a + hex;
        }

        return a;
    }

    public void drawGraphic(int start_x, int start_y, int bmp_size_x, int bmp_size_y, Bitmap bmp)
    {
        pkgs = 0;
        int ByteWidth = (bmp_size_x - 1) / 8 + 1;
        int ByteHeight = bmp_size_y;
        String Data = "";
        byte[] DataByte = new byte[ByteWidth * ByteHeight];
        int offset = 0;
        int cur_idx = 0;
        int last_idx = 0;
        for (int i = 0; i < bmp_size_y; ) {
            for (int j = 0; j < bmp_size_x; ++j) {
                cur_idx = i * ByteWidth + j / 8;
                if ((bmp.getPixel(j, i) & 0xFFFFFF) < 3092271) {
                    int tmp93_91 = cur_idx;
                    byte[] tmp93_89 = DataByte;
                    tmp93_89[tmp93_91] = (byte)(tmp93_89[tmp93_91] | 128 >> j % 8);
                } else {
                    int tmp114_112 = cur_idx;
                    byte[] tmp114_110 = DataByte;
                    tmp114_110[tmp114_112] = (byte)(tmp114_110[tmp114_112] & (128 >> j % 8 ^ 0xFFFFFFFF));
                }
            }
            ++i;
            if (((i - offset) * ByteWidth >= 1024) || (i == ByteHeight)) {
                Data = printHexString(getByte(DataByte, last_idx, cur_idx - last_idx + 1));
                last_idx = cur_idx + 1;
                String CPCLCmd = "EG " + ByteWidth + " " + (i - offset) + " " + start_x + " " + (start_y + offset) + " " +
                        Data + "\r\n";
                portSendCmd(CPCLCmd);
                offset = i;
                pkgs += 1;
            }
        }
    }

    public void drawGraphic2(int start_x, int start_y, int bmp_size_x, int bmp_size_y, Bitmap bmp)
    {
        String command = "";
        int bytesWidth;
        if (bmp_size_x % 8 == 0)
            bytesWidth = bmp_size_x / 8;
        else {
            bytesWidth = bmp_size_x / 8 + 1;
        }

        if ((((bytesWidth > 999) ? 1 : 0) | ((bmp_size_y > 65535) ? 1 : 0)) != 0) {
            return;
        }
        byte[] bmpData = imageProcess(bmp, bmp_size_x, bmp_size_y);

        command = "CG " + bytesWidth + " " + bmp_size_y + " " + String.valueOf(start_x) + " " +
                String.valueOf(start_y) + " ";
        String back = "\r\n\r\n";

        portSendCmd(command);
        portSendCmd(bmpData);

        portSendCmd(back);
    }

    private byte[] imageProcess(Bitmap bitmap, int width, int height)
    {
        int bytesWidth = 0;

        int j = 0;
        int index = 0;
        try
        {
            bytesWidth = (width % 8 == 0) ? width / 8 : width / 8 + 1;
            int byteSize = height * bytesWidth;
            byte[] bmpData = new byte[byteSize];

            for (int i = 0; i < byteSize; ++i) {
                bmpData[i] = 0;
            }

            while (j < height) {
                int[] tempArray = new int[width];
                bitmap.getPixels(tempArray, 0, width, 0, j, width, 1);
                int indexLine = 0;

                for (int i = 0; i < width; ++i) {
                    ++indexLine;
                    int pixel = tempArray[i];
                    if (indexLine > 8) {
                        indexLine = 1;
                        ++index;
                    }

                    if (pixel != -1) {
                        int temp = 1 << 8 - indexLine;
                        int red = Color.red(pixel);
                        int green = Color.green(pixel);
                        int blue = Color.blue(pixel);
                        if ((red + green + blue) / 3 < 128) {
                            bmpData[index] = (byte)(bmpData[index] | temp);
                        }
                    }
                }

                index = bytesWidth * (j + 1);
                ++j;
            }

            return bmpData;
        } catch (Exception e) {
            e.printStackTrace(); }
        return null;
    }

    private byte[] getByte(byte[] dataByte, int start, int length)
    {
        byte[] newDataByte = new byte[length];

        for (int i = 0; i < length; ++i) {
            newDataByte[i] = dataByte[(start + i)];
        }

        return newDataByte;
    }

    public String printerStatus() {
        if (this.Port.isOpen) {
            byte[] Cmd = { 16, 4, 5 };
            this.Port.flushReadBuffer();
            if (!(this.Port.write(Cmd, 0, 3))) {
                return "Print Write Error";
            }
            byte[] Rep = new byte[2];
            if (!(this.Port.read(Rep, 2, 2000))) {
                return "Print Read Error";
            }

            if (Rep[0] == 0) {
                return "OK";
            }
            if ((Rep[0] == 79) && (Rep[1] == 75)) {
                return "OK";
            }

            if ((Rep[0] & 0x10) != 0)
            {
                return "CoverOpened ";
            }
            if ((Rep[0] & 0x1) != 0)
            {
                return "NoPaper";
            }

            if ((Rep[0] & 0x8) != 0)
            {
                return "Printing";
            }
            if ((Rep[0] & 0x4) != 0)
            {
                return "BatteryLow";
            }

            return "OK";
        }

        return "QRPrinter is disconnect";
    }

    public String printerType()
    {
        return "QR";
    }

    public void feed() {
        if (this.Port.isOpen) {
            String stringData = "! 0 200 200 0 1\r\nPAGE-WIDTH 576\r\nGAP-SENSE\r\nFORM\r\nPRINT\r\n";

            portSendCmd(stringData);
        }
    }

    public static String Byte2Hex(Byte inByte)
    {
        return String.format("%02x", new Object[] { inByte }).toUpperCase();
    }

    public static String ByteArrToHex(byte[] inBytArr) {
        StringBuilder strBuilder = new StringBuilder();
        int j = inBytArr.length;
        for (int i = 0; i < j; ++i) {
            strBuilder.append(Byte2Hex(Byte.valueOf(inBytArr[i])));
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    public boolean checkValidPrinter(String address) {
        boolean result = false;
        if (this.Port.isOpen) {
            byte[] Rep = new byte[4];
            this.Port.flushReadBuffer();

            int[] data = QRSDK.CheckValidPrinter(77175792);

            byte[] test = new byte[8];
            for (int i = 0; i < 8; ++i) {
                test[i] = (byte)(data[i] & 0xFF);
            }

            byte[] sdata = new byte[6];
            sdata[0] = 30;
            sdata[1] = 101;
            for (int i = 0; i < 4; ++i) {
                sdata[(2 + i)] = (byte)data[i];
            }

            boolean writeResult = this.Port.write(sdata, 0, sdata.length);
            if ((writeResult) && (this.Port.read(Rep, 4, 3000))) {
                int[] revdata = new int[4];
                for (int j = 0; j < 4; ++j) {
                    revdata[j] = (Rep[j] & 0xFF);
                    if (data[(4 + j)] != revdata[j]) {
                        Log.e("checkValidPrinter", "false");
                        return false;
                    }
                }
                Log.e("checkValidPrinter", "success");
                result = true;
            } else {
                Log.e("checkValidPrinter", "false");
            }
        }

        return result;
    }

    public boolean checkValidPrinter2(String address) {
        boolean result = false;
        if (this.Port.isOpen) {
            byte[] Rep = new byte[1];
            this.Port.flushReadBuffer();
            byte[] cmdJni = { 30, 100, 67 };
            boolean writeResult = this.Port.write(cmdJni, 0, cmdJni.length);
            if ((writeResult) && (this.Port.read(Rep, 1, 3000)) && (Rep[0] >= 48) && (Rep[0] <= 57)) {
                result = true;
            }
        }
        return result;
    }

    public BluetoothPort getPort() {
        return Port;
    }


}