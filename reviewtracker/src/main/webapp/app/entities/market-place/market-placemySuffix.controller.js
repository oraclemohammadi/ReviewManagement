(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('MarketPlaceMySuffixController', MarketPlaceMySuffixController);

    MarketPlaceMySuffixController.$inject = ['$scope', '$state', 'MarketPlace'];

    function MarketPlaceMySuffixController ($scope, $state, MarketPlace) {
        var vm = this;

        vm.marketPlaces = [];

        loadAll();

        function loadAll() {
            MarketPlace.query(function(result) {
                vm.marketPlaces = result;
                vm.searchQuery = null;
            });
        }
    }
})();
