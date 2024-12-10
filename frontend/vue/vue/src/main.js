import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/global.css'
Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'});
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

const debounce = (fn, delay) => {
	let timer = null;

	return function () {
		let context = this;

		let args = arguments;

		clearTimeout(timer);

		timer = setTimeout(function () {
			fn.apply(context, args);
		}, delay);
	};
};

const _ResizeObserver = window.ResizeObserver;

window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
	constructor(callback) {
		callback = debounce(callback, 16);
		super(callback);
	}
};