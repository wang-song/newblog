package com.song.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.song.blog.constant.WebConst;
import com.song.blog.controller.BaseController;
import com.song.blog.dto.LogActions;
import com.song.blog.model.Bo.RestResponseBo;
import com.song.blog.model.Vo.SecretVo;
import com.song.blog.model.Vo.SecretVoExample;
import com.song.blog.service.ILogService;
import com.song.blog.service.ISecretService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author wangsong
 */
@Controller
@RequestMapping("admin/secret")
public class SecretController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretController.class);
    @Resource
    private ISecretService secretService;

    @Resource
    private ILogService logService;

    @GetMapping("")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit) {
        SecretVoExample secretVoExample = new SecretVoExample();
        secretVoExample.setOrderByClause("sid desc");
        PageInfo<SecretVo> secretVoPageInfo = secretService.findSecretLimit(secretVoExample,page,limit);
        request.setAttribute("secrets", secretVoPageInfo);
        return "admin/secret_list";
    }

    @RequestMapping("delete")
    @ResponseBody
    public RestResponseBo delSecretbyid(HttpServletRequest request,
                                        @RequestParam int sid){
        String result = secretService.delSecretByID(sid);
        if (!WebConst.SUCCESS_RESULT.equals(result)) {
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }


    @RequestMapping("new")
    @ResponseBody
    public RestResponseBo buildSecret(HttpServletRequest request,
                                      @RequestParam Double buildMoney,
                                      @RequestParam int buildCount){
        LOGGER.debug("数量{}---金额{}",buildCount,buildMoney);

        StringBuffer stringBuffer = new StringBuffer();

        RestResponseBo restResponseBo = new RestResponseBo();

        try{
            for (int i = 0; i < buildCount; i++) {
                String uuid  = UUID.randomUUID().toString();
                secretService.saveSecret(new SecretVo(uuid,buildMoney));
                stringBuffer.append(uuid);
                stringBuffer.append("\n");
            }
            logService.insertLog(LogActions.ADD_SECRET.getAction(),
                    buildCount+"--"+stringBuffer.toString(),
                    request.getRemoteAddr(),this.getUid(request));
            restResponseBo.setSuccess(true);
            restResponseBo.setMsg(stringBuffer.toString());
            return restResponseBo;

        }catch (Exception e){
            e.printStackTrace();
            restResponseBo.setMsg("系统出错，请重试");
            restResponseBo.setSuccess(false);
            return restResponseBo;
        }
    }
}
