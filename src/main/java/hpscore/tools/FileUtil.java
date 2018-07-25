package hpscore.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/19
 * Time: 22:39
 */

import hpscore.domain.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 *@ClassName: FileUtil
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/6/19 22:39
 **/
public class FileUtil {

    public static void main(String[] args) throws IOException {

        String encoding = System.getProperty("file.encoding");
        System.out.println(encoding);
        List<FileInfo> fileInfoList = readFileInfo(".");
        for (FileInfo fileInfo: fileInfoList){
            System.out.println("name = "+fileInfo.getName());
            System.out.println("size = "+fileInfo.getSize());
            System.out.println("updatetime = "+fileInfo.getUpdateTime());
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
        //获取系统编码
        String encoding = System.getProperty("file.encoding");
        System.out.println("encoding = "+encoding);
        List<String> fileList = new ArrayList<>();
        try {
            File file = new File(new String(filepath.getBytes(encoding),"UTF-8"));
            if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {

                        String pattern = ".*\\.xls";
                        String fileName =readfile.getName();
                        int index = fileName.lastIndexOf('\\');
                        fileName = fileName.substring(index+1);
                        boolean isMatch = Pattern.matches(pattern, fileName);
                        if(isMatch){
                            String fileName2 = new String(fileName.getBytes(encoding),"UTF-8");
                            fileList.add(fileName2);
                        }

                    }
                }
            }
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * @Author haien
     * @Description 读取项目中的Excel表格，提取表格名称、大小和更新时间
     * @Date 10:08 2018/7/25
     * @Param [filepath]
     * @return java.util.List<hpscore.domain.FileInfo>
     **/
    public static List<FileInfo> readFileInfo(String filepath) throws IOException {
        //获取系统编码
        String encoding = System.getProperty("file.encoding");
        System.out.println("encoding = "+encoding);
        List<FileInfo> fileList = new ArrayList<>();

        try {
            File file = new File(new String(filepath.getBytes(encoding),"UTF-8"));
            if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {

                        String pattern = ".*\\.xls";
                        String fileName =readfile.getName();
                        int index = fileName.lastIndexOf('\\');
                        fileName = fileName.substring(index+1);
                        boolean isMatch = Pattern.matches(pattern, fileName);
                        if(isMatch){
                            String fileName2 = new String(fileName.getBytes(encoding),"UTF-8");
                            //获得将要操作的文件
                            Path path = Paths.get(fileName2);
                            //获取访问基本属性的BasicFileAttributeView
                            BasicFileAttributeView basicView = Files.getFileAttributeView(
                                    path, BasicFileAttributeView.class);
                            //获取访问基本属性的BasucFileAttributes
                            BasicFileAttributes basicAttribs = basicView.readAttributes();
                            //最后修改时间
                            Date date =new Date(basicAttribs.lastModifiedTime().toMillis());
                            Format format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
                            String updateTime = format.format(date);
                            //文件大小
                            long size = basicAttribs.size();
                            fileList.add(new FileInfo(fileName2,size,updateTime));
                        }

                    }
                }
            }
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return fileList;
    }
}
