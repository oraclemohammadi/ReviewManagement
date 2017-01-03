(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('purchase-ordermySuffix', {
            parent: 'entity',
            url: '/purchase-ordermySuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PurchaseOrders'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchase-order/purchase-ordersmySuffix.html',
                    controller: 'PurchaseOrderMySuffixController',
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
        .state('purchase-ordermySuffix-detail', {
            parent: 'entity',
            url: '/purchase-ordermySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PurchaseOrder'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchase-order/purchase-ordermySuffix-detail.html',
                    controller: 'PurchaseOrderMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PurchaseOrder', function($stateParams, PurchaseOrder) {
                    return PurchaseOrder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'purchase-ordermySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('purchase-ordermySuffix-detail.edit', {
            parent: 'purchase-ordermySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order/purchase-ordermySuffix-dialog.html',
                    controller: 'PurchaseOrderMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PurchaseOrder', function(PurchaseOrder) {
                            return PurchaseOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('purchase-ordermySuffix.new', {
            parent: 'purchase-ordermySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order/purchase-ordermySuffix-dialog.html',
                    controller: 'PurchaseOrderMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sellerOrderId: null,
                                purchaseLocalDate: null,
                                lastUpdateLocalDate: null,
                                orderStatus: null,
                                fulfillmentChannel: null,
                                orderChannel: null,
                                shipServiceLevel: null,
                                orderTotal: null,
                                currencyCode: null,
                                amount: null,
                                numberOfItemsShipped: null,
                                numberOfItemsUnshipped: null,
                                paymentMethod: null,
                                marketplaceId: null,
                                buyerEmail: null,
                                buyerName: null,
                                shipmentServiceLevelCategory: null,
                                shippedByAmazonTFM: null,
                                tfmShipmentStatus: null,
                                cbaDisplayableShippingLabel: null,
                                orderType: null,
                                earliestShipLocalDate: null,
                                latestShipLocalDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('purchase-ordermySuffix', null, { reload: 'purchase-ordermySuffix' });
                }, function() {
                    $state.go('purchase-ordermySuffix');
                });
            }]
        })
        .state('purchase-ordermySuffix.edit', {
            parent: 'purchase-ordermySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order/purchase-ordermySuffix-dialog.html',
                    controller: 'PurchaseOrderMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PurchaseOrder', function(PurchaseOrder) {
                            return PurchaseOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchase-ordermySuffix', null, { reload: 'purchase-ordermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('purchase-ordermySuffix.delete', {
            parent: 'purchase-ordermySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order/purchase-ordermySuffix-delete-dialog.html',
                    controller: 'PurchaseOrderMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PurchaseOrder', function(PurchaseOrder) {
                            return PurchaseOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchase-ordermySuffix', null, { reload: 'purchase-ordermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
