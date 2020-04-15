package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查找改用户名是否存在
     * @param username 用户名
     * @return user对象
     */
    public User findUserByName(String username);

    /**
     * 新注册用户信息的保存
     */
    public  void saveUser(User user);

    /**
     * 根据激活码查找用户
     * @param code
     * @return
     */
    User findUserByCode(String code);

    /**
     * 更改用户的状态
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    User findUserByNameAndpassword(String username, String password);
}
