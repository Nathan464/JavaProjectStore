### SpringBoot项目-网上商城

#### 一、项目功能：登录、注册、热销、用户管理、购物车、订单模块

#### 二、开发顺序：注册、登录、用户管理、购物车、商品、订单

#### 三、单一模块开发顺序：

* 持久层：根据前端页面的设置规划sql语句
* 业务层：核心功能控制、业务操作、异常处理
* 控制层：接收请求、处理响应
* 前端：JS、Jquery、Ajax

### 流程（用户注册）

#### 1. 根据表结构提取公共字段放到实体类基类BaseEntity中

实体类中构建需要get、set、equals、hashCode、toString方法

```java
package com.nathan.store.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getCreatedUser(), that.getCreatedUser()) && Objects.equals(getCreatedTime(), that.getCreatedTime()) && Objects.equals(getModifiedUser(), that.getModifiedUser()) && Objects.equals(getModifiedTime(), that.getModifiedTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreatedUser(), getCreatedTime(), getModifiedUser(), getModifiedTime());
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createdUser='" + createdUser + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedUser='" + modifiedUser + '\'' +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}

```

#### 2. 创建用户实体类，继承BaseEntity基类

```java
package com.nathan.store.entity;

import java.io.Serializable;

public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String userName;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (getUid() != null ? !getUid().equals(user.getUid()) : user.getUid() != null) return false;
        if (getUserName() != null ? !getUserName().equals(user.getUserName()) : user.getUserName() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getSalt() != null ? !getSalt().equals(user.getSalt()) : user.getSalt() != null) return false;
        if (getPhone() != null ? !getPhone().equals(user.getPhone()) : user.getPhone() != null) return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getGender() != null ? !getGender().equals(user.getGender()) : user.getGender() != null) return false;
        if (getAvatar() != null ? !getAvatar().equals(user.getAvatar()) : user.getAvatar() != null) return false;
        return getIsDelete() != null ? getIsDelete().equals(user.getIsDelete()) : user.getIsDelete() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getSalt() != null ? getSalt().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + (getAvatar() != null ? getAvatar().hashCode() : 0);
        result = 31 * result + (getIsDelete() != null ? getIsDelete().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }
}
```

#### 3. 注册持久层：通过MyBatis操作数据库

##### 3.1 规划需要执行的sql语句

1. 用户注册：对应数据库插入操作

```sql
insert into t_user (username, password)
values (?, ?)
```

2. 注册时判断用户名是否被注册：查询语句

```sql
select *
from t_user
where username = ?
```

##### 3.2 设计接口和方法

1. 定义Mapper接口
2. @MapperScan() 指定mapper接口路径，项目启动时自动加载接口文件

##### 3.3 编写映射

1. 定义.xml映射文件，与对应的接口进行关联
2. 创建接口对应的映射文件，文件名与接口名需一致
3. 配置接口中方法对应上sql语句，借助标签完成insert、update、delete、select

```.xml
<mapper namespace="com.nathan.store.mapper.UserMapper">
    <insert id="insert" useGeneratedKeys="true" keyProperty="uid">
        INSERT INTO t_user (username, password, salt, phone, email, gender, avatar,
                            is_delete, created_user, created_time, modified_user, modified_time)
        VALUES {
            #{username}, #{password}, #{salt}, #{phone}, #{email}, #{gender}, #{avatar},
            #{isDelete}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime}
            }
    </insert>
</mapper>
```

useGeneratedKeys="true" keyProperty="uid"表示以uid字段自增

4. 自定义映射规则

```xml
<!--自定义映射规则
        id: 负责分配唯一的id，对应resultMap=""属性的取值
        type: 类属性，表示数据库中的查询结果与java实体类进行映射
    -->
<resultMap id="UserEntityMap" type="com.nathan.store.entity.User">
    <!--表字段与类属性不一致的字段需要匹配
        column表属性；property类中的属性
        主键的映射不可省略
    -->
    <result column="uid" property="uid"/>
    <result column="is_delete" property="isDelete"/>
    <result column="created_user" property="createdUser"/>
    <result column="created_time" property="createdTime"/>
    <result column="modified_user" property="modifiedUser"/>
    <result column="modified_time" property="modifiedTime"/>
</resultMap>
```

单元测试方法：必须被@Test注解修饰；返回值只能是void；方法参数列表不指定任何类型；方法修饰符必须为public

#### 4.业务层

##### 4.1 规划异常

构建异常基类

```java
package com.nathan.store.service.ex;

public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
```

> 根据业务层不同功能定义具体异常类型，统一继承异常基类

1. 用户名被占用，定义用户名重复异常

```java
package com.nathan.store.service.ex;

// 用户名被占用
public class UsernameDuplicatedException extends ServiceException {
    public UsernameDuplicatedException() {
        super();
    }

    public UsernameDuplicatedException(String message) {
        super(message);
    }

    public UsernameDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameDuplicatedException(Throwable cause) {
        super(cause);
    }

    protected UsernameDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
```

2. 数据插入时，服务器或数据库宕机，执行插入的过程中出现异常

```java
package com.nathan.store.service.ex;

// 数据插入过程中产生的异常
public class InsertException extends ServiceException {
    public InsertException() {
        super();
    }

    public InsertException(String message) {
        super(message);
    }

    public InsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertException(Throwable cause) {
        super(cause);
    }

    protected InsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
```

##### 4.1 设计接口和抽象方法

1. 创建IUserService接口

```java
package com.nathan.store.service;

import com.nathan.store.entity.User;

// 用户模块业务层
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户
     */
    void reg(User user);
}
```

2. 创建UserServiceImpl实现类

```java
package com.nathan.store.service.impl;

import com.nathan.store.entity.User;
import com.nathan.store.mapper.UserMapper;
import com.nathan.store.service.IUserService;
import com.nathan.store.service.ex.InsertException;
import com.nathan.store.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

// 用户业务层实现类
public class UserServiceImpl implements IUserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 通过user获得username，调用findByUsername判断是否被注册过
        User result = userMapper.findByUsername(user.getUsername());
        if (result != null) {
            throw new UsernameDuplicatedException("用户名已存在");
        }
        // 补充用户数据
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        // 为空时执行用户注册
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("用户注册过程中出现未知异常");
        }
    }
}
```

#### 5. 控制层

* 创建响应：将状态码、描述信息、数据封装到一个类中，将该类作为返回值返回到前端

```java
public class JsonResult<E> implements Serializable {
    //状态码
    private Integer state;
    private String message;
    // 泛型数据
    private E data;
}
```

* 设计请求：根据当前业务功能模块进行（请求路径、参数、类型、响应结果）
* 处理请求：创建控制层UserController类，依赖于业务层接口

```java

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
}
```

* 控制层优化设计：抽离公共代码生成父类，BaseController类处理异常

