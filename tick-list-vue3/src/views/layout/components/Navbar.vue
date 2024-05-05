
<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened"
               class="hamburger-container"
               @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown class="avatar-container"
                   trigger="click">
        <div class="avatar-wrapper">
          <el-upload class="avatar-uploader"
                     :action="`${this.$axios.defaults.baseURL}/user/uploadPicture/${this.$store.state.userInfo.id}`"
                     name='multipartFile'
                     :show-file-list="false"
                     :on-success="handleAvatarSuccess">
            <img v-if="imageUrl"
                 :src="imageUrl"
                 class="user-avatar" />
            <i v-else
               class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown"
                          class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item>
              Home
            </el-dropdown-item>
          </router-link>
          <a target="_blank"
             href="https://github.com/milk-abc/tick-list/">
            <el-dropdown-item>Github</el-dropdown-item>
          </a>
          <el-dropdown-item divided
                            @click.native="logout">
            <span style="display:block;">Log Out</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
export default {
  name: 'Navbar',
  components: {
    Breadcrumb,
    Hamburger
  },
  data () {
    return {
      header: {
        "Authorization": localStorage.getItem('token')
      },
      imageUrl: ''
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'username'
    ])
  },
  methods: {
    handleAvatarSuccess (res, multipartFile) {
      let formData = new FormData()
      formData.append('multipartFile', multipartFile.raw)
      this.imageUrl = URL.createObjectURL(multipartFile.raw);
    },
    toggleSideBar () {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout () {
      // await this.$store.dispatch('user/logout')
      this.$axios.defaults.headers.common['Authorization'] = ''
      this.$store.commit('REMOVE_INFO');
      this.$router.push('/login')
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  mounted () {
    if (this.username) {
      this.imageUrl = this.avatar;
    }
  }
}
</script>
<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }
  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .fileInput {
          position: absolute;
          top: 0;
          bottom: 0;
          left: 0;
          right: 0;
          display: block;
          margin: auto;
          width: 40px;
          opacity: 0;
          background-color: red;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>