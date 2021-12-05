package com.fiserv.preproposal.api.application.runnable;

import com.fiserv.preproposal.api.infrastrucutre.context.StaticContextAccessor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public final class ThreadsComponent {

    /**
     *
     */
    @Getter
    @Value("${reports.threads.pool:10}")
    private Integer pool;

    /**
     *
     */
    private ExecutorService executorService;

    /**
     * @return ExecutorService
     */
    public ExecutorService getExecutorService() {
        if (executorService == null || executorService.isShutdown() || executorService.isTerminated())
            executorService = Executors.newFixedThreadPool(pool);
        return executorService;
    }

    /**
     * Accessing this bean without container
     *
     * @param runnable Runnable
     */
    public static void execute(final Runnable runnable) {
        StaticContextAccessor.getBean(ThreadsComponent.class).getExecutorService().execute(runnable);
        StaticContextAccessor.getBean(ThreadsComponent.class).getExecutorService().shutdown();
    }
}