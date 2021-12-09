package cn.edu.neu.visualservice.mapper;

import cn.edu.neu.visualservice.bean.InterMetric;
import cn.edu.neu.visualservice.bean.InterMetricV2;
import cn.edu.neu.visualservice.bean.Statistic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StatisticMapper {
    @Select("select job_name as jobName, subtask_index as subtaskIndex, amount, duration from statistic where job_name like 'CityBrainJob-%' and dt=#{dt} and step_index_1mi=#{step_index_1mi}")
    List<Statistic> selectByDtIndexForFlink(@Param("dt") String dt, @Param("step_index_1mi") Long stepIndex);

    @Select("select job_name as jobName, subtask_index as subtaskIndex, amount, duration from statistic where job_name like 'CityBrainJobWithCache-%' and dt=#{dt} and step_index_1mi=#{step_index_1mi}")
    List<Statistic> selectByDtIndexForGaia(@Param("dt") String dt, @Param("step_index_1mi") Long stepIndex);

    @Select("select inter_id as interId, inter_name as interName from inter_metric")
    List<InterMetric> selectAllInterInfo();

    @Select("select inter_id as interId, inter_name as interName, rid, r_name as rName, turn_dir_no as turnDirNo, travel_time as travelTime, delay, stop_cnt as stopCnt, queue from inter_metric where inter_name=#{inter_name}")
    List<InterMetric> selectListByInter(@Param("inter_name") String interName);

    @Select("select inter_id as interId, f_rid as fRid, turn_dir_no as turnDirNo, dt, step_index as stepIndex, travel_time as travelTime, delay_dur as delayDur, stop_cnt as stopCnt, queue_len as queueLen from inter_metric_v2 where inter_id=#{inter_id} and f_rid=#{f_rid} and turn_dir_no=#{turn_dir_no} and step_index>=#{start_step_index} and step_index<=#{end_step_index}")
    List<InterMetricV2> selectInterFTRidDateTpIndex(@Param("inter_id") String interId,
                                                    @Param("f_rid") String fRid,
                                                    @Param("turn_dir_no") Long turnDirNo,
                                                    @Param("t_rid") String tRid,
                                                    @Param("start_step_index") Long startStepIndex,
                                                    @Param("end_step_index") Long endStepIndex,
                                                    @Param("start_dt") String startDt,
                                                    @Param("end_dt") String endDt);
}
