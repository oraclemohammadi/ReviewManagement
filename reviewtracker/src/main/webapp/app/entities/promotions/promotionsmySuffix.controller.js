(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PromotionsMySuffixController', PromotionsMySuffixController);

    PromotionsMySuffixController.$inject = ['$scope', '$state', 'Promotions'];

    function PromotionsMySuffixController ($scope, $state, Promotions) {
        var vm = this;

        vm.promotions = [];

        loadAll();

        function loadAll() {
            Promotions.query(function(result) {
                vm.promotions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
