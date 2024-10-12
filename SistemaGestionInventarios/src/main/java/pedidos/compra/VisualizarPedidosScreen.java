/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedidos.compra;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class VisualizarPedidosScreen extends JFrame{
    private DefaultListModel<String> modeloLista;
    private JList<String> listaPedidos;
    
    public VisualizarPedidosScreen() {
        setTitle("Lista de Pedido de Compra");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        //Crea el modelo de lista para almacenar los pedidos
        modeloLista = new DefaultListModel<>();
        cargarPedidosDesdeArchivo();
        
        //Crea la lista con el modelo
        listaPedidos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaPedidos);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCerrar);
        
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
    }
    
    private void cargarPedidosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("pedidos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                modeloLista.addElement( linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los pedidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            VisualizarPedidosScreen visualizarPedidos = new VisualizarPedidosScreen();
            visualizarPedidos.setVisible(true);
            
        });
    }
}
