package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbUserRoleRelExample;
import dev.gavin.wb.model.WbUserRoleRelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbUserRoleRelMapper {
    long countByExample(WbUserRoleRelExample example);

    int deleteByExample(WbUserRoleRelExample example);

    int deleteByPrimaryKey(WbUserRoleRelKey key);

    int insert(WbUserRoleRelKey record);

    int insertSelective(WbUserRoleRelKey record);

    List<WbUserRoleRelKey> selectByExampleWithRowbounds(WbUserRoleRelExample example, RowBounds rowBounds);

    List<WbUserRoleRelKey> selectByExample(WbUserRoleRelExample example);

    int updateByExampleSelective(@Param("record") WbUserRoleRelKey record, @Param("example") WbUserRoleRelExample example);

    int updateByExample(@Param("record") WbUserRoleRelKey record, @Param("example") WbUserRoleRelExample example);
}