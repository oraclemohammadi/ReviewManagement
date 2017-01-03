(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('ShippingAddressMySuffixDetailController', ShippingAddressMySuffixDetailController);

    ShippingAddressMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ShippingAddress', 'PurchaseOrder'];

    function ShippingAddressMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, ShippingAddress, PurchaseOrder) {
        var vm = this;

        vm.shippingAddress = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reviewtrackerApp:shippingAddressUpdate', function(event, result) {
            vm.shippingAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
