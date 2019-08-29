package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbRoleMenu;
import dev.gavin.wb.model.WbRoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbRoleMenuMapper {
    long countByExample(WbRoleMenuExample example);

    int deleteByExample(WbRoleMenuExample example);

    int deleteByPrimaryKey(Integer menuId);

    int insert(WbRoleMenu record);

    int insertSelective(WbRoleMenu record);

    List<WbRoleMenu> selectByExampleWithRowbounds(WbRoleMenuExample example, RowBounds rowBounds);

    List<WbRoleMenu> selectByExample(WbRoleMenuExample example);

    WbRoleMenu selectByPrimaryKey(Integer menuId);

    int updateByExampleSelective(@Param("record") WbRoleMenu record, @Param("example") WbRoleMenuExample example);

    int updateByExample(@Param("record") WbRoleMenu record, @Param("example") WbRoleMenuExample example);

    int updateByPrimaryKeySelective(WbRoleMenu record);

    int updateByPrimaryKey(WbRoleMenu record);
}