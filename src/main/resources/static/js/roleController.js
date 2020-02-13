var pageType;
$(function () {
    initGrid();
    // initAdd();
});

function refresh(){
    location.reload(true);
}

function showAdvSearchPanel() {
    $("#searchPanel").slideToggle("fast");
}
function initGrid() {
    $("#grid").jqGrid({
        height: "auto",
        url: basePath + "/sys/role/page",
        datatype: "json",
        mtype:"POST",
        colModel: [
            {name: 'id', label: 'id',hidden:true, width: 40},
            {name: 'roleId', label: '角色编号',editable:true, width: 200},
            {name: 'roleName', label: '角色名称', editable:true,width: 200},
            {name: 'authNames', label: '拥有权限', sortable:false,editable:true,width: 400},
            {name: 'createTime', label: '创建时间',editable:true, width: 200}
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

}

function _search() {
    var serializeArray = $("#searchForm").serializeArray();
    var params = array2obj(serializeArray);
    $("#roleGrid").jqGrid('setGridParam', {
        page : 1,
        postData : params
    });
    $("#roleGrid").trigger("reloadGrid");
}


function refreshRole() {
    _search();
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
    pageType="edit";
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
    $.post(basePath+"/sys/role/save?pageType="+pageType,
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




