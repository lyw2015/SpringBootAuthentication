package org.laiyw.act.seven.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.laiyw.act.seven.entity.User;
import org.laiyw.act.seven.model.ResponseInfo;
import org.laiyw.act.seven.service.IUserService;
import org.laiyw.act.seven.utils.RequestSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/7 12:01
 * @Description TODO
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/info")
    @ResponseBody
    public ResponseInfo info() {
        return ResponseInfo.success(RequestSecurityUtils.getUser());
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseInfo create(User user) {
        userService.createUser(user);
        return ResponseInfo.success();
    }

    @GetMapping("/page")
    @ResponseBody
    public ResponseInfo list() {
        Page page = new Page(1, 1);
        return ResponseInfo.success(userService.listByPage(page));
    }
}
