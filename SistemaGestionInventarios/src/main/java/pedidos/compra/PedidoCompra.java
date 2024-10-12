/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedidos.compra;
import java.util.UUID;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class PedidoCompra {
    private String codigoPedido;
    private String proveedor;
    private List<String> productos;
    private List<Integer> cantidades;
    private double total;
    
    public PedidoCompra(String proveedor, List<String> productos, List<Integer> cantidades, double total){
        this.codigoPedido = generarCodigoPedido();
        this.proveedor = proveedor;
        this.productos = productos;
        this.cantidades = cantidades;
        this.total = total;
    }
    
     // Método para generar un código único utilizando UUID
    private String generarCodigoPedido() {
        return UUID.randomUUID().toString();
    }
    
    public void guardarPedidoEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pedidos.txt", true))) {
            writer.write("Codigo Pedido: " + codigoPedido);
            writer.newLine();
            writer.write("Proveedor: " + proveedor);
            writer.newLine();
            writer.write("Productos: " + productos.toString());
            writer.newLine();
            writer.write("Cantidades: " + cantidades.toString());
            writer.newLine();
            writer.write("Total: " + total);
            writer.newLine();
            writer.newLine();
            
            // Mensaje de confirmación
            JOptionPane.showMessageDialog(null, "Pedido creado correctamente con codigo: " + codigoPedido);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
