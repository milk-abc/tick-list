<template>
  <el-card>
    <div>
      <span>{{minute}}:{{second}}</span>
    </div>
  </el-card>
</template>
<script>
export default {
  name: 'countDown',
  data () {
    return {
    }
  },
  props: {
    countDownTime: Number
  },
  computed: {
    minute () {
      return parseInt(this.countDownTime / 60)
    },
    second () {
      return parseInt(this.countDownTime % 60)
    }
  },
  created () {
  },
  methods: {

  }
}
</script>
<style>
</style>