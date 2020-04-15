package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    Boolean regist(User user);

    Boolean active(String code);

    User login(User user);
}
