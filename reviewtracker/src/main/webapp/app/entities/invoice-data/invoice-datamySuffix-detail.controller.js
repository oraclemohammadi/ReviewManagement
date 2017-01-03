(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('InvoiceDataMySuffixDetailController', InvoiceDataMySuffixDetailController);

    InvoiceDataMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InvoiceData'];

    function InvoiceDataMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, InvoiceData) {
        var vm = this;

        vm.invoiceData = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reviewtrackerApp:invoiceDataUpdate', function(event, result) {
            vm.invoiceData = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
