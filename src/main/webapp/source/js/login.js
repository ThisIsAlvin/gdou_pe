/**
 * Created by viv on 16-5-12.
 */
var xmlhttp;
if (window.XMLHttpRequest) {
    xmlhttp = new XMLHttpRequest();
}else{
    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

function onlogin(){
    var name = document.getElementById("name").value;
    var password = document.getElementById("password").value;
    if (name.value == null) {
        alert("用户名不能为空");
        return;
    }
    if (password.value == null) {
        alert("密码不能为空");
        return;
    }
    var usr = document.getElementById("form");

    xmlhttp.open("POST","/visitor/login?json",false);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");

    xmlhttp.send("name="+name+"&password="+password);

    var json = xmlhttp.responseText;
    var data = JSON.parse(json);
    if (data.result == "success") {
        if (data.message == "student") {
            alert("ok! " + data.message);
        }else if (data.message == "admin") {
            alert("ok! " + data.message);
        }else {
            alert("出错了啊: "+data.message)
        }
    }else {
        alert("出错啦～～～：" + data.message);
    }

}