package com.sunseen.spcontrolsystem.reader;

import com.sunseen.spcontrolsystem.utils.CommonUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 三集一体监测数据
 */
public class ThreeUnitRead {
    private static Map<String, byte[]> map = new HashMap<>();
    private String Time_Stamp = "";
    private float IndoorTemperature = 0;
    private float IndoorHumidity = 0;
    private float TitaniumGun = 0;//钛炮出水温度
    private float CO2 = 0;
    private float AirQuality = 0;


    public ThreeUnitRead(byte[] cmd_id, byte[] bytes) {
        String s = new String(cmd_id);
//        isSet = false;
//        Iterator<Map.Entry<String, byte[]>> it = map.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, byte[]> entry = it.next();
//            if (entry.getKey().equals(s)) {
//                isSet = true;
//                break;
//            }
//        }
//        if (isSet) {
//            //对于已存在集合中的数据先删除
//            map.remove(s);
//        }
        map.put(s, bytes);
    }

    /**
     * @param cmd_id
     * @return 获取采集时间
     */
    public String getTimeStamp(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            Time_Stamp = String.valueOf(CommonUtils.byte2Int(new byte[]{time[0], time[1], time[2], time[3]}));
        }
        return Time_Stamp;
    }

    /**
     * @param cmd_id
     * @return 室外温度
     */
    public float getIndoorTemperature(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[4], time[5]});
            int l = CommonUtils.byte2Int(new byte[]{time[6], time[7]});
            IndoorTemperature = (float) (h + l * 0.01);
        }
        return IndoorTemperature;
    }

    /**
     * @param cmd_id
     * @return 室外湿度
     */
    public float getIndoorHumidity(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[8], time[9]});
            int l = CommonUtils.byte2Int(new byte[]{time[10], time[11]});
            IndoorHumidity = (float) (h + l * 0.01);
        }
        return IndoorHumidity;
    }

    /**
     * @param cmd_id
     * @return 钛炮出水温度
     */
    public float getTitaniumGun(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[12], time[13]});
            int l = CommonUtils.byte2Int(new byte[]{time[14], time[15]});
            TitaniumGun = (float) (h + l * 0.01);
        }
        return TitaniumGun;
    }

    /**
     * @param cmd_id
     * @return CO2含量
     */
    public float getCO2(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[16], time[17]});
            int l = CommonUtils.byte2Int(new byte[]{time[18], time[19]});
            CO2 = (float) (h + l * 0.01);
        }
        return CO2;
    }

    /**
     * @param cmd_id
     * @return 空气质量
     */
    public float getAirQuality(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[20], time[21]});
            int l = CommonUtils.byte2Int(new byte[]{time[22], time[23]});
            AirQuality = (float) (h + l * 0.01);
        }
        return AirQuality;
    }
}
