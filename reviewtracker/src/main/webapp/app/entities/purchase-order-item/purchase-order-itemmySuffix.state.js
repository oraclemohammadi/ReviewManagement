(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('purchase-order-itemmySuffix', {
            parent: 'entity',
            url: '/purchase-order-itemmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PurchaseOrderItems'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchase-order-item/purchase-order-itemsmySuffix.html',
                    controller: 'PurchaseOrderItemMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('purchase-order-itemmySuffix-detail', {
            parent: 'entity',
            url: '/purchase-order-itemmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PurchaseOrderItem'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchase-order-item/purchase-order-itemmySuffix-detail.html',
                    controller: 'PurchaseOrderItemMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PurchaseOrderItem', function($stateParams, PurchaseOrderItem) {
                    return PurchaseOrderItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'purchase-order-itemmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('purchase-order-itemmySuffix-detail.edit', {
            parent: 'purchase-order-itemmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order-item/purchase-order-itemmySuffix-dialog.html',
                    controller: 'PurchaseOrderItemMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PurchaseOrderItem', function(PurchaseOrderItem) {
                            return PurchaseOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('purchase-order-itemmySuffix.new', {
            parent: 'purchase-order-itemmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order-item/purchase-order-itemmySuffix-dialog.html',
                    controller: 'PurchaseOrderItemMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                asin: null,
                                sellerSKU: null,
                                orderItemId: null,
                                title: null,
                                quantityOrdered: null,
                                quantityShipped: null,
                                itemPrice: null,
                                shippingPrice: null,
                                giftWrapPrice: null,
                                itemTax: null,
                                shippingTax: null,
                                giftWrapTax: null,
                                shippingDiscount: null,
                                promotionDiscount: null,
                                codFee: null,
                                codFeeDiscount: null,
                                giftMessageText: null,
                                giftWrapLevel: null,
                                conditionNote: null,
                                conditionId: null,
                                conditionSubtypeId: null,
                                scheduledDeliveryStartLocalDate: null,
                                scheduledDeliveryEndLocalDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('purchase-order-itemmySuffix', null, { reload: 'purchase-order-itemmySuffix' });
                }, function() {
                    $state.go('purchase-order-itemmySuffix');
                });
            }]
        })
        .state('purchase-order-itemmySuffix.edit', {
            parent: 'purchase-order-itemmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order-item/purchase-order-itemmySuffix-dialog.html',
                    controller: 'PurchaseOrderItemMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PurchaseOrderItem', function(PurchaseOrderItem) {
                            return PurchaseOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchase-order-itemmySuffix', null, { reload: 'purchase-order-itemmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('purchase-order-itemmySuffix.delete', {
            parent: 'purchase-order-itemmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order-item/purchase-order-itemmySuffix-delete-dialog.html',
                    controller: 'PurchaseOrderItemMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PurchaseOrderItem', function(PurchaseOrderItem) {
                            return PurchaseOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchase-order-itemmySuffix', null, { reload: 'purchase-order-itemmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
