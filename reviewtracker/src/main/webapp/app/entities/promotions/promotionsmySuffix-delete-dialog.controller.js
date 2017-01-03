(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PromotionsMySuffixDeleteController',PromotionsMySuffixDeleteController);

    PromotionsMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Promotions'];

    function PromotionsMySuffixDeleteController($uibModalInstance, entity, Promotions) {
        var vm = this;

        vm.promotions = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Promotions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
