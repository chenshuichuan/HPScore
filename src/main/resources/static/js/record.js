
// $.ajaxSettings.async = false;
$.ajaxSetup({
    async:false
});
function addRecord(pid,proId,options,model,editor) {
    var index_begin=-1;
    var result =1;
    $.post("/score/add",
        {
            "pid": pid,
            "proId": proId,
            "option1": options[index_begin+1],
            "option2": options[index_begin+2],
            "option3": options[index_begin+3],
            "option4": options[index_begin+4],
            "option5": options[index_begin+5],
            "option6": options[index_begin+6],
            "model": model,
            "editor": editor
        },
        function(data,status){
            //alert(data.result);
            if (data.result === 1) {
                alert(data.message);
            } else {
                result=0;
                alert(data.message);
            }
        });
    return result;
}
function updateRecord(pid,proId,options,model,editor) {
    var index_begin=-1;
    var result =1;
    $.post("/score/update",
        {
            "pid": pid,
            "proId": proId,
            "option1": options[index_begin+1],
            "option2": options[index_begin+2],
            "option3": options[index_begin+3],
            "option4": options[index_begin+4],
            "option5": options[index_begin+5],
            "option6": options[index_begin+6],
            "model": model,
            "editor": editor
        },
        function(data,status){
            //alert(data.result);
            if (data.result === 1) {
                alert(data.message);
            } else {
                result=0;
                alert(data.message);
            }
        });
    return result;
}
function setModalText(pid,proId,data,options,editor) {

    // $('#pingwei2').text(data.pid);
    // $('#works2').text(data.proId);
    // $('#option21').text(data.option1);
    // $('#option22').text(data.option2);
    // $('#option23').text(data.option3);
    // $('#option24').text(data.option4);
    // $('#option25').text(data.option5);
    // $('#option26').text(data.option6);
    // $('#editor1').text(data.editor1);

    var index_begin=0;
    var option1=parseInt(options[index_begin]);
    var option2=parseInt(options[index_begin+1]);
    var option3=parseInt(options[index_begin+2]);
    var option4=parseInt(options[index_begin+3]);
    var option5=parseInt(options[index_begin+4]);
    var option6=parseInt(options[index_begin+5]);

    $('#select-pingwei2').val(pid);
    $('#select-works2').val(proId);
    $('#option31').val(option1);
    $('#option32').val(option2);
    $('#option33').val(option3);
    $('#option34').val(option4);
    $('#option35').val(option5);
    $('#option36').val(option6);
    $('#editor2').text(editor);
}
//根据model检查数据合法性
function IsDataOk(options,model) {
    var benke = [10,15,20,20,20,15];
    var gaozhi = [10,15,10,25,25,15];
    var temp = [];
    var result = 1;

    if(model==="本科组"){
        temp = benke;
    }
    else temp = gaozhi;
    for (var i = 0;i<6;i++){
        if(options[i].length<=0)result=0;
        var value =parseInt(options[i]);
        if(value>temp[i]||value<=0){
            result=0;
        }
    }
    if(result===0)alert("数据不合法！请重新输入！");
    return result;
}
//第一个编辑就是editor，第二个编辑为空时，不可以
//冲突不可以 冲突则返回冲突数据，否则返回null
function IsDataOk2(pid,proId, options,model){
    var optionList=options;
    for(var j=0;j<6;j++){
        options[j]=parseInt(optionList[j]);
    }
    var url = "/score/selectByPidAndProIdAndModel?pid="+pid+"&proId="+proId+"&model="+model;
    var result = -1;//不存在该数据
    $.get(url,function(data,status){
        //alert("数据: " + data + "\n状态: " + status);
        if(data.result===1){
            var score = data.score;
            if(score!==null&&score.proId!==null&&score.proId.length>0){
                if (score.option1!==options[0]||score.option2!==options[1]||score.option3!==options[2]||
                    score.option4!==options[3]||score.option5!==options[4]||score.option6!==options[5]){
                    alert("数据不一致！");
                    result=1;
                }
                else result=0;//数据一致
            }
        }

    });
    // var str ="return result="+result;
    // alert(str);
    return result;
}

