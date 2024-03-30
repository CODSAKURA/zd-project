function on() {
    //浏览器会自动缓存下一张图片，从而导致无论怎么刷新也都会刷新不出来新的图片，所以得在链接后面加不同的数据来表示不同的网页链接
    document.getElementById("checkCodeImage").src = "/zd-project/user/checkCode?" + new Date().getMilliseconds();
}

function checkUserValid() {
    var usernameInput = document.getElementById("username");
    var username = usernameInput.value.trim();

    /*以下代码判断用户名是否符合规则*/
    var reg = /^\w{6,12}$/;
    var flag = reg.test(username);

    if (flag) {
        document.getElementById("username_err").style.display = 'none';
    } else {
        document.getElementById("username_err").style.display = '';
    }
    ;

    //POST简化起别名方法
    axios.post("http://localhost:8080/zd-project/user/selectUser", "username=" + username).then(function (resp) {
        if (resp.data == true) {
            document.getElementById("username_exist").style.display = 'none';
        } else {
            document.getElementById("username_exist").style.display = '';
        }
    })
};

function checkPassword() {
    var passwordInput = document.getElementById("password");
    var password = passwordInput.value.trim();
    var reg = /^\w{6,12}$/;
    var flag = reg.test(password);

    if (flag) {
        document.getElementById("password_err").style.display = 'none';
    } else {
        document.getElementById("password_err").style.display = '';
    }
}

// Function to handle form submission
function handleSubmit() {
    var usernameExist = document.getElementById("username_err").style.display === 'none';
    var usernameValid = document.getElementById("username_exist").style.display === 'none';
    var passwordValid = document.getElementById("password_err").style.display === 'none';

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
            url: "http://localhost:8080/zd-project/user/register",
            data: formData
        }).then(function (resp) {
            // 注册成功
            if (resp.data == "register_success") {
                // 将数据存储到 sessionStorage 中
                sessionStorage.setItem("register_success", "register_success");

                // 打开login页面
                window.open("http://localhost:8080/zd-project/login.html");
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