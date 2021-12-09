package cn.edu.neu.visualservice.bean;

public class InterMetricV2 {
    private String interId;
    private String fRid;
    private Long turnDirNo;
    private String tRid;
    private Long stepIndex;
    private String dataVersion;
    private String adcode;
    private String tp;
    private String dt;

    private Double travelTime;
    private Double stopCnt;
    private Double delayDur;
    private Double queueLen;
    private Double flow;

    public InterMetricV2() {

    }

    public InterMetricV2(String interId,
                         String fRid,
                         Long turnDirNo,
                         Long stepIndex,
                         Double travelTime,
                         Double stopCnt,
                         Double delayDur,
                         Double queueLen,
                         Double flow) {
        this.interId = interId;
        this.fRid = fRid;
        this.turnDirNo = turnDirNo;
        this.stepIndex = stepIndex;
        this.travelTime = travelTime;
        this.stopCnt = stopCnt;
        this.delayDur = delayDur;
        this.queueLen = queueLen;
        this.flow = flow;
    }

    public void settRid(String tRid) {
        this.tRid = tRid;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public Double getDelayDur() {
        return delayDur;
    }

    public Double getFlow() {
        return flow;
    }

    public Double getQueueLen() {
        return queueLen;
    }

    public Double getStopCnt() {
        return stopCnt;
    }

    public Double getTravelTime() {
        return travelTime;
    }

    public Long getStepIndex() {
        return stepIndex;
    }

    public Long getTurnDirNo() {
        return turnDirNo;
    }

    public String getAdcode() {
        return adcode;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public String getDt() {
        return dt;
    }

    public String getfRid() {
        return fRid;
    }

    public String getInterId() {
        return interId;
    }

    public String getTp() {
        return tp;
    }

    public String gettRid() {
        return tRid;
    }
}
