(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('market-placemySuffix', {
            parent: 'entity',
            url: '/market-placemySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MarketPlaces'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/market-place/market-placesmySuffix.html',
                    controller: 'MarketPlaceMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('market-placemySuffix-detail', {
            parent: 'entity',
            url: '/market-placemySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MarketPlace'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/market-place/market-placemySuffix-detail.html',
                    controller: 'MarketPlaceMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MarketPlace', function($stateParams, MarketPlace) {
                    return MarketPlace.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'market-placemySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('market-placemySuffix-detail.edit', {
            parent: 'market-placemySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market-place/market-placemySuffix-dialog.html',
                    controller: 'MarketPlaceMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MarketPlace', function(MarketPlace) {
                            return MarketPlace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('market-placemySuffix.new', {
            parent: 'market-placemySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market-place/market-placemySuffix-dialog.html',
                    controller: 'MarketPlaceMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                accessKey: null,
                                secrectKey: null,
                                sellerId: null,
                                marketPlaceId: null,
                                serviceUrl: null,
                                activated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('market-placemySuffix', null, { reload: 'market-placemySuffix' });
                }, function() {
                    $state.go('market-placemySuffix');
                });
            }]
        })
        .state('market-placemySuffix.edit', {
            parent: 'market-placemySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market-place/market-placemySuffix-dialog.html',
                    controller: 'MarketPlaceMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MarketPlace', function(MarketPlace) {
                            return MarketPlace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('market-placemySuffix', null, { reload: 'market-placemySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('market-placemySuffix.delete', {
            parent: 'market-placemySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market-place/market-placemySuffix-delete-dialog.html',
                    controller: 'MarketPlaceMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MarketPlace', function(MarketPlace) {
                            return MarketPlace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('market-placemySuffix', null, { reload: 'market-placemySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
