$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/accessCount',
        datatype: "json",
        colModel: [
            {label: '#', name: 'index', index: 'index', width: 30, key: true},
            {label: '商品名称', name: 'goodsName', index: 'goodsName', width: 80},
            {label: '访问次数', name: 'score', index: 'score', width: 30},

        ],
        height: 365,
        rowNum: 20,
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        // pager: "#jqGridPager",
        jsonReader: {
            root: "data"
        },
        prmNames: {},
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

    const page2 = $("#jqGrid1").jqGrid('getGridParam', 'page');
    $("#jqGrid1").jqGrid('setGridParam', {
        page: page2
    }).trigger("reloadGrid");
}

$(function () {
    $("#jqGrid1").jqGrid({
        url: '/admin/inquiryCount',
        datatype: "json",
        colModel: [
            {label: '#', name: 'index', index: 'index', width: 30, key: true},
            {label: '商品名称', name: 'goodsName', index: 'goodsName', width: 80},
            {label: 'INQUIRY次数', name: 'score', index: 'score', width: 40},

        ],
        height: 365,
        rowNum: 20,
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        // pager: "#jqGridPager",
        jsonReader: {
            root: "data"
        },
        prmNames: {},
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid1").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid1").setGridWidth($(".card-body").width());
    });


});

function selectChange() {
    const select = document.querySelector(".form-select");
    select.addEventListener("change", function () {
        accessCountEveryDay();
    });
}

accessCountEveryDay();

function accessCountEveryDay() {


    const select = document.querySelector(".form-select");
    const selectId = select.value;
    const url = "/admin/daily/" + selectId;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const dates = Object.keys(data);
            const counts = Object.values(data);

            const ctx = document.getElementById("myCanvas");
            const existingChart = Chart.getChart(ctx);
            if (existingChart) {
                existingChart.destroy();
            }

            ctx.style.maxWidth = '560px';
            ctx.style.maxHeight = '400px';
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: dates,
                    datasets: [{
                        label: '网站访问量',
                        data: counts,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            })
        });
}

function updatePassword() {
    $('#passwordModel').modal('show');
}

$('#submitPassword').click(function () {
    const firstInput = $("#passwordFirst").val();
    const secondInput = $("#passwordSecond").val();

    if (isNull(firstInput) || isNull(secondInput)) {
        swal("密码不能为空", {
            icon: "error",
        });
        return;
    }
    if (firstInput.length < 6 || firstInput.length > 16) {
        swal("密码不能低于6位，多于16位", {
            icon: "error",
        });
        return;
    }
    if (firstInput !== secondInput) {
        swal("密码不一致", {
            icon: "error",
        });
        return;
    }

    const data = {"userName": "","userPassword": firstInput};
    $.ajax({
        type: 'POST',
        url: '/admin/password',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.code === 200) {
                swal("修改成功", {
                    icon: "success",
                }).then(function () {
                    window.location.href = "/admin/login";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }

        },
        error: function () {
            swal("修改失败", {
                icon: "error",
            });
        }
    });
});