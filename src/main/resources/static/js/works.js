
//$.ajaxSettings.async = false;
$.ajaxSetup({
    async:false
});


//按键监听
function setClickListener(worksList) {
    var editor = getCookie("user");
    var model = getCookie("model");
    for (var i=0; i<worksList.length;i++) {
        var changeId= "#"+worksList[i].id+"change-"+worksList[i].code;
        var deleteId= "#"+worksList[i].id+"delete-"+worksList[i].code;

        $(changeId).click(function (e) {
           //alert("change");
           $("#bt-action").text("保存更改");
           setCookie("bt-action","change");
            var myId=$(this).attr("id");
            var index = myId.indexOf('-');
            var code=myId.substring(index+1,myId.length);//得到选取的账号名称
            //从后台获取完整works信息
            var works = getWorksByCodeAndModel(code,model);
            if (works!==null){
                setModalText(works.id,works.code,works.bianHao,works.name,works.partName,works.school,
                    works.teachers,works.students,works.model);
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
            var code=myId.substring(index+1,myId.length);//得到选取的账号名称
            //从后台获取完整pingwei信息
            var works = getWorksByCodeAndModel(code,model);
            if (works!==null){
                setModalText(works.id,works.code,works.bianHao,works.name,works.partName,works.school,
                    works.teachers,works.students,works.model);
                $("#myModal").modal("show");
            }
            else {
                alert("选取的用户不存在！");
            }
        });
    }

    $("#bt-action").click(function (e) {
        var action = getCookie("bt-action");
        //alert("#bt-action="+action);
        var id = $("#works-id").text();
        var code= $("#works-code").val();
        var bianHao=$("#works-bianhao").val();
        var name = $("#works-name").val();
        var partName = $("#works-partName").val();
        var school = $("#works-school").val();
        var teachers = $("#works-teachers").val();
        var students = $("#works-students").val();
        var model = $("#works-model").val();
        //更改
        if (action==="change"){
            updateWorks(id,code,bianHao,name,partName,school,teachers,students,model);
        }
        //删除
        else if (action==="delete"){
            deleteWorksById(id);
        }
        //添加
        else if (action==="add"){
            addWorks(code,bianHao,name,partName,school,teachers,students,model);
            var result = getPingweiByCodeAndModel(code,model);
            if(result!==null){
                alert("已存在该名称的账号！")
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

//根据id从数据库删除works
function deleteWorksById(id) {
    var url = "/works/deleteWorksById?id="+id;
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
//根据code和model从数据库查找works
function getWorksByCodeAndModel(code,model) {
    var url = "/works/getWorksByCodeAndModel?code="+code+"&model="+model;
    var result = null;
    $.get(url,function(data,status){
        //alert("数据: " + data + "\n状态: " + status);
        if(data.result===1) {
            result = data.works;
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
//根据works设置modal的内容
function setModalText(id,code,bianHao,name,partName,school,teachers,students,model) {
    $("#works-id").text(id);
    $("#works-code").val(code);
    $("#works-bianhao").val(bianHao);
    $("#works-name").val(name);
    $("#works-partName").val(partName);
    $("#works-school").val(school);
    $("#works-teachers").val(teachers);
    $("#works-students").val(students);
    $("#works-model").val(model);
}
//添加works
function addWorks(code,bianHao,name,partName,school,teachers,students,model) {
    var result =1;
    $.post("/works/add",
        {
            "code": code,
            "bianHao": bianHao,
            "name": name,
            "partName": partName,
            "school": school,
            "teachers": teachers,
            "students": students,
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
function updateWorks(id,code,bianHao,name,partName,school,teachers,students,model) {
    var result =1;
    $.post("/works/update",
        {
            "id": id,
            "code": code,
            "bianHao": bianHao,
            "name": name,
            "partName": partName,
            "school": school,
            "teachers": teachers,
            "students": students,
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

