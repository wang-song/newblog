package com.song.blog.dao;


import com.song.blog.model.Vo.SecretVo;
import com.song.blog.model.Vo.SecretVoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecretVoMapper {
    long countByExample(SecretVoExample example);

    int deleteByExample(SecretVoExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(SecretVo record);

    int insertSelective(SecretVo record);

    List<SecretVo> selectByExample(SecretVoExample example);

    SecretVo selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") SecretVo record, @Param("example") SecretVoExample example);

    int updateByExample(@Param("record") SecretVo record, @Param("example") SecretVoExample example);

    int updateByPrimaryKeySelective(SecretVo record);

    int updateByPrimaryKey(SecretVo record);
}