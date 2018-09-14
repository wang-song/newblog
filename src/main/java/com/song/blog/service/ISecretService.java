package com.song.blog.service;

import com.github.pagehelper.PageInfo;
import com.song.blog.model.Vo.SecretVo;
import com.song.blog.model.Vo.SecretVoExample;

public interface ISecretService {
    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    PageInfo<SecretVo> findSecretLimit(SecretVoExample secretVoExample,Integer page, Integer limit);

    void saveSecret(SecretVo secretVo);


    /**
     * 根据卡密的 ID 删除卡密
     * @param sid
     * @return
     */
    String delSecretByID(Integer sid);


    /**
     * 根据卡密的 ID 获取卡密
     * @param sid
     * @return
     */
    SecretVo getSecretByID(Integer sid);

}
