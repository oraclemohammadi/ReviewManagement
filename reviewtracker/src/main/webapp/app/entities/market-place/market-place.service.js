(function() {
    'use strict';
    angular
        .module('reviewtrackerApp')
        .factory('MarketPlace', MarketPlace);

    MarketPlace.$inject = ['$resource'];

    function MarketPlace ($resource) {
        var resourceUrl =  'api/market-places/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
