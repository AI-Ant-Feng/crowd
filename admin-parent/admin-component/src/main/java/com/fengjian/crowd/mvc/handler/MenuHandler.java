package com.fengjian.crowd.mvc.handler;

import com.fengjian.crowd.entity.Menu;
import com.fengjian.crowd.service.api.MenuService;
import com.fengjian.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class MenuHandler {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/menu/remove.json")
    public ResultEntity removeMenu(@RequestParam("id") Integer id){
        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping(value = "/menu/update.json")
    public ResultEntity updateMenu(Menu menu){
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping(value = "/menu/save.json")
    public ResultEntity saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping(value = "/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTree(){
        List<Menu> menuList = menuService.getAll();
        Menu root = null;
        Map<Integer,Menu> menuMap = new HashMap<>();
        for(Menu menu:menuList){
            Integer id = menu.getId();
            menuMap.put(id,menu);
        }
        for(Menu menu:menuList){
            Integer pid = menu.getPid();
            if(pid == null){
                root = menu;
                continue;
            }
            Menu father = menuMap.get(pid);
            father.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }

    public ResultEntity<Menu> getWholeTreeOld(){
        List<Menu> menuList = menuService.getAll();
        Menu root = null;
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            if (pid == null) {
                root = menu;
                continue;
            }
            for (Menu maybeFather : menuList) {
                Integer id = maybeFather.getId();
                if (Objects.equals(pid,id)) {
                    maybeFather.getChildren().add(menu);
                    break;
                }
            }
        }
        return ResultEntity.successWithData(root);
    }
}
