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
public class CargarEspecificaciones {
    
    public static List<EspecificacionNumerica> cargarEspecificacionesDesdeArchivo() {
        List<EspecificacionNumerica> especificaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("especificaciones.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(": ");
                String nombre = partes[0];
                String[] valorYUnidad = partes[1].split(" ");
                double valor = Double.parseDouble(valorYUnidad[0]);
                String unidad = valorYUnidad[1];
                
                EspecificacionNumerica especificacion = new EspecificacionNumerica(nombre, valor, unidad);
                especificaciones.add(especificacion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return especificaciones;
    }
    public static void main(String[] args) {
        List<EspecificacionNumerica> especificaciones = cargarEspecificacionesDesdeArchivo();
        for (EspecificacionNumerica especificacion : especificaciones) {
            System.out.println(especificacion);
        }
    }
    
}
