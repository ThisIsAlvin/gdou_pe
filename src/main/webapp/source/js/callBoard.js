/**
 * Created by viv on 16-5-12.
 */
var xmlhttp;
if (window.XMLHttpRequest) {
    xmlhttp = new XMLHttpRequest();
}else {
    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}
xmlhttp.onreadystatechange=function(){
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
        var json = xmlhttp.responseText;
        var data = JSON.parse(json);
        if (data.result == "success") {
            loadDate(data);
        }else {
            alert("出错了啊～～～:" + data.message);
        }
    }
}
xmlhttp.open("GET","/callBoard/show?json&pageIndex=0&type=0",true);
xmlhttp.send();

function loadDate(data){
    if (data.callBoards == null) {
        return;
    }

    switch (data.callBoards[0].type) {
        case 0:
            document.getElementById("board_cap").innerHTML="公共公告";
            break;
        case 1:
            break;
        case 2:
            break;
        case 3:
            break;
        default:
            break;
    }
    var board = document.getElementById("board");

    var content = null;
    var unixTimestamp = null;
    var commonTime = null;
    for(var i = 0; i < data.callBoards.length; i++) {
        unixTimestamp = new Date(data.callBoards[i].recent_time);
        commonTime = unixTimestamp.toLocaleString();
        if (content == null) {
            content ="<tr><td>"+data.callBoards[i].title+"</td><td>"+data.callBoards[i].message+"</td><td><img src='/source/upload/img/"+data.callBoards[i].imgs[0]+"'/></td><td>"+commonTime+"</td></tr>";
        }else {
            content += "<tr><td>"+data.callBoards[i].title+"</td><td>"+data.callBoards[i].message+"</td><td><img src='/source/upload/img/"+data.callBoards[i].imgs[0]+"'/></td><td>"+commonTime+"</td></tr>";

        }

    }
    board.innerHTML = content;

}