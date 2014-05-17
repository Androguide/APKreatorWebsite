'use strict'

angular.module('ngApkreator').controller 'SignUpCtrl', ($scope, $http) ->
    $scope.user = {}
    hoodie = new Hoodie()

    $scope.signUserUp = (isValid) ->
        console.log "signUserUp()", isValid
        $('.ng-pristine').addClass 'ng-invalid'
        if $scope.user.password == $scope.user.confirm
            $scope.user.pwd_error = ""
            $scope.passwordMatch = true
        else
            $scope.user.pwd_error = "Passwords don't match!"

        if isValid && $scope.user.password == $scope.user.confirm
            hoodie.account.signUp($scope.user.email, $scope.user.password).fail (e) ->
                console.log "Sign-up Error: ", e
                switch e.status
                    when 409
                        vex.dialog.alert "The email address " + $scope.user.email + " is already registered."
                    else return

    hoodie.account.on 'signup', (user) ->
        console.log "Signed-up: ", user
        # Store the user infos
        hoodie.store.add("task", {
            firstname: $scope.user.firstname
            lastname: $scope.user.lastname
            username: $scope.user.username
        }).done (obj) ->
            console.log "success: ", obj
            # Ask the API to send an email and confirmation link to the new user
            $http.get('http://localhost:5000/send_confirmation/' + $scope.user.email).success (data) ->
                console.log data
                vex.closeAll()
                vex.dialog.alert "Thank you for signing-up, " + $scope.user.username + ".<br>An email containing a confirmation link was sent to " + $scope.user.email + ", please click on it to confirm your account."
            .error (data, status) ->
                console.log 'Error while sending mail: ' + status + ' : ' + data
                vex.dialog.alert "Sorry, there was an error (" + status + "). Please try again."

        .fail (obj) ->
            console.log "fail: ", obj