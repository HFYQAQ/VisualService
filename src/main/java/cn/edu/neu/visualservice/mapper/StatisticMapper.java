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

    @Select("select distinct b.inter_id as interId, b.inter_name as interName from dws_tfc_state_rid_tp_lastspeed_rt a inner join dwd_tfc_rltn_wide_inter_lane b on a.rid=b.rid;")
    List<InterMetric> selectAllInterInfo();

    @Select("select a.inter_id as interId, a.f_rid as rid, b.name as rName, a.turn_dir_no as turnDirNo, a.travel_time as travelTime, a.delay_dur as delay, a.stop_cnt as stopCnt, a.queue_len as queue from inter_metric_v2 a join dwd_tfc_bas_rdnet_rid_info b on a.f_rid=b.rid where a.step_index=(select max(step_index) from inter_metric_v2) and a.inter_id=#{inter_id}")
    List<InterMetric> selectListByInter(@Param("inter_id") String interId);

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
