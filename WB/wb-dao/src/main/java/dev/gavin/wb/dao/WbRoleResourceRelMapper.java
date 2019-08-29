package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbRoleResourceRelExample;
import dev.gavin.wb.model.WbRoleResourceRelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbRoleResourceRelMapper {
    long countByExample(WbRoleResourceRelExample example);

    int deleteByExample(WbRoleResourceRelExample example);

    int deleteByPrimaryKey(WbRoleResourceRelKey key);

    int insert(WbRoleResourceRelKey record);

    int insertSelective(WbRoleResourceRelKey record);

    List<WbRoleResourceRelKey> selectByExampleWithRowbounds(WbRoleResourceRelExample example, RowBounds rowBounds);

    List<WbRoleResourceRelKey> selectByExample(WbRoleResourceRelExample example);

    int updateByExampleSelective(@Param("record") WbRoleResourceRelKey record, @Param("example") WbRoleResourceRelExample example);

    int updateByExample(@Param("record") WbRoleResourceRelKey record, @Param("example") WbRoleResourceRelExample example);
}