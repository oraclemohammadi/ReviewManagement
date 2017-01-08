(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('MarketPlaceMySuffixDetailController', MarketPlaceMySuffixDetailController);

    MarketPlaceMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MarketPlace'];

    function MarketPlaceMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, MarketPlace) {
        var vm = this;

        vm.marketPlace = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reviewtrackerApp:marketPlaceUpdate', function(event, result) {
            vm.marketPlace = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
