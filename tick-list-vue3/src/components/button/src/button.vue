<template>
  <button class="lq-button"
          @click="handleClick"
          :disabled="buttonDisabled||loading"
          :autofocus="autofocus"
          :type="nativeType"
          :class="[type?'el-button--'+type:'','el-button--default']"></button>
</template>