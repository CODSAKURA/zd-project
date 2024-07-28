import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
Vue.config.productionTip = false

// 引入element-ui + 全局使用
import Element from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
Vue.use(Element)

// 引入axios + 全局使用
import axios from 'axios'
Vue.prototype.$axios = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
