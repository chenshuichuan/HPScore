package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/7
 * Time: 22:33
 */

import java.io.*;
import java.util.*;

import com.sargeraswang.util.ExcelUtil.*;

/**
 *@ClassName: ForTest
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/6/7 22:33
 **/
public class ForTest {
    public static void main(String[] args) {
        String path1 = System.getProperty("user.dir");
        System.out.println("SpringApplication.run successful!");
        System.out.println("path1="+path1);
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = null;
        //给list1赋值
        list1.add("测");
        list1.add("试");

        list1.addAll(list2);
        for (String str:list1)System.out.println(str);
    }
    public static void writeExcel(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map =new LinkedHashMap<>();
        map.put("name", "");
        map.put("age", "");
        map.put("birthday","");
        map.put("sex","");
        Map<String,Object> map2 =new LinkedHashMap<String, Object>();
        map2.put("name", "测试是否是中文长度不能自动宽度.测试是否是中文长度不能自动宽度.");
        map2.put("age", null);
        map2.put("sex", null);
        map.put("birthday",null);
        Map<String,Object> map3 =new LinkedHashMap<String, Object>();
        map3.put("name", "张三");
        map3.put("age", 12);
        map3.put("sex", "男");
        map3.put("birthday",new Date());
        list.add(map);
        list.add(map2);
        list.add(map3);
        Map<String,String> map1 = new LinkedHashMap<>();
        map1.put("name","姓名");
        map1.put("age","年龄");
        map1.put("birthday","出生日期");
        map1.put("sex","性别");
        File f= new File("test.xls");
        OutputStream out = null;
        try {
            out = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ExcelUtil.exportExcel(map1,list, out );

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
