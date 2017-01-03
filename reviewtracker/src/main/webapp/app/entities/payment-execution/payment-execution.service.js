(function() {
    'use strict';
    angular
        .module('reviewtrackerApp')
        .factory('PaymentExecution', PaymentExecution);

    PaymentExecution.$inject = ['$resource'];

    function PaymentExecution ($resource) {
        var resourceUrl =  'api/payment-executions/:id';

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
