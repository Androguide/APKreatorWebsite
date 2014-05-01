'use strict'

angular.module('ngApkreator').directive('card', ->
    restrict: 'EA'
    scope: {}
    template: """
        <div class="card">
            <div class="text-center">
                <h3 class="{{color}} no-margin-top"><i class="glyphicon {{glyph}}"></i>&nbsp;&nbsp;{{title}}</h3>
            </div>
            <p class="text-center">{{text}}</p>
        </div>
        """

    link: (scope, element, attrs) ->
        scope.attrs = attrs
        scope.title = scope.attrs.title
        scope.text = scope.attrs.text
        scope.glyph = scope.attrs.glyph
        scope.color = "asphalt"
        if attrs.color then scope.color = scope.attrs.color
)
