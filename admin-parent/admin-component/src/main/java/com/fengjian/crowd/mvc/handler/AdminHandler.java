package com.fengjian.crowd.mvc.handler;

import com.fengjian.crowd.constant.Constant;
import com.fengjian.crowd.entity.Admin;
import com.fengjian.crowd.service.api.AdminService;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/admin/update.html")
    public String editAdmin(Admin admin,
                            @RequestParam("pageNum") Integer pageNum,
                            @RequestParam("keyword") String keyword,
                            ModelMap modelMap){
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping(value = "/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer id,
                            ModelMap modelMap){
        Admin admin = adminService.getAdminById(id);
        modelMap.addAttribute("admin", admin);
        return "admin-edit";
    }
    @RequestMapping(value = "/admin/save.html")
    public String saveAdmin(Admin admin){
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping(value = "/admin/remove/{id}/{pageNum}/{keyword}.html")
    public String removeAdmin(@PathVariable("id") Integer id,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("keyword") String keyword){
        adminService.deleteAdminById(id);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }


    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session){
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);
        session.setAttribute(Constant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
    @RequestMapping("admin/get/page.html")
    public String getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelmap){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelmap.addAttribute(Constant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-user-manage";
    }

}
