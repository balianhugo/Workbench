package dev.gavin.wb.dao;

import dev.gavin.wb.model.WbOrganizationInf;
import dev.gavin.wb.model.WbOrganizationInfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WbOrganizationInfMapper {
    long countByExample(WbOrganizationInfExample example);

    int deleteByExample(WbOrganizationInfExample example);

    int deleteByPrimaryKey(Integer orgId);

    int insert(WbOrganizationInf record);

    int insertSelective(WbOrganizationInf record);

    List<WbOrganizationInf> selectByExampleWithRowbounds(WbOrganizationInfExample example, RowBounds rowBounds);

    List<WbOrganizationInf> selectByExample(WbOrganizationInfExample example);

    WbOrganizationInf selectByPrimaryKey(Integer orgId);

    int updateByExampleSelective(@Param("record") WbOrganizationInf record, @Param("example") WbOrganizationInfExample example);

    int updateByExample(@Param("record") WbOrganizationInf record, @Param("example") WbOrganizationInfExample example);

    int updateByPrimaryKeySelective(WbOrganizationInf record);

    int updateByPrimaryKey(WbOrganizationInf record);
}