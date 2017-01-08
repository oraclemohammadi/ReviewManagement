'use strict';

describe('Controller Tests', function() {

    describe('PurchaseOrder Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPurchaseOrder, MockInvoiceData, MockShippingAddress, MockPaymentExecution, MockPurchaseOrderItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPurchaseOrder = jasmine.createSpy('MockPurchaseOrder');
            MockInvoiceData = jasmine.createSpy('MockInvoiceData');
            MockShippingAddress = jasmine.createSpy('MockShippingAddress');
            MockPaymentExecution = jasmine.createSpy('MockPaymentExecution');
            MockPurchaseOrderItem = jasmine.createSpy('MockPurchaseOrderItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PurchaseOrder': MockPurchaseOrder,
                'InvoiceData': MockInvoiceData,
                'ShippingAddress': MockShippingAddress,
                'PaymentExecution': MockPaymentExecution,
                'PurchaseOrderItem': MockPurchaseOrderItem
            };
            createController = function() {
                $injector.get('$controller')("PurchaseOrderMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'reviewtrackerApp:purchaseOrderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
