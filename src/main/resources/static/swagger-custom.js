const eventSource = new EventSource('/sse/eureka-updates');

eventSource.addEventListener('eureka-update', (event) => {
    console.log('Received Eureka update event:', event.data);
    window.location.reload(); // Recharger la page pour actualiser Swagger UI
});

eventSource.onerror = (error) => {
    console.error('SSE error:', error);
    eventSource.close();
};