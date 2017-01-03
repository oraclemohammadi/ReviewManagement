(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PurchaseOrderMySuffixDeleteController',PurchaseOrderMySuffixDeleteController);

    PurchaseOrderMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'PurchaseOrder'];

    function PurchaseOrderMySuffixDeleteController($uibModalInstance, entity, PurchaseOrder) {
        var vm = this;

        vm.purchaseOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PurchaseOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
