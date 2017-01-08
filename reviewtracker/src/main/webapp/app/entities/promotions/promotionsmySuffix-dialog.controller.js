(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PromotionsMySuffixDialogController', PromotionsMySuffixDialogController);

    PromotionsMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Promotions', 'PurchaseOrderItem'];

    function PromotionsMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Promotions, PurchaseOrderItem) {
        var vm = this;

        vm.promotions = entity;
        vm.clear = clear;
        vm.save = save;
        vm.purchaseorderitems = PurchaseOrderItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.promotions.id !== null) {
                Promotions.update(vm.promotions, onSaveSuccess, onSaveError);
            } else {
                Promotions.save(vm.promotions, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('reviewtrackerApp:promotionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
