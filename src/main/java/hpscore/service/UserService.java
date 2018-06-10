package hpscore.service;


import hpscore.domain.User;

/**
 * Created by tengj on 2017/4/7.
 */
public interface UserService {
    int add(User user);
    int update(User user);
    int delete(User user);
}
