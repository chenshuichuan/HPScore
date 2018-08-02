package hpscore.Util;

import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class TransferMpF {
    public MultipartFile fileToMpF(File file) throws IOException {
        FileInputStream fis=new FileInputStream(file);
        //注意这里面填什么，mpf里面对应的参数就有什么
        //如果只填了name，则mpf.getName()只能拿到name参数，但是getInputStream()为空
        MultipartFile mpf=new MockMultipartFile(file.getName(),file.getName(),null,fis);
        return mpf;
    }
}
