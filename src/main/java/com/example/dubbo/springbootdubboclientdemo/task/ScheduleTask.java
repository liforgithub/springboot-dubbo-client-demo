package com.example.dubbo.springbootdubboclientdemo.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class ScheduleTask {

    @Scheduled(cron = "0/5 * *  * * ? ")
    public void test() {
        System.out.println("1111111111111111");
    }
}
