/**
 * 登录方法
 */
$(function () {
    //获取登录按钮，绑定单击事件
    $("#btn_sub").click(function () {
   //发送ajax请求
   $.post("user/login",$("#loginForm").serialize(),function (data) {
        if(data.flag){
            location.href="index.html";
        }else {
            $("#errorMsg").html(data.errorMsg);
        }
   })
    });
});
function change(){
    $("#password").attr("type","password");
}