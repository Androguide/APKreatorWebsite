'use strict'

angular.module('ngApkreator').controller 'MainCtrl', ($scope, $rootScope) ->
    $rootScope.headerMenuItems = [
        {name: "Home", url: "#/", class: "active"}
        {name: "App Creator", url: "#/app-creator", class: ""}
        {name: "Features", url: "#/features", class: ""}
        {name: "Contact", url: "#", class: ""}
    ]

    spinIcons = $('.home-features img')
    spinIcons.addClass 'hover-start'
    setTimeout ->
        spinIcons.removeClass 'hover-start'
    , 500