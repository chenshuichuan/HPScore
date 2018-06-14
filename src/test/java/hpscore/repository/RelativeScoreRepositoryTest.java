package hpscore.repository;

import hpscore.domain.RelativeScore;
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
 * Date: 2018/6/14
 * Time: 12:54
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RelativeScoreRepositoryTest {

    @Autowired
    private RelativeScoreRepository relativeScoreRepository;


    @Test
    public void findByModel() throws Exception {
        List<RelativeScore> relativeScoreList = relativeScoreRepository.findByModel("本科组");
        Assert.assertThat(relativeScoreList.size(),is(2));
        List<RelativeScore> relativeScoreList1 = relativeScoreRepository.findByModel("高职高专组");
        Assert.assertThat(relativeScoreList.size(),is(2));
    }

    @Test
    public void findByProIdAndModel() throws Exception {
        RelativeScore relativeScore = relativeScoreRepository.findByProIdAndModel("3","高职高专组");
        Assert.assertThat(relativeScore.getProName(),equalTo("作品3"));
    }
    @Test
    public void add() throws Exception {
        RelativeScore relativeScore = new RelativeScore("3","作品3","高职高专组");
        RelativeScore temp = relativeScoreRepository.findByProIdAndModel(
                relativeScore.getProId(),relativeScore.getModel());
        if(temp == null){
            relativeScore.setMaxScore(20);
            RelativeScore relativeScore1 = relativeScoreRepository.save(relativeScore);
            Assert.assertThat(relativeScore1.getMaxScore(),is((double)20));
        }
        else{
            temp.setMaxScore(20);
            RelativeScore relativeScore1 = relativeScoreRepository.save(temp);
            Assert.assertThat(relativeScore1.getMaxScore(),is((double)20));
        }


        RelativeScore relativeScore2 = new RelativeScore("4","作品4","本科组");

        temp = relativeScoreRepository.findByProIdAndModel(
                relativeScore2.getProId(),relativeScore2.getModel());
        if(temp==null){
            relativeScore2.setMaxScore(30);
            RelativeScore relativeScore3 = relativeScoreRepository.save(relativeScore2);
            Assert.assertThat(relativeScore3.getMaxScore(),is((double)30));
        }
        else{
            temp.setMaxScore(30);
            RelativeScore relativeScore1 = relativeScoreRepository.save(temp);
            Assert.assertThat(relativeScore1.getMaxScore(),is((double)30));
        }

    }

}