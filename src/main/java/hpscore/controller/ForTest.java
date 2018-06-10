package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/7
 * Time: 22:33
 */

import java.util.ArrayList;
import java.util.List;

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

}
