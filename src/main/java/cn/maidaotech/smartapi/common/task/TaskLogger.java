package cn.maidaotech.smartapi.common.task;

public interface TaskLogger {

    void append(String line);

    void flush();

}
