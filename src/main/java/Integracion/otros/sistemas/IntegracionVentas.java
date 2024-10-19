/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Integracion.otros.sistemas;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
/**
 *
 * @author Lenovo
 */
public class IntegracionVentas {
    private static final String API_URL = "https://api.sistemaventas.com/actualizarStock";

    public static void notificarVenta(String productoId, int cantidadVendida) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);
            
            String jsonInputString = "{ \"productoId\": \"" + productoId + "\", \"cantidadVendida\": " + cantidadVendida + " }";
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Stock actualizado exitosamente en el sistema de ventas");
            } else {
                System.out.println("Error al actualizar stock: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
