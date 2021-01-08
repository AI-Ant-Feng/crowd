package com.fengjian.crowd.mvc.handler;

import com.fengjian.crowd.entity.Role;
import com.fengjian.crowd.service.api.RoleService;
import com.fengjian.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ){
        PageInfo<Role> pageInfo;
        try {
            pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
            return ResultEntity.successWithData(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/role/save.json")
    public ResultEntity<String> saveRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping(value = "/role/update.json")
    public ResultEntity<String> updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping(value = "/role/remove/by/role/id/array.json")
    public ResultEntity<String> removeRoleByRoleArray(@RequestBody List<Integer> roleIdList){
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithoutData();
    }



}
