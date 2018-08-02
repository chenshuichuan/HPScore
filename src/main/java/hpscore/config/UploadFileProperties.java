package hpscore.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * @Author haien
 * @Description 对上传的文件做一些限制，extends WebMvcConfigurerAdapter
 * @Date 11:49 2018/7/26
 * @Param
 * @return
 **/
@Configuration
public class UploadFileProperties extends WebMvcConfigurerAdapter {
    @Bean
    //multiPart格式的数据会将一个表单拆分为多个部分，每个部分对应一个输入域。它所对应的部分中会防止一些文本型数据。
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory=new MultipartConfigFactory();
        //设置文件大小限制，超出会抛异常
        factory.setMaxFileSize("10MB");  //KB/MB
        //设置总上传数据大小
        factory.setMaxRequestSize("20MB");
        return factory.createMultipartConfig();
    }
}
