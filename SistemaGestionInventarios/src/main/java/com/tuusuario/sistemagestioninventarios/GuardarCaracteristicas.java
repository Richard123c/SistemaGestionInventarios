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
public class GuardarCaracteristicas {
    
    public static void guardarCaracteristicaEnArchivo(Caracteristica caracteristica) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("caracteristica.txt", true))) {
            writer.write(caracteristica.getNombre() + "|" + String.join(",", caracteristica.getValores()));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Caracteristica color = new Caracteristica("Color");
        color.agregarValor("Rojo");
        color.agregarValor("Azul");
        color.agregarValor("Verde");
        
        guardarCaracteristicaEnArchivo(color);
    }
}
