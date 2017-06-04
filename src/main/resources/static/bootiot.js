angular.module('bootiot', ['ngRoute'])

//.controller('BootiotController', function() {})

.config(function($routeProvider) {
  $routeProvider
    .when('/', {
      controller:'BootiotController',
      controllerAs: 'bootiot',
      templateUrl:'home.html',
    })
    .when('/measurements', {
      controller:'BootiotController',
      controllerAs: 'bootiot',
      templateUrl:'measuremetns.html',
    })
    .when('/logs', {
      controller:'BootiotController',
      controllerAs: 'bootiot',
      templateUrl:'logs.html',
    })
    .otherwise({
      redirectTo:'/'
    });
})

/*
.directive('detectActiveTab', function ($location) {
  return {
    link: function postLink(scope, element, attrs) {
    scope.$on("$routeChangeSuccess", function(event, current, previous) {
      // This var grabs the tab-level off the attribute, or defaults to 1
      var pathLevel = attrs.detectActiveTab || 1,
          // This var finds what the path is at the level specified
          pathToCheck = $location.path().split('/')[pathLevel] || "current $location.path doesn't reach this level",
          // This var finds grabs the same level of the href attribute
          tabLink = attrs.href.split('/')[pathLevel] || "href doesn't include this level";

        // now compare the two:
        if (pathToCheck === tabLink) {
            element.parent('li').addClass("active");
        } else {
            element.parent('li').removeClass("active");
        }
      });
    }
  };
})
*/