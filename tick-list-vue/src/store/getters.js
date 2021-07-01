const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  avatar: state => state.userInfo.headPortrait,
  token: state => state.token,
  username: state => state.userInfo.username,
  roles: state => state.userInfo.roles
};
export default getters;
