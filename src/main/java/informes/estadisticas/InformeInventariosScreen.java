/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package informes.estadisticas;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Lenovo
 */
public class InformeInventariosScreen extends JFrame{
    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboCategorias;
    private JButton btnFiltrar;
    private JButton btnExportarCSV;
    
    public InformeInventariosScreen() {
        setTitle("Informe de Inventario Actual");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);
        
        // Crea la tabla de inventario
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Categoria", "Stock Disponible", "Valor Total Stock", "Punto de Reorden"}, 0);
        tablaInventario = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaInventario);
        
        // Panel de opciones de filtro
        JPanel panelOpciones = new JPanel(new FlowLayout());
        comboCategorias = new JComboBox<>();
        btnFiltrar = new JButton("Filtrar por Categoria");
        btnExportarCSV = new JButton("Exportar a CSV");
        
        panelOpciones.add(new JLabel("Filtrar por categoria"));
        panelOpciones.add(comboCategorias);
        panelOpciones.add(btnFiltrar);
        panelOpciones.add(btnExportarCSV);
        
        // Añadir componentes al JFrame
        add(panelOpciones, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Cargar las categorías y productos
        cargarCategorias();
        cargarProductos();
        
        // Acción del botón Filtrar
        btnFiltrar.addActionListener(e -> filtrarPorCategoria());
        
        // Acción del botón Exportar CSV
        btnExportarCSV.addActionListener(e -> exportarA_CSV());
        
        this.revalidate();
        this.repaint();
        
        
    }
    
    private void cargarCategorias(){
        comboCategorias.addItem("Todas"); // Opción para mostrar todos los productos sin filtro
        Set<String> categorias = new HashSet<>(); // Para evitar categorías duplicadas
        
        // Lee las categorías desde el archivo categorias.txt
        try (BufferedReader reader = new BufferedReader(new FileReader("categorias.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String categoria = linea.trim();
                if (!categoria.isEmpty()) {
                    categorias.add(categoria); // Añade categoría al Set (evita duplicados)
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las categorias.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        // Añade las categorías únicas al JComboBox
        for (String categoria : categorias) {
            comboCategorias.addItem(categoria);
        }
    }
    
    private void cargarProductos(){
        modeloTabla.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datosProductos = linea.split("\\|");
                String nombre = datosProductos[1];
                String categoria = datosProductos[2];
                int stock = Integer.parseInt(datosProductos[6]);
                double precioVenta = Double.parseDouble(datosProductos[7]);
                int puntoReorden = Integer.parseInt(datosProductos[8]);
                
                double valorTotalStock = stock * precioVenta;
                modeloTabla.addRow(new Object[]{nombre, categoria, stock, valorTotalStock, puntoReorden});   
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void filtrarPorCategoria() {
        String categoriaSeleccionada = comboCategorias.getSelectedItem().toString();
        if (categoriaSeleccionada.equals("Toda")) {
            cargarProductos();
        } else {
            // Filtrar productos por categoría
            modeloTabla.setRowCount(0);
            try (BufferedReader reader = new BufferedReader (new FileReader("productos.txt"))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] datosProducto = linea.split("\\|");
                    String categoria = datosProducto[2];
                    if (categoria.equals(categoriaSeleccionada)) {
                        // Agregar fila
                        String nombre = datosProducto[1];
                        int stock = Integer.parseInt(datosProducto[6]);
                        double precioVenta = Double.parseDouble(datosProducto[7]);
                        int puntoReorden = Integer.parseInt(datosProducto[8]);
                        double valorTotalStock = stock * precioVenta;
                        modeloTabla.addRow(new Object[]{nombre, categoria, stock, valorTotalStock, puntoReorden});
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al filtrar productos.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void exportarA_CSV() {
        //Lógica para exportar los datos de la tabla a un archivo CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("informe_inventarios.csv"))) {
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    writer.write(modeloTabla.getValueAt(i ,j).toString() + ",");
                }
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Informe exportado a informe_inventario.cvs");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al exportar informe", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        // Siempre usa invokeLater para crear interfaces gráficas en el EDT
        SwingUtilities.invokeLater(() -> new InformeInventariosScreen());
    }
}
