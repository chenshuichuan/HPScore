package hpscore.service.impl;

import hpscore.repository.PingweiRepository;
import hpscore.repository.ScoreRepository;
import hpscore.service.PingweiService;
import hpscore.service.ScoreService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/13
 * Time: 22:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreServiceImplTest {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private PingweiService pingweiService;

    @Test
    public void checkIfAllTheSameTimes() throws Exception {
        String model = "本科组";
        List<String> pingweiList = pingweiService.selectAllCodeByModel(model);
        int index = scoreService.checkIfAllTheSameTimes(model,pingweiList);
        System.out.println("index = "+index);
        Assert.assertThat(index,lessThan(pingweiList.size()));
    }

    @Test
    public void calculateRelativeScore() throws Exception {
        String model = "本科组";
        List<String> pingweiList = pingweiService.selectAllCodeByModel(model);
        int index = scoreService.calculateRelativeScore(model,pingweiList);
        if(index!=pingweiList.size()){
           System.out.println("第"+pingweiList.get(index)+"位的委评相对分计算出错！");
        }
    }

    @Test
    public void calculateByCodeAndModel() throws Exception {

        int index = scoreService.calculateByCodeAndModel( "1", "本科组");
        Assert.assertThat(index,is(1));
    }

}