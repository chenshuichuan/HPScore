package hpscore.controller;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

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

    @Before
    public void setUp() throws Exception {
        mvc=MockMvcBuilders.webAppContextSetup(wac).build();//初始化MockMvc对象
        session=new MockHttpSession();
        User user=userRepository.findByName("chen");
        session.setAttribute("user",user); //注册用户，通过拦截器
    }

    @Test
    @Transactional(rollbackFor = Exception.class) //开启事务，则自动回滚，数据库无变动（不加rollbackFor也一样）
    public void uploadTest() throws BusinessException {
        File file=new File("./data/zuoping.xls");
        try{
            String result=mvc.perform(
                    MockMvcRequestBuilders
                        .fileUpload("/resolve/upload?year=2019&cover=yes") //接口注意带上参数
                        .file(
                                new MockMultipartFile("file","zuoping.xls",
                                        "multipart/form-data",new FileInputStream(file))
                        )       //name：转换后的文件名称(但是不知何故只能写file) originalFilename：原文件名称 contentType：转换后文件类型 FileInputStream：文件输入流
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