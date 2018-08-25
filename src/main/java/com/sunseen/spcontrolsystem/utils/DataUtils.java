package com.sunseen.spcontrolsystem.utils;

import com.sunseen.spcontrolsystem.reader.WaterCycleRead;
import com.sunseen.spcontrolsystem.reader.WaterQualityRead;

/**
 * 数据处理类
 * by xiaochunhui
 */
public class DataUtils {

    public synchronized static boolean sendPacket(byte[] msg) {
        return true;
    }

    /**
     * 对接收数据进行解析
     *
     * @param bytes
     */
    public synchronized static void readData(byte[] bytes) {
        if (checkData(bytes)) {
            LogMsg.printDebugMsg("readData-->" + bytes.toString());
            switch (bytes[21]) {
                case 0x01://监测数据响应报（监测装置→上位机）
                    LogMsg.printDebugMsg("readData 0x02");
                    monitorPacket(bytes);
                    break;
                case 0x04://控制响应报（监测装置→上位机）
                    LogMsg.printDebugMsg("readData 0x04");
                    controlPacket(bytes);
                    break;
                case 0x05://工作状态报（监测装置→上位机）
                    LogMsg.printDebugMsg("readData 0x06");
                    workingConditionPacket(bytes);
                    break;
//                case 0x02://监测数位据报（上位机→监测装置）
//                    break;
//                case 0x06://工作状态响应报（上位机→监测装置）
//                    break;
//                case 0x03://控制数据报（上机→监测装置）
//                    break;
            }
        }
    }

    /**
     * @return 对发送控制帧数据进行拼接
     */
    public synchronized static byte[] sendDataSplicing(byte model) {

        return null;
    }


    /**
     * @param bytes 监测数据响应报解析
     */
    public synchronized static void monitorPacket(byte[] bytes) {
        int dataLength = 0;
        switch (bytes[22]) {
            case 0x01://水质监测数据报
                dataLength = CommonUtils.byte2Int(new byte[]{bytes[2], bytes[3]});
                byte[] dataByte = new byte[dataLength];
                for (int i = 0; i < dataLength; i++) {
                    dataByte[i] = bytes[22 + i];
                }
                new WaterQualityRead(new byte[]{bytes[4], bytes[5], bytes[6], bytes[7],
                        bytes[8], bytes[9], bytes[10], bytes[11],
                        bytes[12], bytes[13], bytes[14], bytes[15],
                        bytes[16], bytes[17], bytes[18], bytes[19], bytes[20]}, dataByte);
                break;
            case 0x02://水质监测数据报预留字段
            case 0x03:
            case 0x04:
                break;
            case 0x05://水循环监测数据报
                dataLength = CommonUtils.byte2Int(new byte[]{bytes[2], bytes[3]});
                byte[] dataByte2 = new byte[dataLength];
                for (int i = 0; i < dataLength; i++) {
                    dataByte2[i] = bytes[22 + i];
                }
                new WaterCycleRead(new byte[]{bytes[4], bytes[5], bytes[6], bytes[7],
                        bytes[8], bytes[9], bytes[10], bytes[11],
                        bytes[12], bytes[13], bytes[14], bytes[15],
                        bytes[16], bytes[17], bytes[18], bytes[19], bytes[20]}, dataByte2);
                break;
            case 0x06://水循环监测数据报预留字段
            case 0x07:
                break;
            case 0x08://泳池热泵监测数据报
                break;
            case 0x09://泳池热泵类监测数据报预留字段
            case 0x0A:
            case 0x0B:
            case 0x0C:
            case 0x0D:
            case 0x0E:
            case 0x0F:
                break;
            case 0x10://泳池三集一体卧式单压缩机系统
                break;
            case 0x11://泳池三集一体卧式双压缩机系统
                break;
            case 0x12://泳池三集一体卧式三压缩机系统
                break;
            case 0x13://泳池三集一体卧式四压缩机系统
                break;
            case 0x14://泳池三集一体立式单压缩机系统
                break;
            case 0x15://泳池三集一体立式双压缩机系统
                break;
            case 0x16://泳池三集一体保留字段
            case 0x17:
            case 0x18:
            case 0x19:
            case 0x1A:
                break;
            case 0x1B://自动砂缸监测数据报
                break;
            case 0x1C://IO砂缸监测数据报预留字段
                break;
            case 0x1D://自动砂缸监测数据报预留字段
            case 0x1E:
            case 0x1F:
                break;
            case 0x20://盐氯机监测数据报
                break;
            case 0x21://机房监测数据报
                break;
            case 0x22://机房监测数据报预留字段
            case 0x23:
            case 0x24:
            case 0x25:
                break;
            case 0x26://保温覆盖监测数据报
                break;
            case 0x27://水下灯监测数据报
                break;
            case 0x28://水下灯监测数据报预留字段
            case 0x29:
            case 0x2A:
                break;
            case 0x2B://泳池SPA监测数据报
                break;
            case 0x2C://泳池SPA监测数据报预留字段
            case 0x2D:
            case 0x2E:
            case 0x2F:
            case 0x30:
                break;
            case 0x31://泳池排给水监测数据报
                break;
            case 0x32://其他预留监测数据报预留字段
            case 0x33:
            case 0x34:
            case 0x35:
            case 0x36:
            case 0x37:
            case 0x38:
            case 0x39:
            case 0x3A:
            case 0x3B:
            case 0x3C:
            case 0x3D:
            case 0x3E:
            case 0x3F:
            case 0x40:
            case 0x41:
            case 0x42:
            case 0x43:
            case 0x44:
            case 0x45:
            case 0x46:
            case 0x47:
            case 0x48:
            case 0x49:
            case 0x4A:
            case 0x4B:
            case 0x4C:
            case 0x4D:
            case 0x4E:
            case 0x4F:
            case 0x50:
            case 0x51:
            case 0x52:
            case 0x53:
            case 0x54:
            case 0x55:
            case 0x56:
            case 0x57:
            case 0x58:
            case 0x59:
            case 0x5A:
            case 0x5B:
            case 0x5C:
            case 0x5D:
            case 0x5E:
            case 0x5F:
                break;
        }
    }

