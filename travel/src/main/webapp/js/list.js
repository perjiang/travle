$(function () {
    var search = location.search;//获取传递参数的所以
    //serch  ？cid=.
    //切割serch获取cid的值
    var cid = getParameter("cid");
    var rname = getParameter("rname");
    //解码
    if(rname){
        rname = window.decodeURIComponent(rname);
    }
    // alert(cid)
    // alert(rname)
    //加载完成调用函数
    load(cid,null,rname)
})
function load(cid,currentPage,rname) {
    $.get("route/pageQuery",{
        cid:cid,
        currentPage:currentPage,
        rname:rname
    },function (bean) {
        //展示所有的条数以及共多少页
        $("#totalPage").html(bean.totalPage);
        $("#totalCount").html(bean.totalCount);
        var lis = '';
        var firstPage = '<li onclick="javascript:load('+cid+',1,\''+rname+'\')"><a href="javascript:void(0)">首页</a></li>';
        //计算上一页号码
        var before;
        // alert(bean.currentPage)
        if(bean.currentPage > 1){
            before = bean.currentPage -1;
        }else {
            before = 1;
        }
        //计算下一页
        var next;

        if(bean.currentPage < bean.totalPage ){
             next = bean.currentPage +1;
        }if(bean.currentPage == bean.totalPage){
            next = bean.totalPage;
        }

        var beforePage =' <li onclick="javascript:load('+cid+','+before+',\''+rname+'\')"  class="threeword"><a href="javascript:void(0)">上一页</a></li>';
        lis+=firstPage;
        lis+=beforePage;
        var begin;
        var end;
        if(10>bean.totalPage){
            begin = 1;
            end = bean.totalPage;
        }else {
            begin = bean.currentPage - 5;
            end = bean.currentPage +4;
            if(begin<1){
                begin = 1;
                end = begin +9;
            }if(end>bean.totalPage){
                end = bean.totalPage;
                begin = end -9;
            }
        }
        for (var i = begin; i <= end; i++) {
            var li;
                //如果当前页码等于i就加一个样式
                if(bean.currentPage == i){
                     li ='<li class="curPage" onclick="javascript:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">'+i+'</a></li>';
                }else {
                    li ='<li onclick="javascript:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">'+i+'</a></li>';
                }
                lis+=li;
        }
        /*展示条码  遍历totalPage
         for (var i = 1; i <=bean.totalPage ; i++) {
             var li;
             //如果当前页码等于i就加一个样式
             if(bean.currentPage == i){
                  li ='<li class="curPage" onclick="javascript:load('+cid+','+i+')"><a href="javascript:void(0)">'+i+'</a></li>';
             }else {
                 li ='<li onclick="javascript:load('+cid+','+i+')"><a href="javascript:void(0)">'+i+'</a></li>';
             }
            lis+=li;
         }*/
        var nextPage = '<li onclick="javascript:load('+cid+','+next+',\''+rname+'\')" class="threeword"><a href="javascript:void(0)">下一页</a></li>';
        var lastPage = '<li onclick="javascript:load('+cid+','+ bean.totalPage+',\''+rname+'\')" class=\"threeword\"><a href="javascript:void(0)">末页</a></li>';
        lis+=nextPage;
        lis+=lastPage;
        $("#pageNum").html(lis);
        //展示数据
        var route_lis = "";
        for (var i = 0; i <bean.list.length ; i++) {
            var route = bean.list[i];
            var li ='<li>\n' +
                '                            <div class="img"><img src="'+route.rimage+'" style="width: 299px"></div>\n' +
                '                            <div class="text1">\n' +
                '                                <p>'+route.rname+'</p>\n' +
                '                                <br/>\n' +
                '                                <p>'+route.routeIntroduce+'</p>\n' +
                '                            </div>\n' +
                '                            <div class="price">\n' +
                '                                <p class="price_num">\n' +
                '                                    <span>&yen;</span>\n' +
                '                                    <span>'+route.price+'</span>\n' +
                '                                    <span>起</span>\n' +
                '                                </p>\n' +
                '                                <p><a href="route_detail.html?rid='+route.rid+'">查看详情</a></p>\n' +
                '                            </div>\n' +
                '                        </li>';
            route_lis+=li;
        }
        $("#route").html(route_lis);

        //滚动到页面的顶端
        window.scrollTo(0,0);
    })
}