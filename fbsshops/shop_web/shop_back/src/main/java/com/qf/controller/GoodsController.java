package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.feign.GoodsFeign;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @author Yss
 * @Date 2019/10/10 0010
 */

@Controller
@RequestMapping("/goodsManager")
public class GoodsController {


    @Autowired
    private GoodsFeign goodsFeign;

    private String uploadPath = "F:\\Java\\idea\\img上传";

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 获得商品列表
     * @return
     */
    @RequestMapping("/list")
    public String goodsList(ModelMap map){
        List<Goods> goods = goodsFeign.goodsList();
//        System.out.println("shop_back中/goodsManaer/list中的结果为："+goods);
        map.put("goodsList",goods);
        return "goodslist";
    }


    /**
     * 图片上传
     * @return
     */
    @RequestMapping("/uploader")
    @ResponseBody
    public String imgUploader(MultipartFile file){

        /* 1、先确定上传的文件位置 */
        //1.创建新的文件
        File outFile = new File(uploadPath);
        //2.确定文件夹是否存在
        if (!outFile.exists()){
            //2.1 不存在就创建文件夹 （mkdirs--意思：创建一个子文件夹）
            boolean mkdirs = outFile.mkdirs();
            //2.2 判断文件夹是否创建成功
            if (!mkdirs){
                //2.2.1不存在就报错
                throw new RuntimeException("上传路径为空且无法被创建！");
            }
        }
        /* 2、上传位置存在，开始上传 */
        //1.处理文件名称,通过UUID随机生成
        String filename = UUID.randomUUID().toString();
        //2.拼接文件上传路径，文件上传的路径为：上传文件位置+文件名
        outFile = new File(outFile,filename);

        /*以下是将图片上传至fastdfs服务器中*/
        String fullPath = null;
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "JPG",
                    null
            );
            fullPath = storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*以下是直接将图片上传至本地库中*/
//        //3.创建文件I/O流，可能会出现io异常使用try--catch
//        //3.1 在1.7中，try--catch新特性：
//        //3.2 在try后添加（）。（）中加入io流，可在try使用完毕后自动关流
//        try (
//                InputStream in = file.getInputStream();
//                OutputStream out = new FileOutputStream(outFile)
//        ){
//            IOUtils.copy(in,out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        fullPath = "http://192.168.254.128:8080/"+fullPath;
//        System.out.println("/goodsManager/uploader中上传路径为："+fullPath);

        return "{\"filename\":\""+fullPath+"\"}";
    }

    /**
     * 图片回显
     * @param filename
     * @param response
     */
    @RequestMapping("/showImg")
    public void showImg(String filename, HttpServletResponse response){
        File inFile = new File(uploadPath+"\\"+filename);

        try(
                InputStream in = new FileInputStream(inFile);
                OutputStream out = response.getOutputStream();
        ){
            IOUtils.copy(in,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */

    @RequestMapping("/insert")
    public String insert(Goods goods){
//        System.out.println("goodsManager/insert中的goods为："+goods);
        boolean flag = goodsFeign.goodsInsert(goods);
        return flag ? "redirect:http://localhost:16666/back/goodsManager/list" : "error";
    }

    @RequestMapping("/del")
    public String delById(Integer gid){
        System.out.println(gid);
        boolean flag = goodsFeign.delById(gid);
        System.out.println(flag);
        if(flag){
            return "redirect:http://localhost:16666/back/goodsManager/list";
        }
        return "error";
    }

}
