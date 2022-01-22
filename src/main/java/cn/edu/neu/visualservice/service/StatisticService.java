package cn.edu.neu.visualservice.service;

import cn.edu.neu.visualservice.bean.InterMetric;
import cn.edu.neu.visualservice.bean.InterMetricV2;
import cn.edu.neu.visualservice.bean.Statistic;

import java.util.List;

public interface StatisticService {
    List<Statistic> queryStatisticForFlink142(String dt, Long startStepIndex1mi, Long endStepIndex1mi);

    List<Statistic> queryStatisticForFlink180(String dt, Long startStepIndex1mi, Long endStepIndex1mi);

    List<Statistic> queryStatisticForFlink1122(String dt, Long startStepIndex1mi, Long endStepIndex1mi);

    List<Statistic> queryStatisticForGaia(String dt, Long startStepIndex1mi, Long endStepIndex1mi);

    List<InterMetric> queryInterNameList();

    List<InterMetric> queryListByInter(Long stepIndex, String interId);

    List<InterMetricV2> queryInterFTRidDateTpIndex(String interId, String fRid, Long turnDirNo, String tRid, Long startStepIndex, Long endStepIndex, String startDt, String endDt);
}
