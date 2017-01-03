(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PurchaseOrderMySuffixDetailController', PurchaseOrderMySuffixDetailController);

    PurchaseOrderMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PurchaseOrder', 'InvoiceData', 'PaymentExecution', 'ShippingAddress', 'PurchaseOrderItem'];

    function PurchaseOrderMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, PurchaseOrder, InvoiceData, PaymentExecution, ShippingAddress, PurchaseOrderItem) {
        var vm = this;

        vm.purchaseOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reviewtrackerApp:purchaseOrderUpdate', function(event, result) {
            vm.purchaseOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
