'use strict'

angular.module('ngApkreator').controller 'AppCreatorCtrl', ($scope, $rootScope, $compile, $http) ->
    # Set the App Creator menu item as the active one
    $rootScope.headerMenuItems = [
        { name: "Home", url: "#/", class: "" }
        { name: "App Creator", url: "#/app-creator", class: "active" }
        { name: "Contact", url: "#", class: "" }
    ]

    # Initialize variables
    $scope.hasYoutube = true
    $scope.hasGplus = true
    $scope.hasWebsite = true
    $scope.features_text = ""

    # Initialize features variables
    $scope.features =
        youtube: localStorage.getItem "youtube"
        gplus: localStorage.getItem "gplus"

    # Initialize config variables
    $scope.config =
        appName: localStorage.getItem "appName"
        packageName: localStorage.getItem "packageName"
        colorScheme: localStorage.getItem "colorScheme"
        icon: localStorage.getItem "icon"
        presentation: localStorage.getItem "presentation"
        website: localStorage.getItem "website"
        apiKey: localStorage.getItem "apiKey"

    # Adjust the API keys text to match the related features
    $scope.adjustFeaturesText = ->
        if $scope.hasYoutube && $scope.hasGplus
            $scope.features_text = "YouTube & Google+"
        else if $scope.hasYoutube
            $scope.features_text = "YouTube"
        else
            $scope.features_text = "Google+"

    # Validate the form and send the API request
    $scope.generateAPIRequest = ->
        isFormValid = true

        if $scope.config.appName == ""
            $('#appName').addClass "ng-invalid"
            isFormValid = false
        else
            $('#appName').removeClass "ng-invalid"

        if $scope.config.packageName == ""
            $('#packageName').addClass "ng-invalid"
            isFormValid = false
        else
            $('#packageName').removeClass "ng-invalid"

        if $scope.config.colorScheme == ""
            $('#colorScheme').addClass "ng-invalid"
            isFormValid = false
        else
            $('#colorScheme').removeClass "ng-invalid"

        if $scope.config.presentation == ""
            $('textarea[name=presentation]').addClass "ng-invalid"
            isFormValid = false
        else
            $('textarea[name=presentation]').removeClass "ng-invalid"

        if $scope.config.icon == ""
            $('input[name=icon]').addClass "ng-invalid"
            isFormValid = false
        else
            $('input[name=icon]').removeClass "ng-invalid"

        if $scope.config.website == ""
            $('input[name=website]').addClass "ng-invalid"
            isFormValid = false
        else
            $('input[name=website]').removeClass "ng-invalid"

        if $scope.config.apiKey == ""
            $('#apiKey').addClass "ng-invalid"
            isFormValid = false
        else
            $('#apiKey').removeClass "ng-invalid"

        if $scope.hasYoutube and $scope.features.youtube == ""
            $('#youtube').addClass "ng-invalid"
            isFormValid = false
        else
            $('#youtube').removeClass "ng-invalid"

        if $scope.hasGplus and $scope.features.gplus == ""
            $('#gplus').addClass "ng-invalid"
            isFormValid = false
        else
            $('#gplus').removeClass "ng-invalid"

        saveToLocalStorage()

        if isFormValid
            request =
                    "/app/" + encodeURIComponent($scope.config.appName) +
                    "/package/" + encodeURIComponent($scope.config.packageName) +
                    "/color/" + encodeURIComponent($scope.config.colorScheme) +
                    "/icon/" + encodeURIComponent($scope.config.icon) +
                    "/youtube/" + encodeURIComponent($scope.features.youtube) +
                    "/gplus/" + encodeURIComponent($scope.features.gplus) +
                    "/twitter/unimplemented/facebook/unimplemented" + # Unimplemented features
                    "/website/" + encodeURIComponent($scope.config.website) +
                    "/welcome_title/Welcome!" + # TODO: add a card to let the user set the welcome card's title
                    "/welcome_desc/" + encodeURIComponent($scope.config.presentation) +
                    "/api_key/" + encodeURIComponent($scope.config.apiKey)

            console.log request
            _dialog =
            """
            <div class="sign-in-dialog">
                 <div class="text-center">
                     <h1 class="teal no-margin-top">Building Your App</h1>
                 </div>
                 <div class="csspinner bar-follow" style="width: 450px; margin-top: 30px; margin-bottom: 80px;"></div>
            </div>
            """
            vex.dialog.open().html _dialog

            $http({method: 'GET', url: 'http://localhost:5000' + request})
            .success((data, status, headers, config) ->
                console.log data, status, headers, config
                vex.closeAll()
                window.location = data.apkUrl
            ).error((data, status, headers, config) ->
                console.log "error", data, status, headers, config
            )


    $scope.adjustFeaturesText()


    $scope.compileHtml = (html) ->
        linkThat = $compile html
        return linkThat $scope

    # Save the user input to localStorage
    saveToLocalStorage = () ->
        localStorage.setItem "appName", $scope.config.appName
        localStorage.setItem "packageName", $scope.config.packageName
        localStorage.setItem "colorScheme", $scope.config.colorScheme
        localStorage.setItem "icon", $scope.config.icon
        localStorage.setItem "presentation", $scope.config.presentation
        localStorage.setItem "apiKey", $scope.config.apiKey
        localStorage.setItem "hasYoutube", $scope.hasYoutube
        localStorage.setItem "youtube", $scope.features.youtube
        localStorage.setItem "hasGplus", $scope.hasGplus
        localStorage.setItem "gplus", $scope.features.gplus
        localStorage.setItem "website", $scope.config.website

