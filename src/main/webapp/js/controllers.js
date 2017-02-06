/**
 * angular controller module
 */
angular.module('controllers', [])
.controller('loginController', ['$scope', function($scope) {
	$scope.login = function () {
		$.ajax({
			url: '/meidiandian/user/login.do',
			data: {
				username: $scope.username,
				password: $scope.password
			},
			type: 'POST',
		})
		.then(function(data) {
			console.log(data);
		});
	};
}])