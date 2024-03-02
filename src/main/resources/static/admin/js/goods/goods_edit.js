let editorD;
let imagesData;

$(function () {
    // 富文本编辑器，商品详情编辑
    const E = window.wangEditor;
    editorD = new E('#wangEditor')
    editorD.config.height = 500
    editorD.config.uploadImgServer = '/admin/upload/detailFiles'
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

    // // 商品预览图上传
    // new AjaxUpload('#uploadGoodsCoverImg', {
    //     action: '/admin/upload/files',
    //     name: 'files',
    //     autoSubmit: true,
    //     responseType: "json",
    //     onSubmit: function (file, extension) {
    //         console.log('File object:', file);
    //         if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
    //             alert('只支持jpg、png、gif格式的文件！');
    //             return false;
    //         }
    //     },
    //     onComplete: function (file, r) {
    //         if (r != null && r.code === 200) {
    //             $("#goodsCoverImg").attr("src", r.data);
    //             $("#goodsCoverImg").attr("style", "width: 128px;height: 128px;display:block;");
    //             return false;
    //         } else if (r.code === 404) {
    //             alert("Image size is too big, the size limit is 10MB.");
    //         } else {
    //             alert("something error.")
    //         }
    //     }
    // });
});

$('#fileInput').change(function () {
    uploadFiles();
});

function uploadFiles() {
    const fileInput = document.getElementById('fileInput');
    const files = fileInput.files;
    if (files.length === 0) {
        alert('至少上传一张');
        return;
    }

    if (files.length > 8) {
        alert('最多上传 8 张图片')
        return
    }
    const formData = new FormData();
    const allowedFormats = ['jpg', 'jpeg', 'png'];
    const maxSize = 10 * 1024 * 1024; // 10MB
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const fileSize = file.size;
        const fileName = file.name;
        const fileExtension = fileName.split('.').pop().toLowerCase();
        // Check file size
        if (fileSize > maxSize) {
            alert('File size exceeds the limit of 10MB.');
            return;
        }
        // Check file format
        if (!allowedFormats.includes(fileExtension)) {
            alert('文件格式仅限 jpg、jpeg、png');
            return;
        }

        formData.append('files[]', file);
    }

    $.ajax({
        type: 'POST',
        url: '/admin/upload/files',
        processData: false,
        contentType: false,
        data:  formData,
        success: function (result) {
            if (result.code === 200) {
                imagesData = result.data
                showUploadedImages(result.data)
                console.log("上传结果：" + JSON.stringify(result))
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
        },
        error:  () => {
            swal("Server Error", {
                icon: "error",
            });
        }
    });
}

$('#saveButton').click(function () {
    if (imagesData == null) {
        swal("请上传商品图片", {
            icon: "error",
        });
        return;
    }
    const goodsId = $('#goodsId').val();
    const goodsName = $('#goodsName').val();
    const goodsProductionTime = $('#goodsProductionTime').val();
    const goodsCategoryId = $('#levelOne option:selected').val();
    const goodsColor = $('#goodsColor').val();
    const goodsSize = $('#goodsSize').val();
    const goodsMaterial = $('#goodsMaterial').val();
    const goodsImprintMethod = $('#goodsImprintMethod').val();
    const goodsCoverImg = imagesData.join();
    const goodsDetailContent = editorD.txt.html();

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

    if (!validLength(goodsColor, 100)) {
        swal("颜色过长", {
            icon: "error",
        });
        return;
    }

    let url = '/admin/goods/save';
    let swlMessage = '保存成功';
    let data = {
        "goodsName": goodsName,
        "goodsProductionTime": goodsProductionTime,
        "goodsCategoryId": goodsCategoryId,
        "goodsCoverImg": goodsCoverImg,
        "goodsColor": goodsColor,
        "goodsSize": goodsSize,
        "goodsMaterial": goodsMaterial,
        "goodsImprintMethod": goodsImprintMethod,
        "goodsDetails": goodsDetailContent
    };
    if (goodsId > 0) {
        url = '/admin/goods/update';
        swlMessage = '修改成功';
        data = {
            "goodsId": goodsId,
            "goodsName": goodsName,
            "goodsProductionTime": goodsProductionTime,
            "goodsCategoryId": goodsCategoryId,
            "goodsCoverImg": goodsCoverImg,
            "goodsColor": goodsColor,
            "goodsSize": goodsSize,
            "goodsMaterial": goodsMaterial,
            "goodsImprintMethod": goodsImprintMethod,
            "goodsDetails": goodsDetailContent
        };
    }
    // console.log("保存: " + JSON.stringify(data));
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

function showUploadedImages(images) {
    const uploadedImagesDiv = document.getElementById('uploadedImages');
    uploadedImagesDiv.innerHTML = ''; // 清空之前的内容

    images.forEach(imageUrl => {
        const img = document.createElement('img');
        img.src = imageUrl;
        img.style.maxWidth = '50px';
        uploadedImagesDiv.appendChild(img);
    });
}
