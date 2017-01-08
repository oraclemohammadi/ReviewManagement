(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PaymentExecutionMySuffixDetailController', PaymentExecutionMySuffixDetailController);

    PaymentExecutionMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PaymentExecution', 'PurchaseOrder'];

    function PaymentExecutionMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, PaymentExecution, PurchaseOrder) {
        var vm = this;

        vm.paymentExecution = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reviewtrackerApp:paymentExecutionUpdate', function(event, result) {
            vm.paymentExecution = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
