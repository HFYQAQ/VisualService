package cn.edu.neu.visualservice.service.impl;

import cn.edu.neu.visualservice.bean.InterMetric;
import cn.edu.neu.visualservice.bean.InterMetricV2;
import cn.edu.neu.visualservice.bean.Statistic;
import cn.edu.neu.visualservice.mapper.StatisticMapper;
import cn.edu.neu.visualservice.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    StatisticMapper statisticMapper;

    @Override
    public List<Statistic> queryStatisticForFlink(String dt, Long startStepIndex1mi, Long endStepIndex1mi) {
        return statisticMapper.selectByDtIndexForFlink(dt, startStepIndex1mi, endStepIndex1mi);
    }

    @Override
    public List<Statistic> queryStatisticForGaia(String dt, Long startStepIndex1mi, Long endStepIndex1mi) {
        return statisticMapper.selectByDtIndexForGaia(dt, startStepIndex1mi, endStepIndex1mi);
    }

    @Override
    public List<InterMetric> queryInterNameList() {
        return statisticMapper.selectAllInterInfo();
    }

    @Override
    public List<InterMetric> queryListByInter(Long stepIndex, String interId) {
        return statisticMapper.selectListByInter(stepIndex, interId);
    }

    @Override
    public List<InterMetricV2> queryInterFTRidDateTpIndex(String interId, String fRid, Long turnDirNo, String tRid, Long startStepIndex, Long endStepIndex, String startDt, String endDt) {
        return statisticMapper.selectInterFTRidDateTpIndex(interId, fRid, turnDirNo, tRid, startStepIndex, endStepIndex, startDt, endDt);
    }
}
