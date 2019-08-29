package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbMenuResource;
import dev.gavin.wb.model.WbMenuResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbMenuResourceMapper {
    long countByExample(WbMenuResourceExample example);

    int deleteByExample(WbMenuResourceExample example);

    int deleteByPrimaryKey(Integer resourceId);

    int insert(WbMenuResource record);

    int insertSelective(WbMenuResource record);

    List<WbMenuResource> selectByExampleWithRowbounds(WbMenuResourceExample example, RowBounds rowBounds);

    List<WbMenuResource> selectByExample(WbMenuResourceExample example);

    WbMenuResource selectByPrimaryKey(Integer resourceId);

    int updateByExampleSelective(@Param("record") WbMenuResource record, @Param("example") WbMenuResourceExample example);

    int updateByExample(@Param("record") WbMenuResource record, @Param("example") WbMenuResourceExample example);

    int updateByPrimaryKeySelective(WbMenuResource record);

    int updateByPrimaryKey(WbMenuResource record);
}