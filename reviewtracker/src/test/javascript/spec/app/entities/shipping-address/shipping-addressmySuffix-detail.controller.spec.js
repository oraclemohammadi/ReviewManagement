'use strict';

describe('Controller Tests', function() {

    describe('ShippingAddress Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockShippingAddress, MockPurchaseOrder;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockShippingAddress = jasmine.createSpy('MockShippingAddress');
            MockPurchaseOrder = jasmine.createSpy('MockPurchaseOrder');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ShippingAddress': MockShippingAddress,
                'PurchaseOrder': MockPurchaseOrder
            };
            createController = function() {
                $injector.get('$controller')("ShippingAddressMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'reviewtrackerApp:shippingAddressUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
