/**
 * meidiandian app router page
 */
angular.module("meidiandian",['ui.router'])
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
	});
}]);