```java
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
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("用户注册产生异常");
        }
        return result;
    }
}
```

> @ExceptionHandler(ServiceException.class)  // 用于统一处理抛出的异常
> @RestController // 等同于 @Controller + @ResponseBody 表示此方法响应结果以json格式进行数据响应

#### 6. 前端页面

* 在注册的前端页面中编写发送请求的方法，$.ajax()发送异步请求
* Jquery封装的$.ajax()函数可异步加载相关请求，依赖于JavaScript提供的XHR(XmlHttpResponse)对象
* ajax使用，需传递**某个方法体**作为参数，结构为，参数值以字符串标识：

```js
$.ajax({
    url: "", // 请求的地址，不能包含参数列表内容
    type: "", // 请求的数据类型，get、post
    data: "", // 请求地址需要的数据"username=nathan&password=123"，由于登录时使用的是表单，应该使用${"#表单id"}.serialize()
    datatype: "", // 提交的数据类型，一般为json类型
    success: function () {

    }, // 服务器正常响应，调用该方法，服务器返回的数据传递到该方法上
    error: function () {

    } // 与success相反
});
```

### 流程（用户登录）

用户名和密码与数据库匹配，跳转到主页index.html，前端使用jQuery完成

#### 持久层

* 规划sql：根据用户提交信息做select查询，判断用户是否存在，密码判断在业务层

```sql
select *
from t_user
where username = ?
```

与UserMapper.xml的findByUsername一致，可直接调用

* 接口无需重复开发

#### 业务层

* 规划异常

  用户名对应的密码不匹配PasswordNotMatchException

  用户未找到UsernameNotFoundException

  异常编写：继承ServiceException类

* 接口和抽象方法

  在IUserService接口中编写login()，并将登录的用户数据以用户对象封装返回，保存在cookie或者session中，避免反复获取使用多次的数据

  实现类中实现父类方法

  测试登录方法是否正常运行

```
public User login(String username,String password){
    User result=userMapper.findByUsername(username);
    // 用户不存在抛异常
    if(result==null){
        throw new UsernameNotFoundException("用户不存在");
    }
    // 匹配密码: 将密码以相同的规则加密，再与数据库匹配
    String salt=result.getSalt();
    String md5Password=getMD5Password(password,salt);
    if(!result.getPassword().equals(md5Password)){
        throw new PasswordNotMatchException("密码错误");
    }
    // 判断is_delete字段是否为1-已删除
    if(result.getIsDelete()==1){
        throw new UsernameNotFoundException("用户数据不存在");
    }
    // new一个新对象，辅助其它页面的数据展示，且应只封装所需信息，减少数据量
    User user=new User();
    user.setUid(result.getUid());
    user.setUsername(result.getUsername());
    user.setAvatar(result.getAvatar());
    return user;
}
```

* 抽象方法实现

#### 控制层

* 处理异常

  根据业务层抛出的异，需要在统一异常类中进行捕获和处理，未出现的异常需新添加

```
@ExceptionHandler(ServiceException.class)  // 用于统一处理抛出的异常
public JsonResult<Void> handleException(Throwable e){
    JsonResult<Void> result = new JsonResult<>();
    if (e instanceof UsernameDuplicatedException) {
        result.setState(4000);
        result.setMessage("用户名被占用");
    }else if (e instanceof UsernameNotFoundException) {
        result.setState(5001);
        result.setMessage("用户未找到");
    } else if (e instanceof PasswordNotMatchException) {
        result.setState(5002);
        result.setMessage("用户密码错误");
    }else if (e instanceof InsertException) {
        result.setState(5000);
        result.setMessage("用户注册产生异常");
    }
    return result;
}
```

* 设计请求

  请求路径：/users/login

  请求方式：POST

  请求数据：username、password、HttpSession

  响应结果：JsonResult

* 处理请求

  UserController类中编写处理请求的方法

```
  @RequestMapping("login")
  public JsonResult<User> login(String username, String password){
      User user = userService.login(username,password);
      return new JsonResult<User>(success, user);
  }
 ```

#### 前端页面

* 在login.html中使用ajax处理前端数据
* 登录成功则跳转主页，失败弹出提示信息

### 流程（拦截器）

##### 请求先统一经过拦截器

拦截器中应定义过滤规则，不满足则重定向到login.html;

重定向、转发都实现页面跳转，转发会造成url变化，重定向不会造成url变化;

定义一个实现HandlerInterceptor的类。

```java
package com.nathan.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 检测全局session对象中是否有uid，有则放行
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（url+controller）
     * @return 返回为true则放行请求
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object object = request.getSession().getAttribute("uid");
        // 用户为空则重定向
        if (object == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
```

* 注册过滤器：

添加白名单（login、register、reg、product、index.html）、黑名单（需登录才能访问）

借助WebMvcConfig接口注册过滤器

```java
package com.nathan.store.config;

import com.nathan.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

// 登录拦截器注册
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    // 配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();
        // 拦截器直接放行的资源放到一个list集合
        List<String> patterns = new ArrayList<>();
        patterns.add("bootstrap3/**1");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/product.html");
        patterns.add("/web/index.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**") // 拦截所有url
                .excludePathPatterns(patterns); // 除了指定路径
    }
}

```

### 流程（密码修改）

#### 持久层

1. 规划sql

根据uid更改密码

```sql
update t_user
set password=?,
    modified_user=?,
    modified_time=?
where uid = ?
```

根据uid查询用户数据

```sql
select *
from t_user
where uid = ?
```

2. 设计接口和抽象方法
   UserMapper接口定义方法，将方法配置到映射文件UserMapper.xml中

```sql
<
update id="updatePasswordByUid">
UPDATE t_user
SET password=#{password},
    modified_user=#{modifiedUser},
    modified_time=#{modifiedTime}
WHERE uid = #{uid}
          < /
update >
    <
select id = "findByUid" resultMap="UserEntityMap">
SELECT *
FROM t_user
WHERE uid = #{uid}
          < /
select>
```

#### 业务层

1. 规划异常

   密码错误、is_delete=1、uid找不到、update过程异常

2. 设计接口和方法

```
void changePassword(Integer uid, String username, 
        String oldPassword, String newPassword);
```

```
public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User user = userMapper.findByUid(uid);
        if (user==null||user.getIsDelete()==1){
            throw new UsernameNotFoundException("用户不存在");
        }
        String oldMd5Password = getMD5Password(oldPassword,user.getSalt());
        if(!user.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("原密码错误");
        }
        String newMd5Password = getMD5Password(newPassword,user.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
        if (rows!=1){
            throw new UpdateException("更新数据时出现异常");
        }
    }
```

#### 控制层

1. 处理异常

UpdateException配置到BaseController统一异常处理方法中

2. 设计请求

请求路径：/users/change_password

