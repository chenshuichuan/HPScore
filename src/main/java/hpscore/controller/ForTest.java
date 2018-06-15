package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/7
 * Time: 22:33
 */

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import com.sargeraswang.util.ExcelUtil.*;
import hpscore.domain.InnovationScore;

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


        double d = 111231.5585;
        BigDecimal b = new BigDecimal(d);
        double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("2df = "+df);
//        InnovationScore innovationScore = new InnovationScore();
//        System.out.println("average = "+innovationScore.getAverage());


        df = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("3df = "+df);
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
