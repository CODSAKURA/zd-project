function on() {
    //浏览器会自动缓存下一张图片，从而导致无论怎么刷新也都会刷新不出来新的图片，所以得在链接后面加不同的数据来表示不同的网页链接
    document.getElementById("checkCodeImage").src = "/zd_project_war/users/checkCode?" + new Date().getMilliseconds();
}

function checkUserValid() {
    //先隐藏错误提示信息
    document.getElementById("username_err").style.display = 'none'; //不展示
    document.getElementById("username_exist").style.display = 'none'; //不展示

    //获取用户名输入框的值
    var usernameInput = document.getElementById("username");
    var username = usernameInput.value.trim();

    //检查用户名是否为空
    if (username == "") {
        document.getElementById("username_err").style.display = 'none'; //不展示
        document.getElementById("username_exist").style.display = 'none'; //不展示
        return;
    }

    //以下代码判断用户名是否符合规则
    var reg = /^\w{6,12}$/;
    var flag = reg.test(username);
    if(flag == false){
        document.getElementById("username_err").style.display = '';  //展示
        document.getElementById("username_exist").style.display = 'none'; //不展示
        return;
    }

    //检查用户名是否已存在
    axios.get("http://localhost:8080/zd_project_war/users/" + username).then(function (resp) {
        //用户名不存在
        if (resp.data == false) {
            document.getElementById("username_err").style.display = 'none'; //不展示
            document.getElementById("username_exist").style.display = 'none'; //不展示
        }

        //用户名已存在
        if (resp.data == true) {
            document.getElementById("username_err").style.display = 'none'; //不展示
            document.getElementById("username_exist").style.display = ''; //展示
        }
    })
};

function checkPassword() {
    //先隐藏错误提示信息
    document.getElementById("password_err").style.display = 'none';

    //获取密码输入框的值
    var passwordInput = document.getElementById("password");
    var password = passwordInput.value.trim();

    //检查密码是否为空
    if (password == "") {
        document.getElementById("password_err").style.display = 'none'; //不展示
        return;
    }

    //以下代码判断密码是否符合规则
    var reg = /^\w{6,12}$/;
    var flag = reg.test(password);
    if(flag == false) {
        document.getElementById("password_err").style.display = '';  //展示
        return;
    }
}

// Function to handle form submission
function handleSubmit() {
    //判断输入框是否都填写正确
    var usernameExist = document.getElementById("username_err").style.display === 'none';
    var usernameValid = document.getElementById("username_exist").style.display === 'none';
    var passwordValid = document.getElementById("password_err").style.display === 'none';

    //如果都填写正确，则提交注册信息
    if (usernameExist && usernameValid && passwordValid) {
        var usernameInput = document.getElementById("username");
        var username = usernameInput.value.trim();

        var passwordInput = document.getElementById("password");
        var password = passwordInput.value.trim();

        var codeInput = document.getElementById("checkCode");
        var code = codeInput.value.trim();

        var formData = {
            username: "",
            password: "",
            code: ""
        };

        formData.username = username;
        formData.password = password;
        formData.code = code;

        axios({
            method: "POST",
            url: "http://localhost:8080/zd_project_war/users",
            data: formData
        }).then(function (resp) {
            // 注册成功
            if (resp.data == "register_success") {
                // 将数据存储到 sessionStorage 中
                sessionStorage.setItem("register_success", "register_success");

                // 打开login页面
                window.open("http://localhost:8080/zd_project_war/pages/login.html");
            }

            // 注册失败
            if (resp.data == "register_failed") {
                document.getElementById("registerFailedMsg").style.display = '';
            }
        })
    }
}

// Event bindings
document.getElementById("changeImg").onclick = on;
document.getElementById("checkCodeImage").onclick = on;
document.getElementById("username").onblur = checkUserValid;
document.getElementById("password").onblur = checkPassword;
document.getElementById("reg_btn").onclick = handleSubmit;