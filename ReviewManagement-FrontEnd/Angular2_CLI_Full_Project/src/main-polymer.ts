//we have to postpone the Angular application import until the WebComponentsReady event is dispatched
document.addEventListener('WebComponentsReady', () => {
    require('./main.ts');
});
