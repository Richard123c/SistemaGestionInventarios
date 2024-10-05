/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;


/**
 *
 * @author Lenovo
 */
public class Categoria {
    
    private String nombre;
    private Categoria categoriaPadre;
    private JTree arbolCategorias;
    private DefaultMutableTreeNode raizCategoria;
    
    public Categoria(String nombre, Categoria categoriaPadre) {
        this.nombre = nombre;
        this.categoriaPadre = categoriaPadre;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Categoria getCategoriaPadre() {
        return categoriaPadre;
    }
    
     @Override
    public String toString() {
        if (categoriaPadre != null) {
            return categoriaPadre.toString() + " > " + nombre;
        }
        return nombre;
    }

    
    private void inicializarArbolCategorias() {
        raizCategoria = new DefaultMutableTreeNode("Categorias");
        arbolCategorias = new JTree(raizCategoria);
        JScrollPane scrollPane = new JScrollPane(arbolCategorias);
    }
    
    private void agregarCategoriaAlArbol(Categoria categoria) {
        DefaultMutableTreeNode nodoPadre = encontrarNodoPadre(raizCategoria, categoria.getCategoriaPadre());
        DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(categoria.getNombre());
        nodoPadre.add(nuevoNodo);
    }
    
    private DefaultMutableTreeNode encontrarNodoPadre(DefaultMutableTreeNode nodoActual, Categoria categoriaPadre) {
        Enumeration<?> enumeration = nodoActual.children();
        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode nodoHijo = (DefaultMutableTreeNode) enumeration.nextElement();
            if (nodoHijo.toString().equals(categoriaPadre.getNombre())) {
                return nodoHijo;
            }
            DefaultMutableTreeNode nodoEncontrado = encontrarNodoPadre(nodoHijo, categoriaPadre);
            if (nodoEncontrado != null) {
                return nodoEncontrado;
            }
        }
        return nodoActual;
    }
    
    private void guardarJerarquiaCategorias(Categoria nuevaCategoria) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("categorias.txt", true))) {
    Categoria categoriaActual = nuevaCategoria;
    StringBuilder jerarquia = new StringBuilder(categoriaActual.getNombre());
    while (categoriaActual.getCategoriaPadre() != null) {
        categoriaActual = categoriaActual.getCategoriaPadre();
        jerarquia.insert(0, categoriaActual.getNombre() + " > ");
    }
    writer.write(jerarquia.toString());
    writer.newLine();
    } catch (IOException e) {
    e.printStackTrace();
    }
  }
}
    
   
