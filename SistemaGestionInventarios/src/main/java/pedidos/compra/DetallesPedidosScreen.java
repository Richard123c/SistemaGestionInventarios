/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedidos.compra;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class DetallesPedidosScreen extends JFrame{
    private DefaultListModel<String> modeloLista;
    private JList<String> listaPedidos;
    private JTextArea areaDetalles;
    private JComboBox<String> comboEstado;
    private JButton btnModificarEstado, btnCancelarPedido;
    
    public DetallesPedidosScreen() {
        setTitle("Gestion de Pedidos de Compra");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Crear el modelo de lista y cargar los pedidos
        modeloLista = new DefaultListModel();
        cargarPedidosDesdeArchivo();
        
        // Crear la lista de pedidos
        listaPedidos = new JList<>(modeloLista);
        JScrollPane scrollPanePedidos = new JScrollPane(listaPedidos);
        
        // Crear el área de texto para mostrar los detalles del pedido
        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        JScrollPane scrollPaneDetalles = new JScrollPane(areaDetalles);
        
        // Panel para mostrar la lista de pedidos y sus detalles
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPanePedidos, scrollPaneDetalles);
        splitPane.setDividerLocation(300);
        
        // Panel inferior con el combo box para modificar estado y los botones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());

        comboEstado = new JComboBox<>(new String[]{"Pendiente", "En curso", "Completado"});
        btnModificarEstado = new JButton("Modificar Estado");
        btnCancelarPedido = new JButton("Cancelar Pedido");

        panelInferior.add(new JLabel("Estado:"));
        panelInferior.add(comboEstado);
        panelInferior.add(btnModificarEstado);
        panelInferior.add(btnCancelarPedido);
        
        add(splitPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        
        // Agrega el listener para la selección de un pedido
        listaPedidos.addListSelectionListener(e -> mostrarDetallesPedido());
        
        // Agrega el listener para modificar el estado
        btnModificarEstado.addActionListener(e -> modificarEstadoPedido());
        
        // Agrega el listener para cancelar el pedido
        btnCancelarPedido.addActionListener(e -> cancelarPedido());
    }
    
    private void cargarPedidosDesdeArchivo() {
        modeloLista.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("pedidos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                modeloLista.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los pedidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para mostrar los detalles del pedido seleccionado
    private void mostrarDetallesPedido() {
        String pedidoSeleccionado = listaPedidos.getSelectedValue();
        if (pedidoSeleccionado != null) {
            String[] datosPedido = pedidoSeleccionado.split("\\|");
            
            // Muestra los detalles en el área de texto
            areaDetalles.setText("Detalles del pedido\n\n");
            areaDetalles.append("Codigo: " + datosPedido[0].trim() + "\n");
            areaDetalles.append("Proveedor: " + datosPedido[1].trim() + "\n");
            areaDetalles.append("Fecha de Creacion: " + datosPedido[2].trim() + "\n");
            areaDetalles.append("Fecha de Entrega Estimada: " + datosPedido[3].trim() + "\n");
            
            // Productos y cantidades solicitadas
            String productosYcantidades = datosPedido[4].trim();
            String[] productos = productosYcantidades.split(",");
            
            areaDetalles.append("Productos Solicitados:\n");
            for (String producto : productos) {
                String[] detalleProducto = producto.split(":");
                areaDetalles.append(" - Producto: " + detalleProducto[0] + ", Cantidad: " + detalleProducto[1] + "\n");
            }
            
            areaDetalles.append("Total del Pedido: " + datosPedido[5].trim() + "\n");
        }
    }
    
    // Método para modificar el estado del pedido
    private void modificarEstadoPedido() {
        String pedidoSeleccionado = listaPedidos.getSelectedValue();
        if (pedidoSeleccionado != null) {
            String[] datosPedido = pedidoSeleccionado.split("\\|");
            String nuevoEstado = (String) comboEstado.getSelectedItem();
            datosPedido[6] = nuevoEstado;
            
            // Actualizar la línea del pedido en la lista y en el archivo
            String nuevoPedido = String.join("|", datosPedido);
            
            try {
                actualizarPedidosEnArchivo(pedidoSeleccionado, nuevoPedido);
                cargarPedidosDesdeArchivo();
                JOptionPane.showMessageDialog(this, "El estado del pedido ha sido actualizado a " + nuevoEstado);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el estado del pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cancelarPedido() {
        String pedidoSeleccionado = listaPedidos.getSelectedValue();
        if (pedidoSeleccionado != null) {
            String[] datosPedido = pedidoSeleccionado.split("\\|");
            String estadoActual = datosPedido[6].trim();
            
            // Verificar si el estado es "Pendiente", para permitir la cancelación
            if (estadoActual.equals("Pendiente")) {
                datosPedido[6] = "Cancelado";
                
                // Actualizar la línea del pedido en la lista y en el archivo
                String nuevoPedido = String.join("|", datosPedido);
                
                try {
                     actualizarPedidosEnArchivo(pedidoSeleccionado, nuevoPedido);
                    cargarPedidosDesdeArchivo();  // Recargar la lista después de modificar
                    JOptionPane.showMessageDialog(this, "El pedido ha sido cancelado correctamente.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al cancelar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // No permitir la cancelación si el pedido ya está en curso o completado
                JOptionPane.showMessageDialog(this, "No se puede cancelar un pedido que ya ha sido enviado o completado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    // Método para actualizar un pedido en el archivo de texto
    private void actualizarPedidosEnArchivo(String pedidoAntiguo, String pedidoNuevo) throws IOException{
        List<String> lineasArchivo = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("pedidos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
               // Si es la línea del pedido a modificar, la reemplazamos por el nuevo pedido
               if (linea.equals(pedidoAntiguo)) {
                   lineasArchivo.add(pedidoNuevo);
               } else {
                   lineasArchivo.add(linea);
               }
            }
        }
        
         // Sobrescribir el archivo con las líneas actualizadas
         try (BufferedWriter writer = new BufferedWriter(new FileWriter("pedidos.txt"))) {
             for (String linea : lineasArchivo) {
                 writer.write(linea);
                 writer.newLine();
             }
         }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            DetallesPedidosScreen detallesPedidosScreen = new DetallesPedidosScreen();
            detallesPedidosScreen.setVisible(true);
            
        });
    }
}
