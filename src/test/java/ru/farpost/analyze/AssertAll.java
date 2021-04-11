package ru.farpost.analyze;

import org.junit.Rule;
import org.junit.rules.ErrorCollector;

import java.util.Arrays;

public class AssertAll {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    private void assertAll(Runnable... assertions) {
        Arrays.stream(assertions).forEach(this::runAndCollectThrowable);
    }

    private void runAndCollectThrowable(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            collector.addError(throwable);
        }
    }
}
