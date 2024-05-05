<template>
  <div id="card"
       class="timeCard">
    <el-button type="text"
               @click="cancelTime"
               class="btn"
               icon="el-icon-close"></el-button>
    <div class="text">
      <span class="time">{{minute}}:{{second}}</span>
      <span class="taskname">{{taskName}}</span>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
    }
  },
  props: {
    minute: String,
    second: String,
    taskName: String
  },
  mounted () {
    this.init()
  },
  methods: {
    cancelTime () {
      this.$emit("cancelTime")
    },
    init () {
      let card = document.getElementById("card");
      card.onmousedown = function (event) {
        let e = event || window.event;
        let left = e.clientX - card.offsetLeft;
        let top = e.clientY - card.offsetTop;
        function move (event) {
          let e = event || window.event;
          let leftOffset = e.clientX - left;
          let topOffset = e.clientY - top;
          if (leftOffset < 0) {
            leftOffset = 0;
          } else if (leftOffset > card.parentNode.clientWidth - card.offsetWidth) {
            leftOffset = card.parentNode.clientWidth - card.offsetWidth;
          }
          if (topOffset < 0) {
            topOffset = 0;
          }
          card.style.left = leftOffset + "px";
          card.style.top = topOffset + "px";
        };
        function up (event) {
          document.removeEventListener('mousemove', move)
          document.removeEventListener('mouseup', up)
        }
        document.addEventListener('mousemove', move)
        document.addEventListener('mouseup', up)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.timeCard {
  z-index: 1;
  position: absolute;
  width: 200px;
  height: 100px;
  border-radius: 2px;
  box-shadow: 2px 2px 5px #333333;
  background: #ffff;
  .btn {
    display: block;
    position: absolute;
    top: -10px;
    right: 10px;
    width: 10px;
    height: 10px;
    color: rgb(201, 197, 197);
  }
  .text {
    font-size: 30px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    .taskname {
      display: block;
      text-align: center;
    }
  }
}
</style>