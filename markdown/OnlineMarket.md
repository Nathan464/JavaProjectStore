### SpringBoot项目-网上商城
#### 一、项目功能：登录、注册、热销、用户管理、购物车、订单模块
#### 二、开发顺序：注册、登录、用户管理、购物车、商品、订单
#### 三、单一模块开发顺序：
* 持久层：根据前端页面的设置规划sql语句
* 业务层：核心功能控制、业务操作、异常处理
* 控制层：接收请求、处理响应
* 前端：JS、Jquery、Ajax
### 流程
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
insert into t_user (username,password) values (?,?)
```
2. 注册时判断用户名是否被注册：查询语句
```sql
select * from t_user where username=?
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

public class ServiceException extends RuntimeException{
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
public class UsernameDuplicatedException extends ServiceException{
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
public class InsertException extends ServiceException{
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
        if (result!=null){
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
        if (rows!=1){
            throw new InsertException("用户注册过程中出现未知异常");
        }
    }
}
```
