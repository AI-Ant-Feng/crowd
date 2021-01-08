function fillAuthTree() {
    var ajaxReturn = $.ajax({
        "url":"assign/get/all/auth.json",
        "type":"post",
        "dataType":"json",
        "async":false
    });
    if(ajaxReturn.status != 200){
        layer.msg("请求处理出错！响应状态码是："+ajaxReturn.status+"说明是："+ajaxReturn.statusText);
        return;
    }
    var authList = ajaxReturn.responseJSON.data;
    var setting = {
        "data":{
            "simpleData":{
                "enable":true,
                "pIdKey":"categoryId"
            },
            "key":{
                "name":"title"
            }
        },
        "check":{
            "enable":true
        }
    };
    $.fn.zTree.init($("#authTreeDemo"),setting,authList);
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    zTreeObj.expandAll(true);

    ajaxReturn = $.ajax({
        "url":"assign/get/assigned/auth/id/by/role/id.json",
        "type":"post",
        "data":{
            "roleId":window.roleId
        },
        "dataType":"json",
        "async":false
    });

    if(ajaxReturn.status != 200){
        layer.msg("请求处理出错！响应状态码是："+ajaxReturn.status+"说明是："+ajaxReturn.statusText);
        return;
    }
    var authIdArray = ajaxReturn.responseJSON.data;
    for(var i = 0; i < authIdArray.length; i++){
        var authId = authIdArray[i];
        var treeNode = zTreeObj.getNodeByParam("id",authId);
        var checked = true;
        var checkTypeFlag = false;
        zTreeObj.checkNode(treeNode,checked,checkTypeFlag);
    }

}

function showConfirmModal(roleArray) {
    $("#confirmModal").modal("show");

    $("#roleNameDiv").empty();

    window.roleIdArray = [];
    for(var i = 0; i< roleArray.length; i++){
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName + "<br/>");
        var roleId = role.roleId;
        window.roleIdArray.push(roleId);
    }
}

//执行分页，生成页面效果
function generatePage() {
    var pageInfo = getPageInfoRemote();
    //2.填充表格
    fillTableBody(pageInfo);
}

function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        "url":"/role/get/page/info.json",
        "type":"post",
        "data":{
            "pageNum":window.pageNum,
            "pageSize":window.pageSize,
            "keyword":window.keyword
        },
        "async":false,
        "dataType":"json"
    });

    var statusCode = ajaxResult.status;
    if(statusCode != 200){
        layer.msg("失败！响应状态码为："+statusCode+" 说明信息="+ajaxResult.statusText)
        return null;
    }

    var resultEntity = ajaxResult.responseJSON;

    var result = resultEntity.result;

    if(result == "FAILED"){
        layer.msg(resultEntity.message);
        return null;
    }

    var pageInfo = resultEntity.data;

    return pageInfo;
}

function fillTableBody(pageInfo) {
    $("#rolePageBody").empty();
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0){
        $("#rolePageBody").append("<tr><td colspan='4'>抱歉！没有查询到您搜索的数据！</td></tr>");
        return;
    }
    for(var i = 0; i < pageInfo.list.length; i++){
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>"+(i+1)+"</td>";
        var checkboxTd = "<td><input id='"+roleId+"' class='itemCheckbox' type='checkbox'></td>";
        var roleNameTd = "<td>"+roleName+"</td>";
        var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class='glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class='glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>"
        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);
}

function generateNavigator(pageInfo) {
    var totalRecord = pageInfo.total;
    var properties = {
        num_edge_entries: 3,
        num_display_entries: 5,
        callback: paginationCallBack,
        items_per_page: pageInfo.pageSize,
        current_page: pageInfo.pageNum-1,
        prev_text: "上一页",
        next_text: "下一页"
    }
    $("#Pagination").pagination(totalRecord, properties);
}

function paginationCallBack(pageIndex,jQuery) {
    window.pageNum = pageIndex + 1 ;
    generatePage();
    return false;
}