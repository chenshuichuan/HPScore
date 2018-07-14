package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.User;
import hpscore.domain.Works;
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
 *@ClassName: IndexController
 *@Description: 作品页面控制器
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
    private WorksRepository worksRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private LogInfoService logInfoService;

    //根据作品code和model查询该作品是否已经存在，并返回
    @RequestMapping(value = "/getWorksByCodeAndModel",method = RequestMethod.GET)
    public Map<String,Object> getWorksByCodeAndModel(
            @RequestParam("code")String code, @RequestParam("model")String model){
        Map<String,Object> map =new HashMap<String,Object>();
        Works works = worksRepository.findByCodeAndModel(code,model);

        if (works!=null) {
            map.put("result",1);
            map.put("works",works);
            map.put("message","找到对应数据");
        }
        else{
            map.put("result",0);
            map.put("works",null);
            map.put("message","未找到对应数据");
        }
        return map;
    }

    //根据works id 删除works
    @RequestMapping(value = "/deleteWorksById",method = RequestMethod.GET)
    public Map<String,Object> deleteWorksById(
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
        String action =this.getClass().getName()+ ".deleteWorksById-参数：id="+id;

        Map<String,Object> map =new HashMap<String,Object>();
        Works works = worksRepository.findOne(idInt);
        //为查找到数据是score为null
        if (works!=null) {
            worksRepository.delete(idInt);
            action+=",成功删除"+works.getName()+"作品";
            map.put("result",1);
            map.put("message","成功删除"+works.getName()+"作品");
        }
        else{
            action+=",删除作品失败！";
            map.put("result",0);
            map.put("message","删除作品失败！");
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
        String code = request.getParameter("code");
        String bianHao = request.getParameter("bianHao");
        String name = request.getParameter("name");
        String partName = request.getParameter("partName");
        String school = request.getParameter("school");
        String teachers = request.getParameter("teachers");
        String students = request.getParameter("students");
        String model = request.getParameter("model");

        int id = Integer.parseInt(idStr);
        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");
        System.out.println("getgetAttribute(\"model\")="+model);
        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".update-参数：id="+id+",name="+name+
                ", code="+code+",bianHao="+bianHao+
                ", partName="+partName+",school="+school+
                ", teachers="+teachers+",students="+students+",model="+model;

        Works works = worksRepository.findOne(id);
        if(works!=null){
            logger.info("---更新user数据，正在写入数据库-----");
            works.setCode(code);
            works.setBianHao(bianHao);
            works.setName(name);
            works.setPartName(partName);
            works.setSchool(school);
            works.setStudents(students);
            works.setTeachers(teachers);
            works.setModel(model);

            action+=",成功更改作品信息！";
            map.put("result",1);
            map.put("message","成功更改作品信息！");
        }else{
            action+=",更改作品信息失败！";
            map.put("result",0);
            map.put("message","更改作品作品信息！请检查数据是否已存在！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
        return map;
    }

    //添加作品数据
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
        String code = request.getParameter("code");
        String bianHao = request.getParameter("bianHao");
        String name = request.getParameter("name");
        String partName = request.getParameter("partName");
        String school = request.getParameter("school");
        String teachers = request.getParameter("teachers");
        String students = request.getParameter("students");
        String model = request.getParameter("model");

        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");
        System.out.println("getgetAttribute(\"model\")="+model);
        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".update-参数：name="+name+
                ", code="+code+",bianHao="+bianHao+
                ", partName="+partName+",school="+school+
                ", teachers="+teachers+",students="+students+",model="+model;

        Works works = worksRepository.findByCodeAndModel(code,model);
        if(works==null){
            logger.info("---添加works数据，正在写入数据库-----");
            works = new Works(name,code,model);
            works.setBianHao(bianHao);
            works.setPartName(partName);
            works.setSchool(school);
            works.setTeachers(teachers);
            works.setStudents(students);
            worksRepository.save(works);

            action+=",成功添加作品信息！";
            map.put("result",1);
            map.put("message","成功添加作品信息！");
        }else{
            action+=",添加作品信息失败！";
            map.put("result",0);
            map.put("message","添加作品信息失败！请检查数据是否已存在！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
        return map;
    }
}
