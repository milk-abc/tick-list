<template>
  <div id="contextmenu"
       class="contextmenu">
    <div class="context_menu_item"
         @click="handleCountdown">开始专注</div>
    <div class="context_menu_item"
         @click="handleTiming">开始计时</div>
  </div>
</template>
<script>
export default {
  name: 'contextButton',
  methods: {
    init (event) {
      let menu = document.querySelector('#contextmenu')
      let cha = document.body.clientHeight - event.clientY
      if (cha < 120) {
        menu.style.top = event.clientY - 120 + 'px'
      } else {
        menu.style.top = event.clientY - 45 + 'px'
      }
      menu.style.left = event.clientX - 45 + 'px'
      document.addEventListener('click', this.hideMenu)
    },
    hideMenu () {
      this.$emit('hideMenu')
    },
    handleCountdown () {
      this.$emit('handleCountdown')
    },
    handleTiming () {
      this.$emit('handleTiming')
    }
  }
}
</script>
<style lang="scss" scoped>
.contextmenu {
  position: absolute;
  width: 100px;
  font-size: 14px;
  border-radius: 4px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  border: 1px solid rgba(64, 158, 255, 0.2);
  z-index: 1;
  color: #409eff;
  background-color: #ecf5ff;
}
.context_menu_item {
  display: block;
  line-height: 38px;
  text-align: center;
  &:not(:last-child) {
    border-bottom: 1px solid rgba(64, 158, 255, 0.2);
  }
  &:hover {
    cursor: pointer;
    background: #66b1ff;
    border-color: #66b1ff;
    color: #fff;
  }
}
</style>