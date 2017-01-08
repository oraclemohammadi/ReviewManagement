'use strict';

describe('Controller Tests', function() {

    describe('PurchaseOrderItem Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPurchaseOrderItem, MockProduct, MockPurchaseOrder, MockPromotions;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPurchaseOrderItem = jasmine.createSpy('MockPurchaseOrderItem');
            MockProduct = jasmine.createSpy('MockProduct');
            MockPurchaseOrder = jasmine.createSpy('MockPurchaseOrder');
            MockPromotions = jasmine.createSpy('MockPromotions');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PurchaseOrderItem': MockPurchaseOrderItem,
                'Product': MockProduct,
                'PurchaseOrder': MockPurchaseOrder,
                'Promotions': MockPromotions
            };
            createController = function() {
                $injector.get('$controller')("PurchaseOrderItemMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'reviewtrackerApp:purchaseOrderItemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
