$(function () {
    $.get("user/find",{},function (data) {
        var msg = "欢迎回来"+data.name;
        $("#span_username").html(msg);
    })
});
$(function () {
    $.get("category/findAll",{},function (data) {
        var lis = ' <li class="nav-active"><a href="index.html">首页</a></li>';
        for(var i=0;i<data.length;i++){
            var li = '<li><a href="route_list.html?cid='+data[i].cid+'">'+data[i].cname+'</a></li>';
            lis+=li;
        }
        lis+='<li><a href="favoriterank.html">收藏排行榜</a></li>';
        $("#category").html(lis);
    })
    $("#search-button").click(function () {
        var rname = $("#search_input").val();
        //alert(rname);
        var cid = getParameter("cid");
        //alert(cid)
        location.href="http://localhost:8080/travle/route_list.html?cid="+cid+"&rname="+rname;

    })
})