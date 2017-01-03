(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('InvoiceDataMySuffixDialogController', InvoiceDataMySuffixDialogController);

    InvoiceDataMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InvoiceData'];

    function InvoiceDataMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InvoiceData) {
        var vm = this;

        vm.invoiceData = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.invoiceData.id !== null) {
                InvoiceData.update(vm.invoiceData, onSaveSuccess, onSaveError);
            } else {
                InvoiceData.save(vm.invoiceData, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('reviewtrackerApp:invoiceDataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
