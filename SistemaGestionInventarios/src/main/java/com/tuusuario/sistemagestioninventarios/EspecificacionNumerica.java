/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;

/**
 *
 * @author Lenovo
 */
public class EspecificacionNumerica {
    private String nombre; // Nombre de la especificación (ej. "Peso", "Altura")
    private double valor; // Valor numérico de la especificación
    private String unidad; // Unidad de medida asociada (ej. "kg", "cm")
    
    public EspecificacionNumerica(String nombre, double valor, String unidad) {
        this.nombre = nombre;
        this.valor = valor;
        this.unidad = unidad;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public double getValor() {
        return valor;
    }
    
    public String getUnidad() {
        return unidad;
    }
    
    @Override
    public String toString() {
        return nombre + ": " + valor + " " +  unidad;
    }
}


