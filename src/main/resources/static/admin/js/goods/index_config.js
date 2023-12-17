// 首页显示设置

function configSetting() {

}

function configEdit() {
    const id = getSelectedRow();
    if (id == null) {
        swal("请选择一条记录", {
            icon:"error",
        })
        return;
    }

    window.location.href="/admin/goods/edit/" + id;
}

function deleteConfig() {
    const ids = getSelectedRows();
    if (ids == null) {
        swal("请选择一条记录", {
            icon:"error",
        })
        return;
    }
    window.location.href="/admin/goods/delete";
}