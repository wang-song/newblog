package com.song.blog.controller.admin;


import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.song.blog.controller.BaseController;
import com.song.blog.dto.Product;
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
@RequestMapping("/pay")
public class PayController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);
    private static final String SHOPURL = "http://www.admin333.com/ShopProduct-";
    //private static final String PAYURL = "http://www.admin333.com/Pay-";


    @GetMapping(value = "")
    public String index() {
//        List<MetaVo> metas = metasService.getMetas(Types.LINK.getType());
//        request.setAttribute("links", metas);
        return this.render("pay");
    }

    @RequestMapping("/pay")
    @ResponseBody
    public RestResponseBo pay(HttpSession session,
                              @RequestParam String productId,
                              @RequestParam String email,
                              @RequestParam String paymentType) {

        RestResponseBo restResponseBo = new RestResponseBo();

        try {
            //验证邮箱
            if (email == "") {
                return RestResponseBo.fail(0, "邮箱不能为空");
            }

            //获取支付方式，构造参数 map
            Product product = new Product();
            product.setOrderNO("0");
            product.setProductId(productId);
            product.setTotalCount(1);
            product.setEmail(email);
            product.setContact(email);
            product.setPaymentType(paymentType);
            switch (paymentType) {
                case "56":
                    product.setUserPaymentId("5135946");
                    break;
                case "54":
                    product.setUserPaymentId("5135944");
                    break;
                case "58":
                    product.setUserPaymentId("5135948");
                    break;
            }
            LOGGER.debug(product.toString());
            HttpConfig config = HttpConfig.custom().url(SHOPURL + productId + ".html").map(product.toMap());

            String resultHtml = HttpClientUtil.post(config);
            LOGGER.debug(resultHtml);
            JsonObject jsonObject = new JsonParser().parse(resultHtml).getAsJsonObject();

            if(jsonObject.get("Success").getAsBoolean()){
                restResponseBo.setPayload(jsonObject.get("Data").getAsString());
            }

            restResponseBo.setMsg(jsonObject.get("Message").getAsString());
            restResponseBo.setSuccess(jsonObject.get("Success").getAsBoolean());


        } catch (Exception e) {
            e.printStackTrace();
            return RestResponseBo.fail(0, "系统出错");
        }

        return restResponseBo;


    }

}
