(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PaymentExecutionMySuffixDialogController', PaymentExecutionMySuffixDialogController);

    PaymentExecutionMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PaymentExecution', 'PurchaseOrder'];

    function PaymentExecutionMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PaymentExecution, PurchaseOrder) {
        var vm = this;

        vm.paymentExecution = entity;
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
            if (vm.paymentExecution.id !== null) {
                PaymentExecution.update(vm.paymentExecution, onSaveSuccess, onSaveError);
            } else {
                PaymentExecution.save(vm.paymentExecution, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('reviewtrackerApp:paymentExecutionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
