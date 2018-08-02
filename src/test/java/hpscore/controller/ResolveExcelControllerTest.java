package hpscore.controller;

import hpscore.Util.TransferMpF;
import hpscore.domain.BusinessException;
import hpscore.domain.User;
import hpscore.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResolveExcelControllerTest {
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private UserRepository userRepository;

    private MockMvc mvc;
    private MockHttpSession session;

    @Autowired
    private TransferMpF tsfmpf;
    private File file=new File("D:/myDocuments/2018泛珠赛总决赛作品获奖表%28本科组%29.xls");
    MultipartFile multipartFile=null;

    @Before
    public void setUp() throws Exception {
        mvc=MockMvcBuilders.webAppContextSetup(wac).build();//初始化MockMvc对象
        session=new MockHttpSession();
        User user=userRepository.findByName("chen");
        session.setAttribute("user",user); //注册用户，通过拦截器
        //multipartFile=tsfmpf.fileToMpF(file);
    }

    @Test
    public void uploadTest() throws BusinessException {
        try{
            String result=mvc.perform(
                    MockMvcRequestBuilders
                        .fileUpload("/resolve/upload")
                        .file(
                                new MockMultipartFile("作品表.xls","./作品表.xls","multipart/form-data","hello upload".getBytes("utf-8"))
                        )
                        .session(session)
            ).andExpect(MockMvcResultMatchers.status().isOk())
             .andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch(FileNotFoundException e){
            System.out.println("文件未找到");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}