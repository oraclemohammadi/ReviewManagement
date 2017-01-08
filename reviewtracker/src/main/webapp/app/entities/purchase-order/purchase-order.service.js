(function() {
    'use strict';
    angular
        .module('reviewtrackerApp')
        .factory('PurchaseOrder', PurchaseOrder);

    PurchaseOrder.$inject = ['$resource', 'DateUtils'];

    function PurchaseOrder ($resource, DateUtils) {
        var resourceUrl =  'api/purchase-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.purchaseDate = DateUtils.convertLocalDateFromServer(data.purchaseDate);
                        data.lastUpdateDate = DateUtils.convertLocalDateFromServer(data.lastUpdateDate);
                        data.earliestShipDate = DateUtils.convertLocalDateFromServer(data.earliestShipDate);
                        data.latestShipDate = DateUtils.convertLocalDateFromServer(data.latestShipDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.purchaseDate = DateUtils.convertLocalDateToServer(copy.purchaseDate);
                    copy.lastUpdateDate = DateUtils.convertLocalDateToServer(copy.lastUpdateDate);
                    copy.earliestShipDate = DateUtils.convertLocalDateToServer(copy.earliestShipDate);
                    copy.latestShipDate = DateUtils.convertLocalDateToServer(copy.latestShipDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.purchaseDate = DateUtils.convertLocalDateToServer(copy.purchaseDate);
                    copy.lastUpdateDate = DateUtils.convertLocalDateToServer(copy.lastUpdateDate);
                    copy.earliestShipDate = DateUtils.convertLocalDateToServer(copy.earliestShipDate);
                    copy.latestShipDate = DateUtils.convertLocalDateToServer(copy.latestShipDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
