package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    /**
     * 注册用户的方法
     * @param user
     * @return
     */
    private UserDao dao = new UserDaoImpl();
    @Override
    public Boolean regist(User user) {
        //1查询用户名是否已经存在
        User u = dao.findUserByName(user.getUsername());
        //2.不存在就注册用户
        if(u != null){
            return false;
        }
        //设置用户的激活状态和激活码
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        dao.saveUser(user);
        //发送邮件
        String content="<a href=' http://localhost:8080/travle/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    /**
     * 用户激活的方法
     * @param code  激活码
     * @return
     */
    @Override
    public Boolean active(String code) {
        //根据code查找对象
        User user = dao.findUserByCode(code);
        if(user != null){
            //调用dao的方法更改状态
            dao.updateStatus(user);
            return true;
        }else {
            return null;
        }

    }

    @Override
    public User login(User user) {
        User u = dao.findUserByNameAndpassword(user.getUsername(),user.getPassword());
        return u;
    }
}
