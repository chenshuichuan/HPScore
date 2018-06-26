package hpscore.repository;

import hpscore.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/26
 * Time: 17:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void findByNameAndPassword() throws Exception {
        User user= userRepository.findByNameAndPassword("chen","969396");
        Assert.assertThat(user.getName(),equalTo("chen"));
    }

    @Test
    public void findByName() throws Exception {
        User user= userRepository.findByName("chen");
        Assert.assertThat(user.getName(),equalTo("chen"));
    }

}