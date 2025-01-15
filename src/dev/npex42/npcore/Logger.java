package dev.npex42.npcore;

public abstract class Logger {
    protected String name = "Logger";
    private LogLevel minLevel = LogLevel.INFO;
    /**
     * @param thread Calling Thread
     * @param name Name Of The Logger
     * @param level Level Of The Logging Action
     * @param msg Message To Be Logged
     */
    abstract void WriteLine(String thread, String name, String callee, LogLevel level, String msg);


    public void Trace(String format, Object... args) {
        Log(LogLevel.TRACE, format, args);
    }

    public void Debug(String format, Object... args) {
        Log(LogLevel.DEBUG, format, args);
    }

    public void Info(String format, Object... args) {
        Log(LogLevel.INFO, format, args);
    }

    public void Warn(String format, Object... args) {
        Log(LogLevel.WARN, format, args);
    }

    public void Error(String format, Object... args) {
        Log(LogLevel.ERROR, format, args);
    }

    public void Fatal(String format, Object... args) {
        Log(LogLevel.FATAL, format, args);
    }

    private void Log(LogLevel level, String format, Object... args) {
        if (shouldLog(level)) {
            StackTraceElement trace = ThreadUtils.GetStackTrace()[4];
            WriteLine(
                    Thread.currentThread().getName(),
                    name,
                    trace.getMethodName(),
                    level,
                    String.format(format, args)
            );
        }
    }

    private boolean shouldLog(LogLevel level) {
        return level.ordinal() >= minLevel.ordinal();
    }

    public void SetLogLevel(LogLevel level) {
        minLevel = level;
    }
}
