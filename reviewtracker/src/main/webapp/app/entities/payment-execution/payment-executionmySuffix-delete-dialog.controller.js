(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PaymentExecutionMySuffixDeleteController',PaymentExecutionMySuffixDeleteController);

    PaymentExecutionMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'PaymentExecution'];

    function PaymentExecutionMySuffixDeleteController($uibModalInstance, entity, PaymentExecution) {
        var vm = this;

        vm.paymentExecution = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PaymentExecution.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
