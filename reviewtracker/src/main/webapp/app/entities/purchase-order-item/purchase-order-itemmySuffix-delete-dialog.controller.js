(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PurchaseOrderItemMySuffixDeleteController',PurchaseOrderItemMySuffixDeleteController);

    PurchaseOrderItemMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'PurchaseOrderItem'];

    function PurchaseOrderItemMySuffixDeleteController($uibModalInstance, entity, PurchaseOrderItem) {
        var vm = this;

        vm.purchaseOrderItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PurchaseOrderItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
