import Vue from 'vue'
import App from './App.vue'
import router from './router'
import element from "element-ui"
import VueCookies from 'vue-cookies'
import "element-ui/lib/theme-chalk/index.css"

Vue.use(element)
Vue.use(VueCookies)
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
