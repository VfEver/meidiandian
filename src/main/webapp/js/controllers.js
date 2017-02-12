/**
 * angular controller module
 */
angular.module('controllers', ['ngCookies'])
.controller('loginController', ['$scope', '$location', '$cookies', '$cookieStore', function($scope, $location, $cookies, $cookieStore) {
	$scope.login = function () {
		$.ajax({
			url: '/meidiandian/user/login.do',
			data: {
				account: $scope.account,
				password: $scope.password,
				remMe: $scope.remMe?"1":"0"
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			if (res.status !== 200) {
				alert(res.reason);
				return ;
			} else {
				$cookies.put("username", res.username);
				$cookies.put("login", true);
				$location.path("index");
				$scope.$apply();
			}
			
		});
		$scope.login = $cookies.get("login");
		$scope.username = $cookies.get("username");
	};
	$scope.jumpToRegister = function () {
		$location.path("register");
	}
}])
.controller('registerController', ['$scope', '$location', '$cookies', '$cookieStore', function($scope, $location, $cookies, $cookieStore) {
	
	$scope.login = false;
	$scope.register = function () {
		$.ajax({
			url: '/meidiandian/user/register.do',
			data: {
				account: $scope.account,
				password: $scope.password,
				type: $scope.isSeller?"1":"0"
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			
			var expireDate = new Date();
			expireDate.setDate(expireDate.getDate() + 1);
			
			$cookies.put("username", res.username,  {'expires': expireDate});
			$cookies.put("login", true);
			
			$location.path("index");
			$scope.$apply();
			
		});
	};
	$scope.login = $cookies.get("login");
	$scope.username = $cookies.get("username");
}]);