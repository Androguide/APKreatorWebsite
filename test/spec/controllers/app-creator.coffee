'use strict'

describe 'Controller: AppCreatorCtrl', ->

  # load the controller's module
  beforeEach module 'ngApp'

  AppCreatorCtrl = {}
  scope = {}

  # Initialize the controller and a mock scope
  beforeEach inject ($controller, $rootScope) ->
    scope = $rootScope.$new()
    AppCreatorCtrl = $controller 'AppCreatorCtrl', {
      $scope: scope
    }

  it 'should attach a list of awesomeThings to the scope', ->
    expect(scope.awesomeThings.length).toBe 3
