<template>
  <div>
    <el-form ref="feedback"
             :model="feedback"
             :rules="rules"
             label-width="100px"
             class="feedback-el-form">
      <el-form-item label="标题"
                    prop="title">
        <el-input v-model="feedback.title" />
      </el-form-item>
      <el-form-item label="描述"
                    prop="description">
        <el-input v-model="feedback.description"
                  type="textarea" />
      </el-form-item>
      <el-form-item class="text-middle">
        <el-button type="primary"
                   @click="submitForm('feedback')">立即创建</el-button>
        <el-button @click="resetForm('feedback')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data () {
    return {
      feedback: {
        userId: this.global.user.id,
        title: '',
        description: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请填写反馈描述', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('feedback/add', this.feedback).then((res) => {
            this.success('您的反馈我们已经收到，感谢您的支持')
            this.resetForm('feedback')
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    success (message) {
      this.$message({
        message: message,
        type: 'success'
      })
    }
  }
}
</script>

<style>
.feedback-el-form {
  margin-top: 100px;
  margin-left: 120px;
  margin-right: 120px;
}
.text-middle {
  text-align: center;
}
</style>
