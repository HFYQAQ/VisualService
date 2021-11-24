package cn.edu.neu.visualservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterMetric {
    private String interId;
    private String interName;
    private String rid;
    private String rName;
    private Long turnDirNo;

    private Double travelTime;
    private Double delay;
    private Double stopCnt;
    private Double queue;

    @Override
    public String toString() {
        return "InterMetric{" +
                "interId='" + interId + '\'' +
                ", interName='" + interName + '\'' +
                ", rid='" + rid + '\'' +
                ", rName='" + rName + '\'' +
                ", turnDirNo=" + turnDirNo +
                ", travelTime=" + travelTime +
                ", delay=" + delay +
                ", stopCnt=" + stopCnt +
                ", queue=" + queue +
                '}';
    }
}
