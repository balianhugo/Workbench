package dev.gavin.wb.service.impl;

import dev.gavin.wb.dao.WbDataTypeMapper;
import dev.gavin.wb.dao.ext.WbTypeDataMapper;
import dev.gavin.wb.model.WbDataType;
import dev.gavin.wb.model.WbDataTypeExample;
import dev.gavin.wb.model.constant.ModelConstant;
import dev.gavin.wb.model.ext.WbTypeData;
import dev.gavin.wb.service.WbTypeDataService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台数据类型操作接口实现
 * Created by Administrator on 2017/9/22.
 */

@Service
public class WbTypeDataServiceImpl implements WbTypeDataService {

    @Resource
    private WbDataTypeMapper wbDataTypeMapper;


    @Resource
    private WbTypeDataMapper wbTypeDataMapper;

    @Override
    public boolean checkTypeDataByName(String name) {

        WbDataType data = wbDataTypeMapper.selectByPrimaryKey(name);

        if(null != data){
            return true;
        }
        return false;
    }

    @Override
    public List<WbTypeData> queryTypeDataByName(String name) {

        WbDataType data = wbDataTypeMapper.selectByPrimaryKey(name);

        if(data != null){
            if(data.getType() == ModelConstant.WB_DATA_TYPE_TYPE_STRING){

                List<WbTypeData> list = new ArrayList<>();
                String[] kv = data.getValue().split(",");
                for(String str: kv){
                    String[] temp = str.split(":");
                    WbTypeData wbTypeData = new WbTypeData();
                    wbTypeData.setName(temp[0]);
                    wbTypeData.setValue(temp[1]);
                    list.add(wbTypeData);
                }
                return list;

            }

            if(data.getType() == ModelConstant.WB_DATA_TYPE_TYPE_SQL){

                return wbTypeDataMapper.getTypeData(data.getValue());
            }
        }

        return null;
    }

    public String queryTypeDataOptionByName(String name) {
        String value = "";
        WbDataType data = wbDataTypeMapper.selectByPrimaryKey(name);
        if(data.getType() == ModelConstant.WB_DATA_TYPE_TYPE_OPTION){
            value = data.getValue();
        }
        return value;
    }

    @Override
    public int insertTypeData(WbDataType data) {
        return wbDataTypeMapper.insertSelective(data);
    }

    @Override
    public int updateTypeData(WbDataType data) {
        return wbDataTypeMapper.updateByPrimaryKeySelective(data);
    }

    @Override
    public int deleteTypeData(List<String> names) {

        for(String name: names){
            wbDataTypeMapper.deleteByPrimaryKey(name);
        }

        return names.size();
    }

    @Override
    public List<WbDataType> queryBySearrchParam(String name, Byte type, Integer start, Integer limit) {
        WbDataTypeExample ex = new WbDataTypeExample();
        WbDataTypeExample.Criteria criteria = ex.createCriteria();
        if(null != name && !name.isEmpty()){
            criteria.andNameLike("%" + name + "%");
        }
        if(null != type){
            criteria.andTypeEqualTo(type);
        }
        return wbDataTypeMapper.selectByExampleWithRowbounds(ex, new RowBounds(start, limit));
    }

    @Override
    public long countBySearrchParam(String name, Byte type) {
        WbDataTypeExample ex = new WbDataTypeExample();
        WbDataTypeExample.Criteria criteria = ex.createCriteria();
        if(null != name && !name.isEmpty()){
            criteria.andNameLike("%" + name + "%");
        }
        if(null != type){
            criteria.andTypeEqualTo(type);
        }
        return wbDataTypeMapper.countByExample(ex);
    }
}
