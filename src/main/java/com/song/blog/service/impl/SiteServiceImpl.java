package com.song.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.song.blog.constant.WebConst;
import com.song.blog.controller.admin.AttachController;
import com.song.blog.dao.AttachVoMapper;
import com.song.blog.dao.CommentVoMapper;
import com.song.blog.dao.ContentVoMapper;
import com.song.blog.dao.MetaVoMapper;
import com.song.blog.dto.MetaDto;
import com.song.blog.dto.Types;
import com.song.blog.model.Bo.ArchiveBo;
import com.song.blog.model.Bo.BackResponseBo;
import com.song.blog.model.Bo.StatisticsBo;
import com.song.blog.model.Vo.*;
import com.song.blog.service.ISiteService;
import com.song.blog.utils.DateKit;
import com.song.blog.utils.TaleUtils;
import com.song.blog.utils.ZipUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BlueT on 2017/3/7.
 */
@Service
public class SiteServiceImpl implements ISiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);

    private static final String BACKUP_PATH = "backup";

    @Resource
    private CommentVoMapper commentDao;

    @Resource
    private ContentVoMapper contentDao;

    @Resource
    private AttachVoMapper attachDao;

    @Resource
    private MetaVoMapper metaDao;

    @Override
    public List<CommentVo> recentComments(int limit) {
        LOGGER.debug("Enter recentComments method:limit={}", limit);
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        CommentVoExample example = new CommentVoExample();
        example.setOrderByClause("created desc");
        PageHelper.startPage(1, limit);
        List<CommentVo> byPage = commentDao.selectByExampleWithBLOBs(example);
        LOGGER.debug("Exit recentComments method");
        return byPage;
    }


    @Override
    public BackResponseBo backup(String bk_type, String bk_path, String fmt) throws Exception {
        BackResponseBo backResponse = new BackResponseBo();
        if (bk_type.equals("attach")) {
            String bkAttachDir = AttachController.CLASSPATH + "upload";
            String fname = DateKit.dateFormat(new Date(), fmt) + "_" + TaleUtils.getRandomNumber(5) + ".zip";
            String attachPath = BACKUP_PATH + "/" + "attachs_" + fname;

            File file = new File(AttachController.CLASSPATH + BACKUP_PATH);
            if(!file.exists()){
                file.mkdirs();
            }

            ZipUtils.zipFolder(bkAttachDir, attachPath);
            backResponse.setAttachPath("attachs_" + fname);
        }
        if (bk_type.equals("db")) {

            String fname = DateKit.dateFormat(new Date(), fmt) + "_" + TaleUtils.getRandomNumber(5) + ".zip";
            String dbPath = BACKUP_PATH + "/" + "db_" + fname;

            File file = new File(AttachController.CLASSPATH + BACKUP_PATH);
            if(!file.exists()){
                file.mkdirs();
            }


            ZipUtils.zipFile(BACKUP_PATH + "/newblog.db",dbPath);

//            ZipUtils.writeToLocal(getClass().getClassLoader()
//                    .getResourceAsStream("newblog.db"),AttachController.CLASSPATH + dbPath);



            backResponse.setAttachPath("db_" + fname);

        }
        return backResponse;
    }

    @Override
    public List<ContentVo> recentContents(int limit) {
        LOGGER.debug("Enter recentContents method");
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        ContentVoExample example = new ContentVoExample();
        example.createCriteria().andStatusEqualTo(Types.PUBLISH.getType()).andTypeEqualTo(Types.ARTICLE.getType());
        example.setOrderByClause("created desc");
        PageHelper.startPage(1, limit);
        List<ContentVo> list = contentDao.selectByExample(example);
        LOGGER.debug("Exit recentContents method");
        return list;
    }



    @Override
    public CommentVo getComment(Integer coid) {
        if (null != coid) {
            return commentDao.selectByPrimaryKey(coid);
        }
        return null;
    }

    @Override
    public StatisticsBo getStatistics() {
        LOGGER.debug("Enter getStatistics method");
        StatisticsBo statistics = new StatisticsBo();

        ContentVoExample contentVoExample = new ContentVoExample();
        contentVoExample.createCriteria().andTypeEqualTo(Types.ARTICLE.getType()).andStatusEqualTo(Types.PUBLISH.getType());
        Long articles =   contentDao.countByExample(contentVoExample);

        Long comments = commentDao.countByExample(new CommentVoExample());

        Long attachs = attachDao.countByExample(new AttachVoExample());

        MetaVoExample metaVoExample = new MetaVoExample();
        metaVoExample.createCriteria().andTypeEqualTo(Types.LINK.getType());
        Long links = metaDao.countByExample(metaVoExample);

        statistics.setArticles(articles);
        statistics.setComments(comments);
        statistics.setAttachs(attachs);
        statistics.setLinks(links);
        LOGGER.debug("Exit getStatistics method");
        return statistics;
    }

    @Override
    public List<ArchiveBo> getArchives() {
        LOGGER.debug("Enter getArchives method");
        List<ArchiveBo> archives = contentDao.findReturnArchiveBo();
        if (null != archives) {
            archives.forEach(archive -> {
                ContentVoExample example = new ContentVoExample();
                ContentVoExample.Criteria criteria = example.createCriteria().andTypeEqualTo(Types.ARTICLE.getType()).andStatusEqualTo(Types.PUBLISH.getType());
                example.setOrderByClause("created desc");
                String date = archive.getDate();
                Date sd = DateKit.dateFormat(date, "yyyy年MM月");
                int start = DateKit.getUnixTimeByDate(sd);
                int end = DateKit.getUnixTimeByDate(DateKit.dateAdd(DateKit.INTERVAL_MONTH, sd, 1)) - 1;
                criteria.andCreatedGreaterThan(start);
                criteria.andCreatedLessThan(end);
                List<ContentVo> contentss = contentDao.selectByExample(example);
                archive.setArticles(contentss);
            });
        }
        LOGGER.debug("Exit getArchives method");
        return archives;
    }

    @Override
    public List<MetaDto> metas(String type, String orderBy, int limit){
        LOGGER.debug("Enter metas method:type={},order={},limit={}", type, orderBy, limit);
        List<MetaDto> retList=null;
        if (StringUtils.isNotBlank(type)) {
            if(StringUtils.isBlank(orderBy)){
                orderBy = "count desc, a.mid desc";
            }
            if(limit < 1 || limit > WebConst.MAX_POSTS){
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderBy);
            paraMap.put("limit", limit);
            retList= metaDao.selectFromSql(paraMap);
        }
        LOGGER.debug("Exit metas method");
        return retList;
    }


    private void write(String data, File file, Charset charset) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data.getBytes(charset));
        } catch (IOException var8) {
            throw new IllegalStateException(var8);
        } finally {
            if(null != os) {
                try {
                    os.close();
                } catch (IOException var2) {
                    var2.printStackTrace();
                }
            }
        }

    }


