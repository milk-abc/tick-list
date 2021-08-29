<template>
  <div class="task">
    <div>
      <el-container>
        <el-header>
          <el-row>
            <el-col :span="5"
                    align="center">
              <div>
                <el-input v-model="selectCondition.name"
                          placeholder="输入清单名称进行搜索"
                          prefix-icon="el-icon-search" />
              </div>
            </el-col>
            <el-col :span="8"
                    align="center">
              <div>
                清单分类:
                <el-select v-model="selectCondition.categoryId"
                           clearable
                           placeholder="请选择">
                  <el-option v-for="item in categoryParamList"
                             :key="item.id"
                             :label="item.name"
                             :value="item.id" />
                </el-select>
              </div>
            </el-col>
            <el-col :span="8"
                    align="center">
              <div>
                标签分类:
                <el-select v-model="selectCondition.labelIdList"
                           multiple
                           placeholder="请选择">
                  <el-option v-for="label in labelParamList"
                             :key="label.id"
                             :label="label.name"
                             :value="label.id" />
                </el-select>
              </div>
            </el-col>
            <el-col :span="3"
                    align="center">
              <el-row>
                <el-button type="primary"
                           @click="getTaskDataByUserId(taskData.current, taskData.size, selectCondition)">查询</el-button>
              </el-row>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <div v-if="taskData.records.length>0">
            <el-table :data="taskData.records"
                      @row-contextmenu="rowContextmenu"
                      stripe
                      style="width: 100%"
                      border>
              <el-table-column prop="name"
                               width="200"
                               label="清单名称"
                               align="center" />
              <el-table-column prop="description"
                               label="清单描述"
                               align="center" />
              <el-table-column width="200"
                               label="操作"
                               align="center">
                <template slot-scope="scope">
                  <el-button type="text"
                             size="small"
                             @click="changeTaskById(scope.row)">编辑修改</el-button>
                  <el-divider direction="vertical" />
                  <el-button type="text"
                             size="small"
                             @click="deleteTaskById(scope.row.id)">标记完成</el-button>
                </template>
              </el-table-column>
            </el-table>
            <context-button v-if="menuVisible"
                            @hideMenu="hideMenu"
                            ref="contextButton"
                            @handleCountdown="handleCountdown"
                            @handleTiming="handleTiming"></context-button>
          </div>
        </el-main>
      </el-container>
      <el-footer>
        <el-row>
          <el-col :span="16">
            <div>
              <el-pagination background
                             :current-page="taskData.current"
                             :page-sizes="[5, 10, 15, 20]"
                             :page-size="taskData.size"
                             layout="total, sizes, prev, pager, next, jumper"
                             :total="taskData.total"
                             @size-change="handleSizeChange"
                             @current-change="handleCurrentChange" />
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <el-button type="text"
                         @click="addTask()">添加清单</el-button>
            </div>
          </el-col>
        </el-row>
      </el-footer>
    </div>
    <timeCard v-if="isCount"
              :second="second"
              :minute="minute"
              :taskName="taskName"
              @cancelTime="cancelTime"></timeCard>
  </div>
</template>

<style>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}
</style>

<script>
import contextButton from './components/contextButton/index.vue'
import timeCard from './components/timeCard/index.vue'
export default {
  name: 'task',
  components: {
    timeCard,
    contextButton
  },
  data () {
    return {
      selectCondition: {
        labelIdList: [],
        categoryId: null,
        name: null
      },
      labelParamList: [],
      categoryParamList: [],
      taskData: {
        records: [],
        total: null,
        size: null,
        current: null,
        orders: [],
        searchCount: null,
        pages: null
      },
      countDownTime: 1500,
      menuVisible: false,
      isCount: false,
      minute: '',
      second: '',
      timer: null,
      taskName: ''
    }
  },
  created () {

  },
  mounted () {
    this.init()
    let endTime = localStorage.getItem('endTime');
    if (endTime) {
      this.countTime(endTime);
      this.isCount = true;
    }
  },
  beforeDestroy () {
    clearTimeout(this.timer)
  },
  methods: {
    async init () {
      await Promise.all([this.getTaskDataByUserId(1, 5, this.selectCondition), this.getUserCategoryParamList(), this.getLabelParamList()])
    },
    minuteCompute () {
      let curmin = parseInt(this.countDownTime / 60)
      this.minute = (curmin < 10 ? `0${curmin}` : curmin).toString()
    },
    secondCompute () {
      let cursec = parseInt(this.countDownTime % 60)
      this.second = (cursec < 10 ? `0${cursec}` : cursec).toString()
    },
    rowContextmenu (row, column, event) {
      this.menuVisible = !this.menuVisible;//dom节点刚修改
      event.preventDefault();
      this.$nextTick(() => {
        this.$refs.contextButton && this.$refs.contextButton.init(row, column, event)
      });
    },
    hideMenu () {
      this.menuVisible = false
      document.removeEventListener('click', this.hideMenu)
    },
    handleCountdown (taskName) {
      this.taskName = taskName;
      this.isCount = true;
      let endTime = (new Date()).getTime() + 1500000;
      localStorage.setItem('endTime', JSON.stringify(endTime));
      this.countTime(endTime);
    },
    countTime (endTime) {
      this.countDownTime = Math.ceil((endTime - (new Date()).getTime()) / 1000)
      this.minuteCompute();
      this.secondCompute();
      this.timer = setTimeout(() => {
        this.countDownTime--;
        if (this.countDownTime < 1) {
          this.countDownTime = 1500;
          localStorage.removeItem('endTime')
          clearTimeout(this.timer);
          this.isCount = false;
        } else {
          this.countTime(endTime)
        }
      }, 1000)
    },
    handleTiming () { },
    cancelTime () {
      clearTimeout(this.timer);
      localStorage.removeItem('endTime');
      this.countDownTime = 1500;
      this.isCount = false;
    },
    getTaskDataByUserId (currentPage, pageSize, selectCondition) {
      this.$axios.post(`task/getPageList/${this.global.user.id}/${currentPage}/${pageSize}`, this.selectCondition)
        .then((res) => {
          this.taskData = res.data.data
        })
    },
    getUserCategoryParamList () {
      this.$axios.get(`category/listAll/${this.global.user.id}`).then((res) => {
        this.categoryParamList = res.data.data
      })
    },
    getLabelParamList () {
      this.$axios.get(`label/listAll/${this.global.user.id}`).then((res) => {
        this.labelParamList = res.data.data
      })
    },
    handleSizeChange (val) {
      this.getTaskDataByUserId(this.taskData.current, val, this.selectCondition)
    },
    handleCurrentChange (val) {
      this.getTaskDataByUserId(val, this.taskData.size, this.selectCondition)
    },
    deleteTaskById (taskId) {
      this.$axios.get(`task/delete/${taskId}`).then((res) => {
        // 标记完成之后，要重新进行一次查找，这样才会在当前页显示足够的清单数
        this.success('该清单标记完成')
        this.getTaskDataByUserId(1, 5, this.selectCondition)
      })
    },
    changeTaskById (taskParam) {
      this.$router.push({
        path: '/add/addTask',
        query: {
          taskParam: taskParam
        }
      })
    },
    addTask () {
      this.$router.push({ path: '/add/addTask' })
    },
    success (info) {
      this.$message({
        message: info,
        type: 'success'
      })
    }

  }
}
</script>

<style lang="scss" scoped>
.task {
  height: 100%;
}
.el-col {
  border-radius: 4px;
  text-align: center;
}
</style>