请求方式：POST

请求数据：oldPassword、newPassword、HttpSession

响应结果：JsonResult

```
@RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(success);
    }
```

3. 处理请求

```
public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User user = userMapper.findByUid(uid);
        if (user==null||user.getIsDelete()==1){
            throw new UsernameNotFoundException("用户不存在");
        }
        String oldMd5Password = getMD5Password(oldPassword,user.getSalt());
        if(!user.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("原密码错误");
        }
        String newMd5Password = getMD5Password(newPassword,user.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
        if (rows!=1){
            throw new UpdateException("更新数据时出现异常");
        }
    }
```

### 流程（修改用户资料）

#### 持久层

1. 规划sql

根据uid更新用户信息

```sql
update t_user
set phone=?,
    gender=?,
    modified_user=?,
    modified_time=?
where uid = ?
```

根据用户名查询数据

```sql
select *
from t_user
where uid = ?
```

2. 接口和抽象方法

```
    /**
     * 更新用户数据
     * @param user 用户
     * @return 返回受影响的行数
     */
    Integer updateInfoByUid(User user);
```

UserMapper.xml映射

```xml

<update id="updateInfoByUid">
    <!--if条件判断标签在test中的条件成立时才执行if标签内的语句-->
    UPDATE t_user SET
    <if test="phone!=null">phone=#{phone},</if>
    <if test="email!=null">email=#{email},</if>
    <if test="gender!=null">gender=#{gender},</if>
    modified_user=#{modifiedUser},modified_time=#{modifiedTime}
    WHERE uid=#{uid}
</update>
```

#### 业务层

1. 功能设计

- 数据回显（获取信息填入到对应的文本框）
- 用户点击修改按钮执行修改

2. 异常处理

- 数据未找到
- 修改按钮执行前判断用户数据是否存在

3. 接口和抽象方法

```
  /**
  * 获取用户信息
  * @param uid id
  * @return 查询到返回用户信息，否则为空
  */
  User getByUid(Integer uid);

  /**
  * 更改用户信息
  * @param uid id
  * @param username 用户名
  * @param user User对象
  */
  void changeInfo(Integer uid, String username, User user);
```

UserServiceImpl实现方法

```
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户未找到");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        user.setGender(result.getGender());
        return user;
    }
```

```
    public void changeInfo(Integer uid, String username, User user) {
        // 方法传入的user对象包含phone、email、gender，需手动封装uid、username
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户未找到");
        }
        user.setUid(uid);
        user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows!=1){
            throw new UpdateException("更新数据产生异常");
        }
```

#### 控制层

1. 设计请求
    * 个人资料一打开就发送用户数据查询的请求
    * /users/get_by_id -- GET -- HttpSession -- JsonResult
    * 点击修改按钮发送用户数据修改
    * /users/change_info -- POST -- HttpSession,User -- JsonResult

2. 处理请求

```
    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User user = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(success,user);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session){
        // user中已存在：username、phone、email、gender；uid需重新封装
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(success);
    }
```

3. 前端ajax

```
// $(document).ready 指在函数加载完成时自动执行function的内容
<script type="text/javascript">
	$(document).ready(function() {
		$.ajax({
			url: "/users/get_by_uid",
			type: "GET",
			dataType: "json",
			success: function(json) {
				if (json.state == 200) {
					$("#username").val(json.data.username);
					$("#phone").val(json.data.phone);
					$("#email").val(json.data.email);

					let radio = json.data.gender == 0 ? $("#gender-female") : $("#gender-male");
					radio.prop("checked", "checked");
				} else {
					alert("获取用户信息失败！" + json.message);
				    }
			}
		});
	});

	$("#btn-change-info").click(function() {
		$.ajax({
			url: "/users/change_info",
			type: "POST",
			data: $("#form-change-info").serialize(),
			dataType: "json",
			success: function(json) {
				if (json.state == 200) {
					alert("修改成功！");
					location.href = "login.html";
				} else {
					alert("修改失败！" + json.message);
				}
			},
			error: function(xhr) {
				alert("您的登录信息已经过期，请重新登录！HTTP响应码：" + xhr.status);
				location.href = "login.html";
			}
		});
	});
</script>
```

### 流程（上传头像）

#### 持久层

1. sql

   文件对象保存在系统上，并在数据库中记录文件路径，对应一个avatar更新语句
    ```sql
    update t_user set avatar=?, modified_user=?,modified_time=? where uid=?
    ```

2. 设计接口：在UserMapper接口中定义

   @Param("SQL映射文件中#{}占位符的变量名")，解决SQL语句占位符和映射的接口方法的参数名不一致的强行注入

#### 业务层

1. 异常规划
    * 用户数据不存在
    * 更新数据出错

2. 设计接口和抽象方法

```
    /**
     * 更改用户头像
     * @param uid id
     * @param avatar 头像文件路径
     * @param username 用户名
     */
    void changeAvatar(Integer uid, String avatar,String username);
```

```
    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        // 判断用户数据是否存在
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户未找到");
        }
        Integer rows = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if (rows!=1){
            throw new UpdateException("更新数据出现异常");
        }
    }
```

#### 控制层

1. 规划异常
   创建FileUploadException文件异常基类继承RuntimeException
   FileEmptyException、FileSizeException、FileTypeException、FileUploadIOException、FileStateException
2. 处理异常
   在BaseController类中统一编写和处理,

   @ExceptionHandler(**{ServiceException.class,FileUploadException.class}**)可接收数组类型的异常类

```
@ExceptionHandler({ServiceException.class,FileUploadException.class})  // 用于统一处理抛出的异常
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
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("文件为空");
        }else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("文件过大");
        }else if (e instanceof FileStateException) {
            result.setState(6002);
            result.setMessage("文件上传状态异常");
        }else if (e instanceof FileTypeException) {
            result.setState(6003);
            result.setMessage("文件类型不支持");
        }else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage("文件读取异常");
        }
        return result;
    }
```

3. 设计请求
    * /users/change_avatar
    * POST(GET请求提交的最大数据为2kB)
    * HttpSession、String filename、MultipartFile file
    * JsonResult返回图像路径

4. 实现请求

```
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
            directory.mkdirs(); //创建目录
        }
        // 获取文件名，利用UUID随机生成字符串作为文件名，保留文件后缀
        String filename = file.getOriginalFilename(); // 获取原始文件名
        assert filename != null;
        int index = filename.indexOf(".");
        String suffix = filename.substring(index);
        String head = UUID.randomUUID().toString().toUpperCase();
        String realFileName = suffix + head; // 后缀和随机字符串拼接成新文件名以供后续使用
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
        String avatar = "/upload/" + realFileName;
        userService.changeAvatar(uid, avatar, username);
        return new JsonResult<>(success, avatar);
    }
```

#### 前端

