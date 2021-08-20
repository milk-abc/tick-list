<template>
  <div class="register-container">
    <el-form ref="registerForm"
             :model="registerForm"
             :rules="registerRules"
             class="register-form"
             auto-complete="on"
             label-position="left">
      <div class="title-container">
        <h3 class="title">注册</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <i class="el-icon-user"></i>
        </span>
        <el-input ref="username"
                  v-model="registerForm.username"
                  placeholder="用户名"
                  name="username"
                  type="text"
                  tabindex="1" />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <i class="el-icon-lock"></i>
        </span>
        <el-input ref="password"
                  v-model="registerForm.password"
                  placeholder="密码"
                  name="password"
                  :type="passwordType"
                  tabindex="2"
                  auto-complete="on"
                  @keyup.enter.native="handleregister" />
        <span class="show-pwd"
              @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <div class="btngroup">
        <el-button type="primary"
                   class="registerBtn"
                   @click="submitForm('registerForm')">注册</el-button>
        <el-button type="text"
                   @click="$router.push('/login')">登录</el-button>
      </div>
    </el-form>

  </div>
</template>

<script>
import { encrypt, decrypt } from '@/utils/jsencrypt'
export default {
  name: 'register',
  components: {},
  data () {
    //这里存放数据
    return {
      registerForm: {
        username: '',
        password: ''
      },
      registerRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
      },
      passwordType: 'password',
    };
  },
  methods: {
    showPwd () {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      });
    },
    register (jseForm) {
      const _this = this;
      this.$axios.post('/register', jseForm).then(res => {
        this.$axios.post('/login', jseForm).then(res => {
          const jwt = res.headers['authorization']
          const userInfo = res.data.user
          console.log('userInfo', userInfo)
          _this.$store.commit('SET_TOKEN', jwt)
          _this.$store.commit("SET_USERINFO", userInfo)
          // 登录之后，给axios统一设置头部token信息
          this.$axios.defaults.headers.common['Authorization'] = localStorage.getItem('token')
          _this.$router.push('/layout')
        })
      })
    },
    submitForm (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$axios.get('/getPublicKey').then(res => {
            let jsePassword = encrypt(this.registerForm.password, res.data.msg);
            let jseForm = {
              username: this.registerForm.username,
              password: jsePassword
            }
            this.register(jseForm);
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
  .register-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.register-container {
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
.register-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .register-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
    .btngroup {
      text-align: center;
      .registerBtn {
        display: block;
        width: 100%;
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