    /**
     * @param bytes 控制数据响应报解析
     */
    public synchronized static void controlPacket(byte[] bytes) {
        switch (bytes[22]) {
            case 0x60://水质控制数据报
                break;
            case 0x61://水循环控制数据报
                break;
            case 0x62://泳池热泵控制数据报
                break;
            case 0x63://泳池三集一体控制数据报
                break;
            case 0x64://自动砂缸控制数据报
                break;
            case 0x65://盐氯机控制数据报
                break;
            case 0x66://机房控制数据报
                break;
            case 0x67://保温覆盖控制数据报
                break;
            case 0x68://水下灯控制数据报
                break;
            case 0x69://泳池SPA控制数据报
                break;
            case 0x6A://泳池排给水控制数据报
                break;
            case 0x6B://远程升级数据报：软件数据报
                break;
            case 0x6C://远程升级数据报：软件数据报下发结束数据报
                break;
            case 0x6D://远程升级数据报：软件数据报补包数据上传
                break;
            case 0x6E://其他预留控制数据报
            case 0x6F:
            case 0x70:
            case 0x71:
            case 0x72:
            case 0x73:
            case 0x74:
            case 0x75:
            case 0x76:
            case 0x77:
            case 0x78:
            case 0x79:
            case 0x7A:
            case 0x7B:
            case 0x7C:
            case 0x7D:
            case 0x7E:
            case 0x7F:
            case (byte) 0x80:
            case (byte) 0x81:
            case (byte) 0x82:
            case (byte) 0x83:
            case (byte) 0x84:
            case (byte) 0x85:
            case (byte) 0x86:
            case (byte) 0x87:
            case (byte) 0x88:
            case (byte) 0x89:
            case (byte) 0x8A:
            case (byte) 0x8B:
            case (byte) 0x8C:
            case (byte) 0x8D:
            case (byte) 0x8E:
            case (byte) 0x8F:
            case (byte) 0x90:
            case (byte) 0x91:
            case (byte) 0x92:
            case (byte) 0x93:
            case (byte) 0x94:
            case (byte) 0x95:
            case (byte) 0x96:
            case (byte) 0x97:
            case (byte) 0x98:
            case (byte) 0x99:
            case (byte) 0x9A:
            case (byte) 0x9B:
            case (byte) 0x9C:
            case (byte) 0x9D:
            case (byte) 0x9E:
            case (byte) 0x9F:
            case (byte) 0xA0:
            case (byte) 0xA1:
            case (byte) 0xA2:
            case (byte) 0xA3:
            case (byte) 0xA4:
            case (byte) 0xA5:
            case (byte) 0xA6:
            case (byte) 0xA7:
            case (byte) 0xA8:
            case (byte) 0xA9:
            case (byte) 0xAA:
            case (byte) 0xAB:
            case (byte) 0xAC:
            case (byte) 0xAD:
            case (byte) 0xAE:
            case (byte) 0xAF:
            case (byte) 0xB0:
            case (byte) 0xB1:
            case (byte) 0xB2:
            case (byte) 0xB3:
            case (byte) 0xB4:
            case (byte) 0xB5:
            case (byte) 0xB6:
            case (byte) 0xB7:
            case (byte) 0xB8:
            case (byte) 0xB9:
            case (byte) 0xBA:
            case (byte) 0xBB:
            case (byte) 0xBC:
            case (byte) 0xBD:
            case (byte) 0xBE:
            case (byte) 0xBF:
            case (byte) 0xC0:
            case (byte) 0xC1:
            case (byte) 0xC2:
            case (byte) 0xC3:
            case (byte) 0xC4:
            case (byte) 0xC5:
            case (byte) 0xC6:
            case (byte) 0xC7:
            case (byte) 0xC8:
            case (byte) 0xC9:
            case (byte) 0xCA:
            case (byte) 0xCB:
            case (byte) 0xCC:
            case (byte) 0xCD:
            case (byte) 0xCE:
            case (byte) 0xCF:
            case (byte) 0xD0:
            case (byte) 0xD1:
            case (byte) 0xD2:
            case (byte) 0xD3:
            case (byte) 0xD4:
            case (byte) 0xD5:
            case (byte) 0xD6:
            case (byte) 0xD7:
            case (byte) 0xD8:
            case (byte) 0xD9:
            case (byte) 0xDA:
            case (byte) 0xDB:
            case (byte) 0xDC:
            case (byte) 0xDD:
            case (byte) 0xDE:
            case (byte) 0xDF:
            case (byte) 0xE0:
            case (byte) 0xE1:
            case (byte) 0xE2:
            case (byte) 0xE3:
            case (byte) 0xE4:
            case (byte) 0xE5:
                break;
        }
    }