1. 更改默认的大小限制
    * SpringMVC默认为1MB文件可上传，需要在properties文件中配置spring.servlet.multipart.max-file-size=10MB;
    * serialize()：将表单数据自动拼接成key=value的结构进行提交，一般为提交普通控件类型中的数据(text/password/radio)
    * FormData类：将表单中数据保持原有结构进行数据提交；new FormData($("#表单名")[数据下标索引])
    * Ajax默认按照字符串处理数据，以字符串形式提交数据；processData: false, // processData处理数据 contentType: false, //
      contentType发送数据的格式

2. 更新头像后头像不在修改按钮点击前显示，
    * 头像使用cookie保存联合ready方法自动读取cookie可以解决，
    * cookie使用前需要导入cookie.js文件
    * 使用script标签在head头部标签内导入
    * ```<script src="cookie.js路径" type="text/javascript" charset="utf-8"></script>```
    * 头像更改完毕后将最新头像地址保存到cookie中，同名保存会覆盖cookie的值

        ```
       <script type="text/javascript">
                $(document).ready(function () {
                    $("#img-avatar").attr("src", $.cookie("avatar"));
                });
 
                $("#btn-change-avatar").click(function() {
                    $.ajax({
                        url: "/users/change_avatar",
                        type: "POST",
                        data: new FormData($("#form-change-avatar")[0]),
                        dataType: "JSON",
                        processData: false, // processData处理数据
                        contentType: false, // contentType发送数据的格式
                        success: function(json) {
                            if (json.state == 200) {
                                $("#img-avatar").attr("src", json.data); // 设置头像地址
                                $.cookie("avatar", json.data, {expires: 7}); // cookie头像地址覆盖
                            } else {
                                alert("修改失败！" + json.message);
                            }
                        },
                        error: function(xhr) {
                            alert("您的登录信息已经过期，请重新登录！HTTP响应码：" + xhr.status);
                            location.href = "login.html";
                        }
                    });
                });
            </script>
       ```

### 流程（新增收货地址）

数据库创建、新增收货地址（address实体类）

#### 持久层

1. 开发顺序：新增、列表展示、设置默认地址、修改、删除
2. sql

```sql
insert into t_address (除aid字段的所有字段)
values (字段值列表) // properties中控制最多20条地址
select count(*)
from t_address
where uid = ?
```

3. 接口和抽象方法

```
     /**
     * 插入收货地址
     * @param address 地址数据
     * @return 影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户id统计收货地址
     * @param uid id
     * @return 收货地址总数
     */
    Integer countByUid(Integer uid);
```

4. sql映射
   创建AddressMapper.xml

#### 业务层

1. 规划异常：插入地址为首条设置为默认地址（查询到地址总数为0，设置is_default为1，总数大于20，抛出AddressCountLimitException）
2. 接口和抽象方法

```void addNewAddress(Integer uid, String username, Address address);```

    @Value("${user.address.max-count}")  // properties读取数据

```
    private final AddressMapper addressMapper;
    @Value("${user.address.max-count}")  // properties读取数据
    private Integer maxCount;

    @Autowired(required = false)
    public IAddressImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("收货地址超出上限");
        }
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        // 补全修改者、修改时间等信息
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        Integer row = addressMapper.insert(address);
        if (row!=1){
            throw new InsertException("插入数据时发生异常");
        }
    }
```

#### 控制层

1. 异常处理

```
else if (e instanceof AddressCountLimitException) {
            result.setState(7000);
            result.setMessage("收货地址超出限制");
}
```

2. 设计请求

> /address/add_new_address
>
> POST
>
> Address address，HttpSession session
>
> JsonResult返回Void

3. 处理请求

创建新的AddressController，处理与地址相关的请求和响应

### 流程（获取省区市列表）

1. 持久层
   构建数据库；编写实体类District；查询语句

   ```select * from t_dict_district where parent=? order by code ASC```

   抽象方法
   ```
    /**
     *根据父代号查询
     * @param parent 代号
     * @return 区域列表
     */
    List<District> findByParent(Integer parent);
    String findNameByCode(String code);
   ```
   DistrictMapper.xml中的映射
   ```sql
   <select id="findByParent" resultType="com.nathan.store.entity.District">
        select * from t_dict_district where parent=#{parent} order by code
   </select>
   <select id="findNameByCode" resultType="java.lang.String">
        select name from t_dict_district where code=#{code}
   </select>
   ```
2. 业务层
   IDistrictService
   ```
   List<District> getParent(String parent);
   String getNameByCode(String code);
   ```
   DistrictServiceImpl
   ```
    @Autowired(required = false)
    private DistrictMapper districtMapper;
    @Override
    public List<District> getParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        // 无关数据可以设置为null减少数据量
        for (District d:list) {
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }
    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
    ```
   业务层优化
    ```
   // 添加用户的收货地址的业务层
   @Autowired(required = false)
   private IDistrictService districtService;
   ```
   在addNewAddress方法中将districtService接口中获取的省市区数据转移到address对象中
   ```
   //根据districtService获取数据将省市区添加到IAddressImpl中的addNewAddress的address
   String provinceName = districtService.getNameByCode(address.getProvinceCode());
   String cityName = districtService.getNameByCode(address.getCityCode());
   String areaName = districtService.getNameByCode(address.getAreaCode());
   address.setProvinceName(provinceName);
   address.setCityName(cityName);
   address.setAreaName(areaName);
   ```
3. 控制层
    1. 请求设计
   > /districts&emsp;&emsp;GET&emsp;&emsp;String parent&emsp;&emsp;JsonResult返回ListDistrict
    2. 请求处理，创建DistrictController
   ```
    @Autowired(required = false)
    private IDistrictService districtService;

    // districts开头的请求拦截到getByParent方法中
    @RequestMapping({"/", " "}) // districts后续跟/或空格都能拦截到
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> list = districtService.getParent(parent);
        return new JsonResult<>(success, list);
    }
   ```
   将districts中的请求添加LoginInterceptorConfigurer的拦截白名单中：patterns.add("/districts/**")

4. 前端
   注释掉通过js完成省市区列表加载的js代码
   检查前端页面在提交省市区时是否有相关的name和id属性

### 流程（收货地址展示）

持久层

1. sql:
    ```sql
    select * from t_address where uid=? order by is_default DESC,created_time DESC
    ```
2. 接口
   ```
     /**
     * 以uid查询所有用户地址
     * @param uid uid
     * @return 地址集合
     */
     List<Address> findByUid(Integer uid);
   ```
3. AddressMapper.xml添加方法映射
    ```xml
    <!--查询某用户的收货地址列表数据：List<Address> findByUid(Integer uid)-->
    <select id="findByUid" resultMap="AddressEntityMap">
        SELECT
            *
        FROM
            t_address
        WHERE
            uid=#{uid}
        ORDER BY
            is_default DESC, created_time DESC
    </select>
    ```

