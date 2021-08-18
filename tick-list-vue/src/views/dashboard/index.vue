<template>
  <div>
    <loading :isShowLoading="isloading"></loading>
    <el-card>
      <div slot="header"
           class="timeBox">
        <div class="timeDate">
          <div>{{curDay|filterDate}}</div>
          <div><span class="bigFont">{{curYear}}年{{curMonth}}月{{curDate}}日</span></div>
        </div>
        <div class="time">{{curHour}}:{{curMin}}:{{curSec}}</div>
      </div>
      <div class="saticsCard">
        <div class="satics">使用统计</div>
        <el-row>
          <el-col :span="6">{{ statistics.unFinished }}</el-col>
          <el-col :span="6">{{ statistics.weekFinished }}</el-col>
          <el-col :span="6">{{ statistics.monthFinished }}</el-col>
          <el-col :span="6">{{ statistics.totalFinished }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="6">待完成清单</el-col>
          <el-col :span="6">上周完成清单</el-col>
          <el-col :span="6">上月完成清单</el-col>
          <el-col :span="6">已完成清单</el-col>
        </el-row>
      </div>
    </el-card>
    <el-divider />
    <div class="checkSelect">
      <el-checkbox v-model="weekChecked">上周创建完成清单</el-checkbox>
      <el-checkbox v-model="monthChecked">上月完成清单</el-checkbox>
      <el-checkbox v-model="weekCategoryChecked">上周创建标签分类</el-checkbox>
      <el-checkbox v-model="dayChecked">今日完成清单</el-checkbox>
    </div>
    <el-divider />
    <div>
      <el-row>
        <el-col v-if="weekChecked"
                :span="12">
          <div class="zoom-parent">
            <span class="zoom-in-category"
                  @click="zoomIn('week')">
              <i class="el-icon-zoom-in"></i>
            </span>
          </div>
          <ve-histogram :data="dayData"
                        :settings="chartSettings" />
        </el-col>

        <el-col v-if="monthChecked"
                :span="12">
          <div class="zoom-parent">
            <span class="zoom-in-category"
                  @click="zoomIn('month')">
              <i class="el-icon-zoom-in"></i>
            </span>
            <ve-line :data="weekData"
                     :settings="chartSettings" />
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col v-if="weekCategoryChecked"
                :span="12">
          <div class="zoom-parent">
            <span class="zoom-in-category"
                  @click="zoomIn('categoryWeek')">
              <i class="el-icon-zoom-in"></i>
            </span>
          </div>
          <ve-histogram :data="dayCategoryData"
                        :settings="chartSettings" />
        </el-col>
        <el-col v-if="dayChecked"
                :span="12">
          <div class="zoom-parent">
            <span class="zoom-in-category"
                  @click="zoomIn('day')">
              <i class="el-icon-zoom-in"></i>
            </span>
            <ve-waterfall :data="today"
                          :settings="chartSettings"
                          :extend="chartExtend" />
          </div>
        </el-col>
      </el-row>
    </div>
    <zoom-in-category :default-key="zoomInDefaultKey"
                      v-if="zoomInShow"
                      :data-list="getZoomInDataList()"
                      @close="closeZoomIn"
                      title="完成清单统计"></zoom-in-category>
  </div>

</template>

<script>
import 'echarts/lib/component/title'
import 'v-charts/lib/style.css'
import ZoomInCategory from './zoomInCategory'
import loading from '../../components/animation'
export default {
  components: {
    ZoomInCategory,
    loading
  },
  data () {
    this.chartExtend = {
      title: {
        text: '当天完成统计',
        x: 'middle',		// 在图片中的x轴位置 left, right, middle
        y: 'top',		// 在图片中的y轴位置 top, bottom
        textAlign: 'center' // 整体（包括 text 和 subtext）的水平对齐 auto, left, right, center
      }
    }
    this.chartSettings = {
      labelMap: {
        day: '每日完成数量',
        total: '每日创建数量',
        week: '每周完成数量',
        count: '数量',
        categoryDay: '每日创建分类数量',
        labelDay: '每日创建标签数量'
      }
    }
    return {
      curTime: new Date(),
      isloading: false,
      zoomInDefaultKey: '',
      weekChecked: true,
      weekCategoryChecked: true,
      monthChecked: true,
      dayChecked: true,
      dayData: {
        columns: ['date', 'day', 'total'],
        // rows需要初始化为空列表，而不是Null，否则前端会认为这个为空而报错
        rows: []
      },
      dayCategoryData: {
        columns: ['date', 'categoryDay', 'labelDay'],
        // rows需要初始化为空列表，而不是Null，否则前端会认为这个为空而报错
        rows: []
      },
      weekData: {
        columns: ['date', 'week'],
        rows: []
      },
      today: {
        columns: ['categoryName', 'count'],
        rows: []
      },
      statistics: {
        totalFinished: null,
        unFinished: null,
        weekFinished: null,
        monthFinished: null
      },
      zoomInShow: false,
    }
  },
  filters: {
    filterDate (val) {
      const arr = {
        '0': '星期天',
        '1': '星期一',
        '2': '星期二',
        '3': '星期三',
        '4': '星期四',
        '5': '星期五',
        '6': '星期六'
      }
      return arr[val]
    }
  },
  computed: {
    curDay () {
      return this.curTime.getDay()
    },
    curYear () {
      return this.curTime.getFullYear()
    },
    curMonth () {
      const month = this.curTime.getMonth()
      return month + 1 < 10 ? `0${month + 1}` : month + 1
    },
    curDate () {
      const date = this.curTime.getDate()
      return date < 10 ? `0${date}` : date
    },
    curHour () {
      const hour = this.curTime.getHours()
      return hour < 10 ? `0${hour}` : hour
    },
    curMin () {
      const min = this.curTime.getMinutes()
      return min < 10 ? `0${min}` : min
    },
    curSec () {
      const sec = this.curTime.getSeconds()
      return sec < 10 ? `0${sec}` : sec
    },
    weekOption () {
      return {
        type: 've-histogram',
        data: this.dayData,
        settings: this.chartSettings,
        extend: {}
      }
    },
    weekCategoryOption () {
      return {
        type: 've-histogram',
        data: this.dayCategoryData,
        settings: this.chartSettings,
        extend: {}
      }
    },
    monthOption () {
      return {
        type: 've-line',
        data: this.weekData,
        settings: this.chartSettings,
        extend: {}
      }
    },
    dayOption () {
      return {
        type: 've-waterfall',
        data: this.today,
        settings: this.chartSettings,
        extend: this.chartExtend
      }
    },
  },
  created () {
  },
  mounted () {
    this.init();
    setInterval(() => {
      this.curTime = new Date();
    }, 1000);
  },
  methods: {
    async init () {
      this.isloading = true;
      await Promise.all([this.getDayData(), this.getWeekData(), this.getStatistics(), this.getToday()])
      this.isloading = false;
    },
    getDayData () {
      this.$axios.get(`task/countTaskForDay/${this.global.user.id}`).then((res) => {
        this.dayData.rows = res.data.data
      })
    },
    getWeekData () {
      this.$axios.get(`task/countTaskForWeek/${this.global.user.id}`).then((res) => {
        this.weekData.rows = res.data.data
      })
    },
    getCategoryData () {//修改接口名，获得上周创建标签分类的数据
      this.$axios.get(`task/countTaskForWeek/${this.global.user.id}`).then((res) => {
        this.dayCategoryData.rows = res.data.data
      })
    },
    getStatistics () {
      this.$axios.get(`task/getStatistics/${this.global.user.id}`).then((res) => {
        this.statistics = res.data.data
      })
    },
    getToday () {
      this.$axios.get(`task/countTodayForCategory/${this.global.user.id}`).then((res) => {
        this.today.rows = res.data.data
      })
    },
    zoomIn (key) {
      this.zoomInDefaultKey = key
      this.zoomInShow = true
    },
    closeZoomIn () {
      this.zoomInDefaultKey = ''
      this.zoomInShow = false
    },
    getZoomInDataList () {
      return [
        { key: 'week', label: '上周创建清单', options: this.weekOption },
        { key: 'month', label: '上月完成清单', options: this.monthOption },
        { key: 'categoryWeek', label: '上周创建标签分类', options: this.weekCategoryOption },
        { key: 'day', label: '今日完成清单', options: this.dayOption },
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.loading {
  width: 200px;
  height: 200px;
  box-sizing: border-box;
  border-radius: 50%;
  border-top: 10px solid #e74c3c;
  position: relative;
  animation: a1 2s linear infinite;
}

.loading::before,
.loading::after {
  content: '';
  width: 200px;
  height: 200px;
  position: absolute;
  left: 0;
  top: -10px;
  box-sizing: border-box;
  border-radius: 50%;
}
.loading::before {
  border-top: 10px solid #e67e22;
  transform: rotate(120deg);
}
.loading::after {
  border-top: 10px solid #3498db;
  transform: rotate(240deg);
}
.loading span {
  position: absolute;
  width: 200px;
  height: 200pxs;
  color: #fff;
  text-align: center;
  line-height: 200px;
  animation: a2 2s linear infinite;
}
@keyframes a1 {
  to {
    transform: rotate(360deg);
  }
}
@keyframes a2 {
  to {
    transform: rotate(-360deg);
  }
}
.timeBox {
  display: flex;
  align-items: center;
  justify-content: center;
  .timeDate {
    flex: 0 1 160px;
    padding: 10px 5px;
    background-color: rgb(54, 203, 214);
    color: #fff;
    .bigFont {
      font-size: 20px;
    }
  }
  .time {
    flex: 0 1 160px;
    color: rgb(54, 203, 214);
    border: 1px solid #ccc;
    font-size: 35px;
    font-weight: bold;
    padding: 9.5px 5px;
  }
}
.saticsCard {
  position: relative;
  .satics {
    position: absolute;
    top: 20%;
    font-size: 18px;
  }
}
.checkSelect {
  display: flex;
  justify-content: center;
}
.el-col {
  border-radius: 4px;
  text-align: center;
}
.zoom-parent {
  position: relative;
  .zoom-in-category {
    position: absolute;
    right: 5%;
    z-index: 2;
  }
}
</style>
