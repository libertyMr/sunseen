package com.sunseen.spcontrolsystem.reader;

import com.sunseen.spcontrolsystem.utils.CommonUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 热泵监测数据
 */
public class HeatPumpRead {
    private static Map<String, byte[]> map = new HashMap<>();
    private String Time_Stamp = "";
    private float WaterTemperature = 0;


    public HeatPumpRead(byte[] cmd_id, byte[] bytes) {
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
     * @return 水温度
     */
    public float getWaterTemperature(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[4], time[5]});
            int l = CommonUtils.byte2Int(new byte[]{time[6], time[7]});
            WaterTemperature = (float) (h + l * 0.01);
        }
        return WaterTemperature;
    }
}
