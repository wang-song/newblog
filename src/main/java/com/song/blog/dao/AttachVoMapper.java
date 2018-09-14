package com.song.blog.dao;


import com.song.blog.model.Vo.AttachVo;
import com.song.blog.model.Vo.AttachVoExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangsong
 */
@Component
public interface AttachVoMapper {
    /**
     * 查询附件的数量
     * @param example
     * @return
     */
    long countByExample(AttachVoExample example);

    /**
     * 删除附件
     * @param example
     * @return
     */
    int deleteByExample(AttachVoExample example);

    /**
     * 删除附件用附件 ID
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入附件
     * @param record
     * @return
     */
    int insert(AttachVo record);

    /**
     * 插入附件
     * @param record
     * @return
     */
    int insertSelective(AttachVo record);

    /**
     * 查询附件
     * @param example
     * @return
     */
    List<AttachVo> selectByExample(AttachVoExample example);

    /**
     * 查询附件
     * @param id
     * @return
     */
    AttachVo selectByPrimaryKey(Integer id);

    /**
     * 更新附件
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") AttachVo record, @Param("example") AttachVoExample example);

    /**
     * 更新附件
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") AttachVo record, @Param("example") AttachVoExample example);

    /**
     * 更新附件
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AttachVo record);

    /**
     * 更新附件
     * @param record
     * @return
     */
    int updateByPrimaryKey(AttachVo record);
}