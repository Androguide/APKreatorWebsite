// Generated by CoffeeScript 1.6.3
(function() {
  'use strict';
  angular.module('ngApkreator').controller('AppCreatorCtrl', function($scope, $rootScope, $compile, $http) {
    var saveToLocalStorage;
    $rootScope.headerMenuItems = [
      {
        name: "Home",
        url: "#/",
        "class": ""
      }, {
        name: "App Creator",
        url: "#/app-creator",
        "class": "active"
      }, {
        name: "Contact",
        url: "#",
        "class": ""
      }
    ];
    $scope.hasYoutube = true;
    $scope.hasGplus = true;
    $scope.hasWebsite = true;
    $scope.features_text = "";
    $scope.features = {
      youtube: localStorage.getItem("youtube"),
      gplus: localStorage.getItem("gplus")
    };
    $scope.config = {
      appName: localStorage.getItem("appName"),
      packageName: localStorage.getItem("packageName"),
      colorScheme: localStorage.getItem("colorScheme"),
      icon: localStorage.getItem("icon"),
      presentation: localStorage.getItem("presentation"),
      website: localStorage.getItem("website"),
      apiKey: localStorage.getItem("apiKey")
    };
    $scope.adjustFeaturesText = function() {
      if ($scope.hasYoutube && $scope.hasGplus) {
        return $scope.features_text = "YouTube & Google+";
      } else if ($scope.hasYoutube) {
        return $scope.features_text = "YouTube";
      } else {
        return $scope.features_text = "Google+";
      }
    };
    $scope.generateAPIRequest = function() {
      var isFormValid, request, _dialog;
      isFormValid = true;
      if ($scope.config.appName === "") {
        $('#appName').addClass("ng-invalid");
        isFormValid = false;
      } else {
        $('#appName').removeClass("ng-invalid");
      }
      if ($scope.config.packageName === "") {
        $('#packageName').addClass("ng-invalid");
        isFormValid = false;
      } else {
        $('#packageName').removeClass("ng-invalid");
      }
      if ($scope.config.colorScheme === "") {
        $('#colorScheme').addClass("ng-invalid");
        isFormValid = false;
      } else {
        $('#colorScheme').removeClass("ng-invalid");
      }
      if ($scope.config.presentation === "") {
        $('textarea[name=presentation]').addClass("ng-invalid");
        isFormValid = false;
      } else {
        $('textarea[name=presentation]').removeClass("ng-invalid");
      }
      if ($scope.config.icon === "") {
        $('input[name=icon]').addClass("ng-invalid");
        isFormValid = false;
      } else {
        $('input[name=icon]').removeClass("ng-invalid");
      }
      if ($scope.config.website === "") {
        $('input[name=website]').addClass("ng-invalid");
        isFormValid = false;
      } else {
        $('input[name=website]').removeClass("ng-invalid");
      }
      if ($scope.config.apiKey === "") {
        $('#apiKey').addClass("ng-invalid");
        isFormValid = false;
      } else {
        $('#apiKey').removeClass("ng-invalid");
      }
      if ($scope.hasYoutube && $scope.features.youtube === "") {
        $('#youtube').addClass("ng-invalid");
        isFormValid = false;
      } else {
        $('#youtube').removeClass("ng-invalid");
      }
      if ($scope.hasGplus && $scope.features.gplus === "") {
        $('#gplus').addClass("ng-invalid");
        isFormValid = false;
      } else {
        $('#gplus').removeClass("ng-invalid");
      }
      saveToLocalStorage();
      if (isFormValid) {
        request = "/app/" + encodeURIComponent($scope.config.appName) + "/package/" + encodeURIComponent($scope.config.packageName) + "/color/" + encodeURIComponent($scope.config.colorScheme) + "/icon/" + encodeURIComponent($scope.config.icon) + "/youtube/" + encodeURIComponent($scope.features.youtube) + "/gplus/" + encodeURIComponent($scope.features.gplus) + "/twitter/unimplemented/facebook/unimplemented" + "/website/" + encodeURIComponent($scope.config.website) + "/welcome_title/Welcome!" + "/welcome_desc/" + encodeURIComponent($scope.config.presentation) + "/api_key/" + encodeURIComponent($scope.config.apiKey);
        console.log(request);
        _dialog = "<div class=\"sign-in-dialog\">\n     <div class=\"text-center\">\n         <h1 class=\"teal no-margin-top\">Building Your App</h1>\n     </div>\n     <div class=\"csspinner bar-follow\" style=\"width: 450px; margin-top: 30px; margin-bottom: 80px;\"></div>\n</div>";
        vex.dialog.open().html(_dialog);
        return $http({
          method: 'GET',
          url: 'http://localhost:5000' + request
        }).success(function(data, status, headers, config) {
          console.log(data, status, headers, config);
          vex.closeAll();
          return window.location = data.apkUrl;
        }).error(function(data, status, headers, config) {
          return console.log("error", data, status, headers, config);
        });
      }
    };
    $scope.adjustFeaturesText();
    $scope.compileHtml = function(html) {
      var linkThat;
      linkThat = $compile(html);
      return linkThat($scope);
    };
    return saveToLocalStorage = function() {
      localStorage.setItem("appName", $scope.config.appName);
      localStorage.setItem("packageName", $scope.config.packageName);
      localStorage.setItem("colorScheme", $scope.config.colorScheme);
      localStorage.setItem("icon", $scope.config.icon);
      localStorage.setItem("presentation", $scope.config.presentation);
      localStorage.setItem("apiKey", $scope.config.apiKey);
      localStorage.setItem("hasYoutube", $scope.hasYoutube);
      localStorage.setItem("youtube", $scope.features.youtube);
      localStorage.setItem("hasGplus", $scope.hasGplus);
      localStorage.setItem("gplus", $scope.features.gplus);
      return localStorage.setItem("website", $scope.config.website);
    };
  });

}).call(this);

/*
//@ sourceMappingURL=app-creator.map
*/
