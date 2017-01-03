(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('ReviewMySuffixDeleteController',ReviewMySuffixDeleteController);

    ReviewMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Review'];

    function ReviewMySuffixDeleteController($uibModalInstance, entity, Review) {
        var vm = this;

        vm.review = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Review.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
