(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reviewmySuffix', {
            parent: 'entity',
            url: '/reviewmySuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Reviews'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/review/reviewsmySuffix.html',
                    controller: 'ReviewMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('reviewmySuffix-detail', {
            parent: 'entity',
            url: '/reviewmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Review'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/review/reviewmySuffix-detail.html',
                    controller: 'ReviewMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Review', function($stateParams, Review) {
                    return Review.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reviewmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reviewmySuffix-detail.edit', {
            parent: 'reviewmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/review/reviewmySuffix-dialog.html',
                    controller: 'ReviewMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Review', function(Review) {
                            return Review.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reviewmySuffix.new', {
            parent: 'reviewmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/review/reviewmySuffix-dialog.html',
                    controller: 'ReviewMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                itemID: null,
                                reviewID: null,
                                customerName: null,
                                customerID: null,
                                title: null,
                                rating: null,
                                fullRating: null,
                                helpfulVotes: null,
                                totalVotes: null,
                                verifiedPurchase: null,
                                realName: null,
                                reviewLocalDate: null,
                                content: null,
                                specificNote: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reviewmySuffix', null, { reload: 'reviewmySuffix' });
                }, function() {
                    $state.go('reviewmySuffix');
                });
            }]
        })
        .state('reviewmySuffix.edit', {
            parent: 'reviewmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/review/reviewmySuffix-dialog.html',
                    controller: 'ReviewMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Review', function(Review) {
                            return Review.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reviewmySuffix', null, { reload: 'reviewmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reviewmySuffix.delete', {
            parent: 'reviewmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/review/reviewmySuffix-delete-dialog.html',
                    controller: 'ReviewMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Review', function(Review) {
                            return Review.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reviewmySuffix', null, { reload: 'reviewmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
