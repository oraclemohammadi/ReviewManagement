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
                        data.purchaseLocalDate = DateUtils.convertLocalDateFromServer(data.purchaseLocalDate);
                        data.lastUpdateLocalDate = DateUtils.convertLocalDateFromServer(data.lastUpdateLocalDate);
                        data.earliestShipLocalDate = DateUtils.convertLocalDateFromServer(data.earliestShipLocalDate);
                        data.latestShipLocalDate = DateUtils.convertLocalDateFromServer(data.latestShipLocalDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.purchaseLocalDate = DateUtils.convertLocalDateToServer(copy.purchaseLocalDate);
                    copy.lastUpdateLocalDate = DateUtils.convertLocalDateToServer(copy.lastUpdateLocalDate);
                    copy.earliestShipLocalDate = DateUtils.convertLocalDateToServer(copy.earliestShipLocalDate);
                    copy.latestShipLocalDate = DateUtils.convertLocalDateToServer(copy.latestShipLocalDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.purchaseLocalDate = DateUtils.convertLocalDateToServer(copy.purchaseLocalDate);
                    copy.lastUpdateLocalDate = DateUtils.convertLocalDateToServer(copy.lastUpdateLocalDate);
                    copy.earliestShipLocalDate = DateUtils.convertLocalDateToServer(copy.earliestShipLocalDate);
                    copy.latestShipLocalDate = DateUtils.convertLocalDateToServer(copy.latestShipLocalDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
