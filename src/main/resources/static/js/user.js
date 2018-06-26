
//$.ajaxSettings.async = false;
$.ajaxSetup({
    async:false
});


//按键监听
function setClickListener(userList) {
    var editor = getCookie("user");
    var model = getCookie("model");
    for (var i=0; i<userList.length;i++) {
        var changeId= "#change-"+userList[i].name;
        var deleteId= "#delete-"+userList[i].name;

        $(changeId).click(function (e) {
           //alert("change");
           $("#bt-action").text("保存更改");
           setCookie("bt-action","change");
            var myId=$(this).attr("id");
            var index = myId.indexOf('-');
            var name=myId.substring(index+1,myId.length);//得到选取的账号名称
            //从后台获取完整user信息
            var user = getUserByName(name);
            if (user!==null){
                setModalText(user.id,user.name,user.password,user.role);
                $("#myModal").modal("show");
            }
            else {
                alert("选取的用户不存在！");
            }
        });

        $(deleteId).click(function (e) {
            //alert("delete");
            $("#bt-action").text("确认删除");
            setCookie("bt-action","delete");
            var myId=$(this).attr("id");
            var index = myId.indexOf('-');
            var name=myId.substring(index+1,myId.length);//得到选取的账号名称
            //从后台获取完整user信息
            var user = getUserByName(name);
            if (user!==null){
                setModalText(user.id,user.name,user.password,user.role);
                $("#myModal").modal("show");
            }
            else {
                alert("选取的用户不存在！");
            }
        });
    }

    $("#bt-action").click(function (e) {
        var action = getCookie("bt-action");
        alert("#bt-action="+action);
        //更改
        if (action==="change"){
            var id = $("#user-id").text();
            var name= $("#user-name").val();
            var password = $("#user-password").val();
            var role = $("#user-role").val();
            updateUser(id,name,password,role)
        }
        //删除
        else if (action==="delete"){
            var id = $("#user-id").text();
            deleteUserByName(id);
        }
        //添加
        else if (action==="add"){
            var name= $("#user-name").val();
            var password = $("#user-password").val();
            var role = $("#user-role").val();

            var result = getUserByName(name);
            if(result!==null){
                alert("已存在该名称的账号！")
            }
            else {
                addUser(name,password,role);
            }
        }
        //重新加载
        window.location.reload();
    });


    $("#bt-add").click(function (e) {
        setCookie("bt-action","add");
        setModalText("","","","");
        $("#myModal").modal("show");
    });
}

//根据id从数据库删除user
function deleteUserByName(id) {
    var url = "/user/deleteUserById?id="+id;
    var result = null;
    $.get(url,function(data,status){
        //alert("数据: " + data + "\n状态: " + status);
        if(data.result===1) {
            $("#myModal").modal("hide");
            alert(data.message);
        }
        else alert(data.message);
    });
    return result;
}
//根据name从数据库查找user
function getUserByName(name) {
    var url = "/user/getUserByName?name="+name;
    var result = null;
    $.get(url,function(data,status){
        //alert("数据: " + data + "\n状态: " + status);
        if(data.result===1) {
            result = data.user;
            if (result!==null){
                setModalText(result.id,result.name,result.password,result.role);
                $("#myModal").modal("show");
            }
            else {
                alert("选取的用户不存在！");
            }
        }
    });
    return result;
}
//根据user设置modal的内容
function setModalText(id,name,password,role) {
    $("#user-id").text(id);
    $("#user-name").val(name);
    $("#user-password").val(password);
    $("#user-role").val(role);
}
//添加user
function addUser(name,password,role) {
    var result =1;
    $.post("/user/add",
        {
            "name": name,
            "password": password,
            "role": role
        },
        function(data,status){
            //alert(data.result);
            if (data.result === 1) {
                alert(data.message);
                $("#myModal").modal("hide");
            } else {
                result=0;
                alert(data.message);
            }
        });
    return result;
}

//修改user
function updateUser(id,name,password,role) {
    var result =1;
    $.post("/user/update",
        {
            "id": id,
            "name": name,
            "password": password,
            "role": role
        },
        function(data,status){
            //alert(data.result);
            if (data.result === 1) {
                alert(data.message);
                $("#myModal").modal("hide");
            } else {
                result=0;
                alert(data.message);
            }
        });
    return result;
}

