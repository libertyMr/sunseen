package com.sunseen.spcontrolsystem.datapack;

import com.sunseen.spcontrolsystem.utils.LogMsg;

/**
 * 控制数据报
 * head 2
 * pack_length 2
 * CMD_ID 17
 * frame_type 1
 * packet_type 1
 * Request_set_flag 1 参数配置类型标志位 0x00查询， 0x01设置
 * parm_lenht 2 配置/查询长度
 * parm_addr 2 配置/查询起始地址
 * parm N 数据区
 * CRC16校验 2
 * frame_end 2
 */
public class ControlDataBase {
    protected byte[] head = new byte[27];
    protected byte[] end = new byte[4];

    public ControlDataBase() {
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
//    Request_set_flag

    /**
     * @param b 参数配置类型标志位 0x00查询， 0x01设置
     */
    public void setRequestSetFlag(byte b) {
        head[23] = b;
    }

    /**
     * @param b 设置查询/设置的长度
     */
    public void setParmLenht(byte[] b) {
        head[24] = b[0];
        head[25] = b[1];
    }

    /**
     * @param b 设置/查询的首地址
     */
    public void setParmAddr(byte[] b) {
        head[26] = b[0];
        head[27] = b[1];
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

    /**
     * @param data
     * @return 获取数据包
     */
    public byte[] getPackage(byte[] data) {
        int length_byte = data.length + head.length + end.length;
        byte[] all_byte = new byte[length_byte];
        System.arraycopy(head, 0, all_byte, 0, head.length);
        System.arraycopy(data, 0, all_byte, head.length, data.length);
        System.arraycopy(end, 0, all_byte, head.length + data.length, end.length);
        return all_byte;
    }

    /**
     * @param bArray
     * @return bytes转String
     */
    public String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

//    public static void main(String[] arg)
//    {
//        MonitoringDataBase b = new MonitoringDataBase();
//        System.out.println(b.bytesToHexString(b.getPackage(new byte[]{0x42, 0x59, 0x31, 0x30, 0x30, 0x31, 0x31, 0x38, 0x30, 0x34, 0x31, 0x33, 0x30, 0x30, 0x30, 0x30, 0x31})));
////        System.out.println(new String(b.getPackage(new byte[]{0x01,0x01}) ,));
//    }
}
