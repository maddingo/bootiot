'use strict';

/**
 * @ngdoc overview
 * @name publicApp
 * @description
 * # publicApp
 *
 * Main module of the application.
 */
angular
  .module('publicApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/logs', {
        templateUrl: 'views/logs.html',
        controller: 'LogsCtrl',
        controllerAs: 'logs'
      })
      .when('/entries', {
        templateUrl: 'views/entries.html',
        controller: 'EntriesCtrl',
        controllerAs: 'entries'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
