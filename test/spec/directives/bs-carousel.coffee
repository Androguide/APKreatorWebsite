'use strict'

describe 'Directive: bsCarousel', ->

  # load the directive's module
  beforeEach module 'ngApkreator'

  scope = {}

  beforeEach inject ($controller, $rootScope) ->
    scope = $rootScope.$new()

  it 'should make hidden element visible', inject ($compile) ->
    element = angular.element '<bs-carousel></bs-carousel>'
    element = $compile(element) scope
    expect(element.text()).toBe 'this is the bsCarousel directive'
