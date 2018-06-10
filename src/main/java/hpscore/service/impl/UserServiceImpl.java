package hpscore.service.impl;


import hpscore.domain.User;
import hpscore.repository.UserRepository;
import hpscore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tengj on 2017/4/7.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public int add(User user) {
        User user1 = userRepository.save(user);
        if (null != user1) return 1;
        return 0;
    }

    @Override
    public int update(User user) {
        User user1 = userRepository.findOne(user.getId());
       if (null!=user1){
           userRepository.save(user);
           return 1;
       }
       return 0;
    }

    @Override
    public int delete(User user) {
        User user1 = userRepository.findOne(user.getId());
        if (null!=user1){
            userRepository.delete(user);
            return 1;
        }
        return 0;
    }


}
