
//$.ajaxSettings.async = false;
$.ajaxSetup({
    async:false
});


//按键监听
function setClickListener(pingweiList) {
    var editor = getCookie("user");
    var model = getCookie("model");
    for (var i=0; i<pingweiList.length;i++) {
        var changeId= "#change-"+pingweiList[i].code;
        var deleteId= "#delete-"+pingweiList[i].code;

        $(changeId).click(function (e) {
           //alert("change");
           $("#bt-action").text("保存更改");
           setCookie("bt-action","change");
            var myId=$(this).attr("id");
            var index = myId.indexOf('-');
            var code=myId.substring(index+1,myId.length);//得到选取的账号名称
            //从后台获取完整pingwei信息
            var pingwei = getPingweiByCodeAndModel(code,model);
            if (pingwei!==null){
                setModalText(pingwei.id,pingwei.name,pingwei.code,pingwei.model);
                $("#myModal").modal("show");
            }
            else {
                alert("选取的评委不存在！");
            }
        });

        $(deleteId).click(function (e) {
            //alert("delete");
            $("#bt-action").text("确认删除");
            setCookie("bt-action","delete");
            var myId=$(this).attr("id");
            var index = myId.indexOf('-');
            var code=myId.substring(index+1,myId.length);//得到选取的账号名称
            //从后台获取完整pingwei信息
            var pingwei = getPingweiByCodeAndModel(code,model);
            if (pingwei!==null){
                setModalText(pingwei.id,pingwei.name,pingwei.code,pingwei.model);
                $("#myModal").modal("show");
            }
            else {
                alert("选取的评委不存在！");
            }
        });
    }

    $("#bt-action").click(function (e) {
        var action = getCookie("bt-action");
        alert("#bt-action="+action);
        //更改
        if (action==="change"){
            var id = $("#pingwei-id").text();
            var name= $("#pingwei-name").val();
            var code = $("#pingwei-code").val();
            var model = $("#pingwei-model").val();
            updatePingwei(id,name,code,model)
        }
        //删除
        else if (action==="delete"){
            var id = $("#pingwei-id").text();
            deletePingweiById(id);
        }
        //添加
        else if (action==="add"){
            var name= $("#pingwei-name").val();
            var code = $("#pingwei-code").val();
            var model = $("#pingwei-model").val();

            var result = getPingweiByCodeAndModel(code,model);
            if(result!==null){
                alert("已存在该组别该序号的评委！")
            }
            else {
                addPingwei(name,code,model);
            }
        }
    });

    $("#bt-add").click(function (e) {
        setCookie("bt-action","add");
        setModalText("","","","");
        $("#myModal").modal("show");
    });
}

//根据id从数据库删除pingwei
function deletePingweiById(id) {
    var url = "/pingwei/deletePingweiById?id="+id;
    var result = null;
    $.get(url,function(data,status){
        //alert("数据: " + data + "\n状态: " + status);
        if(data.result===1) {
            $("#myModal").modal("hide");
            alert(data.message);
            //重新加载
            window.location.reload();
        }
        else alert(data.message);
    });
    return result;
}
//根据code和model从数据库查找pingwei
function getPingweiByCodeAndModel(code,model) {
    var url = "/pingwei/getPingweiByCodeAndModel?code="+code+"&model="+model;
    var result = null;
    $.get(url,function(data,status){
        //alert("数据: " + data + "\n状态: " + status);
        if(data.result===1) {
            result = data.pingwei;
            if (result!==null){
                // setModalText(result.id,result.name,result.password,result.role);
                // $("#myModal").modal("show");
            }
            else {
                alert("选取的用户不存在！");
            }
        }
    });
    return result;
}
//根据user设置modal的内容
function setModalText(id,name,code,model) {
    $("#pingwei-id").text(id);
    $("#pingwei-name").val(name);
    $("#pingwei-code").val(code);
    $("#pingwei-model").val(model);
}
//添加pingwei
function addPingwei(name,code,model) {
    var result =1;
    $.post("/pingwei/add",
        {
            "name": name,
            "code": code,
            "model": model
        },
        function(data,status){
            //alert(data.result);
            if (data.result === 1) {
                alert(data.message);
                $("#myModal").modal("hide");
                //重新加载
                window.location.reload();
            } else {
                result=0;
                alert(data.message);
            }
        });
    return result;
}

//修改Pingwei
function updatePingwei(id,name,code,model) {
    var result =1;
    $.post("/pingwei/update",
        {
            "id": id,
            "name": name,
            "code": code,
            "model": model
        },
        function(data,status){
            //alert(data.result);
            if (data.result === 1) {
                alert(data.message);
                $("#myModal").modal("hide");
                //重新加载
                window.location.reload();
            } else {
                result=0;
                alert(data.message);
            }
        });
    return result;
}

