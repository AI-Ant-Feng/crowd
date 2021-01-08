package com.fengjian.crowd.mvc.handler;

import com.fengjian.crowd.entity.Auth;
import com.fengjian.crowd.entity.Role;
import com.fengjian.crowd.service.api.AdminService;
import com.fengjian.crowd.service.api.AuthService;
import com.fengjian.crowd.service.api.RoleService;
import com.fengjian.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship(
            @RequestBody Map<String,List<Integer>> map){
        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping(value = "/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId){
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping(value = "/assign/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
    }

    @RequestMapping(value = "/assign/do/role/assign.html")
    public String saveAdminRelationship(
            @RequestParam("adminId")Integer adminId,
            @RequestParam("pageNum")Integer pageNum,
            @RequestParam("keyword")String keyword,
            @RequestParam(value = "roleIdList", required=false)List<Integer> roleIdList){
        adminService.saveAdminRelationship(adminId,roleIdList);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
    @RequestMapping(value = "/assign/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId")Integer adminId,
            ModelMap modelMap){
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
        modelMap.addAttribute("assignedRoleList",assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList",unAssignedRoleList);
        return "assign_role";
    }
}
