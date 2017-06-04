'use strict';

/**
 * @ngdoc function
 * @name publicApp.controller:NavcontrollerCtrl
 * @description
 * # NavcontrollerCtrl
 * Controller of the publicApp
 */
angular.module('publicApp')
  .controller('NavcontrollerCtrl', function ($scope, $location) {
    $scope.isActive = function(viewLocation) {
      return viewLocation === $location.path();
    }
  });
