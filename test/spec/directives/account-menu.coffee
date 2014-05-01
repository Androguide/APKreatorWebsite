'use strict'

describe 'Directive: accountMenu', ->

  # load the directive's module
  beforeEach module 'ngApp'

  scope = {}

  beforeEach inject ($controller, $rootScope) ->
    scope = $rootScope.$new()

  it 'should make hidden element visible', inject ($compile) ->
    element = angular.element '<account-menu></account-menu>'
    element = $compile(element) scope
    expect(element.text()).toBe 'this is the accountMenu directive'
