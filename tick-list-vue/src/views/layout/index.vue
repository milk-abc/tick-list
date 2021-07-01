<!--  -->
<template>
  <div class="app-wrapper">
    <div class="main-container">
      <div>
        <Navbar></Navbar>
      </div>
      <AppMain></AppMain>
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain } from './components'
export default {
  name: 'layout',
  components: {
    Navbar,
    Sidebar,
    AppMain
  },
  data () {
    //这里存放数据
    return {

    };
  },
  methods: {

  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created () {

  }
}
</script>
<style lang="scss" scoped>
@import '~@/styles/mixin.scss';
@import '~@/styles/variables.scss';
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
}
.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - #{$sideBarWidth});
  transition: width 0.28s;
}
</style>