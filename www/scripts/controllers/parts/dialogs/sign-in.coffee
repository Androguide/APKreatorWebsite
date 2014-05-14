'use strict'

angular.module('ngApkreator').controller 'SignInCtrl', ($scope, $http) ->
    hoodie = new Hoodie()
    $scope.user = {name: "", password: ""}

    $scope.signUserIn = (isValid) ->
        $('.ng-pristine').addClass 'ng-invalid'
        if isValid
            $http.get('http://localhost:5000/is_confirmed/' + $scope.user.name)
            .success (data) ->
                console.log "is confirmed: ", data.confirmed
                if data.confirmed
                    hoodie.account.signIn($scope.user.name, $scope.user.password)
                    vex.closeAll()
                else
                    # TODO: implement 'resend the confirmation email' with some kind of rate limiting to avoid spammers
                    console.log "error", data.error, data.status
                    vex.dialog.alert "Please confirm your account by clicking on the link that was sent to you by email"
            .error (data, status) ->
                console.log "error", data, status

        else
            console.log "invalid form"

    hoodie.account.on 'signin', (user) ->
        console.log "Signed-in: ", user
        vex.closeAll()

    hoodie.account.on 'signout', (user) ->
        console.log "Goodbye  ", user