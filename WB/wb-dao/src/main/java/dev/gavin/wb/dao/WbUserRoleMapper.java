package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbUserRole;
import dev.gavin.wb.model.WbUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbUserRoleMapper {
    long countByExample(WbUserRoleExample example);

    int deleteByExample(WbUserRoleExample example);

    int deleteByPrimaryKey(Integer roleId);

    int insert(WbUserRole record);

    int insertSelective(WbUserRole record);

    List<WbUserRole> selectByExampleWithRowbounds(WbUserRoleExample example, RowBounds rowBounds);

    List<WbUserRole> selectByExample(WbUserRoleExample example);

    WbUserRole selectByPrimaryKey(Integer roleId);

    int updateByExampleSelective(@Param("record") WbUserRole record, @Param("example") WbUserRoleExample example);

    int updateByExample(@Param("record") WbUserRole record, @Param("example") WbUserRoleExample example);

    int updateByPrimaryKeySelective(WbUserRole record);

    int updateByPrimaryKey(WbUserRole record);
}