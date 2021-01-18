package org.laiyw.act.seven.controller;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.laiyw.act.seven.model.ResponseInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/14 14:42
 * @Description TODO
 */
@Controller
@RequestMapping("/activiti")
public class ActivitiController {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private DynamicBpmnService dynamicBpmnService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;

    @GetMapping("/processName")
    @ResponseBody
    public ResponseInfo processName() {
        String name = processEngine.getName();
        return ResponseInfo.success(name);
    }

    @GetMapping("/task/create")
    @ResponseBody
    public ResponseInfo createTask() {
        Task task = taskService.newTask();
        task.setCategory("custom_task");
        task.setName("自定义任务");
        task.setAssignee("laiyw");
        task.setDescription("自定义任务描述");
        taskService.saveTask(task);
        TaskVo vo = new TaskVo();
        BeanUtils.copyProperties(task, vo);
        return ResponseInfo.success(vo);
    }

    @GetMapping("/task/complete")
    @ResponseBody
    public ResponseInfo completeTask(String taskId, String comment) {
        Comment result = taskService.addComment(taskId, null, comment);
        taskService.complete(taskId);
        return ResponseInfo.success(result);
    }

    @GetMapping("/task/info")
    @ResponseBody
    public ResponseInfo taskInfo() {
        List<Task> taskList = taskService.createTaskQuery().list();
        List<TaskVo> taskVoList = taskList.stream().map(t ->
                new TaskVo(
                        t.getId(),
                        t.getName(),
                        t.getCategory(),
                        t.getAssignee(),
                        t.getDescription(),
                        JSON.toJSONString(taskService.getTaskComments(t.getId()))
                )
        ).collect(Collectors.toList());
        return ResponseInfo.success(taskVoList);
    }

    @GetMapping("/task/history")
    @ResponseBody
    public ResponseInfo taskHistory() {
        List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery().list();
        List<TaskVo> taskVoList = taskList.stream().map(t ->
                new TaskVo(
                        t.getId(),
                        t.getName(),
                        t.getCategory(),
                        t.getAssignee(),
                        t.getDescription(),
                        JSON.toJSONString(taskService.getTaskComments(t.getId()))
                )
        ).collect(Collectors.toList());
        return ResponseInfo.success(taskVoList);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class TaskVo implements Serializable {
        String id;
        String name;
        String category;
        String assignee;
        String description;
        String comment;
    }
}
