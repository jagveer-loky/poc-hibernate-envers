package com.fiserv.preproposta.api.application.schedulling;

import com.fiserv.preproposta.api.domain.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class CreateReportsJobConfiguration {

    /**
     * Os relatórios serão criados as 11:30 da noite
     *
     * @return Date
     */
    private static Date getElevenThirtyAtNight() {
        final Calendar elevenThirtyAtNight = Calendar.getInstance();
        elevenThirtyAtNight.set(elevenThirtyAtNight.get(Calendar.YEAR),
                elevenThirtyAtNight.get(Calendar.MONTH),
                elevenThirtyAtNight.get(Calendar.DAY_OF_MONTH),
                23,
                30,
                0);
        return elevenThirtyAtNight.getTime();
    }

    /**
     * @return {JobDetail}
     */
    @Bean
    public JobDetail createReportsJobDetail() {
        return JobBuilder.newJob()
                .ofType(CreateReportsJob.class).storeDurably()
                .withIdentity(CreateReportsJob.class.getName()).build();
    }

    /**
     * @param createReportsJobDetail {JobDetail}
     * @return {Trigger}
     */
    @Bean
    public Trigger createReportTrigger(final JobDetail createReportsJobDetail) {
        return TriggerBuilder.newTrigger()
//                .startNow()
//                // Start at Eleven-Thirty at night
                .startAt(getElevenThirtyAtNight())
                .forJob(createReportsJobDetail)
                .withIdentity(CreateReportsJob.class.getName())
                .withSchedule(simpleSchedule()
//                        .repeatForever().withIntervalInSeconds(300))
                        .repeatForever().withIntervalInHours(24))
                .build();
    }

    /**
     *
     */
    @Component
    @RequiredArgsConstructor
    private static class CreateReportsJob implements Job {

        /**
         *
         */
        private final ReportService reportService;

        /**
         * @param context JobExecutionContext
         */
        public void execute(final JobExecutionContext context) {
            reportService.createReports();
        }
    }

}