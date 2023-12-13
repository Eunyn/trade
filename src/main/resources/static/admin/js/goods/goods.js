$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/goods/list',
        datatype: "json",
        colModel: [
            {label: '商品编号', name: 'goodsId', index: 'goodsId', width: 20, key: true, hidden: true},
            {label: '类别', name: 'categoryName', index: 'categoryName', width: 80},
            {label: '商品名', name: 'goodsName', index: 'goodsName', width: 120},
            {label: '商品简介', name: 'goodsInfo', index: 'goodsIntro', width: 120},
            {label: '商品图片', name: 'goodsCoverImg', index: 'goodsCoverImg', width: 100, formatter: coverImageFormatter},
            {label: '商品尺寸', name: 'goodsSize', index: 'goodSize', width: 60},
            {label: '售价', name: 'goodsPrice', index: 'goodsPrice', width: 60},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 100}
        ],
        height: 760,
        rowNum: 20,
        rowList: [10, 20, 30],
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

    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"80\" width=\"80\" alt='商品主图'/>";
    }

});

function reload() {
    const page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

/**
 * 添加商品
 */
function addGoods() {
    window.location.href = "/admin/goods/edit";
}

/**
 * 修改商品
 */
function editGoods() {
    const id = getSelectedRow();
    if (id == null) {
        return;
    }
    window.location.href = "/admin/goods/edit/" + id;
}


/**
 * 删除商品
 */
function putDownGoods() {
    const ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要执行删除操作吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "PUT",
                    url: "/admin/goods/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
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