package com.song.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.song.blog.dao.SecretVoMapper;
import com.song.blog.model.Vo.SecretVo;
import com.song.blog.model.Vo.SecretVoExample;
import com.song.blog.service.ISecretService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SecretServiceImpl implements ISecretService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretServiceImpl.class);

    @Resource
    private SecretVoMapper secretDao;


    @Override
    public PageInfo<SecretVo> findSecretLimit(SecretVoExample secretVoExample, Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<SecretVo> secretVos = secretDao.selectByExample(secretVoExample);
        return new PageInfo<>(secretVos);
    }
}
