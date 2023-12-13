$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/inquiry/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'inquiryId', index: 'inquiryId', width: 50, key: true, hidden: true},
            {label: '商品名称', name: 'goodsName', index: 'goodsName', width: 120},
            {label: '客户姓名', name: 'yourName', index: 'yourName', width: 100},
            {label: 'E-mail', name: 'email', index: 'email', width: 120},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 100},
            {label: '详情', name: 'operate', index: 'operate', width: 120, formatter: operateFormatter}
        ],
        height: 680,
        rowNum: 20,
        rowList: [20, 30, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function operateFormatter(cellvalue, rowObject) {
        return "<a href=\'##\' onclick=openDetailsContent(" + rowObject.rowId + ")>查看详细信息</a>";
    }
});

function reload() {
    const page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

/**
 * 查看详细信息
 * @param inquiryId
 */
function openDetailsContent(inquiryId) {
    $('.modal-title').html('详情');
    $.ajax({
        type: 'GET',
        url: '/admin/inquiry/detail/' + inquiryId,
        contentType: 'application/json',
        success: function (result) {
            if (result.code === 200) {
                $('#inquiryDetailModal').modal('show');
                let itemString = '';
                itemString += "产品名称：" + result.data.goodsName + "<br>" +
                            "客户姓名：" + result.data.yourName + "<br>" +
                            "公司名称：" + result.data.companyName + "<br>" +
                            "客户地址：" + result.data.address  + "<br>" +
                            "客户电话：" + result.data.phone  + "<br>" +
                            "客户传真：" + result.data.fax  + "<br>" +
                            "客户邮箱：" + result.data.email  + "<br>" +
                            "附加信息：<br>" + result.data.message;
                $("#inquiryDetailInfo").html(itemString);
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
        },
        error: function () {
            swal("查看失败", {
                icon: "error",
            });
        }
    });
}

function deleteInquiry() {
    const inquiryId = getSelectedRows();
    if (inquiryId == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除记录吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "PUT",
                    url: "/admin/inquiry/delete",
                    contentType: "application/json",
                    data: JSON.stringify(inquiryId),
                    success: function (r) {
                        if (r.code === 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}

function reset() {
    $("#totalPrice").val(0);
    $("#userAddress").val('');
    $('#edit-error-msg').css("display", "none");
}