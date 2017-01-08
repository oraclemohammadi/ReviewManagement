(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('MarketPlaceMySuffixDeleteController',MarketPlaceMySuffixDeleteController);

    MarketPlaceMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'MarketPlace'];

    function MarketPlaceMySuffixDeleteController($uibModalInstance, entity, MarketPlace) {
        var vm = this;

        vm.marketPlace = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MarketPlace.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
