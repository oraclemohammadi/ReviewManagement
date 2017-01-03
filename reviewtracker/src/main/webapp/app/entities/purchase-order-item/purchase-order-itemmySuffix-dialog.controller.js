(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PurchaseOrderItemMySuffixDialogController', PurchaseOrderItemMySuffixDialogController);

    PurchaseOrderItemMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PurchaseOrderItem', 'Product', 'PurchaseOrder'];

    function PurchaseOrderItemMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PurchaseOrderItem, Product, PurchaseOrder) {
        var vm = this;

        vm.purchaseOrderItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.products = Product.query();
        vm.purchaseorders = PurchaseOrder.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.purchaseOrderItem.id !== null) {
                PurchaseOrderItem.update(vm.purchaseOrderItem, onSaveSuccess, onSaveError);
            } else {
                PurchaseOrderItem.save(vm.purchaseOrderItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('reviewtrackerApp:purchaseOrderItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
