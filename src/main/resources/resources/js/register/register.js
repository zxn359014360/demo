var vmm = new Vue({
    el: "#head_content",
    data: {
        registContent: true,
        downloadContent: false,
        msgFlag: true,
        registFlag: true,
        phone: "",
        spinner: false
    },

    methods: {
        downloadUrl: function () {
            window.location.href = "www.baidu.com";
        },
        getSMS: function () {
            var phone = $("#phone").val();
            if (!mobileCheck.test(phone)) {
                toMsg("请填写正确的手机号码");
                return false;
            }
            if (vmm.msgFlag) {
                vmm.msgFlag = false;
                vmm.spinner = true;
                $.ajax({
                    type: "POST",
                    url: contextPath + "/sendSms",
                    data: {
                        "phone": phone,
                        rsaSign: encrypt(phone)
                    }, success: function (data) {
                        vmm.spinner = false;

                        var result = JSON.parse(data);
                        if (result.success) {
                            toMsg(result.msg);

                            if (result.code == 1) {
                                vmm.phone = result.object;
                                vmm.registContent = false;
                                vmm.downloadContent = true;
                            }

                            var d = 60;
                            var i = 0;
                            var t = setTimeout(function () {
                                document.getElementById('send_phone_captcha').innerHTML = '短信验证码';
                                vmm.msgFlag = true;
                            }, 60000);
                            for (i = 0; i < 60; i++) {
                                d--;
                                var t = setTimeout("document.getElementById('send_phone_captcha').innerHTML='重新获取" + d + "秒'", i * 1000);
                            }
                        } else {
                            toMsg(result.msg);
                            vmm.msgFlag = true;
                            vmm.phone = data.object;
                        }

                    }
                });
            }

        },
        submitRegist: function () {
            var phone = $("#phone").val();
            var code = $("#code").val();
            var password = $("#password").val();
            var confirmPassword = $("#confirmPassword").val();
            if (!mobileCheck.test(phone)) {
                toMsg("请填写正确的手机号码");
                return;
            }
            if (code == "" || code.length != 6) {
                toMsg("请填写正确的验证码");
                return;
            }
            if(password == "" || password.length < 6 || password.length > 16){
                toMsg("请填写6至16位的密码");
                return;
            }
            if (password.indexOf(" ") != -1) {
                toMsg("密码中不允许包含空格");
                return;
            }
            if(password != confirmPassword){
                toMsg("两次密码不一致");
                return;
            }
            var registAgreement = $('input[type=checkbox]').is(':checked');
            if (!registAgreement) {
                toMsg("您还未同意用户注册协议！");
                return;
            }
            if (vmm.registFlag) {
                vmm.registFlag = false;
                password = sha256_digest(password);
                $.ajax({
                    type: "POST",
                    url: contextPath + "/regist",
                    data: {
                        "phone": phone,
                        "code": code,
                        "password": password,
                        rsaSign: encrypt(phone + code + password)
                    }, success: function (data) {
                        var result = JSON.parse(data);
                        if (result.success) {
                            vmm.registContent = false;
                            vmm.downloadContent = true;
                            vmm.phone = result.object;
                        } else {
                            toMsg(result.msg);
                            vmm.registFlag = true;
                        }
                    },
                    error: function () {
                        toMsg("注册失败！");
                        vmm.registFlag = true;
                    }
                });
            }
        },
        registAgreement: function () {
            layer.open({
                title: "用户注册协议",
                area: ['80%', '90%'],
                type: 2,
                content: contextPath + "/registAgreement"
            });
        }
    }
});

var mobileCheck = /^1[3-9]\d{9}$/;



function toMsg(msg) {
    layer.msg(msg);
}

$.fn.getUrlParam = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return r[2];
    return "";
};