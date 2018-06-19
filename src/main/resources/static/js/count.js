
// $.ajaxSettings.async = false;
$.ajaxSetup({
    async:false
});

function setTbbody(data) {
    $("#tb-body").empty();
    for (var i=0;i<data.length;i++){
        var str = "<tr>\n" +
            "<td>"+data[i].proId+"</td>\n" +
            "<td>"+data[i].proName+"</td>\n" +
            "<td>"+data[i].pScore1+"</td>\n" +
            "<td>"+data[i].pScore2+"</td>\n" +
            "<td>"+data[i].pScore3+"</td>\n" +
            "<td>"+data[i].pScore4+"</td>\n" +
            "<td>"+data[i].pScore5+"</td>\n" +
            "<td>"+data[i].pScore6+"</td>\n" +
            "<td>"+data[i].pScore7+"</td>\n" +
            "<td>"+data[i].pScore8+"</td>\n" +
            "<td>"+data[i].pScore9+"</td>\n" +
            "<td>"+data[i].pScore10+"</td>\n" +
            "<td>"+data[i].pScore11+"</td>\n" +
            "<td>"+data[i].maxScore+"</td>\n" +
            "<td>"+data[i].minScore+"</td>\n" +
            "<td>"+data[i].average+"</td>\n" +
            "</tr>";
        $("#tb-body").append(str);
    }
}

function selectRelativeScoreByModel() {
    var model = getCookie("model");
    var url = "/score/selectRelativeScoreByModel?model=" + model;
    $.get(url, function (data, status) {
        alert(data.result);
        if (data.result == "1") {
            alert(data.message);
            //加载表格数据
            setTbbody(data.relativeScores);
        } else {
            alert(data.message);
        }
    });
}
function countScore(model, editor) {
    var url = "/score/countScore?model=" + model + "&editor=" + editor;

    $.get(url, function (data, status) {
        alert(data.result);
        if (data.result == "1") {
            alert(data.message);
            //加载表格数据
            selectRelativeScoreByModel();
        } else {
            alert(data.message);
        }
    });
}

$("#count-bt").click(function (e) {
    //根据权限和model设置页面显示
    //禁用按钮
    document.getElementById("count-bt").disabled=true;
    var editor = getCookie("user");
    var model = getCookie("model");
    countScore(model, editor);
    document.getElementById("count-bt").disabled=false;
});


function countRelativeScore(model) {
    var url = "/score/calculteRelativeScoreAverageAndMaxAndMin?model=" + model;

    $.get(url, function (data, status) {
        //alert(data.result);
        if (data.result == "1") {
            alert(data.message);
            //加载表格数据
            selectRelativeScoreByModel();
        } else {
            alert(data.message);
        }
    });
}
$("#count-relative-bt").click(function (e) {
    //根据权限和model设置页面显示
    //禁用按钮
    document.getElementById("count-relative-bt").disabled=true;
    var model = getCookie("model");
    countRelativeScore(model);
    document.getElementById("count-relative-bt").disabled=false;
});