业务层

1. 设计接口和抽象方法
    ```
    List<Address> getByUid(Integer uid);
    ```
    ```
    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        // 去除不需要展示的内容
        for (Address address:list){
            address.setUid(null);
            address.setAid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setCreatedTime(null);
            address.setIsDefault(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return list;
    }
   ```

控制层

1. 请求设计
   > /addresses&emsp;&emsp;HttpSession session&emsp;&emsp;GET&emsp;&emsp;JsonResult返回List-Address
2. 实现请求
   ```
    @RequestMapping({"", "/"})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> list = addressService.getByUid(uid);
        return new JsonResult<>(success, list);
    }
   ```

### 流程（设置默认地址）

持久层

1. sql

   检测想设置的默认收货地址的数据是否存在

   ```select * from t_address aid=?```

   修改默认地址前，将所有收货地址设为非默认

   ```update t_address set is_default=0 where uid=?```

   将选中地址设置为默认

   ```update t_address set is_default=1, modified_user=?,modified_time=? where aid=?```
2. 设计抽象方法
   在AddressMapper接口中定义方法
   ```
   /**
     * 根据aid查询收货地址
     *
     * @param Aid 收货地址id
     * @return 收货地址数据，查找为无返回null
     */
    Address findByAid(Integer Aid);

    /**
     * 根据用户的uid值来修改用户的收货地址设置为非默认
     *
     * @param uid 用户id
     * @return 返回受影响的函数
     */
    Integer updateNonDefaultByUid(Integer uid);

    /**
     *
     * @param aid 地址id
     * @param modifiedUser 更改者
     * @param modifiedTime 更改时间
     * @return 受影响行数
     */
    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);
   ```
3. 配置AddressMapper.xml的sql映射

业务层

1. 可能的异常：UpdateException、AccessDeniedException、AddressNotFoundException
2. 抽象方法```void setDefault(Integer aid, Integer uid, String username);```
3. 实现抽象方法
   ```
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("地址未找到");
        }
        // 检测收货地址数据的归属人
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问数据");
        }
        // 先将所有地址设置为非默认
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据产生未知异常");
        }
        // 设置默认收货地址
        Integer row = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据产生未知异常");
        }
    }
   ```

控制层

1. 在BaseController处理异常
   ```
   else if (e instanceof AddressNotFoundException) {
            result.setState(7001);
            result.setMessage("收货地址未找到");
   } else if (e instanceof AccessDeniedException) {
            result.setState(8000);
            result.setMessage("非法访问数据");
   }
   ```
2. 设计请求
    * /addresses/{aid}/set_default
    * @PathVariable("aid") Integer aid, HttpSession session
    * GET
    * JsonResult<>
3. 完成请求
   ```
    // RestFul风格请求编写
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,
                                       HttpSession session) {
        addressService.setDefault(aid, getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(success);
    }
   ```

### 流程（删除收货地址）

持久层

1. sql

   判断数据是否存在、判断该地址数据归属人是否是当前用户（前面已开发）
2. 执行删除收货地址

```sql
delete
* from t_address where aid=?
```

如果删除的是默认地址，剩下地址中的最新的地址设置为默认地址

```sql
select *
from where uid=?
order by DESC limit 0, 1 // limit接收参数：limit (pageNum-1)*pageSize,pageSize
```

本身只有一条地址且为默认，删除后无需其它操作

抽象方法
AddressMapper接口设计抽象方法

```
   /**
   * 根据aid删除地址
   * @param aid id
   * @return 受影响行数
   */
   Integer deleteByAid(Integer aid);

    /**
     * 根据uid查询用户最新的地址数据
     * @param uid id
     * @return 最新地址数据
     */
    Address findLastModified(Integer uid);
```

AddressMapper.xml映射

```xml
<!-- 根据收货地址id删除数据：Integer deleteByAid(Integer aid) -->
<delete id="deleteByAid">
    DELETE FROM
    t_address
    WHERE
    aid=#{aid}
</delete>
```

```xml
<!-- 查询某用户最后修改的收货地址：Address findLastModified(Integer uid) -->
<select>id="findLastModified" resultMap="AddressEntityMap">
    SELECT
    *
    FROM
    t_address
    WHERE
    uid=#{uid}
    ORDER BY
    modified_time DESC
    LIMIT 0,1
</select>
```

业务层

1. 异常规划：DeleteException
2. 在IAddressService规划抽象方法
   ```void delete(Integer aid, Integer uid, String username);```
3. 抽象方法实现

```
    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("地址未找到");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问数据");
        }
        Integer row = addressMapper.deleteByAid(aid);
        if (row != 1) {
            throw new DeleteException("删除过程产生未知异常");
        }
        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            return;
        }
        if (address.getIsDefault() == 1) {
            // 将该条数据的is_default设置为1
            Address lastAddress = addressMapper.findLastModified(uid);
            row = addressMapper.updateDefaultByAid(lastAddress.getAid(), username, new Date());
        }
        if (row != 1) {
            throw new UpdateException("更新数据产生未知异常");
        }
    }
```

控制层

1. 处理异常

```
   else if (e instanceof DeleteException) {
       result.setState(8001);
       result.setMessage("删除数据产生异常");
   }
```

2. 设计请求
   /addresses/{aid}/delete&emsp;&emsp;Integer aid,HttpSession session&emsp;&emsp;JsonResult<>
3. 请求处理

``` 
    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid,
                                   HttpSession session) {
        addressService.delete(aid, getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(success);
    }
```

### 流程（热销商品）

创建数据库

1. 编写实体类

```java
package com.nathan.store.entity;

import java.io.Serializable;

public class Product extends BaseEntity implements Serializable {
    private Integer id;
    private Integer categoryId;
    private String itemType;
    private String title;
    private String sellPoint;
    private Long price;
    private Integer num;
    private String image;
    private Integer status;
    private Integer priority;
    // 后续添加getter、setter、equals、hashcode、toString
}
```

持久层

1. 查询热销商品列表的SQL语句大致是

```mysql
SELECT *
FROM t_product
WHERE status = 1
ORDER BY priority DESC
LIMIT 0,4
```

```
SELECT *
FROM t_product
WHERE id = #{id}
```

2. 接口&抽象方法
   ```
   public interface ProductMapper {
      List<Product> findHotList();
      Product findById(Integer id);
   }
   ```
