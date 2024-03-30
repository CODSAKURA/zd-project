window.onload = function () {
    // 从 sessionStorage 中获取数据
    var receivedData = sessionStorage.getItem("register_success");

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

    var formData = {
        username: "",
        password: ""
    };

    formData.username = username;
    formData.password = password;

    axios({
        method: "POST",
        url: "http://localhost:8080/zd-project/user/login",
        data: formData
    }).then(function (resp) {
        // 登录成功
        if (resp.data == "login_success") {
            location.href = "http://localhost:8080/zd-project/brand.html";
        }

        // 登录失败
        if (resp.data == "login_failed") {
            document.getElementById("loginErrorMsg").style.display = '';
        }
    })
};

document.getElementById("reset").onclick = reset;
document.getElementById("submission").onclick = handleSubmit;