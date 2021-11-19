package cn.edu.neu.visualservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    private String jobName;
    private long subtaskIndex;
    private String dt;
    private long stepIndex1mi;

    private long amount;
    private long duration;

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "jobName='" + jobName + '\'' +
                ", subtaskIndex=" + subtaskIndex +
                ", dt='" + dt + '\'' +
                ", stepIndex1mi=" + stepIndex1mi +
                ", amount=" + amount +
                ", duration=" + duration +
                '}';
    }
}
