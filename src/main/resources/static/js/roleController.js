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
    $("#roleGrid").jqGrid({
        height: "auto",
        url: basePath + "/sys/role/page",
        datatype: "json",
        mtype:"POST",
        colModel: [

            {name: 'id', label: 'id',hidden:true, width: 40},
            {name: 'roleId', label: '角色编号',editable:true, width: 200},
            {name: 'roleName', label: '角色名称', editable:true,width: 200},
            // {name: 'authIds', label: '权限ID', hidden:true,width: 40},
            // {name: 'authNames', label: '拥有权限', sortable:false,editable:true,width: 40,hidden:true},
            {name: 'createTime', label: '创建时间',editable:true, width: 200}
            // {name: 'remark', label: '备注', width: 40, sortable: false}

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

function add() {
    location.href = basePath+"/sys/role/addPage.do";
}
function edit() {
    var rowId = $("#roleGrid").jqGrid("getGridParam", "selrow");
    if(rowId) {
        var row = $("#roleGrid").jqGrid('getRowData',rowId);
        location.href = basePath+"/sys/role/editPage.do?roleId="+row.code;
    } else {
        bootbox.alert("请选择一项进行修改！");
    }
}
function initAdd() {
    $.ajax({
        url:basePath+"/sys/role/searchByOwnerId.do?ownerId=01",
        cache:false,
        async:false,
        inheritClass:true,
        type:"POST",
        success:function (data,textStatus) {
            var json=data;
            for (var i = 0; i < json.length; i++) {
                $("#ownerId").append("<option value='"+json[i].code+"'>"+json[i].name+"</option>");
                $("#ownerId").trigger('chosen:updated');
            }
        }
    })
}