    /**
     * @param bytes 工作状态响应报解析
     */
    public synchronized static void workingConditionPacket(byte[] bytes) {
        switch (bytes[22]) {
            case (byte) 0xE6://预留字段
                break;
            case (byte) 0xE7://基本信息报
                break;
            case (byte) 0xE8://工作状态报
                break;
            case (byte) 0xE9://其他报文预留字段
            case (byte) 0xEA:
            case (byte) 0xEB:
            case (byte) 0xEC:
            case (byte) 0xED:
            case (byte) 0xEE:
            case (byte) 0xEF:
            case (byte) 0xF0:
            case (byte) 0xF1:
            case (byte) 0xF2:
            case (byte) 0xF3:
            case (byte) 0xF4:
            case (byte) 0xF5:
            case (byte) 0xF6:
            case (byte) 0xF8:
            case (byte) 0xF9:
            case (byte) 0xFA:
            case (byte) 0xFB:
            case (byte) 0xFC:
            case (byte) 0xFD:
            case (byte) 0xFE:
                break;
        }
    }


    public synchronized static boolean checkData(byte[] bytes) {
        byte[] checkBytes = new byte[bytes.length - 4];
        for (int i = 0; i < bytes.length - 4; i++) {
            checkBytes[i] = bytes[i];
        }
        int crc = CRCCheck.calcCrc16(checkBytes);
        if (((byte) (crc >> 8) == bytes[bytes.length - 4]) && ((byte) (crc & 0x00ff) == bytes[bytes.length - 3])) {
            return true;
        } else {
            return false;
        }
    }

//    public synchronized static
//    public static void main(String[] args) {
//
//        System.out.println(DataUtils.checkData(new byte[]{0x5A, (byte) 0xA5, 0x00, 0x04, 0x42, 0x59, 0x30, 0x39, 0x30, 0x31, 0x31, 0x38,
//                0x30, 0x34, 0x31, 0x33, 0x30, 0x30, 0x30, 0x30, 0x31, 0x01, 0x21, 0x00, 0x00, 0x00, 0x00,
//                (byte) 0xF4, (byte) 0xAB}));
//    }
}
