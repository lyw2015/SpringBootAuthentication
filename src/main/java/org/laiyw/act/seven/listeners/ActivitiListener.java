package org.laiyw.act.seven.listeners;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/14 14:32
 * @Description TODO
 */
@Slf4j
public class ActivitiListener implements TaskListener, ActivitiEventListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.debug("notify > EventName:{}, name:{}", delegateTask.getEventName(), delegateTask.getName());
    }

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        log.debug("onEvent > TypeName:{}", activitiEvent.getType().name());
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