//    @Override
//    public BackResponseBo backup(String bk_type, String bk_path, String fmt) throws Exception {
//        BackResponseBo backResponse = new BackResponseBo();
//        if (bk_type.equals("attach")) {
//            if (StringUtils.isBlank(bk_path)) {
//                throw new TipException("请输入备份文件存储路径");
//            }
//            if (!(new File(bk_path)).isDirectory()) {
//                throw new TipException("请输入一个存在的目录");
//            }
//            String bkAttachDir = AttachController.CLASSPATH + "upload";
//            String bkThemesDir = AttachController.CLASSPATH + "templates/themes";
//
//            String fname = DateKit.dateFormat(new Date(), fmt) + "_" + TaleUtils.getRandomNumber(5) + ".zip";
//
//            String attachPath = BACKUP_PATH + "/" + "attachs_" + fname;
//            String themesPath = bk_path + "/" + "themes_" + fname;
//
//            ZipUtils.zipFolder(bkAttachDir, attachPath);
//            ZipUtils.zipFolder(bkThemesDir, themesPath);
//
//            backResponse.setAttachPath(attachPath);
//            backResponse.setThemePath(themesPath);
//        }
//        if (bk_type.equals("db")) {
//
//            String bkAttachDir = AttachController.CLASSPATH + "upload/";
//            if (!(new File(bkAttachDir)).isDirectory()) {
//                File file = new File(bkAttachDir);
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//            }
//            String sqlFileName = "tale_" + DateKit.dateFormat(new Date(), fmt) + "_" + TaleUtils.getRandomNumber(5) + ".sql";
//            String zipFile = sqlFileName.replace(".sql", ".zip");
//
//            Backup backup = new Backup(TaleUtils.getNewDataSourceSqlite().getConnection());
//            String sqlContent = backup.execute();
//
//            File sqlFile = new File(bkAttachDir + sqlFileName);
//            write(sqlContent, sqlFile, Charset.forName("UTF-8"));
//
//            String zip = bkAttachDir + zipFile;
//            ZipUtils.zipFile(sqlFile.getPath(), zip);
//
//            if (!sqlFile.exists()) {
//                throw new TipException("数据库备份失败");
//            }
//            sqlFile.delete();
//
//            backResponse.setSqlPath(zipFile);
//
//            // 10秒后删除备份文件
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    new File(zip).delete();
//                }
//            }, 10 * 1000);
//        }
//        return backResponse;
//    }

}
