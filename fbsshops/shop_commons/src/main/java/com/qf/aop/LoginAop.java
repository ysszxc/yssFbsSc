package com.qf.aop;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * @author Yss
 * @Date 2019/10/25 0025
 */

@Aspect
@Component
public class LoginAop {


    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(Islogin)")
    public Object loginAop(ProceedingJoinPoint joinPoint){

        /*
        * 获得请求
        */
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        //1.获得Cookie对象
        String loginToken = null ;
        Cookie[] cookies = request.getCookies();
        if (cookies != null ){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("LOGIN_TOKEN")){
                    loginToken = cookie.getValue();
                    break;
                }
            }
        }

        //2.获得Redis内是否存在user
        User user = null;
        if(loginToken != null){
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }

        if (user == null ){

            //获得签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            Method method = signature.getMethod();

            Islogin islogin = method.getAnnotation(Islogin.class);

            boolean mustLogin = islogin.mustLogin();

            if(mustLogin){
                String url = "http://localhost:16666"+request.getRequestURI().toString();
//                System.out.println(url);
                String queryString = request.getQueryString();

                if (queryString != null){
                    url = url + "?" + queryString;
                }

//                System.out.println("url---" + url);

                try {
                    url = URLEncoder.encode(url,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return "redirect:http://localhost:16666/sso/toLogin?returnUrl="+url;
            }

        }

        //获得目标参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(args[i] != null && args[i].getClass() == User.class){
                args[i] = user;
                break;
            }
        }

//        System.out.println("调用目标方法前");

        //调用目标方法
        Object result = null;
        try {
            result = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

//        System.out.println("调用目标方法后");

        return result;

    }

}
