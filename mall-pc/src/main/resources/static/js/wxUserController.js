var searchUrl;
$(function(){
	//初始化
    searchUrl =basePath+"/sys/wxUser/page";
	initGrid();
});
function refresh(){
    location.reload(true);
}
function initGrid(){
	$("#grid").jqGrid({
		height: "auto",
		url:basePath+ "/sys/wxUser/page",
		datatype: "json",
		mtype:"POST",
        colModel: [
            {name: 'avatarUrl', label: '头像', editable:true,width: 100,
                formatter: function (cellValue, options, rowObject) {
                    return '<img class="icon-2x" style="width: 50px;height: 50px" src="' + cellValue + '"/>';
                }},
            {name: 'nickName', label: '姓名', editable:true,width: 200,frozen:true},
            {name: 'id', label: 'id',hidden:true, width: 40},
            {name: 'gender', label: '性别', editable:true,width: 100,
                formatter: function (cellValue, options, rowObject) {
                    var html = "";
                    switch(cellValue) {
                        case "1":
                            html = '<span class="label label-sm label-success">男</span>';
                            break;
                        case "2":
                            html = '<span class="label label-sm label-inverse">女</span>';
                            break;
                        default:
                            break;
                    }
                    return html;
                }},
            {name: 'country', label: '国籍', editable:true,width: 100},
            {name: 'province', label: '省份', editable:true,width: 100},
            {name: 'city', label: '城市', editable:true,width: 100},
            {name: 'phone', label: '电话', editable:true,width: 100,
                formatter: function (cellValue, options, rowObject) {
                    var html = "";
                    if (!cellValue) {
                        html = '<span class="label label-sm label-success">用户无电话数据</span>';
                    }else {
                        html = '<span class="label label-sm label-inverse">'+cellValue+'</span>';
                    }
                    return html;
                }},
            {name: 'createTime', label: '创建时间', editable:true,width: 200},
            {name: 'updateTime', label: '更新时间', editable:true,width: 200},
            {name: 'roleId', label: '角色',editable:true, width: 70,
                formatter: function (cellValue, options, rowObject) {
                    var html = "";
                    switch(cellValue) {
                        case "0":
                            html = '<span class="label label-sm label-success">管理员</span>';
                            break;
                        case "1":
                            html = '<span class="label label-sm label-inverse">商家</span>';
                            break;
                        case "2":
                            html = '<span class="label label-sm label-warning">用户</span>';
                            break;
                        default:
                            break;
                    }
                    return html;
                }},

        ],
        viewrecords: true,
        autowidth:true,
        rownumbers: true,
        altRows: true,
        rowNum: 10,
        rowList: [10, 20, 50],
        pager: "#grid-pager",
        multiselect: false,
        shrinkToFit: false,
        sortname : 'createTime',
        sortorder : "desc",
        autoScroll:false

    });
	$("#grid").jqGrid("setFrozenColumns");
    function isAdmin(cellvalues,option,rowObject){
        if(cellvalues==1){
            return "是";
        }else{
            return "否";
        }
    }

}

function _search() {
	
    var serializeArray = $("#searchForm").serializeArray();
    console.log(serializeArray);
    var params = array2obj(serializeArray);
    console.log(params);
    $("#grid").jqGrid('setGridParam', {
    	url:searchUrl,
        page : 1,
        postData : params     
    });  
  $("#grid").trigger("reloadGrid");
}

function _clearSearch(){
    $("#searchForm").resetForm();
	}

function showAdvSearchPanel() {
    $("#searchPanel").slideToggle("fast");

}
