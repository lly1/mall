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
            url :  basePath+"/sys/user/list",
            cache : false,
            async : false,
            type : "POST",
            success : function (data,textStatus){
                var json= data;
                for(var i=0;i<json.length;i++){
                    $("#form_roleId").append("<option value='"+json[i].code+"'>"+json[i].name+"</option>");
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
            // {name: 'src', label: '来源',editable:true, width: 100,
            //     formatter: function (cellValue, options, rowObject) {
            //         var html = "";
            //         switch(cellValue) {
            //             case "01":
            //                 html = '<span class="label label-sm label-success">系统</span>';
            //                 break;
            //             case "02":
            //                 html = '<span class="label label-sm label-inverse">同步</span>';
            //                 break;
            //             case "03":
            //                 html = '<span class="label label-sm label-warning">导入</span>';
            //                 break;
            //             default:
            //                 html = '<span class="label label-sm label-inverse">系统</span>';
            //         }
            //         return html;
            //     }},
            {name: 'username', label: '姓名', editable:true,width: 200,frozen:true},
            {name: 'id', label: 'id',hidden:true, width: 40},
            {name: 'phone', label: '手机号码', editable:true,width: 200},
            {name: 'password', label: '密码',hidden:true, editable:true,width: 200},
            {name: 'createTime', label: '创建时间', editable:true,width: 200},
            {name: 'roleName', label: '角色',editable:true, width: 200},

        ],
        viewrecords: true,
        autowidth: true,
        rownumbers: true,
        altRows: true,
        rowNum: 20,
        rowList: [20, 50, 100],
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
   /* if ($("#form_ownerId").val()==""||$("#form_unitName").val()==""){
        bootbox.alert("所属方不能为空");
        return;
    }*/
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