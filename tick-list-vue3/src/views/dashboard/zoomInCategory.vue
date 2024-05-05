<template>
  <el-dialog :title="title"
             :visible="true"
             width="90%"
             height="800px"
             @close="closeDialog">
    <div>
      <el-select v-model="selectedKey"
                 size="small">
        <el-option v-for="(item, idx) in dataList"
                   :key="idx"
                   :value="item.key"
                   :label="item.label" />
      </el-select>
    </div>
    <template v-for="(item, idx) in dataList">
      <div v-if="item.key === selectedKey"
           :key="idx">
        <ve-histogram v-if="item.options.type==='ve-histogram'"
                      :data="item.options.data"
                      :settings="item.options.settings" />
        <ve-line v-if="item.options.type==='ve-line'"
                 :data="item.options.data"
                 :settings="item.options.settings" />
        <ve-waterfall v-if="item.options.type==='ve-waterfall'"
                      :data="item.options.data"
                      :settings="item.options.settings"
                      :extend="item.options.extend" />
      </div>
    </template>
  </el-dialog>
</template>

<script>
export default {
  props: {
    title: String,
    defaultKey: String,
    dataList: Array
  },
  data () {
    return {
      selectedKey: this.defaultKey || ''
    }
  },
  computed: {
  },
  methods: {
    closeDialog () {
      this.$emit('close')
    }
  }
}
</script>
