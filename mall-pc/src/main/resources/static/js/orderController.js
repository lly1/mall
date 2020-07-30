var searchUrl;
$(function(){
	//初始化
    searchUrl =basePath+"/sys/order/page";
	initGrid();
});
function refresh(){
    location.reload(true);
}
function initGrid(){
	$("#grid").jqGrid({
		height: "auto",
		url:basePath+ "/sys/order/page",
		datatype: "json",
		mtype:"POST",
        colModel: [
            {name: 'id', label: 'id',hidden:true, width: 40},
            {name: 'shopLogo', label: '店面logo', editable:true,width: 100,
                formatter: function (cellValue, options, rowObject) {
                    return '<img class="icon-2x" style="width: 50px;height: 50px" src="' + basePath+cellValue + '"/>';
                }},
            {name: 'shopName', label: '店铺名称', editable:true,width: 100},
            {name: 'userName', label: '购买用户', editable:true,width: 100},
            {name: 'buyTotal', label: '购买数量', editable:true,width: 100},
            {name: 'payTotal', label: '实付金额', editable:true,width: 100},
            {name: 'orderStatus', label: '订单状态', editable:true,width: 100,
                formatter: function (cellValue, options, rowObject) {
                    var html = "";
                    switch(cellValue) {
                        case 0:
                            html = '<span class="label label-sm label-success">已提交</span>';
                            break;
                        case 1:
                            html = '<span class="label label-sm label-success">已支付</span>';
                            break;
                        case 2:
                            html = '<span class="label label-sm label-success">商家接单</span>';
                            break;
                        case 3:
                            html = '<span class="label label-sm label-success">已完成</span>';
                            break;
                        default:
                            break;
                    }
                    return html;
                }},
            {name: 'remarks', label: '用户备注', editable:true,width: 250},
            {name: 'createTime', label: '创建时间', editable:true,width: 200},
            {name: 'updateTime', label: '更新时间', editable:true,width: 200}
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
