angular.module('app', ['ngRoute','restangular'])


    .config(function($routeProvider,RestangularProvider) {


        $routeProvider
            .when('/', {
                controller:'HomeController as home',
                templateUrl:'app/template/home.html'
            })
            .when('/login', {
                controller:'LoginController as login',
                templateUrl:'app/template/login.html'
            })
            .when('/users', {
                controller:'UserController as users',
                templateUrl:'app/template/users.html'
            })
            .when('/serverjs', {
                controller:'ServerjsController as serverjs',
                templateUrl:'app/template/serverjs.html'
            })
            .otherwise({
                redirectTo:'/'
            });


        //RestangularProvider.setBaseUrl('http://localhost');
    });