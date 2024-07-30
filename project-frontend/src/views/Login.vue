<!--页面展示【类似于html文件】-->
<template>
  <el-row type="flex" justify="center">
    <el-col>
      <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="demo-loginForm">
        <h1 id="loginMsg">用户登录</h1>
        <div id="loginErrorMsg" style="display: none">用户名或密码错误</div>
        <div id="registerSuccessMsg" style="display: none">注册成功，请登录</div>

        <el-form-item class="label" label="用户名：" prop="username">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>

        <el-form-item class="label" label="密码：" prop="password">
          <el-input v-model="loginForm.password"></el-input>
        </el-form-item>

        <el-form-item id="subDiv">
          <el-button class="button" @click="submitForm('loginForm')">登录</el-button>
          <el-button class="button" @click="resetForm('loginForm')">重置</el-button>
          <div class="spacing"></div>
          <a class="register-link" href="/register">没有账号？</a>
        </el-form-item>

      </el-form>

    </el-col>
  </el-row>
</template>

<!--页面功能【类似于js文件】-->
<script>
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
        ]
      },
      loginErrorMsg: false,
      registerSuccessMsg: false
    };
  },
  methods: {
    submitForm() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // 处理提交逻辑
          alert('submit!');
        } else {
          this.loginErrorMsg = true;
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm() {
      this.$refs.loginForm.resetFields();
    }
  }
}
</script>

<!--页面美化【类似于css文件】-->
<style scoped>
    /* 背景样式：整个 el-row 的背景图像设置 */
    .el-row {
      background-image: url("../assets/bg_login.jpg"); /* 背景图片 */
      background-repeat: no-repeat; /* 图片不重复 */
      background-size: 100% 100%; /* 图片拉伸到整个容器 */
      position: fixed; /* 固定位置，覆盖整个视口 */
      height: 100%; /* 高度占满整个视口 */
      width: 100%; /* 宽度占满整个视口 */
      display: flex; /* 使用 Flexbox 布局 */
      align-items: center; /* 垂直居中对齐内容 */
      text-align: center; /* 水平居中对齐内容 */
    }

    /* 样式：el-col 的设置，用于定位和样式化登录框 */
    .el-col {
      width: 37%; /* 宽度占父容器的 37% */
      display: flex; /* 使用 Flexbox 布局 */
      align-items: center; /* 垂直居中对齐内容 */
      justify-content: center; /* 水平居中对齐内容 */
      height: 380px; /* 固定高度 */
      background-color: rgba(75, 81, 95, 0.3); /* 背景颜色及透明度 */
      box-shadow: 7px 7px 17px rgba(52, 56, 66, 0.5); /* 阴影效果 */
      border-radius: 5px; /* 圆角边框 */
      padding-right: 45px; /* 右边距 */
      padding-top: 20px; /* 上边距 */
    }

    /* 样式：el-input 的自定义样式 */
    .el-input /deep/ .el-input__inner {
      background-color: rgba(255, 255, 255, 0.247); /* 输入框背景颜色及透明度 */
    }

    /* 样式：按钮样式 */
    .button {
      border-color: cornsilk; /* 按钮边框颜色 */
      background-color: rgba(100, 149, 237, .7); /* 按钮背景颜色及透明度 */
      color: aliceblue; /* 按钮文字颜色 */
      border-style: hidden; /* 隐藏边框 */
      border-radius: 5px; /* 圆角边框 */
      width: 100px; /* 按钮宽度 */
      height: 31px; /* 按钮高度 */
      font-size: 16px; /* 文字大小 */
      line-height: 9px; /* 行高 */
    }

    /* 样式：表单标签样式 */
    .label {
      margin-top: 30px; /* 上边距 */
      margin-left: 20px; /* 左边距 */
    }

    /* 给当前页面所有的label字体设置样式 */
    /deep/ .el-form-item__label{
      color: azure; /* 标签文字颜色 */
      font-size: 17px; /* 标签字体大小，适当设置 */
    }

    /* 样式：按钮和“没有账号”链接之间的间距 */
    .spacing {
      display: inline-block; /* 使元素在一行内显示 */
      width: 15px; /* 间距宽度 */
    }

    /* 样式：“没有账号”链接的样式 */
    .register-link {
      text-align: center; /* 文字居中对齐 */
      color: blue; /* 文字颜色 */
      font-size: 17px; /* 字体大小 */
    }

    /* 样式：登录消息的样式 */
    #loginMsg {
      text-align: center; /* 文字居中对齐 */
      color: aliceblue; /* 文字颜色 */
    }

    /* 样式：表单项容器的样式 */
    #subDiv {
      text-align: center; /* 文字居中对齐 */
      margin-top: 30px; /* 上边距 */
    }

    /* 样式：错误消息的样式 */
    #loginErrorMsg {
      text-align: center; /* 文字居中对齐 */
      color: red; /* 文字颜色 */
    }

    /* 样式：注册成功消息的样式 */
    #registerSuccessMsg {
      text-align: center; /* 文字居中对齐 */
      color: red; /* 文字颜色 */
    }
</style>
