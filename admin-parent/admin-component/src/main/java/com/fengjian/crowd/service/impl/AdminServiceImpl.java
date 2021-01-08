package com.fengjian.crowd.service.impl;

import com.fengjian.crowd.constant.Constant;
import com.fengjian.crowd.entity.Admin;
import com.fengjian.crowd.entity.AdminExample;
import com.fengjian.crowd.exception.LoginAcctAlreadyInUseException;
import com.fengjian.crowd.exception.LoginAcctAlreadyInUseExceptionForUpdate;
import com.fengjian.crowd.exception.LoginFailedException;
import com.fengjian.crowd.mapper.AdminMapper;
import com.fengjian.crowd.service.api.AdminService;
import com.fengjian.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public void saveAdmin(Admin admin) {
        String userPswd = admin.getUserPswd();
        userPswd = CrowdUtil.md5(userPswd);
        admin.setUserPswd(userPswd);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);
        try {
            adminMapper.insert(admin);
        }catch (Exception e){
            logger.info("异常全类名="+e.getClass().getName());
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(Constant.MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(null);
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria= adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList == null || adminList.size() ==0) {
            throw new LoginFailedException(Constant.MESSAGE_LOGIN_FAILED);
        }
        if (adminList.size() > 1){
            throw new RuntimeException(Constant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = adminList.get(0);
        if (admin == null) {
            throw new LoginFailedException(Constant.MESSAGE_LOGIN_FAILED);
        }
        String userPswdDB = admin.getUserPswd();
        String userPswdFrom = CrowdUtil.md5(userPswd);
        if(!Objects.equals(userPswdDB, userPswdFrom)){
            throw new LoginFailedException(Constant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);
        return new PageInfo<>(adminList);
    }

    @Override
    public void deleteAdminById(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e){
            logger.info("异常全类名="+e.getClass().getName());
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseExceptionForUpdate(Constant.MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRelationship(Integer adminId, List<Integer> roleIdList) {
        adminMapper.deleteRelationship(adminId);
        if(roleIdList != null && roleIdList.size() > 0){
            adminMapper.insertNewRelationship(adminId,roleIdList);
        }
    }
}