3. ProductMapper.xml文件，并在文件中配置findHotList()方法的映射。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cy.store.mapper.ProductMapper">
    <resultMap id="ProductEntityMap" type="com.nathan.store.entity.Product">
        <id column="id" property="id"/>
        <result column="category_id" property="categoryId"/>
        <result column="item_type" property="itemType"/>
        <result column="sell_point" property="sellPoint"/>
        <result column="created_user" property="createdUser"/>
        <result column="created_time" property="createdTime"/>
        <result column="modified_user" property="modifiedUser"/>
        <result column="modified_time" property="modifiedTime"/>
    </resultMap>

    <!-- 查询热销商品的前四名：List<Product> findHostList() -->
    <select id="findHotList" resultMap="ProductEntityMap">
        SELECT
        *
        FROM
        t_product
        WHERE
        status=1
        ORDER BY
        priority DESC
        LIMIT 0,4
    </select>
    <!--  根据商品id查询商品详情：Product findById(Integer id)  -->
    <select id="findById" resultMap="ProductEntityMap">SELECT *
        FROM t_product
        WHERE id = #{id}
    </select>
</mapper>
```

业务层

1.创建IProductService接口，并在接口中添加findHotList()方法。

```java
package com.nathan.store.service;

import com.nathan.store.entity.Product;

import java.util.List;

/** 处理商品数据的业务层接口 */
public interface IProductService {
    /**
     * 查询热销商品前四名
     * @return 热销商品前四名的集合
     */
    List<Product> findHotList();

    Product findById(Integer id);
}
```

2. 创建ProductServiceImpl类，并添加@Service注解；在类中声明持久层对象以及实现接口中的方法。

```java
package com.nathan.store.service.impl;

import com.nathan.store.entity.Product;
import com.nathan.store.mapper.ProductMapper;
import com.nathan.store.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 处理商品数据的业务层实现类 */
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired(required = false)
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        List<Product> list = productMapper.findHotList();
        for (Product product : list) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("商品未找到");
        }
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);
        return product;
    }
}
```

控制层

1. 设计请求

设计用户提交的请求，并设计响应的方式。

	请求路径：/products/hot_list
	请求参数：无
	请求类型：GET
	响应结果：JsonResult<List<Product>>
	是否拦截：否，需要将index.html和products/**添加到白名单

在LoginInterceptorConfigurer类中将index.html页面和products/**请求添加到白名单。

```
   patterns.add("/web/index.html");
   patterns.add("/products/**");
```

2. 处理请求

创建ProductController类继承自BaseController类，类添加@RestController和@RequestMapping("products")注解，并在类中添加业务层对象。

```java
package com.nathan.store.controller;

import com.nathan.store.entity.Product;
import com.nathan.store.service.IProductService;
import com.nathan.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController {
    @Autowired
    private IProductService productService;

    @RequestMapping("hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> list = productService.findHotList();
        return new JsonResult<>(success, list);
    }

    @GetMapping("{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id) {
        Product data = productService.findById(id);
        return new JsonResult<>(success, data);
    }
}
```

2.在类中添加处理请求的getHotList()方法。

```
@RequestMapping("hot_list")
public JsonResult<List<Product>> getHotList() {
    List<Product> data = productService.findHotList();
    return new JsonResult<List<Product>>(OK, data);
}
```

### 流程（购物车）

数据库创建、创建实体类

```java
public class Cart extends BaseEntity implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    // 加入getter、setter、toString、equals、hashcode
}
```

持久层

1. sql规划

```sql
// 插入数据
insert into t_cart (aid除外的字段) values (字段值)
// 商品已存在则更新商品数量
update t_cart
set num=?
where cid = ?
    // 插入或更新前需要查询相关数据
select *
from t_cart
where cid = ?
  and uid = ?
    // 展示购物车
select cid,
       uid,
       pid,
       t_cart.price,
       t_cart.num,
       t_product.title,
       t_product.image,
       t_product.price AS realPrice
from t_cart
         left join _product on t_cart.pid = t_product.id
where uid=#{uid}
order by t_cart.created_time DESC
    // 显示勾选的购物车数据
SELECT cid,
       uid,
       pid,
       t_cart.price,
       t_cart.num,
       t_product.title,
       t_product.price AS realPrice,
       t_product.image
FROM t_cart
         LEFT JOIN t_product ON t_cart.pid = t_product.id
WHERE cid IN (
    <foreach collection="array" item="cid" separator=",">
    #{cid}
    </foreach>
    )
ORDER BY t_cart.created_time DESC
```

2. 设计接口

```java
package com.nathan.store.mapper;

import com.nathan.store.entity.Cart;

import java.util.Date;

public interface CartMapper {
    /**
     * 插入购物车数据
     * @param cart 购物车
     * @return 受影响行数
     */
    Integer insert(Cart cart);

    /**
     * 更新商品数量
     * @param cid 购物车id
     * @param num 数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 受影响行数
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户id和商品id查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return Cart购物车数据
     */
    Cart findByUidAndPid(Integer uid, Integer pid);

    Cart findByCid(Integer cid);

    List<CartVO> findVOByCids(Integer[] cids);
}
```

3. 接口映射文件CartMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.nathan.store.mapper.CartMapper">
    <resultMap id="CartEntityMap" type="com.nathan.store.entity.Cart">
        <id column="cid" property="cid"/>
        <result column="created_user" property="createdUser"/>
        <result column="created_time" property="createdTime"/>
        <result column="modified_user" property="modifiedUser"/>
        <result column="modified_time" property="modifiedTime"/>
    </resultMap>

    <!-- 插入购物车数据：Integer insert(Cart cart) -->
    <insert id="insert" useGeneratedKeys="true" keyProperty="cid">
        INSERT INTO t_cart (uid, pid, price, num, created_user, created_time, modified_user, modified_time)
        VALUES (#{uid}, #{pid}, #{price}, #{num}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})
    </insert>

    <!-- 修改购物车数据中商品的数量：
         Integer updateNumByCid(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime) -->
    <update id="updateNumByCid">
        UPDATE
        t_cart
        SET num=#{num},
        modified_user=#{modifiedUser},
        modified_time=#{modifiedTime}
        WHERE cid = #{cid}
    </update>

    <!-- 根据用户id和商品id查询购物车中的数据：
         Cart findByUidAndPid(
            @Param("uid") Integer uid,
            @Param("pid") Integer pid) -->
    <select id="findByUidAndPid" resultMap="CartEntityMap">
        SELECT *
        FROM t_cart
        WHERE uid = #{uid}
        AND pid = #{pid}
    </select>

    <!-- 查询某用户的购物车数据：List<CartVO> findVOByUid(Integer uid) -->
    <select id="findVOByUid" resultType="com.nathan.store.vo.CartVO">
        SELECT cid, uid, pid, t_cart.price, t_cart.num,
        t_product.title, t_product.price AS realPrice,
        t_product.image
        FROM t_cart
        LEFT JOIN t_product ON t_cart.pid = t_product.id
        WHERE uid = #{uid}
        ORDER BY t_cart.created_time DESC
    </select>

    <!-- 根据购物车数据id查询购物车数据详情：Cart findByCid(Integer cid) -->
    <select id="findByCid" resultMap="CartEntityMap">
        SELECT *
        FROM t_cart
        WHERE cid = #{cid}
    </select>

    <!-- 根据若干个购物车数据id查询详情的列表：List<CartVO> findVOByCids(Integer[] cids) -->
    <select id="findVOByCids" resultType="com.nathan.store.vo.CartVO">
        SELECT cid, uid, pid, t_cart.price, t_cart.num, t_product.title,
        t_product.price AS realPrice, t_product.image
        FROM
        t_cart
        LEFT JOIN t_product ON t_cart.pid = t_product.id
        WHERE
        cid IN (
        <foreach collection="array" item="cid" separator=",">
            #{cid}
        </foreach>
        )
        ORDER BY
        t_cart.created_time DESC
    </select>
</mapper>
```

