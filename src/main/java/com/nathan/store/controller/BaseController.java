package com.nathan.store.controller;

import com.nathan.store.service.ex.*;
import com.nathan.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

// 控制层基类
public class BaseController {
    // 操作成功状态码
    public static final int success = 200;

    // 请求处理方法，返回值为需要传递到前端的数据
    // 自动将异常对象传递给此方法的参数列表上
    @ExceptionHandler(ServiceException.class)  // 用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>();
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } else if (e instanceof UsernameNotFoundException) {
            result.setState(5001);
            result.setMessage("用户未找到");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("用户密码错误");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("用户注册产生异常");
        } else if (e instanceof UpdateException) {
            result.setState(5003);
            result.setMessage("更新数据时产生异常");
        }
        return result;
    }

    /**
     * 获取session中的uid
     * @param session session对象
     * @return uid
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取session中的用户名
     * @param session session对象
     * @return 用户名
     */
    protected final String getUsernameFromSession(HttpSession session){
        return (String) session.getAttribute("username");
    }
}
