const defaultSettings = {
  title: 'Tick-List',

  /**
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: false,

  /**
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: false
}

const { showSettings, fixedHeader, sidebarLogo } = defaultSettings
const state = {
  showSettings: showSettings,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo
}
const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    // eslint-disable-next-line no-prototype-builtins
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  }
}
const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}
