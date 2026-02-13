package com.coordinacioncafesystem.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CoffeeService {

    private static final Logger logger = LoggerFactory.getLogger(CoffeeService.class);

    @Value("${app.coffee.api-key}")
    private String apiKey;

    @Value("${app.coffee.url}")
    private String urlBase;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Obtiene el precio real de la Bolsa de Chicago y realiza la conversión técnica
     * de Centavos/Libra a Dólares por Kilo y Dólares por Quintal (46kg).
     */
    public Map<String, Double> obtenerPreciosConvertidos() {
        String urlCafe = urlBase + apiKey;
        String urlDolar = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=USD&to_currency=MXN&apikey=" + apiKey;

        // Valores por defecto en caso de que falle la API de divisas
        double tipoCambioHoy = 19.50;

        try {
            // --- PARTE A: CAFÉ ---
            Map<String, Object> resCafe = restTemplate.getForObject(urlCafe, Map.class);
            List<Map<String, String>> data = (List<Map<String, String>>) resCafe.get("data");

            if (data == null || data.isEmpty()) {
                logger.error("La API de Café no devolvió datos (Límite excedido)");
                return Map.of("precioKilo", 0.0, "precioQuintal", 0.0);
            }

            double centavosLibra = Double.parseDouble(data.get(0).get("value"));
            double usdKilo = (centavosLibra / 100) * 2.20462;
            double usdQuintal = usdKilo * 46;

            // --- PAUSA DE SEGURIDAD (RETRASO) ---
            // Esperamos 2 segundos para que la API no nos bloquee la segunda petición
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            // --- PARTE B: DÓLAR ---
            try {
                Map<String, Object> resDolar = restTemplate.getForObject(urlDolar, Map.class);
                if (resDolar != null && resDolar.containsKey("Realtime Currency Exchange Rate")) {
                    Map<String, String> exchangeData = (Map<String, String>) resDolar.get("Realtime Currency Exchange Rate");
                    tipoCambioHoy = Double.parseDouble(exchangeData.get("5. Exchange Rate"));
                    logger.info("Tipo de cambio actualizado: ${}", tipoCambioHoy);
                } else {
                    logger.warn("No se pudo obtener el dólar en tiempo real (Rate Limit), usando respaldo: ${}", tipoCambioHoy);
                }
            } catch (Exception e) {
                logger.warn("Error obteniendo dólar, usando respaldo de $19.50");
            }

            // --- PARTE C: CÁLCULO FINAL ---
            double mxnKilo = usdKilo * tipoCambioHoy;
            double mxnQuintal = usdQuintal * tipoCambioHoy;

            return Map.of(
                    "precioKilo", Math.round(mxnKilo * 100.0) / 100.0,
                    "precioQuintal", Math.round(mxnQuintal * 100.0) / 100.0,
                    "tipoCambio", tipoCambioHoy
            );

        } catch (Exception e) {
            logger.error("Error crítico: {}", e.getMessage());
            return Map.of("precioKilo", 0.0, "precioQuintal", 0.0);
        }
    }
}
