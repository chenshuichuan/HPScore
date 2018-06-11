package hpscore.repository;


import hpscore.domain.Works;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/7
 * Time: 21:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorksRepositoryTest {

    @Autowired
    private WorksRepository worksRepository;

    @Test
    public void findByModel() throws Exception {

        //Works works = new Works()
        List<Works> worksList =  worksRepository.findByModel("本科组");
        Assert.assertThat(worksList.size(),is(3));
        List<Works> worksList2 =  worksRepository.findByModel("高职高专组");
        Assert.assertThat(worksList2.size(),is(4));
    }

    @Test
    public void findByNameAndModel() throws Exception {
        //Works works = new Works()

        Works works =  worksRepository.findByNameAndModel("5","高职高专组");
        Assert.assertThat(works.getCode(),equalTo("5"));

        Works works2 =  worksRepository.findByNameAndModel("作品1","本科组");
        Assert.assertThat(works2.getCode(),is("作品1"));
    }

    @Test
    public void findByCodeAndModel() throws Exception {

        Works works =  worksRepository.findByCodeAndModel("5","高职高专组");
        Assert.assertThat(works.getCode(),equalTo("5"));

        Works works2 =  worksRepository.findByCodeAndModel("1","本科组");
        Assert.assertThat(works2.getCode(),is("1"));
    }

}