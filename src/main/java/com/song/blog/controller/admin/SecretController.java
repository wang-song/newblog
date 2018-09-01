package com.song.blog.controller.admin;

import com.song.blog.controller.BaseController;
import com.song.blog.service.IContentService;
import com.song.blog.service.ILogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 13 on 2017/2/21.
 */
@Controller()
@RequestMapping("admin/secret")
public class SecretController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretController.class);

    @Resource
    private IContentService contentsService;

    @Resource
    private ILogService logService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
//        ContentVoExample contentVoExample = new ContentVoExample();
//        contentVoExample.setOrderByClause("created desc");
//        contentVoExample.createCriteria().andTypeEqualTo(Types.PAGE.getType());
//        PageInfo<ContentVo> contentsPaginator = contentsService.getArticlesWithpage(contentVoExample, 1, WebConst.MAX_POSTS);
//        request.setAttribute("articles", contentsPaginator);
        return "admin/secret_list";
    }


}
