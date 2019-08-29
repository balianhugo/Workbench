package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbDataType;
import dev.gavin.wb.model.WbDataTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbDataTypeMapper {
    long countByExample(WbDataTypeExample example);

    int deleteByExample(WbDataTypeExample example);

    int deleteByPrimaryKey(String name);

    int insert(WbDataType record);

    int insertSelective(WbDataType record);

    List<WbDataType> selectByExampleWithRowbounds(WbDataTypeExample example, RowBounds rowBounds);

    List<WbDataType> selectByExample(WbDataTypeExample example);

    WbDataType selectByPrimaryKey(String name);

    int updateByExampleSelective(@Param("record") WbDataType record, @Param("example") WbDataTypeExample example);

    int updateByExample(@Param("record") WbDataType record, @Param("example") WbDataTypeExample example);

    int updateByPrimaryKeySelective(WbDataType record);

    int updateByPrimaryKey(WbDataType record);
}