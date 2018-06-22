package hpscore.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/19
 * Time: 22:39
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *@ClassName: FileUtil
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/6/19 22:39
 **/
public class FileUtil {

    public static void main(String[] args){

       List<String> fileList = FileUtil.readfiles( "./");
       for (String file: fileList){
           String pattern = ".*\\.xls";
           boolean isMatch = Pattern.matches(pattern, file);
           if(isMatch)System.out.println(file);
       }
    }
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

    public static List<String> readfiles(String filepath) {
        List<String> fileList = new ArrayList<>();
        File file = new File(filepath);
        if (file.isDirectory()) {
            System.out.println("文件夹");
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + "\\" + filelist[i]);
                if (!readfile.isDirectory()) {
                    //System.out.println("path=" + readfile.getPath());
                    //System.out.println("name=" + readfile.getName());
                    String pattern = ".*\\.xls";
                    boolean isMatch = Pattern.matches(pattern, readfile.getPath());
                    if(isMatch)fileList.add(readfile.getName());
                }
            }
        }
        return fileList;
    }
}
