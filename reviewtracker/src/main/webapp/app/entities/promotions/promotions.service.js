(function() {
    'use strict';
    angular
        .module('reviewtrackerApp')
        .factory('Promotions', Promotions);

    Promotions.$inject = ['$resource'];

    function Promotions ($resource) {
        var resourceUrl =  'api/promotions/:id';

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
