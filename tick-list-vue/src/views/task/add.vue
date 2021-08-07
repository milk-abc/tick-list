<template>
  <div class="app-container">
    <el-form ref="taskParam"
             :model="taskParam"
             :rules="rules"
             label-width="120px">
      <el-form-item label="清单名称"
                    prop="name">
        <el-input v-model="taskParam.name"
                  maxlength="20"
                  show-word-limit />
      </el-form-item>
      <el-form-item label="清单分类"
                    prop="categoryId">
        <el-select v-model="taskParam.categoryId"
                   placeholder="请选择...">
          <el-option v-for="category in categoryList"
                     :key="category.id"
                     :label="category.name"
                     :value="category.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属标签">
        <el-checkbox-group v-model="taskParam.labelList">
          <el-checkbox v-for="label in labelParamList"
                       :key="label.id"
                       :label="label.id"
                       name="type">{{ label.name }}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="清单描述"
                    prop="description">
        <el-input v-model="taskParam.description"
                  type="textarea"
                  maxlength="30"
                  show-word-limit />
        <el-form-item>
          <el-button type="primary"
                     @click="addTaskParam('taskParam')">添加/修改</el-button>
          <el-button @click="clearInput()">重置</el-button>
        </el-form-item>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      categoryList: [],
      labelParamList: [],
      taskParam: {
        id: null,
        userId: this.global.user.id,
        categoryId: null,
        name: '',
        labelList: [],
        description: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入清单名称', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '请勾选清单分类', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入清单描述', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getUserCategoryList()
    this.getLabelParamList()
    if (Object.keys(this.$route.query).length !== 0) {
      this.taskParam = this.$route.query.taskParam
    }
  },
  methods: {
    addTaskParam (taskParam) {
      this.$refs[taskParam].validate((valid) => {
        if (valid) {
          this.$axios.post('task/save', this.taskParam).then((res) => {
            this.success()
            this.clearInput()
            this.$router.push({ path: '/show/task' })
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    success () {
      this.$message({
        message: '添加/修改清单成功',
        type: 'success'
      })
    },
    onCancel () {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    },
    getUserCategoryList () {
      this.$axios.get(`category/listAll/${this.global.user.id}`).then((res) => {
        this.categoryList = res.data.data
      })
    },
    getLabelParamList () {
      this.$axios.get(`label/listAll/${this.global.user.id}`).then((res) => {
        this.labelParamList = res.data.data
      })
    },
    clearInput () {
      this.taskParam.name = ''
      this.taskParam.categoryId = null
      this.taskParam.labelList = []
      this.taskParam.description = ''
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

