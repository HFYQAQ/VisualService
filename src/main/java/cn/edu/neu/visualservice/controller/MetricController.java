package cn.edu.neu.visualservice.controller;

import cn.edu.neu.visualservice.bean.Statistic;
import cn.edu.neu.visualservice.service.StatisticService;
import cn.edu.neu.visualservice.struct.PerformanceMetric;
import cn.edu.neu.visualservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MetricController {
    @Autowired
    private StatisticService statisticService;

    @RequestMapping("/metric/performance/flink")
    public String getStatisticForFlink(@RequestParam(value = "dt", defaultValue = "20210327") String dt,
                               @RequestParam(value = "step_index_1mi", defaultValue = "884") Long stepIndex1mi) {
        System.out.println("getStatisticForFlink");

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
        System.out.println("getStatisticForGaia");

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
