'use strict'

angular.module('ngApkreator').controller 'HeaderCtrl', ($scope, $rootScope, $compile, $http) ->
    hoodie = new Hoodie()
    $rootScope.account = {}
    $rootScope.dropdown = {label: "Account"}
    $scope.username = hoodie.account.username

    $rootScope.headerMenuItems = [
        {name: "Home", url: "#/"}
        {name: "App Creator", url: "#/app-creator"}
        {name: "Features", url: "#/features"}
        {name: "Contact", url: "#"}
    ]

    if hoodie.account.username # the user is logged in
        $rootScope.dropdown.label = $scope.username
        $rootScope.account.dropdownItems = [
            {name: "My Account", "route": ""}
            {name: "My Apps", "route": ""}
            {name: "Sign Out", onclick: "signOut()"}
        ]

    else # the user is anonymous
        $rootScope.account.dropdownItems = [
            {name: "Sign In", onclick: "signIn()"}
            {name: "Sign Up", onclick: "signUp()"}
        ]

    # Show sign-in dialog and ng-include its html from a separate template
    $scope.signIn = ->
        dialog = $scope.compileHtml """<div ng-include ng-controller="SignInCtrl" src="'views/parts/dialogs/sign-in.html'"></div>"""
        vex.open().append(dialog).bind "vexClose", ->
            hoodie = new Hoodie()
            if hoodie.account.username
                $rootScope.account.dropdownItems = [
                    {name: "My Account", "route": ""}
                    {name: "My Apps", "route": ""}
                    {name: "Sign Out", onclick: "signOut()"}
                ]
                $rootScope.dropdown.label = hoodie.account.username
                $scope.$digest()
        return # this explicit return is mandatory, otherwise CoffeeScript will return the html, causing angular to throw a security error when there really isn't.

    $scope.signUp = ->
        dialog = $scope.compileHtml """<div ng-include ng-controller="SignUpCtrl" src="'views/parts/dialogs/sign-up.html'"></div>"""
        vex.open().append(dialog).bind "vexClose", ->
            hoodie = new Hoodie()
            if hoodie.account.username
                $http.get("http://localhost:5000/is_confirmed/" + hoodie.account.username).success (data) ->
                    if data.confirmed == true
                        $rootScope.account.dropdownItems = [
                            {name: "My Account", "route": ""}
                            {name: "My Apps", "route": ""}
                            {name: "Sign Out", onclick: "signOut()"}
                        ]

                        userInfos = hoodie.store.findAll("task")
                        if userInfos.username
                            userInfos.username
                        else
                            $rootScope.dropdown.label = hoodie.account.username
                    else
                        console.log "not confirmed yet!"

#                    $scope.$digest()
                $scope.signOut()
        return

    # Sign the user out and update the dropdown
    $scope.signOut = ->
        hoodie.account.signOut()
        $rootScope.dropdown = {label: "Sign In"}
        $rootScope.account.dropdownItems = [
            {name: "Sign In", onclick: "signIn()"}
            {name: "Sign Up", onclick: "signUp()"}
        ]

    # Parse object properties as expressions to allow passing functions through the data-binding (ex: the onclick props above)
    $scope.expr = (expr, locals) ->
        return $scope.$eval(expr, locals)

    # We need to manually compile & link angular-enabled html to $scope before appending it
    $scope.compileHtml = (html) ->
        linkThat = $compile html
        return linkThat $scope