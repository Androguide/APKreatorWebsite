'use strict'

angular.module('ngApkreator').controller 'SignInCtrl', ($scope, $rootScope) ->
    hoodie = new Hoodie()
    $scope.user = {name: "", password: ""}

    $scope.signUserIn = (isValid) ->
        $('.ng-pristine').addClass 'ng-invalid'
        if isValid
            hoodie.account.signIn($scope.user.name, $scope.user.password)
        else
            console.log "invalid form"

    hoodie.account.on 'signin', (user) ->
        console.log "Signed-in: ", user
        vex.closeAll()

    hoodie.account.on 'signout', (user) ->
        console.log "Goodbye  ", user