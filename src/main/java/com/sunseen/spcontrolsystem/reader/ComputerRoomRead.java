package com.sunseen.spcontrolsystem.reader;

import com.sunseen.spcontrolsystem.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 机房监测数据
 */
public class ComputerRoomRead {
    private static Map<String, byte[]> map = new HashMap<>();
    private String Time_Stamp = "";
    private float Temperature = 0;
    private float Humidity = 0;

    public ComputerRoomRead(byte[] cmd_id, byte[] bytes) {
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
     * @return 温度
     */
    public float getTemperature(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[4], time[5]});
            int l = CommonUtils.byte2Int(new byte[]{time[6], time[7]});
            Temperature = (float) (h + l * 0.01);
        }
        return Temperature;
    }

    /**
     * @param cmd_id
     * @return 获取湿度
     */
    public float getHumidity(String cmd_id) {
        byte[] time = map.get(cmd_id);
        if (time != null) {
            int h = CommonUtils.byte2Int(new byte[]{time[8], time[9]});
            int l = CommonUtils.byte2Int(new byte[]{time[10], time[11]});
            Humidity = (float) (h + l * 0.01);
        }
        return Humidity;
    }

//    public static void test()
//    {
//        Map<String, String> m = new HashMap<>();
//        m.put("111","2222");
//        m.put("111","3333");
//        m.put("111","4444");
//        m.put("222","555");
//        m.put("222","666");
//        Iterator<Map.Entry<String, String>> it = m.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, String> entry = it.next();
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }
//    }
//
//    public static void main(String[] arg)
//    {
//        ComputerRoomRead.test();
//    }
}
