/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Lenovo
 */
public class GuardarEspecificaciones {
    
    public static void guardarEspecificacionEnArchivo(EspecificacionNumerica especificacion) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("especificaciones.txt", true))) {
            writer.write(especificacion.getNombre() + ": " + especificacion.getValor() + " " + especificacion.getUnidad());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        EspecificacionNumerica peso = new EspecificacionNumerica("Peso", 75.0, "Kg");
        guardarEspecificacionEnArchivo(peso);
    }
}
