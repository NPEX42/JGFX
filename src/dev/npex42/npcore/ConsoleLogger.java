package dev.npex42.npcore;

public class ConsoleLogger extends Logger{

    public ConsoleLogger(String name) {
        this.name = name;
    }

    public ConsoleLogger() {
        this.name = ThreadUtils.GetStackTrace()[3].getFileName();
    }

    public float foo = 3.14f;

    @Override
    void WriteLine(String thread, String name, String callee, LogLevel level, String msg) {
        if (level != LogLevel.ERROR && level != LogLevel.FATAL) {
            System.out.printf("[%s::%s::%s] %s: %s%n", thread, name, callee, level, msg);
        } else {
            System.err.printf("[%s::%s::%s] %s: %s%n", thread, name, callee, level, msg);
        }
    }
}
