package cn.maidaotech.smartapi.common.job;

public interface Job {

    void run() throws Exception;

    void terminate() throws Exception;

}
