package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbUserOrgRelExample;
import dev.gavin.wb.model.WbUserOrgRelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbUserOrgRelMapper {
    long countByExample(WbUserOrgRelExample example);

    int deleteByExample(WbUserOrgRelExample example);

    int deleteByPrimaryKey(WbUserOrgRelKey key);

    int insert(WbUserOrgRelKey record);

    int insertSelective(WbUserOrgRelKey record);

    List<WbUserOrgRelKey> selectByExampleWithRowbounds(WbUserOrgRelExample example, RowBounds rowBounds);

    List<WbUserOrgRelKey> selectByExample(WbUserOrgRelExample example);

    int updateByExampleSelective(@Param("record") WbUserOrgRelKey record, @Param("example") WbUserOrgRelExample example);

    int updateByExample(@Param("record") WbUserOrgRelKey record, @Param("example") WbUserOrgRelExample example);
}