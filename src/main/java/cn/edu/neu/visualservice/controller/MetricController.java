package cn.edu.neu.visualservice.controller;

import cn.edu.neu.visualservice.bean.InterMetric;
import cn.edu.neu.visualservice.bean.InterMetricV2;
import cn.edu.neu.visualservice.bean.Statistic;
import cn.edu.neu.visualservice.service.StatisticService;
import cn.edu.neu.visualservice.struct.PerformanceMetric;
import cn.edu.neu.visualservice.util.JsonHelper;
import cn.edu.neu.visualservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MetricController {
    @Autowired
    private StatisticService statisticService;

    @RequestMapping(value="/metric/performance", produces = {"application/json;charset=UTF-8"})
    public String getStatistic(@RequestParam(value = "dt", defaultValue = "20210327") String dt,
                                       @RequestParam(value = "start_step_index_1mi", defaultValue = "0") Long startStepIndex1mi,
                                       @RequestParam(value = "end_step_index_1mi", defaultValue = "1439") Long endStepIndex1mi) {
        System.out.println("/metric/performance");

        // 只显示当前半小时内的数据
        startStepIndex1mi = endStepIndex1mi - 29;

        JsonHelper arrJsonForFlink142 = new JsonHelper(true);
        statisticService.queryStatisticForFlink142(dt, startStepIndex1mi, endStepIndex1mi)
                .stream()
                .collect(Collectors.groupingBy(Statistic::getStepIndex1mi))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> {
                    Long stepIndex1mi = entry.getKey();
                    List<Statistic> statistics = entry.getValue();

                    PerformanceMetric performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);
                    JsonHelper json = new JsonHelper();
                    json.put("step_index", stepIndex1mi);
                    json.put("throughput", performanceMetric.getThroughput());
                    json.put("timeDelay", performanceMetric.getDelay());
                    arrJsonForFlink142.add(json);
                });

        JsonHelper arrJsonForFlink180 = new JsonHelper(true);
        statisticService.queryStatisticForFlink180(dt, startStepIndex1mi, endStepIndex1mi)
                .stream()
                .collect(Collectors.groupingBy(Statistic::getStepIndex1mi))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> {
                    Long stepIndex1mi = entry.getKey();
                    List<Statistic> statistics = entry.getValue();

                    PerformanceMetric performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);
                    JsonHelper json = new JsonHelper();
                    json.put("step_index", stepIndex1mi);
                    json.put("throughput", performanceMetric.getThroughput());
                    json.put("timeDelay", performanceMetric.getDelay());
                    arrJsonForFlink180.add(json);
                });

        JsonHelper arrJsonForFlink1122 = new JsonHelper(true);
        statisticService.queryStatisticForFlink1122(dt, startStepIndex1mi, endStepIndex1mi)
                .stream()
                .collect(Collectors.groupingBy(Statistic::getStepIndex1mi))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> {
                    Long stepIndex1mi = entry.getKey();
                    List<Statistic> statistics = entry.getValue();

                    PerformanceMetric performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);
                    JsonHelper json = new JsonHelper();
                    json.put("step_index", stepIndex1mi);
                    json.put("throughput", performanceMetric.getThroughput());
                    json.put("timeDelay", performanceMetric.getDelay());
                    arrJsonForFlink1122.add(json);
                });

        JsonHelper arrJsonForGaia = new JsonHelper(true);
        statisticService.queryStatisticForGaia(dt, startStepIndex1mi, endStepIndex1mi)
                .stream()
                .collect(Collectors.groupingBy(Statistic::getStepIndex1mi))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> {
                    Long stepIndex1mi = entry.getKey();
                    List<Statistic> statistics = entry.getValue();

                    PerformanceMetric performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);
                    JsonHelper json = new JsonHelper();
                    json.put("step_index", stepIndex1mi);
                    json.put("throughput", performanceMetric.getThroughput());
                    json.put("timeDelay", performanceMetric.getDelay());
                    arrJsonForGaia.add(json);
                });

        JsonHelper responseJson = new JsonHelper();
        responseJson.put("flink142", arrJsonForFlink142);
        responseJson.put("flink180", arrJsonForFlink180);
        responseJson.put("flink1122", arrJsonForFlink1122);
        responseJson.put("gaia", arrJsonForGaia);
        return responseJson.toString();
    }

