'use strict'

angular.module('ngApkreator').directive('card', ->
    restrict: 'E'
    scope: {}
    template: """
        <div class="ng-card">
            <div>
                <h3 class="{{color}} no-margin-top"><i class="glyphicon {{glyph}}" ng-show="icon"></i>{{title}}</h3>
                <p class="">{{text}}</p>
                <input ng-show="hasInput" type="text" placeholder="{{placeholder}}" ng-disabled="disabled"/>
            </div>
        </div>
        """

    link: (scope, element, attrs) ->
        scope.attrs = attrs
        scope.title = scope.attrs.title
        scope.text = scope.attrs.text
        scope.glyph = scope.attrs.glyph
        scope.icon = true
        if attrs.icon then scope.icon = scope.attrs.icon
        if attrs.color then scope.color = scope.attrs.color
        if attrs.disabled then scope.disabled = scope.attrs.disabled

        if scope.attrs.input
            scope.hasInput = true
            scope.placeholder = scope.attrs.placeholder
)
