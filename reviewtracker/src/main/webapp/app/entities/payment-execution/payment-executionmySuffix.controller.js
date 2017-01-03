(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PaymentExecutionMySuffixController', PaymentExecutionMySuffixController);

    PaymentExecutionMySuffixController.$inject = ['$scope', '$state', 'PaymentExecution'];

    function PaymentExecutionMySuffixController ($scope, $state, PaymentExecution) {
        var vm = this;

        vm.paymentExecutions = [];

        loadAll();

        function loadAll() {
            PaymentExecution.query(function(result) {
                vm.paymentExecutions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
