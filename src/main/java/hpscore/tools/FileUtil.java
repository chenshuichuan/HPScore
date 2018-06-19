package hpscore.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/19
 * Time: 22:39
 */

import java.io.File;
import java.io.IOException;

/**
 *@ClassName: FileUtil
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/6/19 22:39
 **/
public class FileUtil {
    // 判断文件是否存在
    public static boolean FileExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("file exists");
            return true;
        } else {
            System.out.println("file not exists");
            return false;
        }
    }
}
