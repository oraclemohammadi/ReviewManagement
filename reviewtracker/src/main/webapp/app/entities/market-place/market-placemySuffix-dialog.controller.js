(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('MarketPlaceMySuffixDialogController', MarketPlaceMySuffixDialogController);

    MarketPlaceMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MarketPlace'];

    function MarketPlaceMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MarketPlace) {
        var vm = this;

        vm.marketPlace = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.marketPlace.id !== null) {
                MarketPlace.update(vm.marketPlace, onSaveSuccess, onSaveError);
            } else {
                MarketPlace.save(vm.marketPlace, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('reviewtrackerApp:marketPlaceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
