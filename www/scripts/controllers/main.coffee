'use strict'

angular.module('ngApkreator').controller 'MainCtrl', ($scope) ->
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