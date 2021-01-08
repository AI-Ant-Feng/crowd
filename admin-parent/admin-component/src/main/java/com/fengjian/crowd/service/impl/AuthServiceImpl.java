package com.fengjian.crowd.service.impl;

import com.fengjian.crowd.entity.Auth;
import com.fengjian.crowd.entity.AuthExample;
import com.fengjian.crowd.mapper.AuthMapper;
import com.fengjian.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        authMapper.deleteOldRelationship(roleId);
        List<Integer> authIdList = map.get("authIdArray");
        if(authIdList != null && authIdList.size() > 0){
            authMapper.insertRoleAuthRelationship(roleId,authIdList);
        }
    }
}
