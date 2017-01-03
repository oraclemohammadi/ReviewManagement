(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PromotionsMySuffixDetailController', PromotionsMySuffixDetailController);

    PromotionsMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Promotions'];

    function PromotionsMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Promotions) {
        var vm = this;

        vm.promotions = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reviewtrackerApp:promotionsUpdate', function(event, result) {
            vm.promotions = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
