package hpscore.controller;

import hpscore.domain.User;
import hpscore.repository.UserRepository;
import hpscore.service.ExcelService;
import hpscore.service.ScoreService;
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

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/27
 * Time: 13:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreControllerTest {
    @Autowired
    private ExcelService excelService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScoreService scoreService;
    private String model1="本科组";
    //private String model1="高专高职组";

    private MockMvc mvc;
    private MockHttpSession session;
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void beforeTest() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
        session = new MockHttpSession();
        User user =userRepository.findByName("chen");
        session.setAttribute("user",user); //拦截器那边会判断用户是否登录，所以这里注入一个用户
    }
    @Test
    public void selectByPidAndProIdAndModel() throws Exception {
    }

    @Test
    public void add() throws Exception {
    }

    @Test
    public void countAward() throws Exception {
        String url = "/score/countAward?model="+model1;
        mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void countScore() throws Exception {
        String url = "/score/countScore?model="+model1+"&editor=chen";
        mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void selectRelativeScoreByModel() throws Exception {
    }

    @Test
    public void calculteRelativeScoreAverageAndMaxAndMin() throws Exception {
    }

    @Test
    public void selectInnovationScore() throws Exception {
    }

    @Test
    public void selectUsefulScoreByModel() throws Exception {
    }

    @Test
    public void generateExcelByFileAndModel() throws Exception {
    }

}