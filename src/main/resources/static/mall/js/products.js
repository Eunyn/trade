// $(document).ready(function () {
//     console.log("URL: " + window.location);
//     const requestURL = window.location.href;
//     // var apiEndpoint = '/products/list';
//     var url = '/products/list';
//     if (!requestURL.match(/category/)) {
//         url = '/products/list';
//         sendRequest(url);
//         console.log("right");
//     }
//
//     // const split = requestURL.split("/");
//     // const categoryId = document.querySelectorAll('#categoryId');
//     // var test = 'product_' + categoryId[0].value;
//     // // var val = $('product_' + categoryId[0].value).val();
//     // console.log('var: ' + test);
//     // const product = document.getElementById(test);
//     // if (product !== null) {
//     //     console.log("product: " + product);
//     //     product.addEventListener("click", function () {
//     //         const a = categoryId[0].value;
//     //         url = '/products/category/' + a;
//     //         console.log("request url: " + url);
//     //         sendRequest(url);
//     //     });
//     // }
//
//
//
//
//     function sendRequest(url) {
//         // const apiEndpoint = '/products/list';
//         const params = {
//             page: 1,
//             limit: 12
//         };
//         $.ajax({
//             url: url,
//             type: 'GET',
//             dataType: 'application/json',
//             data: params,
//
//             success: function (data) {
//                 displayShops(data);
//             },
//             error: function (error) {
//                 console.error('Error fetching shops:', error);
//             }
//         });
//     }
//
//     function displayShops(shops) {
//         console.log("data:" + shops);
//         const shopContainer = $('#shopContainer');
//         if (shops.data.totalCount > 0) {
//             $.each(shops.data.list, function (index, shop) {
//                 const shopHtml = `
//                         <div class="col" id="productImg">
//                             <div class="shop-item">
//                                 <a class="product_name" href="/products/ + ${shop.goodsId}">
//                                     <img class="imgShow" src="${shop.goodsCoverImg}" alt="Shop Image">
//                                     <p >${shop.goodsName}</p>
//                                 </a>
//                             </div>
//                         </div>
//                     `;
//                 shopContainer.append(shopHtml);
//             });
//         } else {
//             const noShopHtml = `
//                     <div class="col">
//                         <h2><span>No shops available.</span></h2>
//                     </div>
//                 `;
//             shopContainer.append(noShopHtml);
//         }
//     }
// });

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

    const url = '/inquiry?verifyCode=' + verifyCode;
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
                    window.location.href = "/products"
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
                window.location.href='/search?keyword='+searchValue;
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

    window.location.href='/search?keyword='+searchValue;
}