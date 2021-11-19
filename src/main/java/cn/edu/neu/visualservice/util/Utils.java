package cn.edu.neu.visualservice.util;

import cn.edu.neu.visualservice.struct.PerformanceMetric;

public class Utils {
    public static String performanceMetric2Json(PerformanceMetric performanceMetric) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"performanceMetric\":{\"throughput\":\"")
                .append(performanceMetric.getThroughput())
                .append("\",\"timeDelay\":\"")
                .append(performanceMetric.getDelay())
                .append("\"}}");
        return sb.toString();
    }
}
