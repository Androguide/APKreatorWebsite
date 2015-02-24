// Generated by CoffeeScript 1.6.3
(function() {
  'use strict';
  angular.module('ngApkreator').directive('card', function() {
    return {
      restrict: 'E',
      scope: {},
      template: "<div class=\"ng-card\">\n    <div>\n        <h3 class=\"{{color}} no-margin-top\"><i class=\"glyphicon {{glyph}}\" ng-show=\"icon\"></i>{{title}}</h3>\n        <p class=\"\">{{text}}</p>\n        <input ng-show=\"hasInput\" type=\"text\" placeholder=\"{{placeholder}}\" ng-disabled=\"disabled\"/>\n    </div>\n</div>",
      link: function(scope, element, attrs) {
        scope.attrs = attrs;
        scope.title = scope.attrs.title;
        scope.text = scope.attrs.text;
        scope.glyph = scope.attrs.glyph;
        scope.icon = true;
        if (attrs.icon) {
          scope.icon = scope.attrs.icon;
        }
        if (attrs.color) {
          scope.color = scope.attrs.color;
        }
        if (attrs.disabled) {
          scope.disabled = scope.attrs.disabled;
        }
        if (scope.attrs.input) {
          scope.hasInput = true;
          return scope.placeholder = scope.attrs.placeholder;
        }
      }
    };
  });

}).call(this);

/*
//@ sourceMappingURL=card.map
*/