package com.nathan.store.controller;

import com.nathan.store.controller.ex.*;
import com.nathan.store.entity.User;
import com.nathan.store.service.IUserService;
import com.nathan.store.util.JsonResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("users")
@RestController // @Controller + @ResponseBody表示此方法响应结果以json格式进行数据响应
public class UserController extends BaseController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

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
        return new JsonResult<>(success, user);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(success);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User user = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(success, user);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // user中已存在：username、phone、email、gender；uid需重新封装
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(success);
    }

    // 头像文件大小的最大值
    public static final Integer AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    // 限制文件类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile属于SpringMvc，用于获取文件类型，并自动将文件数据赋值给参数
     *
     * @param session session
     * @param file    文件
     * @return 文件路径
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @NotNull @RequestParam("file") MultipartFile file) {
        if (!AVATAR_TYPE.contains(file.getContentType())) {
            throw new FileTypeException("文件类型不支持");
        }
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件过大");
        }
        String uploadParent = session.getServletContext().getRealPath("upload");
        File directory = new File(uploadParent);
        if (!directory.exists()) { //检测目录是否存在
            boolean createdDir = directory.mkdirs(); //创建目录
            if (!createdDir) {
                throw new RuntimeException("创建目录时产生错误");
            }
        }
        // 获取文件名，利用UUID随机生成字符串作为文件名，保留文件后缀
        String filename = file.getOriginalFilename(); // 获取原始文件名
        assert filename != null;
        int index = filename.indexOf(".");
        String suffix = filename.substring(index);
        String head = UUID.randomUUID().toString().toUpperCase();
        String realFileName = head + suffix; // 后缀和随机字符串拼接成新文件名以供后续使用
        File createFile = new File(directory, realFileName); //空文件
        try {
            file.transferTo(createFile); // 将原始数据传给新文件，要求文件后缀一致
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        }
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 返回头像的相对路径
        String avatar = "src/main/resources/static/upload/" + realFileName;
        userService.changeAvatar(uid, avatar, username);
        return new JsonResult<>(success, avatar);
    }
}
