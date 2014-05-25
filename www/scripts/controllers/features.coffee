'use strict'

angular.module('ngApkreator').controller 'FeaturesCtrl', ($scope, $rootScope) ->
    $rootScope.headerMenuItems = [
        {name: "Home", url: "#/", class: ""}
        {name: "App Creator", url: "#/app-creator", class: ""}
        {name: "Features", url: "#/features", class: "active"}
        {name: "Contact", url: "#", class: ""}
    ]
