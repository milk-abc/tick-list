<template>
  <div class="login-container">
    <el-form ref="loginFormRef"
             :model="loginForm"
             :rules="loginRules"
             class="login-form"
             auto-complete="on"
             label-position="left">
      <div class="title-container">
        <h3 class="title">Tick-List</h3>
      </div>
      <div class="inputForm">
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
                    :type="passwordType"
                    tabindex="2"
                    auto-complete="on" />
          <span class="show-pwd"
                @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </el-form-item>
      </div>
      <div class="securImg">
        <el-form-item class="lastItem"
                      prop="securityCodeValue">
          <el-input ref="securityCodeValue"
                    v-model="loginForm.securityCodeValue"
                    placeholder="验证码"
                    name="securityCodeValue"
                    tabindex="2" />
        </el-form-item>
        <img id="img"
             @click="getCode"
             :src="imgSrc" />
      </div>
      <div class="btn">
        <el-button type="text"
                   @click="$router.push('/register')">注册</el-button>
        <el-button type="text"
                   @click="$router.push('/forgetword')">忘记密码</el-button>
      </div>
      <el-button type="primary"
                 class="loginBtn"
                 @click="submitForm()">登录</el-button>
    </el-form>

  </div>
</template>

<script lang="ts" setup name="login">
import { onMounted, reactive, ref, nextTick } from 'vue'
import service from '@/axios'
import router from '@/router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { encrypt, decrypt } from '@/utils/jsencrypt'
const store = useStore()
const imgRef = ref(null)
const imgSrc = ref('')
const password = ref(null)
const loginFormRef = ref(null)
const loginForm = reactive({
  username: 'admin',
  password: '123456',
  securityCodeKey: '',
  securityCodeValue: ''
})
const loginRules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
  ]
})
const passwordType = ref('password')
onMounted(() => {
  getCode()
})
function getCode() {
  let xhr = new XMLHttpRequest()
  xhr.open('get', `${service.defaults.baseURL}/getSecurityCode`, true)
  xhr.responseType = 'blob'
  xhr.send(null)
  xhr.onreadystatechange = () => {
    if (xhr.readyState === 4) {
      if (xhr.status == 200) {
        let URL = window.URL || window.webkitURL
        let url = URL.createObjectURL(new Blob([xhr.response]))
        imgSrc.value = url
        loginForm.securityCodeKey = xhr.getResponseHeader('Securitycode')
      }
    }
  }
}
function showPwd() {
  if (passwordType.value === 'password') {
    passwordType.value = ''
  } else {
    passwordType.value = 'password'
  }
  nextTick(() => {
    password.value.focus()
  })
}
function debounce(fn, delay) {
  //加入防抖优化
  let timer
  return function () {
    clearTimeout(timer)
    timer = setTimeout(() => {
      fn(...arguments)
    }, delay)
  }
}
function login(jseForm) {
  service.post('/login', jseForm).then((res) => {
    const jwt = res.headers['authorization']
    const userInfo = res.data.user
    console.log('userInfo', userInfo)
    store.commit('SET_TOKEN', jwt)
    store.commit('SET_USERINFO', userInfo)
    // 登录之后，给axios统一设置头部token信息
    service.defaults.headers.common['Authorization'] = localStorage.getItem('token')
    router.push('/layout')
  })
}
function submitForm(formName) {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      service.get('/getPublicKey').then((res) => {
        let jsePassword = encrypt(loginForm.password, res.data.msg)
        let jseForm = Object.assign({}, loginForm, {
          password: jsePassword
        })
        login(jseForm)
      })
    } else {
      ElMessage({
        type: 'error',
        message: '用户登录：提交错误！！'
      })
      return false
    }
  })
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

    .el-input__wrapper {
      background: #283443;
    }

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
  .securImg {
    width: 100%;
    display: flex;
    .lastItem {
      flex: 1 1 auto;
    }
    #img {
      flex: 0 0 30%;
      height: 47px;
    }
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

    .btn {
      margin-top: -10px;
      text-align: justify;
      width: 100%;
      &:after {
        content: '';
        display: inline-block;
        width: 100%;
      }
    }
    .loginBtn {
      display: block;
      width: 100%;
      margin-top: -20px;
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