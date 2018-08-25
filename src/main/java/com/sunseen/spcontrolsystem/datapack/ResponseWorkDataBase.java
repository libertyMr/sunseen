package com.sunseen.spcontrolsystem.datapack;

import com.sunseen.spcontrolsystem.utils.LogMsg;

/**
 * 响应工作状态数据报
 */
public class ResponseWorkDataBase {
    protected byte[] head = new byte[24];
    protected byte[] end = new byte[4];

    public ResponseWorkDataBase() {
        setMessageHead();//默认设置报文头部
        setFrameEnd();
    }

    /**
     * 统一为5AA5,默认设置
     */
    public void setMessageHead() {
        head[0] = 0x5A;
        head[1] = (byte) 0xA5;
    }

    /**
     * @param bytes 设置报文数据段N长度
     */
    public void setPackLength(byte[] bytes) {
        if (bytes.length == 2) {
            head[2] = bytes[0];
            head[3] = bytes[1];
        } else {
            LogMsg.printErrorMsg("setPackLength bytes length != 2");
        }
    }

    /**
     * @param bytes 设置设备id
     */
    public void setCmdID(byte[] bytes) {
        head[4] = bytes[0];
        head[5] = bytes[1];
        head[6] = bytes[2];
        head[7] = bytes[3];
        head[8] = bytes[4];
        head[9] = bytes[5];
        head[10] = bytes[6];
        head[11] = bytes[7];
        head[12] = bytes[8];
        head[13] = bytes[9];
        head[14] = bytes[10];
        head[15] = bytes[11];
        head[16] = bytes[12];
        head[17] = bytes[13];
        head[18] = bytes[14];
        head[19] = bytes[15];
        head[20] = bytes[16];
    }

    /**
     * @param b 设置数据来源及去向
     */
    public void setFrameType(byte b) {
        head[21] = b;
    }

    /**
     * @param b 设置数据类型
     */
    public void setPackageType(byte b) {
        head[22] = b;
    }


    /**
     * @param b 设置CRC校验值
     */
    public void setCrc16(byte[] b) {
        end[0] = b[0];
        end[1] = b[1];
    }
    /**
     * 设置包结尾
     */
    public void setFrameEnd() {
        end[2] = 0x6A;
        end[3] = 0x69;
    }
}
