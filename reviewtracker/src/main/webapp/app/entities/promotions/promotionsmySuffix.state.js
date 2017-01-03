(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('promotionsmySuffix', {
            parent: 'entity',
            url: '/promotionsmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Promotions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/promotions/promotionsmySuffix.html',
                    controller: 'PromotionsMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('promotionsmySuffix-detail', {
            parent: 'entity',
            url: '/promotionsmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Promotions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/promotions/promotionsmySuffix-detail.html',
                    controller: 'PromotionsMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Promotions', function($stateParams, Promotions) {
                    return Promotions.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'promotionsmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('promotionsmySuffix-detail.edit', {
            parent: 'promotionsmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotions/promotionsmySuffix-dialog.html',
                    controller: 'PromotionsMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Promotions', function(Promotions) {
                            return Promotions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('promotionsmySuffix.new', {
            parent: 'promotionsmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotions/promotionsmySuffix-dialog.html',
                    controller: 'PromotionsMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                promotionId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('promotionsmySuffix', null, { reload: 'promotionsmySuffix' });
                }, function() {
                    $state.go('promotionsmySuffix');
                });
            }]
        })
        .state('promotionsmySuffix.edit', {
            parent: 'promotionsmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotions/promotionsmySuffix-dialog.html',
                    controller: 'PromotionsMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Promotions', function(Promotions) {
                            return Promotions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('promotionsmySuffix', null, { reload: 'promotionsmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('promotionsmySuffix.delete', {
            parent: 'promotionsmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotions/promotionsmySuffix-delete-dialog.html',
                    controller: 'PromotionsMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Promotions', function(Promotions) {
                            return Promotions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('promotionsmySuffix', null, { reload: 'promotionsmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