//    @RequestMapping("/metric/performance/flink")
//    public String getStatisticForFlink(@RequestParam(value = "dt", defaultValue = "20210327") String dt,
//                               @RequestParam(value = "step_index_1mi", defaultValue = "884") Long stepIndex1mi) {
//        System.out.println("/metric/performance/flink");
//
//        // prepare data
//        Map<String, List<Statistic>> statisticsPerJob = statisticService.queryStatisticForFlink(dt, stepIndex1mi)
//                .stream()
//                .collect(Collectors.groupingBy(Statistic::getJobName));
//        List<Statistic> statistics = null;
//        for (Map.Entry<String, List<Statistic>> entry : statisticsPerJob.entrySet()) {
//            statistics = entry.getValue();
//            break;
//        }
//        if (statistics == null || statistics.size() == 0) {
//            return "{}";
//        }
//
//        // calculate
//        PerformanceMetric performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);
//
//        // to json
//        String json = Utils.performanceMetric2Json(performanceMetric);
//
//        return json;
//    }
//
//    @RequestMapping("/metric/performance/gaia")
//    public String getStatisticForGaia(@RequestParam(value = "dt", defaultValue = "20210327") String dt,
//                               @RequestParam(value = "step_index_1mi", defaultValue = "884") Long stepIndex1mi) {
//        System.out.println("/metric/performance/gaia");
//
//        Map<String, List<Statistic>> statisticsPerJob = statisticService.queryStatisticForGaia(dt, stepIndex1mi)
//                .stream()
//                .collect(Collectors.groupingBy(Statistic::getJobName));
//
//        List<Statistic> statistics = null;
//        for (Map.Entry<String, List<Statistic>> entry : statisticsPerJob.entrySet()) {
//            statistics = entry.getValue();
//            break;
//        }
//        if (statistics == null || statistics.size() == 0) {
//            return "{}";
//        }
//
//        // calculate
//        PerformanceMetric performanceMetric = calculatePerformanceMetric(statistics, dt, stepIndex1mi);
//
//        // to json
//        String json = Utils.performanceMetric2Json(performanceMetric);
//
//        return json;
//    }

    @RequestMapping(value = "/metric/inter/info", produces = {"application/json;charset=UTF-8"})
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

    @RequestMapping(value = "/metric/inter/getListByInter", produces = {"application/json;charset=UTF-8"})
    public String getInterList(@RequestParam(value = "step_index", defaultValue = "884") Long stepIndex, @RequestParam(value = "inter_id", defaultValue = "") String interId) {
        System.out.println("/metric/inter/getListByInter");

        Map<String, List<InterMetric>> interMetricsByRidMap = statisticService.queryListByInter(stepIndex, interId)
                .stream()
                .collect(Collectors.groupingBy(InterMetric::getRid));

        // to json
        JsonHelper arrJson1 = new JsonHelper(true);
        for (Map.Entry<String, List<InterMetric>> entry : interMetricsByRidMap.entrySet()) {
            List<InterMetric> list = entry.getValue();
            if (list == null || list.size() == 0) {
                continue;
            }
            String rName = list.get(0).getRName();

            JsonHelper arrJson2 = new JsonHelper(true);
            for (InterMetric interMetric : list) {
                JsonHelper jsonHelper = new JsonHelper();
                jsonHelper.put("turnDirNo", interMetric.getTurnDirNo());
                jsonHelper.put("travelTime", interMetric.getTravelTime());
                jsonHelper.put("delay", interMetric.getDelay());
                jsonHelper.put("stopCnt", interMetric.getStopCnt());
                jsonHelper.put("queue", interMetric.getQueue());
                arrJson2.add(jsonHelper);
            }
            JsonHelper interMetricJson = new JsonHelper();
            interMetricJson.put("fRName", rName);
            interMetricJson.put("turnDirList", arrJson2);
            arrJson1.add(interMetricJson);
        }
        JsonHelper responseJson = new JsonHelper();
        responseJson.put("interId", interId);
        responseJson.put("metricsByInter", arrJson1);

        return responseJson.toString();
    }

    @RequestMapping(value = "/trafficproduct/getInterFTRidDateTpIndex", produces = {"application/json;charset=UTF-8"})
    public String getInterFTRidDateTpIndex(@RequestParam(value = "inter_id", defaultValue = "") String interId,
                                           @RequestParam(value = "f_rid", defaultValue = "") String fRid,
                                           @RequestParam(value = "turn_dir_no", defaultValue = "") Long turnDirNo,
                                           @RequestParam(value = "t_rid", defaultValue = "") String tRid,
                                           @RequestParam(value = "start_step_index", defaultValue = "") Long startStepIndex,
                                           @RequestParam(value = "end_step_index", defaultValue = "") Long endStepIndex,
                                           @RequestParam(value = "start_dt", defaultValue = "") String startDt,
                                           @RequestParam(value = "end_dt", defaultValue = "") String endDt,
                                           @RequestParam(value = "data_version", defaultValue = "") String dataVersion,
                                           @RequestParam(value = "adcode", defaultValue = "") String adcode,
                                           @RequestParam(value = "tp", defaultValue = "") String tp) {
        System.out.println("/metric/inter/getInterFTRidDateTpIndex");

        List<InterMetricV2> interMetricList = statisticService.queryInterFTRidDateTpIndex(interId, fRid, turnDirNo, tRid, startStepIndex, endStepIndex, startDt, endDt);

        // to json
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
        return responseJson.toString();
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

    @RequestMapping(value = "/metric/test", produces = {"application/json;charset=UTF-8"})
    public String test() {
        System.out.println("/metric/test");

        return "[{\"text\":\"hfy\"},{\"text\":\"js\"}]";
    }
}
