'use strict'

angular.module('ngApkreator').controller 'MainCtrl', ($scope, $rootScope) ->
    $rootScope.headerMenuItems = [
        {name: "Home", url: "#/", class: "active"}
        {name: "App Creator", url: "#/app-creator", class: ""}
        {name: "Contact", url: "#", class: ""}
    ]

    $scope.carouselItems =
      [
        {
          class: "active turquoise-bg"
          image: "assets/img/device1.png"
          alt: ""
        }
        {
          class: "amethyst-bg"
          image: "assets/img/device2.png"
          alt: ""
        }
        {
          class: "belize-bg"
          image: "assets/img/device3.png"
          alt: ""
        }
      ]
