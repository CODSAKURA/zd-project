window.onload = function () {
    // 从 sessionStorage 中获取数据
    var receivedData = sessionStorage.getItem("register_success");

    // TODO 更改获取后端数据方式
    if(receivedData == "register_success"){
        document.getElementById("registerSuccessMsg").style.display = '';
    }
};

function reset(){
    document.getElementById("username").value = '';
    document.getElementById("password").value = '';
}

function handleSubmit() {
    var usernameInput = document.getElementById("username");
    var username = usernameInput.value.trim();

    var passwordInput = document.getElementById("password");
    var password = passwordInput.value.trim();

    axios({
        method: "GET",
        url: "http://localhost:8080/zd_project_war/users/username/" + username + "/password/" + password
    }).then(function (resp) {
        // 登录成功 TODO 更改获取后端数据方式
        if (resp.data == "login_success") {
            location.href = "../pages/brand.html";
        }

        // 登录失败 TODO 更改获取后端数据方式
        if (resp.data == "login_failed") {
            document.getElementById("registerSuccessMsg").style.display = 'none';
            document.getElementById("loginErrorMsg").style.display = '';
        }
    })
};

document.getElementById("reset").onclick = reset;
document.getElementById("submission").onclick = handleSubmit;