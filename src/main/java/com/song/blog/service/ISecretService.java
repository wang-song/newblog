package com.song.blog.service;

import com.github.pagehelper.PageInfo;
import com.song.blog.model.Vo.SecretVo;
import com.song.blog.model.Vo.SecretVoExample;

public interface ISecretService {
    /**
     * 分页查询
     * @param secretVoExample
     * @param page
     * @param limit
     * @return
     */
    PageInfo<SecretVo> findSecretLimit(SecretVoExample  secretVoExample,Integer page,Integer limit);
}
