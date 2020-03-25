package org.hero.renche.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SchedulingTask {
    @Autowired
    private  SchedulingTaskManage schedulingTaskManage;

    @Scheduled(cron = "0 0 2 ? * MON")
    public void taskContractRemaindWeek() {
        schedulingTaskManage.manageTask("week");
    }

    @Scheduled(cron = "0 0 2 1 * ?")
    public void taskContractRemaindMonth() {
        schedulingTaskManage.manageTask("month");
    }

}
