package dev.npex42.npcore;

public class ThreadUtils {
    public static StackTraceElement[] GetStackTrace() {
        return Thread.currentThread().getStackTrace();
    }
}
