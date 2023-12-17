$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/accessCount',
        datatype: "json",
        colModel: [
            {label: '#', name: 'index', index: 'index', width: 30, key: true},
            {label: '商品名称', name: 'goodsName', index: 'goodsName', width: 80},
            {label: '访问次数', name: 'score', index: 'score', width: 30},

        ],
        height: 300,
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
        height: 300,
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
accessCountEveryDay();

function accessCountEveryDay() {
    fetch("/admin/daily")
        .then(response => response.json())
        .then(data => {
            const dates = Object.keys(data);
            const counts = Object.values(data);

            const ctx = document.getElementById("myCanvas");
            ctx.style.maxWidth = '560px';
            ctx.style.maxHeight = '400px';
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: dates,
                    datasets: [{
                        label: '网站每周访问量',
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