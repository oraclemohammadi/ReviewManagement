(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PurchaseOrderItemMySuffixDetailController', PurchaseOrderItemMySuffixDetailController);

    PurchaseOrderItemMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PurchaseOrderItem', 'Product', 'PurchaseOrder'];

    function PurchaseOrderItemMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, PurchaseOrderItem, Product, PurchaseOrder) {
        var vm = this;

        vm.purchaseOrderItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reviewtrackerApp:purchaseOrderItemUpdate', function(event, result) {
            vm.purchaseOrderItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
