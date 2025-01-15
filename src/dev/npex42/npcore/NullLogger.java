package dev.npex42.npcore;

public class NullLogger extends Logger{

    private NullLogger() {}

    private static final NullLogger INSTANCE = new NullLogger();

    public static NullLogger Get() {
        return INSTANCE;
    }

    @Override
    void WriteLine(String thread, String name, String callee, LogLevel level, String msg) {
        /* No-Op */
    }
}
