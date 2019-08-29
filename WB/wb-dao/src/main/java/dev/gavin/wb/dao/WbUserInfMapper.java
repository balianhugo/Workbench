package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbUserInf;
import dev.gavin.wb.model.WbUserInfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbUserInfMapper {
    long countByExample(WbUserInfExample example);

    int deleteByExample(WbUserInfExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(WbUserInf record);

    int insertSelective(WbUserInf record);

    List<WbUserInf> selectByExampleWithRowbounds(WbUserInfExample example, RowBounds rowBounds);

    List<WbUserInf> selectByExample(WbUserInfExample example);

    WbUserInf selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") WbUserInf record, @Param("example") WbUserInfExample example);

    int updateByExample(@Param("record") WbUserInf record, @Param("example") WbUserInfExample example);

    int updateByPrimaryKeySelective(WbUserInf record);

    int updateByPrimaryKey(WbUserInf record);
}