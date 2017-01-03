(function() {
    'use strict';
    angular
        .module('reviewtrackerApp')
        .factory('ShippingAddress', ShippingAddress);

    ShippingAddress.$inject = ['$resource'];

    function ShippingAddress ($resource) {
        var resourceUrl =  'api/shipping-addresses/:id';

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
