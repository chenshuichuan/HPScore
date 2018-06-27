
// $.ajaxSettings.async = false;
$.ajaxSetup({
    async:true
});

// $('#final-score-tb').dataTable( {
//  "aaSorting": [[ 10, "desc" ]]
// } );
//
// $('#innovation-score-tb').dataTable( {
//   "aaSorting": [[ 10, "desc" ]]
// } );
// $('#useful-score-tb').dataTable( {
//   "aaSorting": [[ 10, "desc" ]]
// } );


$(function(){
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        // 获取已激活的标签页的名称
        var activeTab = $(e.target).text();
        // 获取前一个激活的标签页的名称
        var previousTab = $(e.relatedTarget).text();
        $(".active-tab span").html(activeTab);
        $(".previous-tab span").html(previousTab);
    });
});
function appendSpinner() {
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
function setTbBodyData() {
    appendSpinner();
    var model = getCookie("model");
    var url1 = "/score/selectRelativeAward?model=" + model;
    selectScoreByModel("#final-score-tb-body",url1);

    var url2 = "/score/selectInnovationAward?model=" + model;
    selectScoreByModel("#innovation-score-tb-body",url2);

    var url3 = "/score/selectUsefulAward?model=" + model;
    selectScoreByModel("#useful-score-tb-body",url3);
}
function setTbbody(tbBodyId,data) {

    $(tbBodyId).empty();
    $(tbBodyId+"-spinner").empty();

    for (var i=0;i<data.length;i++){
        var str="<tr>" +
            "<td>"+data[i].code+"</td>" +
            "<td>"+data[i].bianHao+"</td>" +
            "<td>"+data[i].name+"</td>" +
            "<td>"+data[i].partName+"</td>" +
            "<td>"+data[i].school+"</td>" +
            "<td>"+data[i].teachers+"</td>" +
            "<td>"+data[i].students+"</td>" +
            "<td>"+data[i].finalScore+"</td>" +
            "<td>"+data[i].ranking+"</td>" +
            "<td>"+"</td>" +
            "</tr>";
        $(tbBodyId).append(str);
    }
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

