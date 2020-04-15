/**
 * 这个js的主要做的是注册表单的校验
 */
//校验用户名  8-20位英文
function checkUsername() {
    var username = $("#username").val();
    var reg_username = /^\w{8,20}$/
    var flag = reg_username.test(username);
    if(flag){
        $("#username").css("border","");
    }else {
        $("#username").css("border","1px solid red")
    }
    return flag ;
}
//校验密码
function checkPassword(){
    var password = $("#password").val();
    var reg_username = /^\w{8,20}$/
    var flag = reg_username.test(password);
    if(flag){
        $("#password").css("border","");
    }else {
        $("#password").css("border","1px solid red")
    }
    return flag ;
}
//校验邮箱
function checkEmail(){
    var email = $("#email").val();
    var reg_email = /^\w+@\w+\.\w+$/
    var flag = reg_email.test(email);
    if(flag){
        $("#email").css("border","");
    }else {
        $("#email").css("border","1px solid red")
    }
    return flag ;
}
//校验姓名
function checkName(){
    var name = $("#name").val();
    var flag = name.length > 0;
    if(flag){
        $("#name").css("border","");
    }else {
        $("#name").css("border","1px solid red")
    }
    return flag;
}
//手机号
function checkPhone(){
    var phone = $("#telephone").val();
    var reg_phone = /^1[3456789]\d{9}$/
    var flag = reg_phone.test(phone);
    if(flag){
        $("#telephone").css("border","");
    }else {
        $("#telephone").css("border","1px solid red")
    }
    return flag;
}
//出生日期
function checkBirthday(){
    var birthday = $("#birthday").val();
    var flag = birthday.length>0;
    if(flag){
        $("#birthday").css("border","");
    }else {
        $("#birthday").css("border","1px solid red")
    }
    return flag;
}
//验证码
function checkCode(){
    var code = $("#check").val();
    var flag = code.length == 4;
    if(flag){
        $("#check").css("border","");
    }else {
        $("#check").css("border","1px solid red")
    }
    return flag;
}
function change(){
   $("#password").attr("type","password");
}


$(function () {
//当表单提交的时候调用所有的校验方法，如果没有返回值或者返回值为true则提交表单，否则不提交
   $("#registerForm").submit(function () {
       //如果验证通过就ajax提交数据
       if(checkUsername() && checkPassword() && checkEmail() && checkName() && checkPhone() && checkBirthday() && checkCode()){
            $.post("user/register",$(this).serialize(),function (data) {
                if(data.flag){
                    location.href="register_ok.html";
                }else {
                    $("#errorMsg").html(data.errorMsg);
                }
            });
       }
     return false ;
   });
   //每个组件失去焦点都调用对应的验证方法
    $("#username").blur(checkUsername);
    $("#password").blur(checkPassword);
    $("#email").blur(checkEmail);
    $("#name").blur(checkName);
    $("#telephone").blur(checkPhone);
    $("#birthday").blur(checkBirthday);
    $("#check").blur(checkCode);
})
