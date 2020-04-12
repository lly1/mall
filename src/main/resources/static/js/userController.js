var searchUrl;
var pageType;
$(function(){
	//初始化
    searchUrl =basePath+"/sys/user/page";
	initGrid();
    initadd();
});
function refresh(){
    location.reload(true);
}
function initadd(){
        $.ajax({
            url :  basePath+"/sys/role/list",
            cache : false,
            async : false,
            type : "POST",
            success : function (data,textStatus){
                var json= data;
                for(var i=0;i<json.length;i++){
                    $("#form_roleId").append("<option value='"+json[i].roleId+"'>"+json[i].roleName+"</option>");
                    $("#form_roleId").trigger('chosen:updated');
                }
            }
        })

}
function initGrid(){
	$("#grid").jqGrid({
		height: "auto",
		url:basePath+ "/sys/user/page",
		datatype: "json",
		mtype:"POST",
        colModel: [
            {name: 'username', label: '姓名', editable:true,width: 300,frozen:true},
            {name: 'id', label: 'id',hidden:true, width: 40},
            {name: 'phone', label: '手机号码', editable:true,width: 300},
            {name: 'password', label: '密码',hidden:true, editable:true,width: 200},
            {name: 'createTime', label: '创建时间', editable:true,width: 300},
            {name: 'updateTime', label: '更新时间', editable:true,width: 300},
            {name: 'roleName', label: '角色',editable:true, width: 70,
                formatter: function (cellValue, options, rowObject) {
                    var html = "";
                    switch(cellValue) {
                        case "admin":
                            html = '<span class="label label-sm label-success">管理员</span>';
                            break;
                        case "business":
                            html = '<span class="label label-sm label-inverse">商家</span>';
                            break;
                        case "customer":
                            html = '<span class="label label-sm label-warning">用户</span>';
                            break;
                        default:
                            break;
                    }
                    return html;
                }
            },

        ],
        viewrecords: true,
        autowidth:true,
        rownumbers: true,
        altRows: true,
        rowNum: 5,
        rowList: [5, 10, 20],
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
function add(){

    pageType="add";
    $("#editForm").resetForm();
    $("#form_code").attr("readOnly",false);
    $("#form_isAdmin").find("option[value='1']").removeAttr("selected");
	$("#form_isAdmin").find("option[value='0']").attr("selected",true);
    $("#edit-dialog").modal('show');
}
function edit(){
    pageType="edit"
	$("#editForm").resetForm();
    $("#form_code").attr("readOnly",true);
    var rowId = $("#grid").jqGrid("getGridParam", "selrow");
    if(rowId) {
        var row = $("#grid").jqGrid('getRowData',rowId);
		if(row.isAdmin=="是"){
		    row.isAdmin="1";   	
		} else {
			row.isAdmin="0";
		}
        $("#edit-dialog").modal("show");
        $("#editForm").loadData(row);
        
    } else {
        bootbox.alert("请选择一项进行修改！");
    }
}

function closeEditDialog() {
    $("#edit-dialog").modal('hide');
}

function save(){
    $('#editForm').data('bootstrapValidator').validate();
    if(!$('#editForm').data('bootstrapValidator').isValid()){
        return ;
    }
    var progressDialog = bootbox.dialog({
        message: '<p><i class="fa fa-spin fa-spinner"></i> 数据上传中...</p>'
    });
    $.post(basePath+"/sys/user/save?pageType="+pageType,
        $("#editForm").serialize(),
        function(result) {
            if(result.success == true || result.success == 'true') {
                progressDialog.modal('hide');
                $("#edit-dialog").modal('hide');
                $("#grid").trigger("reloadGrid");
            }else{
                bootbox.alert(result.msg);
                progressDialog.modal('hide');
            }
        }, 'json');
}