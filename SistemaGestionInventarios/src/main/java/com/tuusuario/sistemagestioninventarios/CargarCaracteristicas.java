/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class CargarCaracteristicas {
    
    public static List<Caracteristica> cargarCaracteristicasDesdeArchivo() {
        List<Caracteristica> caracteristicas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("caracteristicas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                String nombre = partes[0];
                String[] valores = partes[1].split(",");
                
                Caracteristica caracteristica = new Caracteristica(nombre);
                for (String valor : valores) {
                    caracteristica.agregarValor(valor.trim());
                }
                caracteristicas.add(caracteristica);
            }
        } catch (IOException e) {
                e.printStackTrace();
                }
        return caracteristicas;
    }
        public static void main(String[] args) {
            List<Caracteristica> caracteristicas = cargarCaracteristicasDesdeArchivo();
            for (Caracteristica caracteristica : caracteristicas) {
                System.out.println(caracteristica);
            }
        }
}
