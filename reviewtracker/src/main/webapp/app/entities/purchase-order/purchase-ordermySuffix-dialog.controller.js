(function() {
    'use strict';

    angular
        .module('reviewtrackerApp')
        .controller('PurchaseOrderMySuffixDialogController', PurchaseOrderMySuffixDialogController);

    PurchaseOrderMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PurchaseOrder', 'InvoiceData', 'PaymentExecution', 'ShippingAddress', 'PurchaseOrderItem'];

    function PurchaseOrderMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, PurchaseOrder, InvoiceData, PaymentExecution, ShippingAddress, PurchaseOrderItem) {
        var vm = this;

        vm.purchaseOrder = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.invoices = InvoiceData.query({filter: 'purchaseorder-is-null'});
        $q.all([vm.purchaseOrder.$promise, vm.invoices.$promise]).then(function() {
            if (!vm.purchaseOrder.invoiceId) {
                return $q.reject();
            }
            return InvoiceData.get({id : vm.purchaseOrder.invoiceId}).$promise;
        }).then(function(invoice) {
            vm.invoices.push(invoice);
        });
        vm.payments = PaymentExecution.query({filter: 'purchaseorder-is-null'});
        $q.all([vm.purchaseOrder.$promise, vm.payments.$promise]).then(function() {
            if (!vm.purchaseOrder.paymentId) {
                return $q.reject();
            }
            return PaymentExecution.get({id : vm.purchaseOrder.paymentId}).$promise;
        }).then(function(payment) {
            vm.payments.push(payment);
        });
        vm.shippingaddresses = ShippingAddress.query();
        vm.purchaseorderitems = PurchaseOrderItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.purchaseOrder.id !== null) {
                PurchaseOrder.update(vm.purchaseOrder, onSaveSuccess, onSaveError);
            } else {
                PurchaseOrder.save(vm.purchaseOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('reviewtrackerApp:purchaseOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.purchaseLocalDate = false;
        vm.datePickerOpenStatus.lastUpdateLocalDate = false;
        vm.datePickerOpenStatus.earliestShipLocalDate = false;
        vm.datePickerOpenStatus.latestShipLocalDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
