package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.Pingwei;
import hpscore.domain.User;
import hpscore.repository.PingweiRepository;
import hpscore.repository.ScoreRepository;
import hpscore.repository.UserRepository;
import hpscore.repository.WorksRepository;
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
 *@ClassName: PingweiController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 21:29
 **/
@RestController
@RequestMapping("/pingwei")
public class PingweiController {

    private final static Logger logger = LoggerFactory.getLogger(PingweiController.class);
    @Autowired
    private WorksService worksService;
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private WorksRepository worksRepository;
    @Autowired
    private PingweiRepository pingweiRepository;
    @Autowired
    private LogInfoService logInfoService;

    //根据评委序号和model查询该评委是否已经存在，并返回
    @RequestMapping(value = "/getPingweiByCodeAndModel",method = RequestMethod.GET)
    public Map<String,Object> getUserByName(
            @RequestParam("code")String code, @RequestParam("model")String model){
        Map<String,Object> map =new HashMap<String,Object>();
        Pingwei pingwei = pingweiRepository.findByCodeAndModel(code,model);
        //为查找到数据是score为null
        if (pingwei!=null) {
            map.put("result",1);
            map.put("pingwei",pingwei);
            map.put("message","找到对应数据");
        }
        else{
            map.put("result",0);
            map.put("pingwei",null);
            map.put("message","未找到对应数据");
        }

        return map;
    }

    //根据user id 删除user
    @RequestMapping(value = "/deletePingweiById",method = RequestMethod.GET)
    public Map<String,Object> deletePingweiById(
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
        Pingwei pingwei = pingweiRepository.findOne(idInt);
        //为查找到数据是pingwei为null
        if (pingwei!=null) {
            pingweiRepository.delete(idInt);
            action+=",成功删除"+pingwei.getCode()+"评委记录";
            map.put("result",1);
            map.put("message","成功删除"+pingwei.getCode()+"评委记录");
        }
        else{
            action+=",删除评委失败！";
            map.put("result",0);
            map.put("message","删除评委失败！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
        return map;
    }

    //更新pingwei数据
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> update(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String model = request.getParameter("model");
        int id = Integer.parseInt(idStr);
        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");

        System.out.println("getgetAttribute(\"model\")="+model);
        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".update-参数：id="+id+",name="+name+
                ", code="+code+",model="+model;

        Pingwei pingwei = pingweiRepository.findOne(id);
        if(pingwei!=null){
            logger.info("---更新user数据，正在写入数据库-----");
            pingwei.setName(name);
            pingwei.setCode(code);
            pingwei.setModel(model);
            pingweiRepository.save(pingwei);

            action+=",成功更改评委信息！";
            map.put("result",1);
            map.put("message","成功更改评委信息！");
        }else{
            action+=",更改评委信息失败！";
            map.put("result",0);
            map.put("message","更改评委信息失败！请检查数据是否已存在！");
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
        String code = request.getParameter("code");
        String model = request.getParameter("model");

        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");
        System.out.println("getgetAttribute(\"model\")="+model);
        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".update-参数：name="+name+
                ", code="+code+",role="+model;

        Pingwei pingwei = pingweiRepository.findByCodeAndModel(code,model);
        if(pingwei==null){
            logger.info("---添加评委数据，正在写入数据库-----");
            pingweiRepository.save(new Pingwei (name,code,model));

            action+=",成功添加评委记录！";
            map.put("result",1);
            map.put("message","成功添加评委记录！");
        }else{
            action+=",添加评委记录失败！";
            map.put("result",0);
            map.put("message","添加评委记录失败！请检查数据是否已存在！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
        return map;
    }
}
