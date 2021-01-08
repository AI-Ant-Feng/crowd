package com.fengjian.crowd.service.api;

import com.fengjian.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {

    public void saveAdmin(Admin admin);

    public List<Admin> getAll();

    public Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    public void deleteAdminById(Integer id);

    public Admin getAdminById(Integer id);

    public void update(Admin admin);

    void saveAdminRelationship(Integer adminId, List<Integer> roleIdList);
}
