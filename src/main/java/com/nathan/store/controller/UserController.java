package com.nathan.store.controller;

import com.nathan.store.entity.User;
import com.nathan.store.service.IUserService;
import com.nathan.store.service.ex.InsertException;
import com.nathan.store.service.ex.UsernameDuplicatedException;
import com.nathan.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("users")
@RestController // @Controller + @ResponseBody表示此方法响应结果以json格式进行数据响应
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(success);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password) {
        User user = userService.login(username, password);
        return new JsonResult<User>(success, user);
    }

}
