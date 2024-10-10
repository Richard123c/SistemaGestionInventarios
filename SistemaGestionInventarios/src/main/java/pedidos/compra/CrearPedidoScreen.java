/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedidos.compra;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;/**
 *
 * @author Lenovo
 */
public class CrearPedidoScreen extends JFrame{
    
    private JComboBox<String> comboBoxProveedores;
    private JButton btnCrearPedido;
    private DefaultListModel<String> modeloListaProductos;
    private JList<String> listaProductos;
    
    
    public CrearPedidoScreen() {
        setTitle("Creacion de Pedido de Compra");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JLabel lblProveedor = new JLabel("Seleccione el proveedor:");
        comboBoxProveedores = new JComboBox<>();
        // Carga proveedores desde archivo o base de datos
        cargarProveedores();
        
        //Productos
        JLabel lblProductos = new JLabel("Seleccione los productos:");
        modeloListaProductos = new DefaultListModel<>();
        listaProductos = new JList<>(modeloListaProductos);
        listaProductos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        cargarProductos();
        
        // Botón para crear el pedido
        btnCrearPedido = new JButton("Crear Pedido");
        
        // Paneles
        JPanel panelSuperior = new JPanel(new GridLayout(2,2));
        panelSuperior.add(lblProveedor);
        panelSuperior.add(comboBoxProveedores);
        panelSuperior.add(lblProductos);
        
        JScrollPane scrollProductos = new JScrollPane(listaProductos);
        panelSuperior.add(scrollProductos);
        
        add(panelSuperior, BorderLayout.CENTER);
        add(btnCrearPedido, BorderLayout.SOUTH);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));
        panel.add(lblProveedor);
        panel.add(new JLabel(""));
        panel.add(btnCrearPedido);
        
        add(panel, BorderLayout.CENTER);
        
        //Accion al presionar el boton de crear pedido
        btnCrearPedido.addActionListener(e -> {
            String proveedorSeleccionado = (String) comboBoxProveedores.getSelectedItem();
            List<String> productosSeleccionados = listaProductos.getSelectedValuesList();
            
            if (proveedorSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Map para almacenar producto y cantidad
            Map<String, Integer> productosConCantidad = new HashMap<>();
            
            // Solicitar la cantidad de cada producto seleccionado
            for (String producto : productosSeleccionados) {
                String cantidadSrt = JOptionPane.showInputDialog("Ingrese la cantidad para el producto: " + producto);
                
                if (cantidadSrt == null || cantidadSrt.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Deb3 ingresar una cantidad valida para " + producto, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    if (cantidad <= 0) {
                        JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0 para " + producto, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    productosConCantidad.put(producto, cantidad);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número válido para la cantidad de " + producto, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            
            if (productosSeleccionados.isEmpty()){
                JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Pedido creado para el proveedor: " + proveedorSeleccionado);
        });
    }
    
    private void cargarProveedores() {
        // Aquí iría la lógica para cargar proveedores desde un archivo o base de datos
        comboBoxProveedores.addItem("Proveedor 1");
        comboBoxProveedores.addItem("Proveedor 2");
        comboBoxProveedores.addItem("Proveedor 3");
    }
    
    private void cargarProductos() {
        // Lógica para cargar productos (ejemplo de prueba)
        modeloListaProductos.addElement("Producto 1");
        modeloListaProductos.addElement("Producto 2");
        modeloListaProductos.addElement("Producto 3");
        modeloListaProductos.addElement("Producto 4");
    }
}
