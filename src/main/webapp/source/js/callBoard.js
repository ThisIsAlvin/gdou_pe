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
    for(var i = 0; i < data.callBoards.length; i++) {
        if (content == null) {
            content ="<tr><td>"+data.callBoards[i].title+"</td><td>"+data.callBoards[i].message+"</td><td><img src='/source/img/"+data.callBoards[i].img+"'/></td><td>"+data.callBoards[i].recent_time+"</td></tr>";
        }else {
            content += "<tr><td>"+data.callBoards[i].title+"</td><td>"+data.callBoards[i].message+"</td><td><img src='/source/img/"+data.callBoards[i].img+"'/></td><td>"+data.callBoards[i].recent_time+"</td></tr>";

        }

    }
    board.innerHTML = content;

/*    var tr;
    var td;
    var title;
    var message;
    var img;
    var time;
    var anode;
    for(var i = 0; i < data.callBoards.length; i++) {
        tr = document.createElement("tr");

        td = document.createElement("td");
        message = document.createElement("a");
        anode = document.createTextNode(data.callBoards[i].message);
        message.appendChild(anode);
        td.appendChild(message);
        tr.appendChild(td);

        td = document.createElement("td");
        time = document.createElement("p");
        anode = document.createTextNode(data.callBoards[i].recent_time);
        time.appendChild(anode);
        td.appendChild(time);
        tr.appendChild(td);

        td = document.createElement("td");
        title = document.createElement("p");
        title.innerHTML = ""


        board.appendChild(tr);
    }

    img = document.createElement("img");
    img.src = "../../source/img/2.png";
    board.appendChild(img);*/


}