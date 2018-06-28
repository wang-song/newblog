package com.song.blog.controller.admin;


import com.song.blog.constant.WebConst;
import com.song.blog.controller.BaseController;
import com.song.blog.dto.LogActions;
import com.song.blog.exception.TipException;
import com.song.blog.model.Bo.BackResponseBo;
import com.song.blog.model.Bo.RestResponseBo;
import com.song.blog.model.Vo.OptionVo;
import com.song.blog.service.ILogService;
import com.song.blog.service.IOptionService;
import com.song.blog.service.ISiteService;
import com.song.blog.utils.GsonUtils;
import com.song.blog.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangq on 2017/3/20.
 */
@Controller
@RequestMapping("/admin/setting")
public class SettingController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingController.class);

    @Resource
    private IOptionService optionService;

    @Resource
    private ILogService logService;

    @Resource
    private ISiteService siteService;

    /**
     * 系统设置
     */
    @GetMapping(value = "")
    public String setting(HttpServletRequest request) {
        List<OptionVo> voList = optionService.getOptions();
        Map<String, String> options = new HashMap<>();
        voList.forEach((option) -> {
            options.put(option.getName(), option.getValue());
        });
        if (options.get("site_record") == null) {
            options.put("site_record", "");
        }
        request.setAttribute("options", options);
        return "admin/setting";
    }

    /**
     * 保存系统设置
     */
    @PostMapping(value = "")
    @ResponseBody
    public RestResponseBo saveSetting(@RequestParam(required = false) String site_theme, HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> querys = new HashMap<>();
            parameterMap.forEach((key, value) -> {
                querys.put(key, join(value));
            });
            optionService.saveOptions(querys);
            WebConst.initConfig = querys;
            if (StringUtils.isNotBlank(site_theme)) {
                BaseController.THEME = "themes/" + site_theme;
            }
            logService.insertLog(LogActions.SYS_SETTING.getAction(), GsonUtils.toJsonString(querys), request.getRemoteAddr(), this.getUid(request));
            return RestResponseBo.ok();
        } catch (Exception e) {
            String msg = "保存设置失败";
            return RestResponseBo.fail(msg);
        }
    }


    /**
     * 系统备份
     *
     * @return
     */
    @PostMapping(value = "backup")
    @ResponseBody
    public RestResponseBo backup(@RequestParam String bk_type, HttpServletRequest request) {
        if (StringUtils.isBlank(bk_type)) {
            return RestResponseBo.fail("请确认信息输入完整");
        }
        try {
            BackResponseBo backResponse = siteService.backup(bk_type, null, "yyyyMMddHHmm");
            logService.insertLog(LogActions.SYS_BACKUP.getAction(), null, request.getRemoteAddr(), this.getUid(request));

            return RestResponseBo.ok(backResponse);
        } catch (Exception e) {
            String msg = "备份失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponseBo.fail(msg);
        }
    }


    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam String filename) {

        if (filename != null) {
            //设置文件路径
            String realPath = TaleUtils.getUploadFilePath()+"backup";
            File file = new File(realPath , filename);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * 数组转字符串
     *
     * @param arr
     * @return
     */
    private String join(String[] arr) {
        StringBuilder ret = new StringBuilder();
        String[] var3 = arr;
        int var4 = arr.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String item = var3[var5];
            ret.append(',').append(item);
        }

        return ret.length() > 0 ? ret.substring(1) : ret.toString();
    }

}
