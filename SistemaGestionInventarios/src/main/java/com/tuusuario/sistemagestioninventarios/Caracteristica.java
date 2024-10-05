/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class Caracteristica {
    private String nombre;
    private List<String> valores; // Lista de valores asociados a esta característica
    
    public Caracteristica(String nombre) {
        this.nombre = nombre;
        this.valores = new ArrayList<>();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public List<String> getValores() {
        return valores;
    }
    
    public void agregarValor(String valor) {
        if (!valores.contains(valor)) {
            valores.add(valor);
        }
    }
    
    // Representa la característica y sus valores en un formato legible
    @Override
    public String toString() {
        return nombre + ": " + String.join(", ", valores);
    }
}
