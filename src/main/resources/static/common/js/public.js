/**
 * 判空
 *
 * @param obj
 * @returns {boolean}
 */
function isNull(obj) {
    return obj == null || false || obj.trim() === "";

}

/**
 * 参数长度验证
 *
 * @param obj
 * @param length
 * @returns {boolean}
 */
function validLength(obj, length) {
    return obj.trim().length < length;

}

/**
 * url验证
 *
 * @returns {boolean}
 * @param str_url
 */
function isURL(str_url) {
    const strRegex = "^((https|http|ftp|rtsp|mms)?://)"
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}"
        + "|"
        + "([0-9a-zA-Z_!~*'()-]+\.)*"
        + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\."
        + "[a-zA-Z]{2,6})"
        + "(:[0-9]{1,4})?"
        + "((/?)|"
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    const re = new RegExp(strRegex);

    return re.test(str_url);
}

/**
 * 手机号正则验证
 * @returns {boolean}
 */
function validPhoneNumber(phone) {
    return (/^1(3|4|5|6|7|8|9)\d{9}$/.test(phone));
}

/**
 * 正则匹配2-18位的中英文字符串
 *
 * @param str
 * @returns {boolean}
 */
function validCN_ENString2_18(str) {
    const pattern = /^[a-zA-Z0-9-\u4E00-\u9FA5_,， ]{2,18}$/;
    return pattern.test(str.trim());
}

/**
 * 获取jqGrid选中的一条记录
 * @returns {*}
 */
function getSelectedRow() {
    const grid = $("#jqGrid");
    const rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    const selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        swal("只能选择一条记录", {
            icon: "warning",
        });
        return;
    }
    return selectedIDs[0];
}

/**
 * 获取jqGrid选中的一条记录(不出现弹框)
 * @returns {*}
 */
function getSelectedRowWithoutAlert() {
    const grid = $("#jqGrid");
    const rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        return;
    }
    const selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        return;
    }
    return selectedIDs[0];
}

/**
 * 获取jqGrid选中的多条记录
 * @returns {*}
 */
function getSelectedRows() {
    const grid = $("#jqGrid");
    const rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    return grid.getGridParam("selarrrow");
}