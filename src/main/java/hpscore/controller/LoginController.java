package hpscore.controller;

import hpscore.domain.User;
import hpscore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tengj on 2017/4/10.
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/userlogin")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String userName=request.getParameter("userName");
        String password=request.getParameter("passWord");
        User user = userRepository.findByNameAndPassword(userName,password);
        //System.out.println("login -- login --- ,userName="+user.getName()+",role="+user.getRole());
        if(user!=null){
            System.out.println("login -- userlogin:user!=null ");
            request.getSession().setAttribute("user",user);
            User user1 = (User)request.getSession().getAttribute("user");
            System.out.println("login -- userlogin:user1.name="+user1.getName());
            map.put("result",1);
            map.put("role",user.getRole());
        }else{
            map.put("result",0);
        }
        return map;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public Map<String,Object> logout(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();

        User user1 = (User)request.getSession().getAttribute("user");
        if(user1 == null){
            System.out.println("logout 成功！ -- logout:user==null ");
            try {
                response.sendRedirect("toLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("result",1);
        }else{
            map.put("result",0);
        }
        return map;
    }
}
