package hpscore.repository;

import hpscore.domain.LogInfo;
import hpscore.domain.Score;
import hpscore.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/6
 * Time: 22:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogInfoRepository logInfoRepository;

    @Test
    public void ScoreRepositoryTest() throws Exception {
        Score score = new Score("pid","proid",
                10,20,30,4,5,6,
                1, "本科组");
        Score score1 = scoreRepository.save(score);
        Assert.assertThat(score1,notNullValue());
        Score score2 = scoreRepository.findOne(score1.getId());
        Assert.assertThat(score2.getOption1(),is(10));

        score2.setEditor1("ricardo");
        scoreRepository.save(score2);
        score2 = scoreRepository.findOne(score2.getId());
        Assert.assertThat(score2.getEditor1(),equalTo("ricardo"));

        scoreRepository.delete(score2);
        score2 = scoreRepository.findOne(score2.getId());
        Assert.assertThat(score2,nullValue());
    }

    @Test
    public void UserRepositoryTest() throws Exception {
        User score = new User("admin","admin",0);
        User score1 = userRepository.save(score);
        Assert.assertThat(score1,notNullValue());

        User score2 = userRepository.findOne(score1.getId());
        Assert.assertThat(score2.getRole(),is(0));

        score2.setName("ricardo");
        userRepository.save(score2);
        score2 = userRepository.findOne(score2.getId());
        Assert.assertThat(score2.getName(),equalTo("ricardo"));

        userRepository.delete(score2);
        score2 = userRepository.findOne(score2.getId());
        Assert.assertThat(score2,nullValue());
    }

    @Test
    public void logInfoRepositoryTest() throws Exception {

        long time = System.currentTimeMillis();
        Date date = new java.sql.Date(time);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr =df.format(date);
        LogInfo logInfo = new LogInfo("admin","ip:1245.ddd.d",timeStr,"action","本科组");
        LogInfo logInfo1 = logInfoRepository.save(logInfo);
        Assert.assertThat(logInfo1,notNullValue());

        LogInfo logInfo2 = logInfoRepository.findOne(logInfo1.getId());
        Assert.assertThat(logInfo2.getAction(),equalTo("action"));

        logInfo2.setName("ricardo");
        logInfoRepository.save(logInfo2);
        logInfo2 = logInfoRepository.findOne(logInfo2.getId());
        Assert.assertThat(logInfo2.getName(),equalTo("ricardo"));

        logInfoRepository.delete(logInfo2);
        logInfo2 = logInfoRepository.findOne(logInfo2.getId());
        Assert.assertThat(logInfo2,nullValue());
    }
}