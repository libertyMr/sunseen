package com.sunseen.spcontrolsystem.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.sunseen.spcontrolsystem.activity.BaseActivity;
import com.sunseen.spcontrolsystem.activity.MainActivity;
import com.sunseen.spcontrolsystem.model.SystemConfig;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    /**
     * @param bytes
     * @return bytes 转 int
     */
    public synchronized static int byte2Int(byte[] bytes) {
        if (bytes.length < 4) {
            if (bytes.length != 0) {
                byte[] bytes1 = new byte[4];
                if (bytes.length == 3) {
                    bytes1[0] = 0x00;
                    bytes1[1] = bytes[0];
                    bytes1[2] = bytes[1];
                    bytes1[3] = bytes[2];
                } else if (bytes.length == 2) {
                    bytes1[0] = 0x00;
                    bytes1[1] = 0x00;
                    bytes1[2] = bytes[0];
                    bytes1[3] = bytes[1];
                } else if (bytes.length == 1) {
                    bytes1[0] = 0x00;
                    bytes1[1] = 0x00;
                    bytes1[2] = 0x00;
                    bytes1[3] = bytes[0];
                }

                return (bytes1[0] & 0xff) << 24
                        | (bytes1[1] & 0xff) << 16
                        | (bytes1[2] & 0xff) << 8
                        | (bytes1[3] & 0xff);
            } else {
                return 0;
            }
        }
        return (bytes[0] & 0xff) << 24
                | (bytes[1] & 0xff) << 16
                | (bytes[2] & 0xff) << 8
                | (bytes[3] & 0xff);
    }

    /**
     * @param seconds
     * @return 世纪秒法转年月时分秒
     */
    public synchronized static String timeFormat(String seconds) {
        if (seconds == null)
            return " ";
        else {
            Date date = new Date();
            try {
                date.setTime(Long.parseLong(seconds) * 1000);
            } catch (NumberFormatException nfe) {
                LogMsg.printInfoMsg("time NumberFormatException");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
    }

    /**
     * @param bytes
     * @return bytes 转 String，GB2312编码
     */
    public synchronized static String bytesToString(byte[] bytes) {
        try {
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] == (byte) 0xFF) {
                    bytes[i] = 0x00;
                }
            }
            String str = new String(bytes, "GB2312");
            return str.trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 检测是否付费
     */
    public static boolean checkOverdue(MainActivity act) {
        String date = act.dbManager.queryConfigValue(SystemConfig.FIRST_PHASE_TIME, "2018-1-1 0:0");
        return getTime(date);
    }

    /**
     * @param date
     * @return
     */
    public static boolean getTime(String date) {
        if (date == null || date.equals(""))
            return false;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        java.util.Date date1 = null;
        java.util.Date date2 = null;
        try {
            date1 = myFormatter.parse(date);
            date2 = new Date(System.currentTimeMillis());
            if (date1.getTime() / 1000 - date2.getTime() / 1000 > 600) {
                LogMsg.printInfoMsg("checkOverdue getTime return true");
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return 比较两个时间先后, false two小于first,true two 大于first
     */
    public static boolean compareTime(String first, String two) {
        if (TextUtils.isEmpty(first) || TextUtils.isEmpty(two)) {
            return false;
        }
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        java.util.Date date1 = null;
        java.util.Date date2 = null;

        try {
            date1 = myFormatter.parse(first);
            date2 = myFormatter.parse(two);
            if (date2.getTime() / 1000 - date1.getTime() / 1000 > 60 * 60 * 24) {
                LogMsg.printInfoMsg("checkOverdue getTime return true");
                return true;
            }

        } catch (Exception e) {
            return false;
        }
        return false;
    }


    public static boolean checkPWD(BaseActivity act, String pwd) {
        String first_pwd = act.dbManager.queryConfigValue(SystemConfig.FIRST_PHASE_PWD, "");
        String second_pwd = act.dbManager.queryConfigValue(SystemConfig.SECOND_PHASE_PWD, "");
        String third_pwd = act.dbManager.queryConfigValue(SystemConfig.THIRD_PHASE_PWD, "");
        String fourth_pwd = act.dbManager.queryConfigValue(SystemConfig.FOURTH_PHASE_PWD, "");
        String fifth_pwd = act.dbManager.queryConfigValue(SystemConfig.FIFTH_PHASE_PWD, "");
        String sixth_pwd = act.dbManager.queryConfigValue(SystemConfig.SIXTH_PHASE_PWD, "");
        if (TextUtils.isEmpty(first_pwd) || TextUtils.isEmpty(second_pwd) || TextUtils.isEmpty(third_pwd)
                || TextUtils.isEmpty(fourth_pwd) || TextUtils.isEmpty(fifth_pwd) || TextUtils.isEmpty(sixth_pwd)) {
            Toast.makeText(act,"密码未设置",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pwd.equalsIgnoreCase(first_pwd)) {
            String third_time = act.dbManager.queryConfigValue(SystemConfig.THIRD_PHASE_TIME,"2018-1-1 0:0");
            act.dbManager.insertSystemConfig(new SystemConfig(SystemConfig.FIRST_PHASE_TIME,third_time));
            act.dbManager.insertSystemConfig(new SystemConfig(SystemConfig.SECOND_PHASE_TIME,third_time));
            return true;
        }
        if (pwd.equalsIgnoreCase(second_pwd)){
            String second_time = act.dbManager.queryConfigValue(SystemConfig.SECOND_PHASE_TIME,"2018-1-1 0:0");
            act.dbManager.insertSystemConfig(new SystemConfig(SystemConfig.FIRST_PHASE_TIME,second_time));
            return true;
        }

        return false;
    }

//    public static void main(String[] args) {
//////        int i = m.byte2Int(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFC,(byte)0x18});
////        System.out.println(CommonUtils.bytesToString(new byte[]{(byte)0xBD, (byte)0xAD, (byte)0xCE, (byte)0xF7,
////                (byte)0xB1, (byte)0xB4, (byte)0xD4, (byte)0xCA, (byte)0xBD, (byte)0xDA,
////                (byte)0xC4, (byte)0xDC, (byte)0xBF, (byte)0xC6, (byte)0xBC, (byte)0xBC,
////                (byte)0xD3, (byte)0xD0, (byte)0xCF, (byte)0xDE, (byte)0xB9, (byte)0xAB, (byte)0xCB, (byte)0xBE}));
//        System.out.println(CommonUtils.bytesToString(new byte[]{
//                (byte)0xB8, (byte)0xD3, (byte)0xD6, (byte)0xDD, (byte)0xB1,
//                (byte)0xB4, (byte)0xD4, (byte)0xCA, (byte)0xD3, (byte)0xBE,
//                (byte)0xB3, (byte)0xD8, (byte)0xC9, (byte)0xE8, (byte)0xB1,
//                (byte)0xB8, (byte)0xD3, (byte)0xD0, (byte)0xCF, (byte)0xDE,
//                (byte)0xB9, (byte)0xAB, (byte)0xCB, (byte)0xBE, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
//        }));
//
//        System.out.println(CommonUtils.bytesToString(new byte[]{0x42, 0x59, 0x30, 0x36, 0x30, 0x31, 0x31, 0x38,
//                0x30, 0x34, 0x31, 0x33, 0x30, 0x30, 0x30, 0x30, 0x31}));
//        getTime("2018-1-1 00:00");
//    }
}
