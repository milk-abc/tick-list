<template>
  <div class="app-container">
    <template v-for="(item,index) in entireForm">
      <div class="taskForm"
           :key="item.id">
        <el-form :ref="`taskParam${item.id}`"
                 :key="item.id"
                 :model="item"
                 :rules="rules"
                 label-width="120px">
          <el-form-item label="清单名称"
                        prop="name">
            <el-input v-model="item.name"
                      maxlength="30"
                      show-word-limit />
          </el-form-item>
          <el-form-item label="清单分类"
                        prop="categoryId">
            <el-select v-model="item.categoryId"
                       placeholder="请选择...">
              <el-option v-for="category in categoryList"
                         :key="category.id"
                         :label="category.name"
                         :value="category.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="所属标签">
            <el-select v-model="item.labelList"
                       multiple
                       placeholder="请选择">
              <el-option v-for="label in labelParamList"
                         :key="label.id"
                         :value="label.id"
                         :label="label.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="清单描述"
                        prop="description"
                        class="form-item">
            <el-input v-model="item.description"
                      class="input"
                      type="textarea"
                      maxlength="30"
                      show-word-limit />
            <div class="icon">
              <i v-if="index===entireForm.length-1"
                 class="el-icon-circle-plus-outline"
                 @click="addForm"></i>
              <i v-else
                 class="el-icon-remove-outline"
                 @click="deleteForm(index)"></i>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <div class="footer">
      <el-button type="primary"
                 @click="addTaskParam()">确认</el-button>
      <el-button @click="clearInput()">重置</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      categoryList: [],
      labelParamList: [],
      entireForm: [],
      tid: 0,
      taskParam: {
        id: 0,
        userId: this.$store.state.userInfo.id,
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
          { required: true, message: '请勾选清单分类', trigger: 'change' }
        ],
        description: [
          { required: true, message: '请输入清单描述', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.getUserCategoryList()
    this.getLabelParamList()
    if (Object.keys(this.$route.query).length !== 0) {
      this.taskParam = this.$route.query.taskParam
    }
    this.entireForm.push({ ...this.taskParam });
  },
  methods: {
    addForm () {
      this.tid++;
      this.taskParam.id = this.tid;
      this.entireForm.push({ ...this.taskParam })
    },
    deleteForm (index) {
      this.entireForm.splice(index, 1);
    },
    addTaskParam () {
      this.entireForm.forEach((item) => {
        this.$refs[`taskParam${item.id}`][0].validate((valid) => {
          if (!valid) {
            console.log('error submit!!')
            return false
          }
        })
      })
      this.$axios.post('/task/addList', this.entireForm).then((res) => {
        this.success()
        this.clearInput()
        this.$router.push({ path: '/show/task' })
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
      this.$axios.get(`category/listAll/${this.$store.state.userInfo.id}`).then((res) => {
        this.categoryList = res.data.data
      })
    },
    getLabelParamList () {
      this.$axios.get(`label/listAll/${this.$store.state.userInfo.id}`).then((res) => {
        this.labelParamList = res.data.data
      })
    },
    clearInput () {
      this.entireForm.forEach((item) => {
        this.$refs[`taskParam${item.id}`][0].resetFields();
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  .taskForm {
    max-width: 90%;
    &:nth-child(n + 2) {
      margin-top: 50px;
    }
    &::after {
      content: '';
      position: absolute;
      text-align: center;
      width: 90%;
      height: 2px;
      background-color: #000000;
    }
    .form-item {
      .input {
        width: 90%;
      }
      .icon {
        display: inline;
        margin-left: 20px;
        font-size: 30px;
      }
    }
  }
}
.el-form-item__content {
  display: flex;
}
.footer {
  text-align: right;
  float: left;
  font-size: 40px;
  width: 240px;
}
.line {
  text-align: center;
}
</style>

