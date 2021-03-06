// Generated by CoffeeScript 1.6.3
(function() {
  'use strict';
  angular.module('ngApkreator').controller('MainCtrl', function($scope, $rootScope) {
    var spinIcons;
    $rootScope.headerMenuItems = [
      {
        name: "Home",
        url: "#/",
        "class": "active"
      }, {
        name: "App Creator",
        url: "#/app-creator",
        "class": ""
      }, {
        name: "Features",
        url: "#/features",
        "class": ""
      }, {
        name: "Contact",
        url: "#",
        "class": ""
      }
    ];
    spinIcons = $('.home-features img');
    spinIcons.addClass('hover-start');
    return setTimeout(function() {
      return spinIcons.removeClass('hover-start');
    }, 500);
  });

}).call(this);

/*
//@ sourceMappingURL=main.map
*/
