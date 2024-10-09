/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control.existencias;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author Lenovo
 */
public class VerificadorStockBajo {
    
    
    public VerificadorStockBajo() {
    // Crea un temporizador para verificar stock cada cierto intervalo (por ejemplo, cada 24 horas)
    Timer timer = new Timer();
    
    // Intervalo de 24 horas en milisegundos (24 * 60 * 60 * 1000)
    long intervalo = 24 * 60 * 60 * 1000;
    
    //Programar la tarea
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
        verificarStock();
    }
  }, 0, intervalo);
}
    private void verificarStock() {
        List<String> lineasArchivo = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datosProducto = linea.split("\\|");
                
                String codigoProducto = datosProducto[0];
                String nombreProducto = datosProducto[1];
                int stockActual = Integer.parseInt(datosProducto[6]);
                int puntoReorden = Integer.parseInt(datosProducto[7]);
                
                // Verifica si el stock actual es menor o igual al punto de reorden
                if (stockActual <= puntoReorden) {
                    generarAlertaStockBajo(codigoProducto, nombreProducto, stockActual, puntoReorden);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Guarda la alerta en un archivo o lo envia por email
    private void generarAlertaStockBajo(String codigoProducto, String nombreProducto, int stockActual, int puntoReorden) {
        // Formato de la alerta, incluyendo el nombre del producto, nivel de stock actual y punto de reorden
        String mensaje = "ALERTA: El producto '" + nombreProducto + "' (Código: " + codigoProducto + ") tiene un stock bajo.\n" +
                         "Stock actual: " + stockActual + " unidades. Punto de reorden: " + puntoReorden + " unidades.";

        
        //Aqui muestra una notificacion visual al usuario
        JOptionPane.showMessageDialog(null, mensaje, "Alerta de Stock Bajo", JOptionPane.WARNING_MESSAGE);
        
        //Guarda la alerta en un achivo de texto
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("alertas_stock.txt",true))) {
            writer.write(mensaje);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la alerta de stock.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
public static void main(String[] args) {
    new VerificadorStockBajo();
  }
}
