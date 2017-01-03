(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('ProductMySuffixDetailController', ProductMySuffixDetailController);

    ProductMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'Review', 'PurchaseOrderItem'];

    function ProductMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, Review, PurchaseOrderItem) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reviewtrackerApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
