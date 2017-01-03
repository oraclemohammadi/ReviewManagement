(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('ShippingAddressMySuffixDeleteController',ShippingAddressMySuffixDeleteController);

    ShippingAddressMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'ShippingAddress'];

    function ShippingAddressMySuffixDeleteController($uibModalInstance, entity, ShippingAddress) {
        var vm = this;

        vm.shippingAddress = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ShippingAddress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
