angular.module('app')
    .controller('LoginController', function($scope,Restangular) {
        $scope.clickButton = function () {
            Restangular.one("listall").get().then(function (value) {
                console.log(value);
            });
        };


    });