业务层

1. 异常：InsertException、UpdateException
2. 创建ICartService

```java
package com.nathan.store.service;

public interface ICartService {
    /**
     * 商品添加到购物车
     * @param uid 用户id
     * @param pid 商品id
     * @param num 商品数量
     * @param username 修改者
     */
    void addToCart(Integer uid, Integer pid, Integer num, String username);

    List<CartVO> getVOByUid(Integer uid);

    Integer addNum(Integer cid, Integer uid, String username);

    Integer subNum(Integer cid, Integer uid, String username);

    List<CartVO> getVOByCids(Integer uid, Integer[] cids);
}

```

3. 实现接口CartServiceImpl

```java
package com.nathan.store.service.impl;

import com.nathan.store.entity.Cart;
import com.nathan.store.mapper.CartMapper;
import com.nathan.store.mapper.ProductMapper;
import com.nathan.store.service.ICartService;
import com.nathan.store.service.ex.InsertException;
import com.nathan.store.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartServiceImpl implements ICartService {
    // 购物车业务层依赖于商品和购物车的持久层
    @Autowired(required = false)
    private CartMapper cartMapper;
    @Autowired(required = false)
    private IProductService productService;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer num, String username) {
        // 查询商品是否存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date date = new Date();
        if (result == null) {  // 不存在则插入数据
            Cart cart = new Cart();
            // 补全数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(num);
            cart.setPrice(productService.findById(pid).getPrice());
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            Integer row = cartMapper.insert(cart);
            if (row != 1) {
                throw new InsertException("插入数据时产生异常");
            }
        } else {  // 存在则更新数据
            Integer amount = result.getNum() + num;
            Integer row = cartMapper.updateNumByCid(result.getCid(), amount, username, date);
            if (row != 1) {
                throw new UpdateException("数据更新产生异常");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问数据");
        }
        Integer num = result.getNum() + 1;
        Date date = new Date();
        Integer row = cartMapper.updateNumByCid(cid, num, username, date);
        if (row != 1) {
            throw new UpdateException("更新数量时产生异常");
        }
        return num;
    }

    @Override
    public Integer subNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问数据");
        }
        Integer num = result.getNum() - 1;
        Date date = new Date();
        Integer row = cartMapper.updateNumByCid(cid, num, username, date);
        if (row != 1) {
            throw new UpdateException("更新数量时产生异常");
        }
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);
        list.removeIf(cart -> !cart.getUid().equals(uid));
        return list;
    }
}
```

控制层

1. 设计请求
    * /carts/add_to_cart&emsp;&emsp;GET&emsp;&emsp;pid, amount, session&emsp;&emsp;JsonResult-Void
    * /carts/&emsp;&emsp;GET&emsp;&emsp;session&emsp;&emsp;JsonResult-List-CartVO
    * /carts/{cid}/num/add&emsp;&emsp;cid, session&emsp;&emsp;JsonResult>Integer
    * /carts/{cid}/num/sub&emsp;&emsp;cid, session&emsp;&emsp;JsonResult>Integer
    * /carts/list&emsp;&emsp;cids, session&emsp;&emsp;JsonResult>List>CartVO
2. 处理请求:创建CartController

```java
package com.nathan.store.controller;

import com.nathan.store.service.ICartService;
import com.nathan.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {
    @Autowired(required = false)
    private ICartService cartService;

    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount,
                                      HttpSession session) {
        cartService.addToCart(getUidFromSession(session), pid, amount,
                getUsernameFromSession(session));
        return new JsonResult<>(success);
    }

    @RequestMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> data = cartService.getVOByUid(getUidFromSession(session));
        return new JsonResult<>(success, data);
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.addNum(cid, getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(success, data);
    }

    @RequestMapping("{cid}/num/sub")
    public JsonResult<Integer> subNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.subNum(cid, getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(success, data);
    }

    @GetMapping("list")
    public JsonResult<List<CartVO>> getVOByCids(HttpSession session, Integer[] cids) {
        List<CartVO> data = cartService.getVOByCids(getUidFromSession(session), cids);
        return new JsonResult<>(success, data);
    }
}
```

ValueObject: VO，值对象，查询结果数据为多张表中的内容，不能使用pojo接收，可构建VO对象存储查询结果

```java
public class CartVO implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private Long realPrice;
    private String image;
    // 加入getter、setter、toString、equals、hashcode
}
```

### 流程（订单）

持久层

```java
// 订单实体类
public class Order extends BaseEntity implements Serializable {
    private Integer oid;
    private Integer uid;
    private String recvName;
    private String recvPhone;
    private String recvProvince;
    private String recvCity;
    private String recvArea;
    private String recvAddress;
    private Long totalPrice;
    private Integer status;
    private Date orderTime;
    private Date payTime;
    // // 加入getter、setter、toString、equals、hashcode
}
```

```java
// 订单商品实体类
public class OrderItem extends BaseEntity implements Serializable {
    private Integer id;
    private Integer oid;
    private Integer pid;
    private String title;
    private String image;
    private Long price;
    private Integer num;
    // 加入getter、setter、toString、equals、hashcode
}
```

1. sql

```sql
// 订单
INSERT INTO t_order (uid, recv_name, recv_phone, recv_province,
                     recv_city, recv_area, recv_address,
                     total_price, status, order_time, pay_time,
                     created_user, created_time, modified_user, modified_time)
VALUES (
#{uid},
#{recvName},
#{recvPhone},
#{recvProvince},
#{recvCity},
#{recvArea},
#{recvAddress},
#{totalPrice},
#{status},
#{orderTime},
#{payTime},
#{createdUser},
#{createdTime},
#{modifiedUser},
#{modifiedTime}
)
```

```sql
// 订单商品
INSERT INTO t_order_item (oid, pid, title, image, price, num, created_user,
                          created_time, modified_user, modified_time)
VALUES (
#{oid},
#{pid},
#{title},
#{image},
#{price},
#{num},
#{createdUser},
#{createdTime},
#{modifiedUser},
#{modifiedTime}
)
```

