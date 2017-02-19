/**
 * angular controller module
 */
angular.module('controllers', ['ngCookies'])
.config(function ($httpProvider) {
	$httpProvider.defaults.transformRequest = function(data){
        if (data === undefined) {
            return data;
        }
        return $.param(data);
    }
    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
    $httpProvider.defaults.headers.post['Content-Type'] =  'application/x-www-form-urlencoded';
})
.controller('loginController', ['$scope', '$location', '$cookies', '$cookieStore', function($scope, $location, $cookies, $cookieStore) {
	//登录
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
				$cookies.put("id", res.id);
				
				$location.path("index");
				$scope.$apply();
			}
			
		});
		$scope.login = $cookies.get("login");
		$scope.username = $cookies.get("username");
	};
	//跳入注册页面
	$scope.jumpToRegister = function () {
		$location.path("register");
	}
	
	$scope.userCenter = function () {
		$location.path("settings");
	}
	
}])
.controller('registerController', ['$scope', '$location', '$cookies', '$cookieStore', function($scope, $location, $cookies, $cookieStore) {
	
	$scope.login = false;
	//注册
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
			
			if (res.status == 200) {
				var expireDate = new Date();
				expireDate.setDate(expireDate.getDate() + 1);
				
				$cookies.put("username", res.username,  {'expires': expireDate});
				$cookies.put("login", true);
				$cookies.put("id", res.id);
				
				$location.path("index");
				$scope.$apply();
			} else {
				alert("注册失败！请重新注册!");
			}
			
		});
	};
	$scope.login = $cookies.get("login");
	$scope.username = $cookies.get("username");
	
	//退出
	$scope.logout = function () {
		var id = $cookies.get("id");
		$.ajax({
			url: '/meidiandian/user/logout.do',
			data: {
				id: id
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			if(res.status == 200) {
				$cookies.remove("id");
				$cookies.remove("username");
				$cookies.remove("login");
				
				$location.path("login");
				$scope.$apply();
				$scope.login = false;
			}
		});
	}
	
}])
.controller('settingsController', ['$scope', '$location', '$cookies', '$http', function($scope, $location, $cookies, $http) {
	
	$scope.type = 0;
	$scope.userInfoDiv = true;
	$scope.storeInfoDiv = false;
	
	function getUserInfo() {
		$.ajax({
			url: '/meidiandian/user/userinfo.do',
			data: {
				id: $cookies.get("id")
			},
			type: 'GET',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			if(res.status == 200) {
				$scope.username = res.username;
				$scope.account = res.account;
				$scope.address = res.address;
				$scope.createTime = res.createTime;
				$scope.password = res.password;
				$scope.type = res.type;
				$scope.$apply();
			}
		});
	}
	getUserInfo();
	
	//更新用户信息
	$scope.updateUserInfo = function () {
		$.ajax({
			url: '/meidiandian/user/updateuserinfo.do',
			data: {
				id: $cookies.get("id"),
				account: $scope.account,
				username: $scope.newUsername,
				address: $scope.newAddress,
				password: $scope.newPassword
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			if(res.status == 200) {
				getUserInfo();
				$scope.newUsername = "";
				$scope.newAddress = "";
				$scope.newPassword = "";
				$scope.$apply();
			}
		});
	}
	//点击进入用户信息界面
	$scope.changeToUserInfo = function() {
		$("li").removeClass("active");
		$("#userInfoID").addClass("active");
		$scope.userInfoDiv = true;
		$scope.storeInfoDiv = false;
		$scope.storeInfo = false;
	}
	//点击进入店铺界面
	$scope.changeToStoreInfo = function () {
		
		$("li").removeClass("active");
		$("#stroeInfoID").addClass("active");
		$scope.userInfoDiv = false;
		$scope.storeInfoDiv = false;
		$scope.storeInfo = true;
		
		$.ajax({
			url: '/meidiandian/store/storeinfo.do',
			data: {
				id: $cookies.get("id")
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			if(res.status == 200) {
				if (res.hasStore === "no") {
					$scope.storeInfo = false;
					$scope.storeInfoDiv = true;
					$scope.createButton = true;
					$scope.newStore = false;
					$scope.$apply();
				} else if (res.hasStore === "yes") {
					$scope.createButton = false;
					$scope.newStore = false;
					$scope.storeInfo = true;
					$scope.storeName = res.storeName;
					$scope.storeHours = res.storeHours;
					$scope.storeAddress = res.storeAddress;
					$scope.cost = res.cost;
					$scope.$apply();
				}
			} else {
				alert(res.reason);
			}
		});
	}
	
	//创建店铺
	$scope.createStore = function () {
		$scope.createButton = false;
		$scope.newStore = true;
	}
	//保存创建的店铺信息
	$scope.saveStore = function () {
		$.ajax({
			url: '/meidiandian/store/savestore.do',
			data: {
				storeName: $scope.storeName,
				storeAddress: $scope.storeAddress,
				storeHours: $scope.storeHours,
				cost: $scope.cost,
				id: $cookies.get("id")
			},
			type: 'POST',
		})
		.then(function (data) {
			var res = JSON.parse(data);
			if (res.status == 200) {
				$scope.changeToStoreInfo();
			} else {
				alert(res.reason);
			}
		})
	}
}]);



















