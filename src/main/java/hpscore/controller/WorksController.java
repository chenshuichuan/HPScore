package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.User;
import hpscore.repository.ScoreRepository;
import hpscore.repository.UserRepository;
import hpscore.service.LogInfoService;
import hpscore.service.WorksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName: IndexController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 21:29
 **/
@RestController
@RequestMapping("/works")
public class WorksController {

    private final static Logger logger = LoggerFactory.getLogger(WorksController.class);


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorksService worksService;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private LogInfoService logInfoService;

    //根据评委以及作品和model查询该作品评分记录是否已经存在，并返回
    @RequestMapping(value = "/getWorksByName",method = RequestMethod.GET)
    public Map<String,Object> getUserByName(
            @RequestParam("name")String name){
        Map<String,Object> map =new HashMap<String,Object>();
        User user = userRepository.findByName(name);
        //为查找到数据是score为null
        if (user!=null) {
            map.put("result",1);
            map.put("user",user);
            map.put("message","找到对应数据");
        }
        else{
            map.put("result",0);
            map.put("user",null);
            map.put("message","未找到对应数据");
        }

        return map;
    }

    //根据user id 删除user
    @RequestMapping(value = "/deleteUserById",method = RequestMethod.GET)
    public Map<String,Object> deleteUserById(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("id")String id){
        int idInt = Integer.parseInt(id);

        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");
        String model = (String)request.getSession().getAttribute("model");
        System.out.println("getgetAttribute(\"model\")="+model);
        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".getUserByName-参数：id="+id;

        Map<String,Object> map =new HashMap<String,Object>();
        User user = userRepository.findOne(idInt);
        //为查找到数据是score为null
        if (user!=null) {
            userRepository.delete(idInt);
            action+=",成功删除"+user.getName()+"账号";
            map.put("result",1);
            map.put("message","成功删除"+user.getName()+"账号");
        }
        else{
            action+=",删除账号失败！";
            map.put("result",0);
            map.put("message","删除账号失败！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
        return map;
    }

    //更新user数据
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> update(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String roleStr = request.getParameter("role");
        int id = Integer.parseInt(idStr);
        int role = Integer.parseInt(roleStr);

        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");
        String model = (String)request.getSession().getAttribute("model");
        System.out.println("getgetAttribute(\"model\")="+model);
        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".update-参数：id="+id+",name="+name+
                ", password="+password+",role="+role;

        User user = userRepository.findOne(id);
        if(user!=null){
            logger.info("---更新user数据，正在写入数据库-----");
            user.setName(name);
            user.setPassword(password);
            user.setRole(role);
            userRepository.save(user);

            action+=",成功更改账号记录！";
            map.put("result",1);
            map.put("message","成功更改账号记录！");
        }else{
            action+=",更改账号记录失败！";
            map.put("result",0);
            map.put("message","更改账号记录失败！请检查数据是否已存在！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
        return map;
    }

    //添加user数据
    /**
     *@Author: Ricardo
     *@Description:
     *@Date: 23:43 2018/6/9
     *@param:
     **/
    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String,Object> add(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String roleStr = request.getParameter("role");
        int role = Integer.parseInt(roleStr);

        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");
        String model = (String)request.getSession().getAttribute("model");
        System.out.println("getgetAttribute(\"model\")="+model);
        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".update-参数：name="+name+
                ", password="+password+",role="+role;

        User user = userRepository.findByName(name);
        if(user==null){
            logger.info("---添加user数据，正在写入数据库-----");
            userRepository.save(new User (name,password,role));

            action+=",成功添加账号记录！";
            map.put("result",1);
            map.put("message","成功添加账号记录！");
        }else{
            action+=",添加user记录失败！";
            map.put("result",0);
            map.put("message","添加账号记录失败！请检查数据是否已存在！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
        return map;
    }
}
