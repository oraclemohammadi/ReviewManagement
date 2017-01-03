(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('payment-executionmySuffix', {
            parent: 'entity',
            url: '/payment-executionmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PaymentExecutions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payment-execution/payment-executionsmySuffix.html',
                    controller: 'PaymentExecutionMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('payment-executionmySuffix-detail', {
            parent: 'entity',
            url: '/payment-executionmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PaymentExecution'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payment-execution/payment-executionmySuffix-detail.html',
                    controller: 'PaymentExecutionMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PaymentExecution', function($stateParams, PaymentExecution) {
                    return PaymentExecution.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'payment-executionmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('payment-executionmySuffix-detail.edit', {
            parent: 'payment-executionmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-execution/payment-executionmySuffix-dialog.html',
                    controller: 'PaymentExecutionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PaymentExecution', function(PaymentExecution) {
                            return PaymentExecution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payment-executionmySuffix.new', {
            parent: 'payment-executionmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-execution/payment-executionmySuffix-dialog.html',
                    controller: 'PaymentExecutionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                paymentAmount: null,
                                currencyCode: null,
                                paymentMethod: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('payment-executionmySuffix', null, { reload: 'payment-executionmySuffix' });
                }, function() {
                    $state.go('payment-executionmySuffix');
                });
            }]
        })
        .state('payment-executionmySuffix.edit', {
            parent: 'payment-executionmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-execution/payment-executionmySuffix-dialog.html',
                    controller: 'PaymentExecutionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PaymentExecution', function(PaymentExecution) {
                            return PaymentExecution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment-executionmySuffix', null, { reload: 'payment-executionmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payment-executionmySuffix.delete', {
            parent: 'payment-executionmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-execution/payment-executionmySuffix-delete-dialog.html',
                    controller: 'PaymentExecutionMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PaymentExecution', function(PaymentExecution) {
                            return PaymentExecution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment-executionmySuffix', null, { reload: 'payment-executionmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
