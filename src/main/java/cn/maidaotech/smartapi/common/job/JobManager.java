package cn.maidaotech.smartapi.common.job;

import cn.maidaotech.smartapi.common.L;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class JobManager {
    private JobScheduler instance;
    @Value("${job.schedule}")
    private boolean schedule;

    @PostConstruct
    public void init() {
        if (schedule) {
            instance = new JobScheduler();
            L.warn("Enable job-executing");
        }
    }

    public boolean isScheduleEnabled() {
        return schedule;
    }

    public  void schedule(String jobName, String crontab, Job job) {
        if (instance == null) {
            return;
        }
        instance.schedule(jobName, crontab, job);
    }
}
