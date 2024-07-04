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

    // 获取当前页面的基本URL部分
    var baseUrl = window.location.origin;

    // 拼接完整的URL
    var url = baseUrl + "/zd_project_war/users/username/" + username + "/password/" + password;

    axios({
        method: "GET",
        url: url,
        withCredentials: true
    }).then(function (resp) {
        if (resp.data.code == 20021) {
            location.href = "../pages/brand.html";
        }

        if (resp.data.code == 20020) {
            document.getElementById("registerSuccessMsg").style.display = 'none';
            document.getElementById("loginErrorMsg").style.display = '';
        }
    }).catch(function (error) {
        console.error("There was an error with the request:", error);
    });
};

// 将按钮与方法名绑定
document.getElementById("reset").onclick = reset;
document.getElementById("submission").onclick = handleSubmit;
