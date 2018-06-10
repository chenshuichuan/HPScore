
/*动态文字时钟*/
var click = setInterval(function() {
    var username = getCookie("user");
    var welcome = "欢迎<span style='color: #5375e1;'>"+username+",</span>";

    var time = new Date();
    var timeStr = time.getFullYear()+'年'+(time.getMonth()+1)+'月'+time.getDate()+'日 '+
        time.getHours()+':'+time.getMinutes()+':'+time.getSeconds();
    $("#click").html(welcome+timeStr).css();
}, 1000);

$(document).ready(function(){

    //根据权限和model设置页面显示
    var role = getCookie("role");
    var model = getCookie("model");
    //不是管理员，则隐藏count页面
    if(role != 0){
        var count=document.getElementById("count");
        count.style.display='none';
    }
    //不是本科组，隐藏record1
    if(model != "本科组"){
        var count=document.getElementById("record1");
        count.style.display='none';
    }
    //不是本科组，隐藏record2
    if(model != "高职高专组"){
        var count=document.getElementById("record2");
        count.style.display='none';
    }


    $("#logout").click(function (e) {
        delCookie("user");
        delCookie("pswd");
        delCookie("model");
        delCookie("role");
        window.location.href ="/logout";
    });

    var userName = getCookie("user");
    $("#record1").click(function (e) {
        var url = "/record1?editor="+userName;
        window.location.href = url;
    });
    $("#record2").click(function (e) {
        var url = "/record2?editor="+userName;
        window.location.href = url;
    });
    $("#record3").click(function (e) {
        var url = "/record3?editor="+userName+"&model="+model;
        window.location.href = url;
    });
});

