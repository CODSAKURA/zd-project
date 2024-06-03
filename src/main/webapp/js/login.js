// 由于注册成功会跳转回login页面，所以要冲session中获取是否有注册成功表示。
window.onload = function () {
    // 从 sessionStorage 中获取数据
    var receivedData = sessionStorage.getItem("register_success");

    if(receivedData == "register_success"){
        document.getElementById("registerSuccessMsg").style.display = '';
        document.getElementById("loginErrorMsg").style.display = 'none';
    }
};

// 重置按钮所对应的方法
function reset(){
    document.getElementById("username").value = '';
    document.getElementById("password").value = '';
}

// 登录所对应的方法
function handleSubmit() {
    var usernameInput = document.getElementById("username");
    var username = usernameInput.value.trim();

    var passwordInput = document.getElementById("password");
    var password = passwordInput.value.trim();

    axios({
        method: "GET",
        url: "http://localhost:8080/zd_project_war/users/username/" + username + "/password/" + password
    }).then(function (resp) {
        if (resp.data.code == 20021) {
            location.href = "../pages/brand.html";
        }

        if (resp.data.code == 20020) {
            document.getElementById("registerSuccessMsg").style.display = 'none';
            document.getElementById("loginErrorMsg").style.display = '';
        }
    })
};

// 将按钮与方法名绑定
document.getElementById("reset").onclick = reset;
document.getElementById("submission").onclick = handleSubmit;