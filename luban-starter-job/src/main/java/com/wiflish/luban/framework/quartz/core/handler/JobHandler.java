package com.wiflish.luban.framework.quartz.core.handler;

/**
 * 任务处理器
 *
 * @author wiflish
 */
public interface JobHandler {

    /**
     * 执行任务
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    String execute(String param) throws Exception;

}
