(function() {
    'use strict';
    angular
        .module('reviewtrackerApp')
        .factory('Review', Review);

    Review.$inject = ['$resource', 'DateUtils'];

    function Review ($resource, DateUtils) {
        var resourceUrl =  'api/reviews/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.reviewDate = DateUtils.convertLocalDateFromServer(data.reviewDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.reviewDate = DateUtils.convertLocalDateToServer(copy.reviewDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.reviewDate = DateUtils.convertLocalDateToServer(copy.reviewDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
