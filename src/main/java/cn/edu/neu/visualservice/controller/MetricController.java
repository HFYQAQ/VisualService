package cn.edu.neu.visualservice.controller;

import cn.edu.neu.visualservice.bean.InterMetric;
import cn.edu.neu.visualservice.bean.Statistic;
import cn.edu.neu.visualservice.service.StatisticService;
import cn.edu.neu.visualservice.struct.PerformanceMetric;
import cn.edu.neu.visualservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MetricController {
    @Autowired
    private StatisticService statisticService;

    @RequestMapping("/metric/performance")
    public String getStatistic(@RequestParam(value = "dt", defaultValue = "20210327") String dt,
                                       @RequestParam(value = "step_index_1mi", defaultValue = "884") Long stepIndex1mi) {
        System.out.println("/metric/performance");

        // flink
        // prepare data
        Map<String, List<Statistic>> statisticsPerJob = statisticService.queryStatisticForFlink(dt, stepIndex1mi)
                .stream()
                .collect(Collectors.groupingBy(Statistic::getJobName));
        List<Statistic> statistics = null;
        for (Map.Entry<String, List<Statistic>> entry : statisticsPerJob.entrySet()) {
            statistics = entry.getValue();
            break;
        }
        if (statistics == null || statistics.size() == 0) {
            return "{}";
        }
        // calculate
        PerformanceMetric performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);
        // to json
        String jsonForFlink = Utils.performanceMetric2Json(performanceMetric);

        // gaia
        statisticsPerJob = statisticService.queryStatisticForGaia(dt, stepIndex1mi)
                .stream()
                .collect(Collectors.groupingBy(Statistic::getJobName));
        statistics = null;
        for (Map.Entry<String, List<Statistic>> entry : statisticsPerJob.entrySet()) {
            statistics = entry.getValue();
            break;
        }
        if (statistics == null || statistics.size() == 0) {
            return "{}";
        }
        // calculate
        performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);
        // to json
        String jsonForGaia = Utils.performanceMetric2Json(performanceMetric);

        // to json
        // { "flink": flinkjson, "gaia": ga
        String json = "{\"flink\": " + jsonForFlink + ", \"gaia\": " + jsonForGaia + "}";
        return json;
    }

    @RequestMapping("/metric/performance/flink")
    public String getStatisticForFlink(@RequestParam(value = "dt", defaultValue = "20210327") String dt,
                               @RequestParam(value = "step_index_1mi", defaultValue = "884") Long stepIndex1mi) {
        System.out.println("/metric/performance/flink");

        // prepare data
        Map<String, List<Statistic>> statisticsPerJob = statisticService.queryStatisticForFlink(dt, stepIndex1mi)
                .stream()
                .collect(Collectors.groupingBy(Statistic::getJobName));
        List<Statistic> statistics = null;
        for (Map.Entry<String, List<Statistic>> entry : statisticsPerJob.entrySet()) {
            statistics = entry.getValue();
            break;
        }
        if (statistics == null || statistics.size() == 0) {
            return "{}";
        }

        // calculate
        PerformanceMetric performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);

        // to json
        String json = Utils.performanceMetric2Json(performanceMetric);

        return json;
    }

    @RequestMapping("/metric/performance/gaia")
    public String getStatisticForGaia(@RequestParam(value = "dt", defaultValue = "20210327") String dt,
                               @RequestParam(value = "step_index_1mi", defaultValue = "884") Long stepIndex1mi) {
        System.out.println("/metric/performance/gaia");

        Map<String, List<Statistic>> statisticsPerJob = statisticService.queryStatisticForGaia(dt, stepIndex1mi)
                .stream()
                .collect(Collectors.groupingBy(Statistic::getJobName));

        List<Statistic> statistics = null;
        for (Map.Entry<String, List<Statistic>> entry : statisticsPerJob.entrySet()) {
            statistics = entry.getValue();
            break;
        }
        if (statistics == null || statistics.size() == 0) {
            return "{}";
        }

        // calculate
        PerformanceMetric performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);

        // to json
        String json = Utils.performanceMetric2Json(performanceMetric);

        return json;
    }

    @RequestMapping("/metric/inter/info")
    public String getInterList() {
        System.out.println("/metric/inter/info");

        List<InterMetric> interMetrics = statisticService.queryInterNameList();
        Map<String, String> interInfosMap = new HashMap<>();
        for (InterMetric interMetric : interMetrics) {
            interInfosMap.put(interMetric.getInterId(), interMetric.getInterName());
        }
        if (interInfosMap.size() == 0) {
            return "{}";
        }

        // to json
        StringBuilder sb = new StringBuilder("{\"interInfoList\":[");
        for (Map.Entry<String, String> entry : interInfosMap.entrySet()) {
            sb
                    .append("{\"interId\":\"")
                    .append(entry.getKey())
                    .append("\",\"interName\":\"")
                    .append(entry.getValue())
                    .append("\"},");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]}");
        String json = sb.toString();

        return json;
    }

    @RequestMapping("/metric/inter/getListByInter")
    public String getInterList(@RequestParam(value = "inter_name", defaultValue = "") String interName) {
        System.out.println("/metric/inter/getListByInter");

        Map<String, List<InterMetric>> interMetricsByRidMap = statisticService.queryListByInter(interName)
                .stream()
                .collect(Collectors.groupingBy(InterMetric::getRid));

        // to json
        StringBuilder sb = new StringBuilder("{\"interName\":\"");
        sb.append(interName).append("\",\"metricsByInter\":[");
        for (Map.Entry<String, List<InterMetric>> entry : interMetricsByRidMap.entrySet()) {
            List<InterMetric> list = entry.getValue();
            if (list == null || list.size() == 0) {
                continue;
            }
            String rName = list.get(0).getRName();

            sb
                    .append("{\"fRName\":\"")
                    .append(rName)
                    .append("\",\"turnDirList\":[");
            for (InterMetric interMetric : list) {
                sb
                        .append("{\"turnDirNo\":")
                        .append(interMetric.getTurnDirNo())
                        .append(",\"travelTime\":")
                        .append(interMetric.getTravelTime())
                        .append(",\"delay\":")
                        .append(interMetric.getDelay())
                        .append(",\"stopCnt\":")
                        .append(interMetric.getStopCnt())
                        .append(",\"queue\":")
                        .append(interMetric.getQueue())
                        .append("},");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]},");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]}");
        String json = sb.toString();

        return json;
    }

    private PerformanceMetric calculatePerformanceMetric(List<Statistic> statistics, String dt, Long stepIndex1mi) {
        long totalAmount = 0;
        long maxDuration = 0;
        for (Statistic statistic : statistics) {
            totalAmount += statistic.getAmount();
            maxDuration = Math.max(maxDuration, statistic.getDuration());
        }
        double throughput = totalAmount * 1.0 / maxDuration * 1000;
        double delay = maxDuration * 1.0 / totalAmount;
        return new PerformanceMetric(statistics.get(0).getJobName(), dt, stepIndex1mi, throughput, delay);
    }
}
