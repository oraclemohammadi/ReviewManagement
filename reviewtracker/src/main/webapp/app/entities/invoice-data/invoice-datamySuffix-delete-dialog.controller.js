(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('InvoiceDataMySuffixDeleteController',InvoiceDataMySuffixDeleteController);

    InvoiceDataMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'InvoiceData'];

    function InvoiceDataMySuffixDeleteController($uibModalInstance, entity, InvoiceData) {
        var vm = this;

        vm.invoiceData = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InvoiceData.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
