(function() {
    'use strict';
    angular
        .module('reviewtrackerApp')
        .factory('PurchaseOrderItem', PurchaseOrderItem);

    PurchaseOrderItem.$inject = ['$resource'];

    function PurchaseOrderItem ($resource) {
        var resourceUrl =  'api/purchase-order-items/:id';

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
