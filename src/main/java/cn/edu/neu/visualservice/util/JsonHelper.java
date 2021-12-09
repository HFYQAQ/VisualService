package cn.edu.neu.visualservice.util;

import cn.edu.neu.visualservice.bean.InterMetricV2;

import java.util.*;

public class JsonHelper {
    private final boolean isArr;

    private Map<String, Object> map = new LinkedHashMap<>();
    private List<JsonHelper> list = new LinkedList<>();
    private String json = "";

    public JsonHelper() {
        this(false);
    }

    public JsonHelper(boolean isArr) {
        this.isArr = isArr;
    }

    public void put(String key, Object val) {
        if (isArr) {
            return;
        }

        map.put(key, val);
    }

    public void add(JsonHelper elem) {
        if (!isArr) {
            return;
        }

        list.add(elem);
    }

    public String format() {
        if (!isArr) {
            return formatFromMap();
        } else {
            return formatFromList();
        }
    }

    private String formatFromMap() {
        if (map == null || map.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            if (val instanceof JsonHelper) {
                val = val.toString();
            } else if (val instanceof String) {
                val = String.format("\"%s\"", val);
            }

            sb.append("\"").append(key).append("\":").append(val).append(",");
        }
        json = sb.toString();
        if (json.endsWith(",")) {
            json = json.substring(0, json.length() - 1);
        }
        json += "}";

        return json;
    }

    private String formatFromList() {
        if (list == null || list.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("[");
        for (JsonHelper elem : list) {
            sb.append(elem).append(",");
        }
        json = sb.toString();
        if (json.endsWith(",")) {
            json = json.substring(0, json.length() - 1);
        }
        json += "]";

        return json;
    }

    @Override
    public String toString() {
        if (json.equals("")) {
            format();
        }
        return json;
    }

    public static void main(String[] args) {
        String tRid = "14VM209DLP014VL209DM200";
        String dataVersion = "20191230";
        String adcode = "310000";
        String tp = "5mi";
        String endDt = "20210130";

        List<InterMetricV2> interMetricList = new ArrayList<InterMetricV2>() {
            {
                add(new InterMetricV2("14VM209DLX0", "14VLC09DLO014VM209DXP00", 0L, 883L, 25.24, 0.16, 19.36, 6.25, -1d));
                add(new InterMetricV2("14VM209DLX0", "14VLC09DLO014VM209DXP00", 0L, 884L, 15.24, 1.13, 9.36, 6.12, -1d));
                add(new InterMetricV2("14VM209DLX0", "14VLC09DLO014VM209DXP00", 0L, 885L, 22.24, 0.06, 1.36, 6.02, -1d));
            }
        };

        JsonHelper responseJson = new JsonHelper();
        JsonHelper dataJson = new JsonHelper(true);
        for (InterMetricV2 interMetric : interMetricList) {
            JsonHelper interMetricJson = new JsonHelper();
            interMetricJson.put("inter_id", interMetric.getInterId());
            interMetricJson.put("f_rid", interMetric.getfRid());
            interMetricJson.put("turn_dir_no", interMetric.getTurnDirNo());
            interMetricJson.put("t_rid", tRid); //
            interMetricJson.put("step_index", interMetric.getStepIndex());
            interMetricJson.put("data_version", dataVersion); //
            interMetricJson.put("adcode", adcode); //
            interMetricJson.put("tp", tp); //
            interMetricJson.put("travel_time", interMetric.getTravelTime());
            interMetricJson.put("stop_cnt", interMetric.getStopCnt());
            interMetricJson.put("delay_dur", interMetric.getDelayDur());
            interMetricJson.put("queue_len", interMetric.getQueueLen());
            interMetricJson.put("flow", -1d); //
            interMetricJson.put("dt", endDt); //

            dataJson.add(interMetricJson);
        }
        responseJson.put("data", dataJson);
        responseJson.put("error", null);
        responseJson.put("isError", false);
//        responseJson.put("requestId", "43f24712-ca10-4ef9-8fab-ba7dae490277");
        System.out.println(responseJson);
    }
}
