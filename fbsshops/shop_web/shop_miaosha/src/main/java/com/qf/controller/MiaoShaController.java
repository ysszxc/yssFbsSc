package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yss
 * @Date 2019/11/4 0004
 */

@Controller
@RequestMapping("/miaosha")
public class MiaoShaController {


    public String queryNow(){

        return "index";
    }

}
