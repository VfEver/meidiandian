/**
 * meidiandian app router page
 */
angular.module("meidiandian", ['ui.router', 'controllers'])
.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/index');
	$stateProvider
	.state('index', {
		url: '/index',
		views: {
			'navinfo': {
				templateUrl: 'tpls/index-nav-tpl.html'
			},
			'main': {
				templateUrl: 'tpls/index-main-tpl.html'
			},
			'footer': {
				templateUrl: 'tpls/index-footer-tpl.html'
			}
		}
	})
	.state('login', {
		url: '/login',
		views: {
			'navinfo': {
				templateUrl: 'tpls/login-nav-tpl.html'
			},
			'main': {
				templateUrl: 'tpls/login-main-tpl.html'
			},
			'footer': {
				templateUrl: 'tpls/index-footer-tpl.html'
			}
		}
	})
	.state('register', {
		url: '/register',
		views: {
			'navinfo': {
				templateUrl: 'tpls/login-nav-tpl.html'
			},
			'main': {
				templateUrl: 'tpls/register-main-tpl.html'
			},
			'footer': {
				templateUrl: 'tpls/index-footer-tpl.html'
			}
		}
	})
	.state('goods', {
		url: '/goods',
		views: {
			'navinfo': {
				templateUrl: ''
			},
			'main': {
				templateUrl: 'tpls/goods-main-tpl.html'
			},
			'footer': {
				templateUrl: 'tpls/index-footer-tpl.html'
			}
		}
	});
}]);
