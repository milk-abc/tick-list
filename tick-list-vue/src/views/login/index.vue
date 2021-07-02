<template>
  <div class="login-container">
    <el-form ref="loginForm"
             :model="loginForm"
             :rules="loginRules"
             class="login-form"
             auto-complete="on"
             label-position="left">
      <div class="title-container">
        <h3 class="title">Tick-List</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <i class="el-icon-user"></i>
        </span>
        <el-input ref="username"
                  v-model="loginForm.username"
                  placeholder="用户名"
                  name="username"
                  type="text"
                  tabindex="1"
                  auto-complete="on" />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <i class="el-icon-lock"></i>
        </span>
        <el-input ref="password"
                  v-model="loginForm.password"
                  placeholder="密码"
                  name="password"
                  tabindex="2"
                  auto-complete="on"
                  @keyup.enter.native="handleLogin" />
      </el-form-item>
      <div style="margin-bottom: 30px">
      </div>
      <el-button type="primary"
                 style="width:100%;margin-bottom:30px;"
                 @click="submitForm('loginForm')">登录</el-button>
    </el-form>
  </div>
</template>

<script>

export default {
  name: 'login',
  components: {},
  data () {
    //这里存放数据
    return {
      loginForm: {
        username: 'admin',
        password: '123456'
      },
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
      },
    };
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const _this = this
          this.$axios.post('/login', this.loginForm).then(res => {

            const jwt = res.headers['authorization']
            const userInfo = res.data.user
            console.log('userInfo', userInfo)
            _this.$store.commit('SET_TOKEN', jwt)
            _this.$store.commit("SET_USERINFO", userInfo)
            // 登录之后，给axios统一设置头部token信息
            this.$axios.defaults.headers.common['Authorization'] = localStorage.getItem('token')
            _this.$router.push('/layout')
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created () {
  }
}
</script>
<style lang="scss">
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;
$dark_gray: #889aa4;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>
<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;

    .forget {
      display: block;
      float: right;
      margin-bottom: 10px;
      font-size: 14px;
      color: #fff;
    }

    .register {
      width: 154px;
      height: 20px;
      font-size: 14px;
      color: #ffffff;
      line-height: 20px;
      float: right;

      a {
        color: #437db7;
      }
    }
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-top: 40px;
    text-align: center;
    overflow: hidden;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 20px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>