(function() {
    'use strict';
    angular
        .module('reviewtrackerApp')
        .factory('InvoiceData', InvoiceData);

    InvoiceData.$inject = ['$resource'];

    function InvoiceData ($resource) {
        var resourceUrl =  'api/invoice-data/:id';

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
