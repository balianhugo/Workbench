package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbRoleMenuRelExample;
import dev.gavin.wb.model.WbRoleMenuRelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbRoleMenuRelMapper {
    long countByExample(WbRoleMenuRelExample example);

    int deleteByExample(WbRoleMenuRelExample example);

    int deleteByPrimaryKey(WbRoleMenuRelKey key);

    int insert(WbRoleMenuRelKey record);

    int insertSelective(WbRoleMenuRelKey record);

    List<WbRoleMenuRelKey> selectByExampleWithRowbounds(WbRoleMenuRelExample example, RowBounds rowBounds);

    List<WbRoleMenuRelKey> selectByExample(WbRoleMenuRelExample example);

    int updateByExampleSelective(@Param("record") WbRoleMenuRelKey record, @Param("example") WbRoleMenuRelExample example);

    int updateByExample(@Param("record") WbRoleMenuRelKey record, @Param("example") WbRoleMenuRelExample example);
}