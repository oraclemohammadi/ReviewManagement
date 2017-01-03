(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('invoice-datamySuffix', {
            parent: 'entity',
            url: '/invoice-datamySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InvoiceData'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/invoice-data/invoice-datamySuffix.html',
                    controller: 'InvoiceDataMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('invoice-datamySuffix-detail', {
            parent: 'entity',
            url: '/invoice-datamySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InvoiceData'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/invoice-data/invoice-datamySuffix-detail.html',
                    controller: 'InvoiceDataMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'InvoiceData', function($stateParams, InvoiceData) {
                    return InvoiceData.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'invoice-datamySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('invoice-datamySuffix-detail.edit', {
            parent: 'invoice-datamySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invoice-data/invoice-datamySuffix-dialog.html',
                    controller: 'InvoiceDataMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InvoiceData', function(InvoiceData) {
                            return InvoiceData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('invoice-datamySuffix.new', {
            parent: 'invoice-datamySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invoice-data/invoice-datamySuffix-dialog.html',
                    controller: 'InvoiceDataMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                invoiceRequirement: null,
                                buyerSelectedInvoiceCategory: null,
                                invoiceTitle: null,
                                invoiceInformation: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('invoice-datamySuffix', null, { reload: 'invoice-datamySuffix' });
                }, function() {
                    $state.go('invoice-datamySuffix');
                });
            }]
        })
        .state('invoice-datamySuffix.edit', {
            parent: 'invoice-datamySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invoice-data/invoice-datamySuffix-dialog.html',
                    controller: 'InvoiceDataMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InvoiceData', function(InvoiceData) {
                            return InvoiceData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('invoice-datamySuffix', null, { reload: 'invoice-datamySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('invoice-datamySuffix.delete', {
            parent: 'invoice-datamySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invoice-data/invoice-datamySuffix-delete-dialog.html',
                    controller: 'InvoiceDataMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InvoiceData', function(InvoiceData) {
                            return InvoiceData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('invoice-datamySuffix', null, { reload: 'invoice-datamySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
