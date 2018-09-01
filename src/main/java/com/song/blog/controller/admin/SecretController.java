package com.song.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.song.blog.controller.BaseController;
import com.song.blog.model.Vo.SecretVo;
import com.song.blog.model.Vo.SecretVoExample;
import com.song.blog.service.ISecretService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/secret")
public class SecretController extends BaseController {

    @Resource
    private ISecretService secretService;

    @GetMapping("")
    public String index(HttpServletRequest request) {
        SecretVoExample secretVoExample = new SecretVoExample();
        secretVoExample.setOrderByClause("sid desc");
        PageInfo<SecretVo> secretVoPageInfo = secretService.findSecretLimit(secretVoExample,1,10);
        request.setAttribute("secrets", secretVoPageInfo);
        return "admin/secret_list";
    }
}
