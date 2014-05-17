// Generated by CoffeeScript 1.6.3
(function() {
  'use strict';
  angular.module('ngApkreator').controller('HeaderCtrl', function($scope, $rootScope, $compile, $http) {
    var hoodie;
    hoodie = new Hoodie();
    $rootScope.account = {};
    $rootScope.dropdown = {
      label: "Sign In"
    };
    $scope.username = hoodie.account.username;
    $rootScope.headerMenuItems = [
      {
        name: "Home",
        url: "#/"
      }, {
        name: "App Creator",
        url: "#/app-creator"
      }, {
        name: "Contact",
        url: "#"
      }
    ];
    if (hoodie.account.username) {
      $rootScope.dropdown.label = $scope.username;
      $rootScope.account.dropdownItems = [
        {
          name: "My Account",
          "route": ""
        }, {
          name: "My Apps",
          "route": ""
        }, {
          name: "Sign Out",
          onclick: "signOut()"
        }
      ];
    } else {
      $rootScope.account.dropdownItems = [
        {
          name: "Sign In",
          onclick: "signIn()"
        }, {
          name: "Sign Up",
          onclick: "signUp()"
        }
      ];
    }
    $scope.signIn = function() {
      var dialog;
      dialog = $scope.compileHtml("<div ng-include ng-controller=\"SignInCtrl\" src=\"'views/parts/dialogs/sign-in.html'\"></div>");
      vex.open().append(dialog).bind("vexClose", function() {
        hoodie = new Hoodie();
        if (hoodie.account.username) {
          $rootScope.account.dropdownItems = [
            {
              name: "My Account",
              "route": ""
            }, {
              name: "My Apps",
              "route": ""
            }, {
              name: "Sign Out",
              onclick: "signOut()"
            }
          ];
          $rootScope.dropdown.label = hoodie.account.username;
          return $scope.$digest();
        }
      });
    };
    $scope.signUp = function() {
      var dialog;
      dialog = $scope.compileHtml("<div ng-include ng-controller=\"SignUpCtrl\" src=\"'views/parts/dialogs/sign-up.html'\"></div>");
      vex.open().append(dialog).bind("vexClose", function() {
        hoodie = new Hoodie();
        if (hoodie.account.username) {
          $http.get("http://localhost:5000/is_confirmed/" + hoodie.account.username).success(function(data) {
            var userInfos;
            if (data.confirmed === true) {
              $rootScope.account.dropdownItems = [
                {
                  name: "My Account",
                  "route": ""
                }, {
                  name: "My Apps",
                  "route": ""
                }, {
                  name: "Sign Out",
                  onclick: "signOut()"
                }
              ];
              userInfos = hoodie.store.findAll("task");
              if (userInfos.username) {
                return userInfos.username;
              } else {
                return $rootScope.dropdown.label = hoodie.account.username;
              }
            } else {
              return console.log("not confirmed yet!");
            }
          });
          return $scope.signOut();
        }
      });
    };
    $scope.signOut = function() {
      hoodie.account.signOut();
      $rootScope.dropdown = {
        label: "Sign In"
      };
      return $rootScope.account.dropdownItems = [
        {
          name: "Sign In",
          onclick: "signIn()"
        }, {
          name: "Sign Up",
          onclick: "signUp()"
        }
      ];
    };
    $scope.expr = function(expr, locals) {
      return $scope.$eval(expr, locals);
    };
    return $scope.compileHtml = function(html) {
      var linkThat;
      linkThat = $compile(html);
      return linkThat($scope);
    };
  });

}).call(this);

/*
//@ sourceMappingURL=header.map
*/
