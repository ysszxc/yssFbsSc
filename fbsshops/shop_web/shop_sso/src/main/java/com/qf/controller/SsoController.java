package com.qf.controller;

import com.qf.entity.Email;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Yss
 * @Date 2019/10/21 0021
 */

@Controller
@RequestMapping("/sso")
public class SsoController {


    @Autowired
    private IUserService userService;

    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisTemplate redisTemplate;

    @RequestMapping("/toForgetPassword")
    public String toForgetPassword(){
        return "forgetPassword";
    }

    @RequestMapping("toUpdataPassword")
    public String toUpdataPassword(String username){
        return "updatepassword";
    }

    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/register")
    public String register(User user, ModelMap map){

        int result = userService.register(user);

        if (result > 0){
//            System.out.println("注册成功");
            return "login";
        }
        if(result == -1){
//            System.out.println("注册失败");
            map.put("error","用户名已经存在");
        }else {
            map.put("error","请联系管理员");
        }

        return "register";
    }


    @RequestMapping("/findPassword")
    @ResponseBody
    public ResultData findPassword(String username, ModelMap map){
//        System.out.println("sso中findPassword中接收的username为："+username);

        //查询用户在不在,获得用户信息
        User user = userService.queryByUsername(username);
//        System.out.println("sso中findPassword中查询到的User为："+user);
        if (user == null){
            return new ResultData().setCode("1000").setMsg("用户名不存在");
        }
//        System.out.println("sso中findPassword中查询到的Email为："+user.getEmail());

        String token = UUID.randomUUID().toString();
//        System.out.println("token:"+token);
        stringRedisTemplate.opsForValue().set(token,username);
        stringRedisTemplate.expire(token,5, TimeUnit.MINUTES);

        String url = "http://localhost:16666/sso/toUpdataPassword?token="+token;
        String content = "点击以下链接找回密码：<a href='"+url+"'>" + url + "</a>";

        Email email = new Email()
                .setSubject("shop CZ")
                .setTo(user.getEmail())
                .setContent(content);

        //通过邮箱发送邮箱信息
        rabbitTemplate.convertAndSend("mail_exceptiion","",email);

//        System.out.println("sso中findPassword中的邮件发送成功");


        Map<String ,String> data = new HashMap<>();
        String emailMiddleInfo = user.getEmail().substring(3,user.getEmail().indexOf("@"));
        String mailInfo = user.getEmail().replace(emailMiddleInfo,"********");
        data.put("mailInfo",mailInfo);


        String emailUrl = " http://mail." + user.getEmail().substring(user.getEmail().indexOf("@") + 1);
        data.put("emailUrl",emailUrl);

        return new ResultData().setCode("0000").setMsg("邮件发送成功").setData(data);
    }

    @RequestMapping("updatePassword")
    public String updatePassword(String token,String password){
//        System.out.println("密码修改成功");

        String username = stringRedisTemplate.opsForValue().get(token);

//        System.out.println(username+"--"+password);

        if (username !=null){
            int result = userService.updataPasswordByUserName(username, password);
//            System.out.println(result);
            if (result > 0){
                stringRedisTemplate.delete(token);
                return "login";
            }
        }

        return "updataError";
    }

    @RequestMapping("login")
    public String login(String username, String password,String returnUrl,
                        HttpServletResponse response){

        if (returnUrl == null || returnUrl.equals("")){
            returnUrl = "http://localhost:16666/";
        }

        User user = userService.login(username, password);

        if (user != null){
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(token,user);
            redisTemplate.expire(token,7,TimeUnit.DAYS);

            Cookie cookie = new Cookie("LOGIN_TOKEN",token);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            response.addCookie(cookie);
            try {
                System.out.println("returnUrl+++++"+returnUrl);
                return "redirect:http://localhost:16666/cart/merge?returnUrl="+ URLEncoder.encode(returnUrl,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "login";
    }

    @RequestMapping("isLogin")
    @ResponseBody
    public ResultData<User> isLogin(@CookieValue(value = "LOGIN_TOKEN",required = false) String loginToken){
//        System.out.println("判断是否登录，cookie的值为:"+loginToken);
        if(loginToken != null){
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if(user!=null){
                return new  ResultData<User>().setCode("0000").setMsg("已登录").setData(user);
            }
        }
        return  new  ResultData<User>().setCode("2001").setMsg("未登录");
    }


    @RequestMapping("logout")
    public String logout(@CookieValue(value = "LOGIN_TOKEN",required = false) String loginToken,HttpServletResponse response){

//        System.out.println("loginToken:"+loginToken);
        if (loginToken != null){
            //清除cookie
            Cookie cookie = new Cookie("LOGIN_TOKEN",loginToken);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            //清除redis
            redisTemplate.delete(loginToken);
        }
        return "login";
    }
}


