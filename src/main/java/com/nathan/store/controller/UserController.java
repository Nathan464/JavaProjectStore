package com.nathan.store.controller;

import com.nathan.store.entity.User;
import com.nathan.store.service.IUserService;
import com.nathan.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User user = userService.login(username, password);
        session.setAttribute("uid", user.getUid());
        session.setAttribute("username", user.getUsername());
        return new JsonResult<User>(success, user);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(success);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User user = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(success, user);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session){
        // user中已存在：username、phone、email、gender；uid需重新封装
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(success);
    }
}
