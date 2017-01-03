(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('ProductMySuffixDialogController', ProductMySuffixDialogController);

    ProductMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Product', 'Review', 'PurchaseOrderItem'];

    function ProductMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Product, Review, PurchaseOrderItem) {
        var vm = this;

        vm.product = entity;
        vm.clear = clear;
        vm.save = save;
        vm.reviews = Review.query();
        vm.purchaseorderitems = PurchaseOrderItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.product.id !== null) {
                Product.update(vm.product, onSaveSuccess, onSaveError);
            } else {
                Product.save(vm.product, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('reviewtrackerApp:productUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
