<template>
  <div class="login-mobile" v-if="isMobile">
    <div class="top-logo-mobile">
      <!-- <h2 style="font-weight: 700;">云脉能源管理平台</h2> -->
      <h2 style="font-weight: 700;">{{ sysTitle }}</h2>
      <!-- <img src="../assets/logo/logo-heng.png" style="height: 50px;"> -->
    </div>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">
        <!-- <img src="../assets/logo/logo-横.png" style="height: 30px;" v-if="logoShow"> -->
        <!-- <h2>{{ sysTitle }}</h2> -->
        <h2 style="font-weight: 700; margin: 0;">登录</h2>
      </h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号" class="unchanged">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码"
          @keyup.enter.native="handleLogin" show-password class="unchanged">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%"
          @keyup.enter.native="handleLogin" class="unchanged">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img" />
        </div>
      </el-form-item>
      <div class="select-click">
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;" class="unchanged">记住密码</el-checkbox>
      </div>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="primary" style="width:100%;" class="btn-fixed"
          @click.native.prevent="handleLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
        <router-link class="link-type" :to="'/applyAccount'" style="font-weight:bold"
          v-if="experienceShow">获取体验账号</router-link>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <!-- <span>苏州云脉软件技术有限公司 © 版权所有</span> -->
    </div>
  </div>
  <div class="login" v-else-if="!isMobile">
    <div class="top-logo">
      <h2 style="font-weight: 700;">{{ sysTitle }}</h2>
      <!-- <img src="../assets/logo/logo-heng.png" style="height: 50px;"> -->
    </div>
    <div class="left-pic">
      <img src="../assets/images/hero-img.png" style="height: 450px;">
    </div>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">
        <!-- <img src="../assets/logo/logo-横.png" style="height: 30px;" v-if="logoShow"> -->
        <!-- <h2>{{ sysTitle }}</h2> -->
        <h2 style="font-weight: 700; margin: 0;">登录</h2>
      </h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号" class="unchanged">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码"
          @keyup.enter.native="handleLogin" show-password class="unchanged">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%"
          @keyup.enter.native="handleLogin" class="unchanged">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img" />
        </div>
      </el-form-item>
      <div class="select-click">
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;" class="unchanged">记住密码</el-checkbox>
      </div>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="primary" style="width:100%;" class="btn-fixed"
          @click.native.prevent="handleLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
        <router-link class="link-type" :to="'/applyAccount'" style="font-weight:bold"
          v-if="experienceShow">获取体验账号</router-link>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <!-- <span>苏州云脉软件技术有限公司 © 版权所有</span> -->
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      isMobile: false, // 默认为Web端
      codeUrl: "",
      loginForm: {
        username: undefined,
        password: undefined,
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "blur", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  mounted() {
    this.checkWindowSize(); // 在组件挂载后检查窗口大小
    window.addEventListener('resize', this.checkWindowSize); // 监听窗口大小变化
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.checkWindowSize); // 组件销毁前移除事件监听
  },
  computed: {
    sysTitle: function () {
      return this.$store.getters.logoInfo.sysTitle
      // return process.env.VUE_APP_TITLE
    },
    logoShow() {
      return this.$store.getters.logoInfo.loginLogo
    },
    experienceShow() {
      return this.$store.getters.logoInfo.experienceShow
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    checkWindowSize() {
      if (window.innerWidth <= 768) {
        this.isMobile = true; // 窗口宽度小于等于768px时，判断为手机端
      } else {
        this.isMobile = false; // 窗口宽度大于768px时，判断为Web端
      }
    },
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.data.captchaEnabled === undefined ? true : res.data.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.data.img;
          this.loginForm.uuid = res.data.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(() => { });
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login {
  padding: 200px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 100%;
  background-size: cover;
  background-color: #0e1d34;
  /* background-image: url("../assets/images/cta-bg.jpg"); */
  background-image: url("../assets/images/hero-bg.png");
}

.login-mobile {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 100%;
  background-size: cover;
  background-color: #0e1d34;
  /* background-image: url("../assets/images/cta-bg.jpg"); */
  background-image: url("../assets/images/hero-bg.png");
}

.title {
  /* margin: 0px auto 30px auto; */
  text-align: center;
  /* color: #333333; */
  font-size: 21px;
  color: #001f8d;
  letter-spacing: 5px;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 10px 25px 10px 25px;

  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.form-login {
  .el-input {
    height: 40px;

    input {
      height: 40px;
    }
  }

  .input-icon {
    height: 40px;
    width: 15px;
    margin-left: 2px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 38px;
}

.link-type {
  color: #7baafc;
  font-size: 12px;
}

.link-type:hover {
  cursor: pointer;
  color: rgb(32, 160, 255);
}

.select-click {
  display: flex;
  justify-content: space-between;
  // align-items: center;
}

.top-logo {
  position: fixed;
  left: 100px;
  top: 0px;
  font-size: 30px;
  letter-spacing: 1px;
  margin-bottom: 20px;
  padding: 0;
  color: #ffffff;
}

.top-logo-mobile {
  position: absolute;
  top: 0px;
  font-size: 26px;
  letter-spacing: 1px;
  margin-bottom: 20px;
  padding: 0;
  color: #ffffff;
}

.left-pic {
  padding-top: 70px;
}</style>
