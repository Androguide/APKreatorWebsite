'use strict'

angular.module('ngApkreator').directive('bsCarousel', ->
    template:
      """
      <div id="carousel-home-apkreator" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
          <li ng-repeat="item in carouselItems" data-target="#carousel-home-apkreator" data-slide-to="{{$index}}" class="{{item.class}}"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner">
          <div ng-repeat="item in carouselItems" class="item {{item.class}}">
            <center><img class="img-responsive" ng-src="{{item.image}}" alt="{{item.alt}}"/></center>
          </div>
        </div>
      </div>
      """
    restrict: 'E'

    link: (scope, element, attrs) ->

  )
