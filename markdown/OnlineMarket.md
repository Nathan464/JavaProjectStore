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