'use strict';

describe('Controller Tests', function() {

    describe('Promotions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPromotions, MockPurchaseOrderItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPromotions = jasmine.createSpy('MockPromotions');
            MockPurchaseOrderItem = jasmine.createSpy('MockPurchaseOrderItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Promotions': MockPromotions,
                'PurchaseOrderItem': MockPurchaseOrderItem
            };
            createController = function() {
                $injector.get('$controller')("PromotionsMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'reviewtrackerApp:promotionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
