(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('shipping-addressmySuffix', {
            parent: 'entity',
            url: '/shipping-addressmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShippingAddresses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipping-address/shipping-addressesmySuffix.html',
                    controller: 'ShippingAddressMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('shipping-addressmySuffix-detail', {
            parent: 'entity',
            url: '/shipping-addressmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShippingAddress'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipping-address/shipping-addressmySuffix-detail.html',
                    controller: 'ShippingAddressMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ShippingAddress', function($stateParams, ShippingAddress) {
                    return ShippingAddress.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'shipping-addressmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('shipping-addressmySuffix-detail.edit', {
            parent: 'shipping-addressmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipping-address/shipping-addressmySuffix-dialog.html',
                    controller: 'ShippingAddressMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShippingAddress', function(ShippingAddress) {
                            return ShippingAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipping-addressmySuffix.new', {
            parent: 'shipping-addressmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipping-address/shipping-addressmySuffix-dialog.html',
                    controller: 'ShippingAddressMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                addressLine1: null,
                                addressLine2: null,
                                addressLine3: null,
                                city: null,
                                county: null,
                                postalCode: null,
                                district: null,
                                stateOrRegion: null,
                                phone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('shipping-addressmySuffix', null, { reload: 'shipping-addressmySuffix' });
                }, function() {
                    $state.go('shipping-addressmySuffix');
                });
            }]
        })
        .state('shipping-addressmySuffix.edit', {
            parent: 'shipping-addressmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipping-address/shipping-addressmySuffix-dialog.html',
                    controller: 'ShippingAddressMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShippingAddress', function(ShippingAddress) {
                            return ShippingAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipping-addressmySuffix', null, { reload: 'shipping-addressmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipping-addressmySuffix.delete', {
            parent: 'shipping-addressmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipping-address/shipping-addressmySuffix-delete-dialog.html',
                    controller: 'ShippingAddressMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ShippingAddress', function(ShippingAddress) {
                            return ShippingAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipping-addressmySuffix', null, { reload: 'shipping-addressmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
