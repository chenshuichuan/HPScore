package hpscore.service.impl;

import hpscore.domain.User;
import hpscore.repository.UserRepository;
import hpscore.service.ExcelService;
import hpscore.service.ScoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/17
 * Time: 10:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelServiceImplTest {
    @Autowired
    private ExcelService excelService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScoreService scoreService;
    private String model1="本科组";
    //private String model1="高专高职组";

    @Test
    public void reviewExcel() throws Exception {
        String result = excelService.reviewExcel(model1);
        Assert.assertThat(result,notNullValue());
    }

    @Test
    public void reviewTransferExcel() throws Exception {
        String result = excelService.reviewTransferExcel(model1);
        Assert.assertThat(result,notNullValue());
    }

    @Test
    public void relativeScoreExcel() throws Exception {
        String result = excelService.relativeScoreExcel(model1);
        Assert.assertThat(result,notNullValue());
    }

    @Test
    public void finalScoreExcel() throws Exception {

        String result = excelService.finalScoreExcel(model1);
        Assert.assertThat(result,notNullValue());
    }


    @Test
    public void scoringSumUpExcel() throws Exception {

        String result = excelService.scoringSumUpExcel(model1);
        Assert.assertThat(result,notNullValue());
    }
}