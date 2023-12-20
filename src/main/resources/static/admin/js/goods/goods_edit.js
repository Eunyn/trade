let editorD;

$(function () {
    // 富文本编辑器，商品详情编辑
    const E = window.wangEditor;
    editorD = new E('#wangEditor')
    editorD.config.height = 500
    editorD.config.uploadImgServer = '/admin/upload/files'
    editorD.config.uploadFileName = 'files'
    editorD.config.uploadImgMaxSize = 10 * 1024 * 1024
    editorD.config.uploadImgMaxLength = 5
    editorD.config.showLinkImg = false
    editorD.config.uploadImgHooks = {
        success: function (xhr) {
            console.log('success', xhr)
        },
        fail: function (xhr, editor, resData) {
            console.log('fail', resData)
        },
        error: function (xhr, editor, resData) {
            console.log('error', xhr, resData)
        },
        timeout: function (xhr) {
            console.log('timeout')
        },
        customInsert: function (insertImgFn, result) {
            if (result != null && result.code === 200) {
                // insertImgFn 可把图片插入到编辑器，传入图片 src ，执行函数即可
                result.data.forEach(img => {
                    insertImgFn(img)
                });
            } else {
                alert("error");
            }
        }
    }
    editorD.create();

    // 商品预览图上传
    new AjaxUpload('#uploadGoodsCoverImg', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            console.log('File object:', file);
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.code === 200) {
                $("#goodsCoverImg").attr("src", r.data);
                $("#goodsCoverImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else if (r.code === 404) {
                alert("Image size is too big, the size limit is 10MB.");
            } else {
                alert("something error.")
            }
        }
    });
});

$('#saveButton').click(function () {
    const goodsId = $('#goodsId').val();
    const goodsName = $('#goodsName').val();
    const goodsIntro = $('#goodsInfo').val();
    const goodsCategoryId = $('#levelOne option:selected').val();
    const goodsColor = $('#goodsColor').val();
    const goodsSize = $('#goodsSize').val();
    const goodsMaterial = $('#goodsMaterial').val();
    const goodsPrice = $('#goodsPrice').val();
    const goodsCoverImg = $('#goodsCoverImg')[0].src;
    const goodsDetailContent = editorD.txt.html();
    console.log("goodsId: " + goodsId);

    if (isNull(goodsCategoryId)) {
        swal("请选择分类", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsName)) {
        swal("请输入商品名称", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsName, 100)) {
        swal("商品名称过长", {
            icon: "error",
        });
        return;
    }
    // if (isNull(goodsIntro)) {
    //     swal("请输入商品简介", {
    //         icon: "error",
    //     });
    //     return;
    // }
    if (!validLength(goodsIntro, 200)) {
        swal("简介过长", {
            icon: "error",
        });
        return;
    }
    // if (isNull(goodsColor)) {
    //     swal("请输入商品颜色", {
    //         icon: "error",
    //     });
    //     return;
    // }
    if (!validLength(goodsColor, 100)) {
        swal("颜色过长", {
            icon: "error",
        });
        return;
    }
    // if (isNull(goodsSize)) {
    //     swal("请输入商品尺寸", {
    //         icon: "error",
    //     });
    //     return;
    // }
    // if (isNull(goodsMaterial)) {
    //     swal("请输入商品材质", {
    //         icon: "error",
    //     });
    //     return;
    // }
    // if (isNull(goodsPrice)) {
    //     swal("请输入商品价格", {
    //         icon: "error",
    //     });
    //     return;
    // }

    // if (isNull(goodsDetailContent)) {
    //     swal("请输入商品介绍", {
    //         icon: "error",
    //     });
    //     return;
    // }
    // if (!validLength(goodsDetailContent, 50000)) {
    //     swal("商品介绍内容过长", {
    //         icon: "error",
    //     });
    //     return;
    // }
    if (isNull(goodsCoverImg) || goodsCoverImg.indexOf('img-upload') !== -1) {
        swal("封面图片不能为空", {
            icon: "error",
        });
        return;
    }
    let url = '/admin/goods/save';
    let swlMessage = '保存成功';
    let data = {
        "goodsName": goodsName,
        "goodsInfo": goodsIntro,
        "goodsCategoryId": goodsCategoryId,
        "goodsCoverImg": goodsCoverImg,
        "goodsColor": goodsColor,
        "goodsSize": goodsSize,
        "goodsMaterial": goodsMaterial,
        "goodsPrice": goodsPrice,
        "goodsDetails": goodsDetailContent
    };
    if (goodsId > 0) {
        url = '/admin/goods/update';
        swlMessage = '修改成功';
        data = {
            "goodsId": goodsId,
            "goodsName": goodsName,
            "goodsInfo": goodsIntro,
            "goodsCategoryId": goodsCategoryId,
            "goodsCoverImg": goodsCoverImg,
            "goodsColor": goodsColor,
            "goodsSize": goodsSize,
            "goodsMaterial": goodsMaterial,
            "goodsPrice": goodsPrice,
            "goodsDetails": goodsDetailContent
        };
    }
    // console.log(data);
    $.ajax({
        type: 'POST',
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.code === 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: '返回商品列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/goods";
                })
            } else {
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
});

$('#cancelButton').click(function () {
    window.location.href = "/admin/goods";
});
