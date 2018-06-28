
// $.ajaxSettings.async = false;
$.ajaxSetup({
    async:true
});
function appendSpinnerToTable() {
    var str = " <!--预测参数 start-->\n" +
        "            <div class=\"row\" id=\"my-spinner\">\n" +
        "                <div class=\"col-md-12\">\n" +
        "                    <div style=\"color: #4c4ae1;text-align: center;\">正在加载 .... </div>\n" +
        "                    <div class=\"spinner\"></div>\n" +
        "                </div>\n" +
        "            </div>";
    $("#final-score-tb-body-spinner").append(str);
    $("#innovation-score-tb-body-spinner").append(str);
    $("#useful-score-tb-body-spinner").append(str);
}
//根据评委的个数，设置table的header头
function setHeader(pingweiSize) {
    var index=0;
    var strOrigin="<tr>" +
        "        <th>作品编号</th>" ;

    for (index=1;index<=pingweiSize;index++){
        strOrigin = strOrigin+
            "        <th>评委"+index+"</th>" ;
    }
    var strFinal="        <th>相对平均分</th>" +
        "        <th>名次1</th>" +
        "    </tr>";
    var strInnovation="        <th>创新平均分</th>" +
        "        <th>名次1</th>" +
        "    </tr>";
    var strUseful="        <th>实用平均分</th>" +
        "        <th>名次1</th>" +
        "    </tr>";
    $("#final-score-tb-header").append(strOrigin+strFinal);
    $("#innovation-score-tb-header").append(strOrigin+strInnovation);
    $("#useful-score-tb-header").append(strOrigin+strUseful);
}

function setTbbody(tbBodyId,data) {
    $(tbBodyId).empty();
    $(tbBodyId+"-spinner").empty();
    for (var i=0;i<data.length;i++){
        var index=0;
        var strOrigin="<tr>" +
            "<td>"+data[i].bianHao+"</td>" ;
        var pScores = data[i].pScores;
        for (index=0;index<pScores.length;index++){
            strOrigin = strOrigin+ "<td>"+pScores[index]+"</td>" ;
        }
        var str =strOrigin+ "<td>"+data[i].average+"</td><td>"+data[i].ranking+"</td>" +
            "</tr>";
        $(tbBodyId).append(str);
    }
}
function setTbBodyData() {
    appendSpinnerToTable();
    var model = getCookie("model");
    var url1 = "/score/calculteRelativeScoreAverageAndMaxAndMin?model=" + model;
    selectScoreByModel("#final-score-tb-body",url1);
    var url2 = "/score/selectInnovationScoreByModel?model=" + model;
    selectScoreByModel("#innovation-score-tb-body",url2);
    var url3 = "/score/selectUsefulScoreByModel?model=" + model;
    selectScoreByModel("#useful-score-tb-body",url3);
}
//
function selectScoreByModel(tbBodyId,url) {
    $.get(url, function (data, status) {
        //alert(data.result);
        if (data.result === 1) {
            //alert(data.message);
            //加载表格数据
            setTbbody(tbBodyId,data.scoreList);
        } else {
            //alert(data.message);
        }
    });
}

function countScore(model, editor) {
    //根据权限和model设置页面显示
    //禁用按钮
    var myspinner=document.getElementById("my-spinner");
    myspinner.style.display='block';
    $("#spinner-text").text("正在计算 ...");
    document.getElementById("count-bt").disabled=true;
    document.getElementById("export-excel1").disabled=true;
    document.getElementById("export-excel2").disabled=true;
    document.getElementById("export-excel3").disabled=true;
    var url = "/score/countScore?model=" + model + "&editor=" + editor;
    $.get(url, function (data, status) {
        //alert(data.result);
        if (data.result ===1) {
            alert(data.message);
            //加载表格数据
            //selectRelativeScoreByModel();
            window.location.reload();
        } else {

            alert(data.message);
        }
        document.getElementById("count-bt").disabled=false;
        document.getElementById("export-excel1").disabled=false;
        document.getElementById("export-excel2").disabled=false;
        document.getElementById("export-excel3").disabled=false;
        myspinner.style.display='none';
    });
}
//打分转换表
function getExcelByName(exportBtId,model, excelName) {
    // //alert("export-url open!");
    // $(exportBtId).attr("disabled",true);
    var myspinner=document.getElementById("my-spinner");
    myspinner.style.display='block';
    $("#spinner-text").text("正在加载 ...");
    document.getElementById("count-bt").disabled=true;
    document.getElementById("export-excel1").disabled=true;
    document.getElementById("export-excel2").disabled=true;
    document.getElementById("export-excel3").disabled=true;
    var url = "/score/generateExcelByFileAndModel?file="+excelName+"&model="+model;
    $.get(url, function (data, status) {
        //alert(data.result);
        if (data.result === 1) {
            //alert('获取Excel表成功!');
            window.open("/score/getExcel1?fileName="+data.fileName);
        } else {
            alert('获取Excel表失败!');
        }
        //$(exportBtId).attr("disabled",false);
        document.getElementById("count-bt").disabled=false;
        document.getElementById("export-excel1").disabled=false;
        document.getElementById("export-excel2").disabled=false;
        document.getElementById("export-excel3").disabled=false;
        myspinner.style.display='none';
    });
}

//按键监听
function setClickListener() {
    var editor = getCookie("user");
    var model = getCookie("model");

    //计算请求监听
    $("#count-bt").click(function (e) {
        countScore(model, editor);
    });
    var export1="#export-excel1";
    var export2="#export-excel2";
    var export3="#export-excel3";
    //打分转换表
    $(export1).click(function () {
        getExcelByName(export1,model, "打分转换表");
    });
    $(export2).click(function () {
        getExcelByName(export2,model, "打分统计表");
    });
    $(export3).click(function () {
        getExcelByName(export3,model, "平均分统计表");
    });

}
