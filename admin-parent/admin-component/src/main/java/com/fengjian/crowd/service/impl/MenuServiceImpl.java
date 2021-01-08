package com.fengjian.crowd.service.impl;

import com.fengjian.crowd.entity.Menu;
import com.fengjian.crowd.entity.MenuExample;
import com.fengjian.crowd.mapper.MenuMapper;
import com.fengjian.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    public MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        //由于pid没有传入，一定要使用有选择的更新，保证pid子段不能被置空
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
