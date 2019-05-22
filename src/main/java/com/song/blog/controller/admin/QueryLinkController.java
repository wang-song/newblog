package com.song.blog.controller.admin;


import com.song.blog.controller.BaseController;
import com.song.blog.model.Bo.RestResponseBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by 13 on 2017/2/21.
 */
@Controller
@RequestMapping("/query_link")
public class QueryLinkController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryLinkController.class);


    @GetMapping(value = "")
    public String index() {

        return this.render("query_link");
    }

    @RequestMapping("/query")
    @ResponseBody
    public RestResponseBo pay(HttpSession session,
                              @RequestParam String productId) {

        RestResponseBo restResponseBo = new RestResponseBo();
        try {

            restResponseBo.setPayload(productId);
            restResponseBo.setMsg("成功跳转");
            restResponseBo.setSuccess(true);


        } catch (Exception e) {
            e.printStackTrace();
            return RestResponseBo.fail(0, "系统出错");
        }
        return restResponseBo;

    }

}