2. 创建OrderMapper接口

```java
public interface OrderMapper {
    /**
     * 插入订单数据
     *
     * @param order 订单
     * @return 受影响行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单商品数据
     *
     * @param orderItem 订单商品
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);
}
```

3. OrderMapper.xml映射

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.nathan.store.mapper.OrderMapper">
    <resultMap id="AddressEntityMap" type="com.nathan.store.entity.Order">
        <id column="oid" property="oid"/>
        <result column="recv_name" property="recvName"/>
        <result column="recv_phone" property="recvPhone"/>
        <result column="recv_provice" property="recvProvice"/>
        <result column="recv_city" property="recvCity"/>
        <result column="recv_area" property="recvArea"/>
        <result column="recv_address" property="recvAddress"/>
        <result column="total_price" property="totalPrice"/>
        <result column="order_time" property="orderTime"/>
        <result column="pay_time" property="payTime"/>
    </resultMap>

    <!-- 插入订单数据：Integer insertOrder(Order order) -->
    <insert id="insert" useGeneratedKeys="true" keyProperty="oid">
        INSERT INTO t_order (uid, recv_name, recv_phone, recv_province,
        recv_city, recv_area, recv_address,
        total_price, status, order_time, pay_time,
        created_user, created_time, modified_user, modified_time)
        VALUES (#{uid}, #{recvName}, #{recvPhone}, #{recvProvince}, #{recvCity}, #{recvArea},
        #{recvAddress}, #{totalPrice}, #{status}, #{orderTime}, #{payTime}, #{createdUser},
        #{createdTime}, #{modifiedUser}, #{modifiedTime})
    </insert>

    <!-- 插入订单商品数据：Integer insertOrderItem(OrderItem orderItem) -->
    <insert id="insertOrderItem" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO t_order_item (oid, pid, title, image, price, num, created_user,
        created_time, modified_user, modified_time)
        VALUES (#{oid}, #{pid}, #{title}, #{image}, #{price}, #{num}, #{createdUser},
        #{createdTime}, #{modifiedUser}, #{modifiedTime})
    </insert>
</mapper>
```

业务层

1. 在IAddressService接口中定义根据收货地址的id获取收货地址并实现接口
   ```Address getByAid(Integer aid,Integer uid);```

```
    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("地址未找到");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }
```

2. IOrderService和OrderServiceImpl

```java
public interface IOrderService {
    /**
     * 创建订单
     * @param aid 收货地址的id
     * @param cids 即将购买的商品数据在购物车表中的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 成功创建的订单数据
     */
    Order create(Integer aid, Integer[] cids, Integer uid, String username);
}
```

```java
package com.nathan.store.service.impl;

import com.nathan.store.entity.Address;
import com.nathan.store.entity.Order;
import com.nathan.store.entity.OrderItem;
import com.nathan.store.mapper.OrderMapper;
import com.nathan.store.service.IAddressService;
import com.nathan.store.service.ICartService;
import com.nathan.store.service.IOrderService;
import com.nathan.store.service.ex.InsertException;
import com.nathan.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired(required = false)
    private OrderMapper orderMapper;
    @Autowired(required = false)
    private IAddressService addressService;
    @Autowired(required = false)
    private ICartService cartService;

    @Override
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        List<CartVO> list = cartService.getVOByCids(uid, cids);
        Long totalPrice = 0L;

        for (CartVO cartVO : list) {
            totalPrice = cartVO.getPrice() * cartVO.getNum();
        }
        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        // 封装数据
        order.setUid(uid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());
        order.setOrderTime(new Date());
        Integer row = orderMapper.insertOrder(order);
        if (row != 1) {
            throw new InsertException("插入数据产生异常");
        }
        // 订单详细项的数据
        for (CartVO cartVO : list) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setNum(cartVO.getNum());
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());
            Integer count = orderMapper.insertOrderItem(orderItem);
            if (count != 1) {
                throw new InsertException("插入数据产生异常");
            }

        }
        return order;
    }
}
```

控制层

1. 设计请求

```
/orders/create
aid, HttpSession, cids
POST
JsonResult<Order>
```

2. 处理请求：OrderController类

```java

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
    @Autowired(required = false)
    private IOrderService orderService;

    @RequestMapping("create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.create(aid, cids, uid, username);
        return new JsonResult<>(success, data);
    }
}
```

## AOP

### 1 Spring AOP

AOP：面向切面（Aspect）编程。AOP并不是Spring框架的特性，只是Spring很好的支持了AOP。

如果需要在处理每个业务时，都执行特定的代码，则可以假设在整个数据处理流程中存在某个切面，切面中可以定义某些方法，当处理流程执行到切面时，就会自动执行切面中的方法。最终实现的效果就是：只需要定义好切面方法，配置好切面的位置（连接点），在不需要修改原有数据处理流程的代码的基础之上，就可以使得若干个流程都执行相同的代码。

### 2 切面方法

1.切面方法的访问权限是public。

2.切面方法的返回值类型可以是void或Object，如果使用的注解是@Around时，必须使用Object作为返回值类型，并返回连接点方法的返回值；如果使用的注解是@Before或@After等其他注解时，则自行决定。

3.切面方法的名称可以自定义。

4.切面方法的参数列表中可以添加ProceedingJoinPoint接口类型的对象，该对象表示连接点，也可以理解调用切面所在位置对应的方法的对象，如果使用的注解是@Around时，必须添加该参数，反之则不是必须添加。

### 3 统计业务方法执行时长

1.在使用Spring AOP编程时，需要先在pom.xml文件中添加两个关于AOP的依赖aspectjweaver和aspectjtools。

```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
</dependency>
```
```
<dependency>
<groupId>org.aspectj</groupId>
<artifactId>aspectjtools</artifactId>
</dependency>
```

2.在com.cy.store.aop包下创建TimerAspect切面类，在类之前添加@Aspect和@Component注解修饰。

```java
package com.cy.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimerAspect {

}
```

3.在类中添加切面方法around(ProceedingJoinPoint pjp)。

```
public Object around(ProceedingJoinPoint pjp)throws Throwable{
        // 记录起始时间
        long start=System.currentTimeMillis();
        // 执行连接点方法，即切面所在位置对应的方法。本项目中表示执行注册或执行登录等
        Object result=pjp.proceed();
        // 记录结束时间
        long end=System.currentTimeMillis();
        // 计算耗时
        System.err.println("耗时："+(end-start)+"ms.");
        // 返回连接点方法的返回值
        return result;
}
```

4.最后需要在方法之前添加@Around注解，以配置连接点，即哪些方法需要应用该切面。

```
@Around("execution(* com.cy.store.service.impl.*.*(..))");
```

5.启动项目，在前端浏览器访问任意一个功能模块进行功能的测试。