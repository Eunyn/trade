$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/categories/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'categoryId', index: 'categoryId', width: 50, key: true, hidden: true},
            {label: '分类名称', name: 'categoryName', index: 'categoryName', width: 120},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 120}
        ],
        height: 500,
        rowNum: 10,
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
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
});


function reload() {
    const page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function categoryAdd() {
    reset();
    $('.modal-title').html('分类添加');
    $('#categoryModal').modal('show');
}

$('#submitCategory').click(function () {
    let formData = {
        categoryName: document.getElementById('categoryName').value
    };
    let url = '/admin/insertCategory';
    if ($('.modal-title').html() === '分类修改') {
        // console.log('分类修改')
        url = '/admin/updateCategory';

        formData = {
            categoryId: getSelectedRow(),
            categoryName: document.getElementById('categoryName').value
        };
    }
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(formData)
    })
        .then(response => response.json())
        .then(data => {
            // Handle the response
            if (data.code === 200) {
                $('#categoryModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            } else {
                $('#categoryModal').modal('hide');
                swal(data.message, {
                    icon: "error",
                });
            }
            console.log(data);
        })
        .catch(error => console.error('Error:', error));
});

// 绑定modal上的保存按钮
$('#saveButton').click(function () {
    const categoryName = $("#categoryName").val();
    if (!validCN_ENString2_18(categoryName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的分类名称！");
    } else {
        let data = {
            "categoryName": categoryName
        };
        let url = '/admin/updateCategory';
        const id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/admin/updateCategory';
            data = {
                "categoryId": id,
                "categoryName": categoryName,
            };
        }
        $.ajax({
            type: 'POST',
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.code === 200) {
                    $('#categoryModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $('#categoryModal').modal('hide');
                    swal(result.message, {
                        icon: "error",
                    });
                }
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    }
});

function categoryEdit() {
    reset();
    const id = getSelectedRow();
    if (id == null) {
        return;
    }
    const rowData = $("#jqGrid").jqGrid("getRowData", id);
    $('.modal-title').html('分类修改');
    $('#categoryModal').modal('show');
    $("#categoryId").val(id);
    $("#categoryName").val(rowData.categoryName);
}

function deleteCategory() {
    const ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除吗？\n此操作会删除该分类下的所有产品。",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "DELETE",
                    url: "/admin/deleteCategory",
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

function reset() {
    $("#categoryName").val('');
    $('#edit-error-msg').css("display", "none");
}