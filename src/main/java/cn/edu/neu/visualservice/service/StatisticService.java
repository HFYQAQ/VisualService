package cn.edu.neu.visualservice.service;

import cn.edu.neu.visualservice.bean.InterMetric;
import cn.edu.neu.visualservice.bean.InterMetricV2;
import cn.edu.neu.visualservice.bean.Statistic;

import java.util.List;

public interface StatisticService {
    List<Statistic> queryStatisticForFlink(String dt, Long stepIndex1mi);

    List<Statistic> queryStatisticForGaia(String dt, Long stepIndex1mi);

    List<InterMetric> queryInterNameList();

    List<InterMetric> queryListByInter(String interId);

    List<InterMetricV2> queryInterFTRidDateTpIndex(String interId, String fRid, Long turnDirNo, String tRid, Long startStepIndex, Long endStepIndex, String startDt, String endDt);
}
