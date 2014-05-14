'use strict'

angular.module('ngApkreator').controller 'AppCreatorCtrl', ($scope, $rootScope) ->
    $rootScope.headerMenuItems = [
        {name: "Home", url: "#/", class: ""}
        {name: "App Creator", url: "#/app-creator", class: "active"}
        {name: "Contact", url: "#", class: ""}
    ]

    $scope.features = {youtube: "", gplus: ""}
    $scope.config = {appName: "", packageName: "", colorScheme: "", icon: "", apiKey: ""}
    $scope.hasYoutube = true
    $scope.hasGplus = true
    $scope.hasWebsite = true
    $scope.features_text = ""

    $scope.adjustFeaturesText = ->
        if $scope.hasYoutube && $scope.hasGplus
            $scope.features_text = "YouTube & Google+"
        else if $scope.hasYoutube
            $scope.features_text = "YouTube"
        else
            $scope.features_text = "Google+"

    $scope.generateAPIRequest = ->
        request = ""
        $('.ng-pristine').addClass "ng-invalid"
        if $scope.config.appName == "" then $('#appName').addClass "ng-invalid"
        if $scope.config.packageName == "" then $('#packageName').addClass "ng-invalid"
        if $scope.config.colorScheme == "" then $('#colorScheme').addClass "ng-invalid"
        if $scope.hasYoutube and $scope.youtube == "" then $('#has_youtube').addClass "ng-invalid"
        if $scope.hasGplus and $scope.gplus == "" then $('#has_gplus').addClass "ng-invalid"
        if $scope.hasWebsite and $scope.website == "" then $('#has_website').addClass "ng-invalid"

        request =
                "/app/" + encodeURIComponent($scope.config.appName) +
                "/package/" + encodeURIComponent($scope.config.packageName) +
                "/color/" + encodeURIComponent($scope.config.colorScheme) +
                "/icon/" + encodeURIComponent($scope.config.icon) +
                "/youtube/" + encodeURIComponent($scope.features.youtube) +
                "/gplus/" + encodeURIComponent($scope.features.gplus) +
                "/twitter/unimplemented/facebook/unimplemented" +
                "/welcome_title/Welcome!" +
                "/welcome_desc/" + encodeURIComponent($scope.presentation) +
                "/api_key/" + encodeURIComponent($scope.config.apiKey)

        console.log request
        window.location = "http://localhost:5000" + request

    $scope.adjustFeaturesText()