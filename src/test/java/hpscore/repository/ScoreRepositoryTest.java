package hpscore.repository;

import hpscore.domain.Score;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/7
 * Time: 22:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreRepositoryTest {

    @Autowired
    private ScoreRepository scoreRepository;

    @Test
    public void testOthers() throws Exception {
        List<Score> scores = scoreRepository.findAll();
        Assert.assertThat(scores.size(),greaterThan(5));
    }
    @Test
    public void findByEditor1AndModel() throws Exception {

        List<Score> scores = scoreRepository.findByEditor1AndModel("ricardo","本科组");
        Assert.assertThat(scores.size(),greaterThan(5));
    }

    @Test
    public void findByEditor2AndModel() throws Exception {
        List<Score> scores = scoreRepository.findByEditor2AndModel("record2","高职高专组");
        Assert.assertThat(scores.size(),greaterThan(5));
    }

    @Test
    public void findScoreByEditTimes() throws Exception {
        List<Score> scores = scoreRepository.findScoreLessThanEditTimes(2);
        for (Score s:scores){
            System.out.println("score ="+s.getPid()+","+s.getProId());
        }
        Assert.assertThat(scores.size(),greaterThan(2));
    }

}