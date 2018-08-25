package com.sunseen.spcontrolsystem.reader;

import com.sunseen.spcontrolsystem.utils.CommonUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 水质监测数据包接收数据有效数据
 */
public class WaterQualityRead {
    private static Map<String, byte[]> map = new HashMap<>();
    private String Time_Stamp = "";//采集时间
    private float HP = 0;
    private float ORP = 0;
    private float ResidualChlorine = 0;
    private float LimpidGrade = 0;
    private float ChlorineContent = 0;

    public WaterQualityRead(byte[] cmd_id, byte[] bytes) {
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
     * @return 获取HP
     */
    public float getHP(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[8], time[9]});
            int l = CommonUtils.byte2Int(new byte[]{time[10], time[11]});
            HP = (float) (h + l * 0.01);
        }
        return HP;
    }

    /**
     * @param cmd_id
     * @return 获取ORP值，-1000~1000
     */
    public float getORP(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            ORP = CommonUtils.byte2Int(new byte[]{time[12], time[13], time[14], time[15]});
        }
        return ORP;
    }

    /**
     * @param cmd_id
     * @return 余氯
     */
    public float getResidualChlorine(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[16], time[17]});
            int l = CommonUtils.byte2Int(new byte[]{time[18], time[19]});
            ResidualChlorine = (float) (h + l * 0.01);
        }
        return ResidualChlorine;
    }

    /**
     * @param cmd_id
     * @return 浑浊度
     */
    public float getLimpidGrade(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[20], time[21]});
            int l = CommonUtils.byte2Int(new byte[]{time[22], time[23]});
            LimpidGrade = (float) (h + l * 0.01);
        }
        return LimpidGrade;
    }

    /**
     * @param cmd_id
     * @return 获取制氯时氯气含量
     */
    public float getChlorineContent(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[24], time[25]});
            int l = CommonUtils.byte2Int(new byte[]{time[26], time[27]});
            ChlorineContent = (float) (h + l * 0.01);
        }
        return ChlorineContent;
    }
}
