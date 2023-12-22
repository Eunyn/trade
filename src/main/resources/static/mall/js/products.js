
$('#sendInquiry').click(function () {
    const goodsId = $('#goodsId').val();
    const goodsName = $('#goodsName').val();
    const yourName = $('#yourName').val();
    const companyName = $('#companyName').val();
    const address = $('#address').val();
    const phone = $('#phone').val();
    const fax = $('#fax').val();
    const email = $('#email').val();
    const message = $('#message').val();
    const verifyCode = $('#verifyCode').val();

    if (isNull(goodsName)) {
        swal("Please choose product", {
            icon: "error",
        });
        return;
    }
    if (isNull(email)) {
        swal("E-mail is null", {
            icon: "error",
        });
        return;
    }
    if (isNull(verifyCode)) {
        swal("VerifyCode is null", {
            icon: "error",
        });
        return;
    }

    const url = '/mall/inquiry?verifyCode=' + verifyCode;
    const swlMessage = 'Send Success';
    const data = {
        "goodsId": goodsId,
        "goodsName": goodsName,
        "yourName": yourName,
        "companyName": companyName,
        "address": address,
        "phone": phone,
        "fax": fax,
        "email": email,
        "message": message
    };

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
                    confirmButtonText: 'Return product list',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/mall/products"
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
        },
        error: function (result) {
            swal("Verify code or something wrong.", {
                icon: "error",
            });
        }
    });
});

$(function () {
    $('#keyword').keypress(function (e) {
        const key = e.which;
        if (key === 13) {
            const searchValue = $(this).val();
            if (searchValue && searchValue !== '') {
                window.location.href = '/mall/search?keyword=' + searchValue;
            }
        }
    })
});

function search() {
    const searchValue = $('#keyword').val();
    if (isNull(searchValue)) {
        swal("please input something", {
            icon: "error",
        });
    }

    window.location.href = '/mall/search?keyword=' + searchValue;
}

$('#inquiryButton').click(function (){
    const captchaImage = document.getElementById('captchaImage');
    if (captchaImage) {
        captchaImage.src = '/common/mall/captcha';
    }
});

function refreshCaptcha() {
    const captchaImage = document.getElementById('captchaImage');
    if (captchaImage) {
        captchaImage.src = '/common/mall/captcha?d=' + new Date().getTime();
    }
}