'use strict'

angular.module('ngApkreator', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]).config ($routeProvider) ->
    $routeProvider

    .when '/',
            templateUrl: 'views/main.html'
            controller: 'MainCtrl'

    .when '/app-creator',
            templateUrl: 'views/app-creator.html'
            controller: 'AppCreatorCtrl'

    .when '/sign-in',
            templateUrl: 'views/parts/dialogs/sign-in.html'
            controller: 'SignInCtrl'

    .when '/sign-up',
            templateUrl: 'views/parts/dialogs/sign-up.html'
            controller: 'SignUpCtrl'

    .otherwise
            redirectTo: '/'