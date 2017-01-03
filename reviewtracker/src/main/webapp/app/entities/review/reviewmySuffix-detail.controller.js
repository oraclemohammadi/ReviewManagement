(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('ReviewMySuffixDetailController', ReviewMySuffixDetailController);

    ReviewMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Review', 'Product'];

    function ReviewMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Review, Product) {
        var vm = this;

        vm.review = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reviewtrackerApp:reviewUpdate', function(event, result) {
            vm.review = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
