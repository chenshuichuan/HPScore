
$.ajaxSettings.async = false;

function addRecord(pid,jqInputs,model,editor) {
    var index_begin=1;
    $.post("/score/add",
        {
            "pid": pid,
            "proId": jqInputs[index_begin].value,
            "option1": jqInputs[index_begin+1].value,
            "option2": jqInputs[index_begin+2].value,
            "option3": jqInputs[index_begin+3].value,
            "option4": jqInputs[index_begin+4].value,
            "option5": jqInputs[index_begin+5].value,
            "option6": jqInputs[index_begin+6].value,
            "model": model,
            "editor": editor
        },
        function(data,status){
            //alert(data.result);
            if (data.result == "1") {
                alert(data.message);
            } else {
                alert(data.message);
            }
        });
}
function addRecordGet(pid, jqInputs, model, editor) {
    var url = "/score/add_get?" + "pid=" + pid + "&proId=" +
        jqInputs[1].value + "&option1=" + jqInputs[2].value + "&option2=" + jqInputs[3].value + "&option3=" + jqInputs[4].value +
        "&option4=" + jqInputs[5].value + "&option5=" + jqInputs[6].value + "&option6=" + jqInputs[7].value +
        "&model=" + model + "&editor=" + editor;

    $.get(url, function (data, status) {
        alert(data.result);
        if (data.result == "1") {
            alert('添加数据成功!');
        } else {
            alert('添加数据失败!');
        }
    });
}
function setModalText(pid,data,jqInputs,editor) {

    $('#pingwei2').text(data.pid);
    $('#works2').text(data.proId);
    $('#option21').text(data.option1);
    $('#option22').text(data.option2);
    $('#option23').text(data.option3);
    $('#option24').text(data.option4);
    $('#option25').text(data.option5);
    $('#option26').text(data.option6);
    $('#editor1').text(data.editor1);

    var index_begin=1;
    var proId = jqInputs[index_begin].value;
    var option1=parseInt(jqInputs[index_begin+1].value);
    var option2=parseInt(jqInputs[index_begin+2].value);
    var option3=parseInt(jqInputs[index_begin+3].value);
    var option4=parseInt(jqInputs[index_begin+4].value);
    var option5=parseInt(jqInputs[index_begin+5].value);
    var option6=parseInt(jqInputs[index_begin+6].value);


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
function IsDataOk(pid, jqInputs,model) {
    // var option1=jqInputs[1].value;
    // var option2=jqInputs[2].value;
    // var option3=jqInputs[3].value;
    // var option4=jqInputs[4].value;
    // var option5=jqInputs[5].value;
    // var option6=jqInputs[6].value;
    var benke = [10,15,20,20,20,15];
    var gaozhi = [10,15,10,25,25,15];
    var temp = [];
    var result = 1;
    if(pid.length<1)result=0;

    if(model==="本科组"){
        temp = benke;
    }
    else temp = gaozhi;

    for (var i = 1;i<7;i++){
        if(jqInputs[i+1].value.length<1)result=0;
        var value =parseInt(jqInputs[i+1].value);
        if(value>temp[i]||value<=0){
            result=0;
        }
    }
    if(result===0)alert("数据不合法！请重新输入！");
    return result;
}
//第一个编辑就是editor，第二个编辑为空时，不可以
//冲突不可以 冲突则返回冲突数据，否则返回null
function IsDataOk2(pid, jqInputs,model,editor){
    var index_begin=1;
    var option1=parseInt(jqInputs[index_begin+1].value);
    var option2=parseInt(jqInputs[index_begin+2].value);
    var option3=parseInt(jqInputs[index_begin+3].value);
    var option4=parseInt(jqInputs[index_begin+4].value);
    var option5=parseInt(jqInputs[index_begin+5].value);
    var option6=parseInt(jqInputs[index_begin+6].value);
    var url = "/score/selectByPidAndProIdAndModel?pid="+pid+"&proId="+jqInputs[index_begin].value+"&model="+model;

    var result = null;
    $.get(url,function(data,status){
        alert("数据: " + data + "\n状态: " + status);
        if(data.result===1){
            var score = data.score;
            if(score!==null&&score.proId!==null&&score.proId.length>0){
                //没有做同二检查，因为数据通过，则同一个账号录入的数据总是只占有一个editor
                // if(data.editor2===null){
                // }
                // //该editor已经录过一次了，可作为修改添加处理添加
                // if(data.editor1===editor||data.editor2===editor){
                //     alert("您已经录过一次了本次作为修改数据！");
                // }
                if (score.option1!==option1||score.option2!==option2||score.option3!==option3||
                    score.option4!==option4||score.option5!==option5||score.option6!==option6){
                    alert("数据不一致！");
                    result=score;
                }
            }
        }

    });
    var str ="return result="+result;
    alert(str);
    return result;
}

var EditableTable = function () {

    return {

        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[0].innerHTML = '<input type="text" readonly class="form-control small" value="' + aData[0] + '">';
                jqTds[1].innerHTML = '<input type="text" class="form-control small" value="' + aData[1] + '">';
                jqTds[2].innerHTML = '<input type="text" class="form-control small" value="' + aData[2] + '">';
                jqTds[3].innerHTML = '<input type="text" class="form-control small" value="' + aData[3] + '">';
                jqTds[4].innerHTML = '<input type="text" class="form-control small" value="' + aData[4] + '">';
                jqTds[5].innerHTML = '<input type="text" class="form-control small" value="' + aData[5] + '">';
                jqTds[6].innerHTML = '<input type="text" class="form-control small" value="' + aData[6] + '">';
                jqTds[7].innerHTML = '<input type="text" class="form-control small" value="' + aData[7] + '">';

                jqTds[8].innerHTML = '<a class="edit" href="">Save</a>';
                jqTds[9].innerHTML = '<a class="cancel" href="">Cancel</a>';
            }

            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                var pid = $("#select-pingwei").val();
                //var bar_text=document.getElementById("bar-text");
                var model = getCookie("model");
                var editor = getCookie("user");
                //检查数据合法性
                var result = IsDataOk(pid,jqInputs,model);
                //数据格式合法
                if(result===1){
                    //检测数据是否冲突
                    var data = IsDataOk2(pid, jqInputs,model,editor);
                    //数据冲突
                    if(data !==null){
                        //设置modal显示内容
                        setModalText(pid,data,jqInputs,editor);
                        m1.show();
                        var pid = $('#select-pingwei2').val();
                        var proId = $('#select-works2').val();
                        var option1 = $('#option31').val();
                        var option2 = $('#option32').val();
                        var option3 = $('#option33').val();
                        var option4 = $('#option34').val();
                        var option5 = $('#option35').val();
                        var option6 = $('#option36').val();

                        $('#select-pingwei').val(pid);
                        oTable.fnUpdate("", nRow, 0, false);

                        oTable.fnUpdate(proId, nRow, 1, false);
                        oTable.fnUpdate(option1, nRow, 2, false);
                        oTable.fnUpdate(option2, nRow, 3, false);
                        oTable.fnUpdate(option3, nRow, 4, false);
                        oTable.fnUpdate(option4, nRow, 5, false);
                        oTable.fnUpdate(option5, nRow, 6, false);
                        oTable.fnUpdate(option6, nRow, 7, false);
                        oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 8, false);
                        oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 9, false);
                        oTable.fnDraw();
                        nEditing = null;

                    }
                    //数据没有问题(数据格式没问题，属于新增数据)开始写入后台
                    else {
                        alert("数据没有问题(数据格式没问题，属于新增数据)开始写入后台")
                        addRecord(pid,jqInputs,model,editor);
                        oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);

                        oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                        oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                        oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                        oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
                        oTable.fnUpdate(jqInputs[5].value, nRow, 5, false);
                        oTable.fnUpdate(jqInputs[6].value, nRow, 6, false);
                        oTable.fnUpdate(jqInputs[7].value, nRow, 7, false);

                        oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 8, false);
                        oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 9, false);
                        oTable.fnDraw();
                        nEditing = null;
                    }
                }
            }

            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);

                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
                oTable.fnUpdate(jqInputs[5].value, nRow, 5, false);
                oTable.fnUpdate(jqInputs[6].value, nRow, 6, false);
                oTable.fnUpdate(jqInputs[7].value, nRow, 7, false);

                oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 8, false);
                oTable.fnDraw();
            }

            var oTable = $('#editable-sample').dataTable({
                "aLengthMenu": [
                    [10, 15, 20, -1],
                    [24, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 24,
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ 条记录每页",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0]
                    }
                ]
            });

            jQuery('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
            jQuery('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

            var nEditing = null;

            $('#editable-sample_new').click(function (e) {
                e.preventDefault();
                var aiNew = oTable.fnAddData(['','', '', '', '','','','',
                        '<a class="edit" href="">Edit</a>', '<a class="cancel" data-mode="new" href="">Cancel</a>'
                ]);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                editRow(oTable, nRow);
                nEditing = nRow;
            });

            $('#editable-sample a.delete').live('click', function (e) {
                e.preventDefault();

                if (confirm("Are you sure to delete this row ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
                alert("成功移除! 该行数据仅从页面移除 :)");
            });

            $('#editable-sample a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#editable-sample a.edit').live('click', function (e) {
                e.preventDefault();

                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];

                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    /* Editing this row and want to save it */
                    saveRow(oTable, nEditing);
                    //alert("保存成功! Do not forget to do some ajax to sync with backend :)");
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });
        }

    };

}();