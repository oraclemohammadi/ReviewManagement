(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('ShippingAddressMySuffixDialogController', ShippingAddressMySuffixDialogController);

    ShippingAddressMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ShippingAddress', 'PurchaseOrder'];

    function ShippingAddressMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ShippingAddress, PurchaseOrder) {
        var vm = this;

        vm.shippingAddress = entity;
        vm.clear = clear;
        vm.save = save;
        vm.purchaseorders = PurchaseOrder.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shippingAddress.id !== null) {
                ShippingAddress.update(vm.shippingAddress, onSaveSuccess, onSaveError);
            } else {
                ShippingAddress.save(vm.shippingAddress, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('reviewtrackerApp:shippingAddressUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
