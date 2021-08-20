<template>
  <div class="forget-container">
    <el-form ref="forgetForm"
             :model="forgetForm"
             :rules="forgetRules"
             class="forget-form"
             auto-complete="on"
             label-position="left">
      <div class="title-container">
        <h3 class="title">重置密码</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <i class="el-icon-user"></i>
        </span>
        <el-input ref="username"
                  v-model="forgetForm.username"
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
                  v-model="forgetForm.oldpassword"
                  placeholder="旧密码"
                  name="password"
                  :type="passwordType"
                  tabindex="2"
                  auto-complete="on" />
        <span class="show-pwd"
              @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <i class="el-icon-lock"></i>
        </span>
        <el-input ref="password"
                  v-model="forgetForm.newpassword"
                  placeholder="请输入新密码"
                  name="password"
                  :type="passwordType"
                  tabindex="2"
                  auto-complete="on" />
        <span class="show-pwd"
              @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-button type="primary"
                 class="forgetBtn"
                 @click="submitForm('forgetForm')">更换密码</el-button>
    </el-form>
  </div>
</template>

<script>

export default {
  name: 'forgetword',
  components: {},
  data () {
    //这里存放数据
    return {
      forgetForm: {
        username: 'admin',
        oldpassword: '123456',
        newpassword: '1234567'
      },
      forgetRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        oldpassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        newpassword: [
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
    updatePassword (jseForm) {
      const _this = this;
      this.$axios.post('/updatePassword/{33}', jseForm).then(res => {
        _this.$router.push('/login');
      })
    },
    submitForm (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$axios.get('/getPublicKey').then(res => {
            let jseOldPassword = this.$encrypt(this.forgetForm.oldpassword, res.data.msg);
            let jseNewPassword = this.$encrypt(this.forgetForm.newpassword, res.data.msg);
            let jseForm = {
              username: this.forgetForm.username,
              oldPassword: jseOldPassword,
              newPassword: jseNewPassword
            }
            console.log(jseForm)
            this.updatePassword(jseForm);
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
  .forget-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.forget-container {
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
.forget-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .forget-form {
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
    .forgetBtn {
      display: block;
      width: 100%;
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