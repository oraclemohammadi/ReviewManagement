(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('InvoiceDataMySuffixController', InvoiceDataMySuffixController);

    InvoiceDataMySuffixController.$inject = ['$scope', '$state', 'InvoiceData'];

    function InvoiceDataMySuffixController ($scope, $state, InvoiceData) {
        var vm = this;

        vm.invoiceData = [];

        loadAll();

        function loadAll() {
            InvoiceData.query(function(result) {
                vm.invoiceData = result;
                vm.searchQuery = null;
            });
        }
    }
})();
