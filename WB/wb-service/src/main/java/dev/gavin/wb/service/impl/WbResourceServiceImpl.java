package dev.gavin.wb.service.impl;

import dev.gavin.wb.dao.WbMenuResourceMapper;
import dev.gavin.wb.model.WbMenuResource;
import dev.gavin.wb.model.WbMenuResourceExample;
import dev.gavin.wb.service.WbResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单对应资源管理接口实现类
 * Created by Administrator on 2017-1-3.
 */
@Service
public class WbResourceServiceImpl implements WbResourceService {

    @Resource
    private WbMenuResourceMapper wbMenuResourceMapper;

    @Override
    public List<WbMenuResource> queryResourceListByMenuId(Integer menuId) {
        WbMenuResourceExample ex = new WbMenuResourceExample();
        ex.createCriteria().andMenuIdEqualTo(menuId);
        return wbMenuResourceMapper.selectByExample(ex);
    }

    @Override
    public int insert(WbMenuResource record) {
        return wbMenuResourceMapper.insert(record);
    }

    @Override
    public int update(WbMenuResource record) {
        return wbMenuResourceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(List<Integer> ids) {
        WbMenuResourceExample ex = new WbMenuResourceExample();
        ex.createCriteria().andResourceIdIn(ids);
        return wbMenuResourceMapper.deleteByExample(ex);
    }
}