//生成输入框列表
function generateEditTable() {
    var model =  getCookie("model");
    var header1="<tr>" +
        "<th>作品</th>" +
        "<th>选题</th>" +
        "<th>科学性</th>" +
        "<th>创新性</th>" +
        "<th>难/易程度</th>" +
        "<th>实用价值及效果</th>" +
        "<th>文档质量及答辩效果</th>" +
        "<th>编辑/保存</th>" +
        "</tr>";
    var header2="<tr>" +
        "<th>作品</th>" +
        "<th>选题</th>" +
        "<th>技术性</th>" +
        "<th>创新性</th>" +
        "<th>难/易程度</th>" +
        "<th>实用价值及效果</th>" +
        "<th>演示答辩效果</th>" +
        "<th>编辑/保存</th>" +
        "</tr>";
    if(model==="本科组")$("#edit-header").append(header1);
    else $("#edit-header").append(header2);

    var header5="<tr>" +
        "<th>作品</th>" +
        "<th>选题</th>" +
        "<th>科学性</th>" +
        "<th>创新性</th>" +
        "<th>难/易程度</th>" +
        "<th>实用价值及效果</th>" +
        "<th>文档质量及答辩效果</th>" +
        "</tr>";
    var header6="<tr>" +
        "<th>作品</th>" +
        "<th>选题</th>" +
        "<th>技术性</th>" +
        "<th>创新性</th>" +
        "<th>难/易程度</th>" +
        "<th>实用价值及效果</th>" +
        "<th>演示答辩效果</th>" +
        "</tr>";
    if(model==="本科组")$("#search-header").append(header5);
    else $("#search-header").append(header6);


    var header3="<tr>" +
        "<th>评委</th>" +
        "<th>作品</th>" +
        "<th>选题</th>" +
        "<th>科学性</th>" +
        "<th>创新性</th>" +
        "<th>难/易程度</th>" +
        "<th>实用价值及效果</th>" +
        "<th>文档质量及答辩效果</th>" +
        "<th>编辑者</th>"+
        "</tr>";
    var header4="<tr>" +
        "<th>评委</th>" +
        "<th>作品</th>" +
        "<th>选题</th>" +
        "<th>技术性</th>" +
        "<th>创新性</th>" +
        "<th>难/易程度</th>" +
        "<th>实用价值及效果</th>" +
        "<th>演示答辩效果</th>" +
        "<th>编辑者</th>"+
        "</tr>";
    if(model==="本科组")$("#modal-header").append(header3);
    else $("#modal-header").append(header4);

    for (var i=1; i<=24;i++){
        var strId="input"+i;
        var proId="<tr>\n" +
            "    <td id='"+strId+0+"' style=\"color: rgba(180,49,133,1);font-size: 18px;\">" +
                   i+
            "</td>\n" ;
        var option1="<td>\n" +
            "        <input id='"+strId+1+"' />\n" +
            "       </td>\n" ;
        var option2="<td>\n" +
            "        <input id='"+strId+2+"' />\n" +
            "       </td>\n" ;
        var option3="<td>\n" +
            "        <input id='"+strId+3+"' />\n" +
            "       </td>\n" ;
        var option4="<td>\n" +
            "        <input id='"+strId+4+"' />\n" +
            "       </td>\n" ;
        var option5="<td>\n" +
            "        <input id='"+strId+5+"' />\n" +
            "       </td>\n" ;
        var option6="<td>\n" +
            "        <input id='"+strId+6+"' />\n" +
            "       </td>\n" ;
        var bt = "<td>\n" +
            "        <button id='"+strId+7+"' type=\"button\" class=\"btn btn-success\">保存</button>\n" +
            "    </td>\n" +
            "</tr>";
        var str =proId+option1+option2+option3+option4+option5+option6+bt;
        $("#edit-table").append(str);
    }
}

//生成保存按钮监听
function generateSaveListener() {
    for (var i=1; i<=24;i++){
        var strId="#input"+i;
        var btId = strId+7;
        $(btId).click().on("click", function() {
            var myId=$(this).attr("id");
            var preId="#"+myId.substring(0,myId.length-1);
            var option=[];
            var options=[];
            for(var j=0;j<6;j++){
                option[j]=preId+(j+1);
                options[j]=$(option[j]).val();
            }
            var model =  getCookie("model");
            var editor =  getCookie("user");
            var pid = $("#select-pingwei").val();
            var proId = $(preId+0).text();
            var result = IsDataOk(options,model);
            if(result===1){
                result = IsDataOk2(pid,proId, options,model);
                //数据冲突
                if(result===1){
                    //设置modal显示内容
                    setModalText(pid,proId,result,options,editor);
                    m1.show();
                }
                //数据和数据库一致，更新数据
                else if (result===0){
                    //写入
                    result = updateRecord(pid,proId,options,model,editor);
                }
                //数据库未存在该数据，新增数据
                else {
                    //写入
                    result = addRecord(pid,proId,options,model,editor);
                }
                //设置按钮为绿色
                if (result===1)$("#"+myId).attr("class","btn btn-success");
                $("#search-pingwei").val(pid);
                searchListener();
            }
        });

    }
}

//添加输入值改变监听
function generateInputChangeListener() {
    for (var i=1; i<=24;i++){
        var strId="#input"+i;

        for (var j =1;j<7;j++){
            $(strId+j).bind('input propertychange', function() {
                var myId=$(this).attr("id");
                var preId="#"+myId.substring(0,myId.length-1);
                var btId = preId+7;
                $(btId).attr("class","btn btn-danger")
            });
        }
    }
}
//重设所有输入框
function resetAllInput() {
    $("#reset-bt").click().on("click", function() {
        for (var i=1; i<=24;i++){
            var strId="#input"+i;
            for (var j =1;j<7;j++){
                var myId=strId+j;
                $(myId).val("");
            }
            $(strId+7).attr("class","btn btn-success")
        }
    });
}

function selectByPidAndModel(pid,editor,model){
    var url = "/pingweiScore/selectByPidAndEditorAndModel?pid="+pid+"&editor="+editor+"&model="+model;
    var result = null;
    $.get(url,function(data,status){
        //alert("数据: " + data + "\n状态: " + status);
        if(data.result===1) {
            result = data.pingweiScoreList;
        }
    });
    // var str ="return result="+result;
    // alert(str);
    return result;
}
//监听查询评委列表改变
function searchListener() {
    var pingwei = $("#search-pingwei").val();
    var model = getCookie("model");
    var editor = getCookie("user");
    $("#search-table").empty();
    var pingweiScoreList = selectByPidAndModel(pingwei,editor,model);
    if(pingweiScoreList!==null){
        $("#search-tips").text(pingweiScoreList.length);
        for (var i=0;i<pingweiScoreList.length;i++){
            var str ="<tr>"+
                "<td>"+pingweiScoreList[i].proId+"</td>"+
                "<td>"+pingweiScoreList[i].option1+"</td>"+
                "<td>"+pingweiScoreList[i].option2+"</td>"+
                "<td>"+pingweiScoreList[i].option3+"</td>"+
                "<td>"+pingweiScoreList[i].option4+"</td>"+
                "<td>"+pingweiScoreList[i].option5+"</td>"+
                "<td>"+pingweiScoreList[i].option6+"</td>"+
                "</tr>";
            $("#search-table").append(str);
        }
    }
}