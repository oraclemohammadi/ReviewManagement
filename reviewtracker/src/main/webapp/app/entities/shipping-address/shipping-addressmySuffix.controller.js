(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('ShippingAddressMySuffixController', ShippingAddressMySuffixController);

    ShippingAddressMySuffixController.$inject = ['$scope', '$state', 'ShippingAddress'];

    function ShippingAddressMySuffixController ($scope, $state, ShippingAddress) {
        var vm = this;

        vm.shippingAddresses = [];

        loadAll();

        function loadAll() {
            ShippingAddress.query(function(result) {
                vm.shippingAddresses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
