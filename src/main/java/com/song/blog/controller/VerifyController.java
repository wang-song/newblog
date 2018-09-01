package com.song.blog.controller;


import com.song.blog.model.Bo.RestResponseBo;
import com.song.blog.utils.RandomValidateCodeUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 验证
 * Created by BlueT on 2017/3/11.
 */
@Controller
@RequestMapping("/verify")
public class VerifyController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyController.class);


    /**
     * 校验验证码
     */
    @RequestMapping(value = "/")
    public String index() {

        return this.render("test");

    }

    @RequestMapping(value = "getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil.getRandcode(request, response);//输出验证码图片方法
            LOGGER.debug("获取验证码成功");
//            return RestResponseBo.ok();
        } catch (Exception e) {
            LOGGER.error("获取验证码失败>>>>   ", e);
//            return RestResponseBo.fail("获取验证码失败>>>>");
        }
    }

    /**
     * 校验验证码
     */
    @RequestMapping(value = "/checkVerify")
    @ResponseBody
    public RestResponseBo checkVerify(@RequestParam String verifyInput, HttpSession session) {
        try{
            //从session中获取随机数
            String random = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
            LOGGER.debug("取verifyInput:{}-----random:{}-----RANDOMVALIDATECODEKEY:{}",verifyInput,random,RandomValidateCodeUtil.RANDOMCODEKEY);
            if (random == null || verifyInput == "") {
                return RestResponseBo.fail("验证码为空");
            }
            if (random.equals(verifyInput)) {
                return RestResponseBo.ok("校验成功");
            } else {
                return RestResponseBo.fail("验证码错误");
            }
        }catch (Exception e){
            LOGGER.error("验证码校验失败", e);
            return RestResponseBo.fail("校验失败");
        }
    }


}
