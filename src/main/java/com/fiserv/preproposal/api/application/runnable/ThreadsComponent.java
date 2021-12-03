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
    private final ExecutorService executorService;

    /**
     *
     * @param pool
     */
    private ThreadsComponent(@Value("${reports.threads.pool:10}") Integer pool) {
        this.executorService = Executors.newFixedThreadPool(pool);
    }

    /**
     * Accessing this bean without container
     *
     * @param runnable Runnable
     */
    public static void execute(final Runnable runnable) {
        StaticContextAccessor.getBean(ThreadsComponent.class).getExecutorService().execute(runnable);
    }
}