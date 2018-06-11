package hpscore.repository;

import hpscore.domain.Pingwei;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/7
 * Time: 21:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PingweiRepositoryTest {
    @Autowired
    private PingweiRepository pingweiRepository;

    @Test
    public void findByModel() throws Exception {

        //Works works = new Works()
        List<Pingwei> worksList =  pingweiRepository.findByModel("本科组");
        Assert.assertThat(worksList.size(),is(4));
        List<Pingwei> worksList2 =  pingweiRepository.findByModel("高职高专组");
        Assert.assertThat(worksList2.size(),is(4));
    }

    @Test
    public void findByNameAndModel() throws Exception {
        //Works works = new Works()

        Pingwei works =  pingweiRepository.findByNameAndModel("p4","高职高专组");
        Assert.assertThat(works.getCode(),equalTo("4"));

        Pingwei works2 =  pingweiRepository.findByNameAndModel("p7","本科组");
        Assert.assertThat(works2.getCode(),is("7"));
    }

    @Test
    public void findByCodeAndModel() throws Exception {

        Pingwei works =  pingweiRepository.findByCodeAndModel("2","高职高专组");
        Assert.assertThat(works.getName(),equalTo("p2"));

        Pingwei works2 =  pingweiRepository.findByCodeAndModel("7","本科组");
        Assert.assertThat(works2.getName(),is("p7"));
    }

}