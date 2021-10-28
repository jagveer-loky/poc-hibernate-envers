package com.fiserv.preproposal.api.application.schedulling;

import com.fiserv.preproposal.api.domain.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class DeleteExpiredReportsJobConfiguration {

    /**
     * Serão excluídos os relatórios cujo a data de requisição seja anterior a dois dias
     *
     * @return Date
     */
    private static Date getMidNight() {
        final Calendar midNight = Calendar.getInstance();
        midNight.set(midNight.get(Calendar.YEAR),
                midNight.get(Calendar.MONTH),
                midNight.get(Calendar.DAY_OF_MONTH),
                23,
                59,
                59);
        return midNight.getTime();
    }

    /**
     * @return {JobDetail}
     */
    @Bean
    public JobDetail deleteExpiredReportsJobDetail() {
        return JobBuilder.newJob()
                .ofType(DeleteExpiredReportsJob.class).storeDurably()
                .withIdentity(DeleteExpiredReportsJob.class.getName()).build();
    }

    /**
     * @param deleteExpiredReportsJobDetail {JobDetail}
     * @return {Trigger}
     */
    @Bean
    public Trigger deleteExpiredReportsTrigger(final JobDetail deleteExpiredReportsJobDetail) {
        return TriggerBuilder.newTrigger()
//                .startNow()
//                // Start at Eleven-Thirty at night
                .startAt(getMidNight())
                .forJob(deleteExpiredReportsJobDetail)
                .withIdentity(DeleteExpiredReportsJob.class.getName())
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
    public static class DeleteExpiredReportsJob implements Job {

        /**
         *
         */
        private final ReportService reportService;

        /**
         * @param context JobExecutionContext
         */
        public void execute(final JobExecutionContext context) {
            reportService.deleteExpired();
        }
    }

}