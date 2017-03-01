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
.controller('storeController', ['$scope', '$location', '$cookies', '$cookieStore', function($scope, $location, $cookies, $cookieStore) {
	
	//点击搜索按钮响应跳转和查询
	$scope.searchStore = function () {
		$cookies.put("city", $scope.city);
		$location.path("store");
	}

	searchStores();
	
	//查询相应城市的商铺
	function searchStores() {
		$.ajax({
			url: '/meidiandian/store/findstore.do',
			data: {
				city: $cookies.get("city")
			},
			type: 'GET',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			
			if (res.status == 200) {
				$scope.storeList = res.storeList;
				$scope.$apply();
			} else {
				alert("出现问题，请重试！");
			}
		})
	}
	
	//查询店铺商品信息
	$scope.checkDetail = function (id) {
		$location.path("goods");
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
.controller('registerController', ['$scope', '$location', '$cookies', '$cookieStore', function($scope, $location, $cookies, $cookieStore) {
	
	
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
	
	//获取店铺信息
	function getStoreInfo () {
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
					$scope.storeImg = res.storeImage;
					$cookies.put("storeID", res.storeID);
					
					$scope.$apply();
				}
			} else {
				alert(res.reason);
			}
		});
	}
	
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
		$scope.goodsPage = false;
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
		
		getStoreInfo();
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
	
	//更新店铺信息
	$scope.updateStoreInfo = function () {
		$.ajax({
			url: '/meidiandian/store/updatestore.do',
			data: {
				
				storeName: $scope.storeName,
				storeAddress: $scope.storeAddress,
				storeHours: $scope.storeHours,
				cost: $scope.cost,
				userID: $cookies.get("id"),
				id: $cookies.get("storeID")
			},
			type: 'POST',
		})
		.then(function (data) {
			var res = JSON.parse(data);
			if (res.status == 200) {
				getStoreInfo();
			} else {
				alert(res.reason);
			}
		})
	}
	
	//查找店铺内商品
	function findGoodsFun() {
		$scope.storeInfo = false;
		$scope.goodsPage = true;
		$.ajax({
			url: '/meidiandian/goods/goodsdetail.do',
			data: {
				id: $cookies.get("storeID"),
			},
			type: 'POST',
		})
		.then(function(data) {
			
			var res = JSON.parse(data);
			if (res.status == 200) {
				$scope.goodsList = res.goodsList; 
				$scope.$apply();
			} else {
				alert("出现问题...");
			}
		});
	}
	
	$scope.findGoods = function () {
		
		findGoodsFun();
		
	}
	
	//上传店铺图片
	$('#uploadImg').on('change', function() {
		var files = this.files[0];
		var fr = new FileReader();
		
		
		fr.onload = function(ev) {
			$.ajax({
				url: '/meidiandian/store/storeimg.do',
				data: {
					id: $cookies.get("storeID"),
					img: ev.target.result
				},
				type: 'POST',
			})
			.then(function(res) {
				if (res.length > 0) {
					alert("上传成功!");
					getStoreInfo();
				} else {
					alert('出现问题，请重试');
				}
			});
		};
		fr.readAsDataURL(files);
	});
	
	//点击查看具体商品具体信息，可以进行修改
	$scope.checkGoods = function (id) {
		$("#goodsDetailModal").modal("toggle");
		$.ajax({
			url: '/meidiandian/goods/goodsinfo.do',
			data: {
				id: id
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			
			if (res.status == 200) {
				
				$scope.goodsName = res.goodsName;
				$scope.goodsPrice = res.goodsPrice;
				$scope.goodsImage = res.goodsImage;
				$scope.goodsID = res.goodsID;
				$scope.$apply();
				
			} else {
				alert('出现问题，请重试');
			}
		});
	}
	
	//点击修改商品信息
	$scope.updateGoodsInfo = function () {
		$.ajax({
			url: '/meidiandian/goods/updategoodsinfo.do',
			data: {
				id: $scope.goodsID,
				goodsPrice: $scope.goodsPrice,
				goodsName: $scope.goodsName
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			
			if (res.status == 200) {
				alert("更新成功");
				findGoodsFun();
			} else {
				alert('出现问题，请重试');
			}
		});
	}
	
	//删除商品
	$scope.deleteGoods = function () {
		$.ajax({
			url: '/meidiandian/goods/deletegoods.do',
			data: {
				id: $scope.goodsID
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			
			if (res.status == 200) {
				alert("删除成功");
				findGoodsFun();
			} else {
				alert('出现问题，请重试');
			}
		});
	}
	
	//点击添加商品按钮
	$scope.addNewGoodsBtn = function () {
		$("#newGoodsModal").modal("toggle");
	}
	
	//添加商品函数
	function addNewGoodsFun(img) {
		$.ajax({
			url: '/meidiandian/goods/addgoods.do',
			data: {
				storeID: $cookies.get("storeID"),
				goodsName: $scope.newGoodsName,
				goodsPrice: $scope.newGoodsPrice,
				goodsImage: img
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			
			if (res.status == 200) {
				alert("添加成功");
			} else {
				alert('出现问题，请重试');
			}
		});
	}
	
	
	$('#goodsImg').on('change', function() {
		var files = this.files[0];
		var fr = new FileReader();
		
		fr.onload = function(ev) {
			$.ajax({
				url: '/meidiandian/goods/goodsimg.do',
				data: {
					id: $cookies.get("storeID"),
					img: ev.target.result
				},
				type: 'POST',
			})
			.then(function(data) {
				var res = JSON.parse(data);
				if (res.status == 200) {
					$scope.img = res.imgUrl;
				} else {
					alert('出现问题，请重试');
				}
			});
		};
		fr.readAsDataURL(files);
	});
	
	//点击添加
	$scope.addNewGoods = function () {
		$.ajax({
			url: '/meidiandian/goods/addgoods.do',
			data: {
				storeID: $cookies.get("storeID"),
				goodsName: $scope.newGoodsName,
				goodsPrice: $scope.newGoodsPrice,
				goodsImage: $scope.img
			},
			type: 'POST',
		})
		.then(function(data) {
			var res = JSON.parse(data);
			
			if (res.status == 200) {
				alert("添加成功");
				findGoodsFun();
			} else {
				alert('出现问题，请重试');
			}
		});
	}
	
	$scope.back = function () {
		$scope.goodsPage = false;
		$scope.storeInfo = true;
	}
	
}]);



















