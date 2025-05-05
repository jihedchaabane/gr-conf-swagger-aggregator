const eventSource = new EventSource('/sse/eureka-updates');

//eventSource.addEventListener('eureka-update', (event) => {
//    console.log('Received Eureka update event:', event.data);
//    window.location.reload(); 
//});


let debounceTimeout;
eventSource.addEventListener('eureka-update', (event) => {
    console.log('Received Eureka update event:', event.data);
    clearTimeout(debounceTimeout);
    debounceTimeout = setTimeout(() => {
		document.title = "Recharger la page pour actualiser Swagger UI";
        window.location.reload(); // Recharger la page pour actualiser Swagger UI
    }, 2000); // Attendre 1 seconde avant de recharger
});

eventSource.onerror = (error) => {
    console.error('SSE error:', error);
    eventSource.close();
};