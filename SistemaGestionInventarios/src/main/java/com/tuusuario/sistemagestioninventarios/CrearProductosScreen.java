/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
/**
 *
 * @author Lenovo
 */
public class CrearProductosScreen extends JFrame {
    
    private JTextField txtNombreProducto;
    private JComboBox<String> comboCategorias;
    private DefaultComboBoxModel<String> modeloCategorias;
    
    private JList<String> listaCaracteristicas;
    private DefaultListModel<String> modeloCaracteristicas;
    
    private JList<String> listaEspecificaciones;
    private DefaultListModel<String> modeloEspecificaciones;
    
    private JTextField valorCaracteristica1;
    private JTextField valorEspecificacion1;
    
    private Map<String, String> valoresCaracteristicas;
    private Map<String, String> valoresEspecificaciones;
    
    private JList<String> listaProductos;
    private DefaultListModel<String> modeloProductos;
    
    private Map<String, String> mapaTiposDatos;
    
    private JTextArea txtDescripcionProducto;
    
    private JTextField txtPrecioVenta;
    private JTextField txtCantidadStock;
    
    private JComboBox<String> comboProductosEliminar;
    private DefaultComboBoxModel<String> modeloProductosEliminar;
    private JButton btnEliminarProducto;
    
    public CrearProductosScreen() {
        setTitle("Crear nuevo producto");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Map para almacenar los valores de las características y especificaciones
        valoresCaracteristicas = new LinkedHashMap<>();
        valoresEspecificaciones = new LinkedHashMap<>();
        
        // Campo para el nombre del producto
        txtNombreProducto = new JTextField(20);
        
        valorCaracteristica1 = new JTextField(20);
        valorEspecificacion1 = new JTextField(20);
        
        txtPrecioVenta = new JTextField(20);
        txtCantidadStock = new JTextField(20);
        
        // Campo de texto para la descripcion del producto
        txtDescripcionProducto = new JTextArea(4, 20);
        txtDescripcionProducto.setLineWrap(true);
        txtDescripcionProducto.setWrapStyleWord(true);
        
        // JComboBox para seleccionar la categoría
        modeloCategorias = new DefaultComboBoxModel<>();
        comboCategorias = new JComboBox<>(modeloCategorias);
        
        cargarCategoriasDesdeArchivo(); // Método que carga las categorías
        cargarCaracteristicasDesdeArchivo();
        cargarEspecificacionesDesdeArchivo();
        
        modeloCaracteristicas = new DefaultListModel<>();
        listaCaracteristicas = new JList<>(modeloCaracteristicas);
        listaCaracteristicas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        modeloEspecificaciones = new DefaultListModel<>();
        listaEspecificaciones = new JList<>(modeloEspecificaciones);
        listaEspecificaciones.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        JPanel panelCampos = new JPanel(new GridLayout(5, 2));
        panelCampos.add(new JLabel("Nombre del Producto:"));
        panelCampos.add(txtNombreProducto);
        panelCampos.add(new JLabel("Categoria:"));
        panelCampos.add(comboCategorias);
        panelCampos.add(new JLabel("Caracteristicas:"));
        panelCampos.add(listaCaracteristicas);
        panelCampos.add(new JLabel("Especificaciones:"));
        panelCampos.add(listaEspecificaciones);
        panelCampos.add(new JLabel("Valor para Caracteristica:"));
        panelCampos.add(valorCaracteristica1);
        panelCampos.add(new JLabel("Valor para Especificacion:"));
        panelCampos.add(valorEspecificacion1);
        panelCampos.add(new JLabel("Descripcion del Producto:"));
        panelCampos.add(new JScrollPane(txtDescripcionProducto));
        panelCampos.add(new JLabel("Precio venta"));
        panelCampos.add(new JScrollPane(txtPrecioVenta));
        panelCampos.add(new JLabel("CantidadStock"));
        panelCampos.add(new JScrollPane(txtCantidadStock));
        
        JButton btnGuardarProducto = new JButton("Guardar Producto");
        btnGuardarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
        
        JButton btnIngresarValores = new JButton("Ingresar Valores");
        btnIngresarValores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarValores();
            }
        });
        
        add(panelCampos, BorderLayout.CENTER);
        add(btnGuardarProducto, BorderLayout.SOUTH);
    }
    
    private void ingresarValores(){
        List<String> caracteristicasSeleccionadas = listaCaracteristicas.getSelectedValuesList();
        List<String> especificacionesSeleccionadas = listaEspecificaciones.getSelectedValuesList();
        
        if (caracteristicasSeleccionadas.isEmpty() ||  especificacionesSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una caracteristica y una especificacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //Se ingresan los valores para cada caracteristica seleccionada
        for (String caracteristica : caracteristicasSeleccionadas) {
            String valor = JOptionPane.showInputDialog(this, "Ingrese valor para la caracteristica: " + caracteristica);
            if (valor != null && !valor.trim().isEmpty()) {
                valoresCaracteristicas.put(caracteristica, valor.trim());
            }
        }
        
        //Se ingresan los valores para cada especificacion seleccionada
        for (String especificacion : especificacionesSeleccionadas) {
            String valor = JOptionPane.showInputDialog(this, "Ingrese valor para la especificacion: " + especificacion);
            if (valor != null && !valor.trim().isEmpty()) {
                valoresEspecificaciones.put(especificacion, valor.trim());
            }
        }
    }
    
    private void guardarProducto() {
         // Genera un identificador único para el producto
        String idProducto = UUID.randomUUID().toString();
        
        // Captura valores de los campos
        String nombreProducto = txtNombreProducto.getText().trim();
        String categoriaSeleccionada = comboCategorias.getSelectedItem().toString();
        List<String> caracteristicasSeleccionadas = listaCaracteristicas.getSelectedValuesList();
        List<String> especificacionesSeleccionadas = listaEspecificaciones.getSelectedValuesList();
        String descripcionProducto = txtDescripcionProducto.getText().trim();
        String precioVentaStr = txtPrecioVenta.getText().trim();
        String cantidadStockSrt = txtCantidadStock.getText().trim();
        
        if (nombreProducto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del producto no puede estar vacio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (caracteristicasSeleccionadas.isEmpty() || especificacionesSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una caracteristicas y una especificacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (categoriaSeleccionada == null || categoriaSeleccionada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una categoria.");
            return;
        }
        
        Map<String, String> valoresIngresados = new HashMap<>();
        
        if (valoresCaracteristicas.isEmpty() || valoresEspecificaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar valores para caracteristicas y especificicaciones.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        valoresIngresados.put("Caracteristica1", valorCaracteristica1.getText());
        valoresIngresados.put("Especificacion1", valorEspecificacion1.getText());
        
        if (!validarTiposDeDatos(valoresIngresados)) {
            return;
        }
        
        if (descripcionProducto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripcion del producto no puede estar vacia", "Advertencia", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
                if (precioVentaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El precio de venta no puede estar vacio.");
            return;
        }
        
        double precioVenta;
        
        try {
             // Convierte el string a número decimal (double)
            precioVenta = Double.parseDouble(precioVentaStr);
            
             // Valida que el precio sea mayor que 0
            if (precioVenta <= 0) {
                JOptionPane.showMessageDialog(this, "El precio mayor a 0.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un numero valido para el precio de venta");
            return;
        }
        
        if (cantidadStockSrt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La cantidad inicial de stock no puede estar vacia.");
            return;
        }
        
        int cantidadStock;
        
        try {
            // Convierte el string a número entero (int)
            cantidadStock = Integer.parseInt(cantidadStockSrt);
            
            // Valida que la cantidad de stock sea mayor o igual a 0
            if (cantidadStock < 0) {
                JOptionPane.showMessageDialog(this, "La cantidad de stock debe ser mayor o igual a 0.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un numero valido para la cantidad de stock.");
            return;
        }
        
        // Si todas las validaciones son correctas, se procede a guardar el producto
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt", true))) {
            writer.write(nombreProducto + "|" + categoriaSeleccionada + "|" +
                    String.join(",", caracteristicasSeleccionadas) + "|" +
                    String.join(",", especificacionesSeleccionadas) + "|" +
                    descripcionProducto + "|" + precioVenta + "|" + cantidadStock);
            writer.newLine();
            
            writer.write("Valores de caracteristicas: ");
            for (String caracteristica : caracteristicasSeleccionadas) {
                String valor = JOptionPane.showInputDialog(this, "Ingrese el valor para la caracteristica: " + caracteristica);
                String valorCaracteristica = valoresCaracteristicas.get(caracteristica);
                writer.write(caracteristica + ": " + valorCaracteristica + "; ");
                
                if (valor == null || valor.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar un valor para la caracteristica " + caracteristica);
                    return;
                }
                
                valoresCaracteristicas.put(caracteristica, valor);
            }
            writer.newLine();
            
            // Guarda las características con los valores
            writer.write("Valores de especificaciones");
            for (String especificacion : especificacionesSeleccionadas) {
                String valorEspecificacion = valoresEspecificaciones.get(especificacion);
                writer.write(especificacion + ": " + valorEspecificacion + "; ");
            }
            writer.newLine();
            
            JOptionPane.showMessageDialog(this, "Producto guardado exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el productos", "Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    // Método que carga las categorías desde un archivo de texto
    private void cargarCategoriasDesdeArchivo(){
        String archivoCategorias = "categorias.txt"; // Archivo que contiene las categorías
        File archivo = new File(archivoCategorias);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo de categorias no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: El archivo de categorias no existe.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Categoria leida: " + linea);
                modeloCategorias.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las categorias.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void cargarCaracteristicasDesdeArchivo(){
        String archivoCaracteristicas = "caracteristicas.txt";
        File archivo = new File(archivoCaracteristicas);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo de características no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: El archivo de caracteristicas no existe.");
            return;
        }
        
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(" Caracteristica leida: " + linea);
                modeloCaracteristicas.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las caracteristicas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void cargarEspecificacionesDesdeArchivo(){
        String archivoEspecificaciones = "especificaciones.txt";
        File archivo = new File(archivoEspecificaciones);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo de especificaciones no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: El archivo de especificaciones no existe.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Especificaciones leida: " + linea);
                modeloEspecificaciones.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las especificaciones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } 
    
    private void cargarProductosDesdeArchivo() {
        String archivoProductos = "productos.txt";
        File archivo = new File(archivoProductos);
        modeloProductos = new DefaultListModel<>();
        
        if(!archivo.exists()) {
            try {
               if (archivo.createNewFile()) { 
            JOptionPane.showMessageDialog(this, "El archivo de productos, se ha creado uno nuevo.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
          } catch (IOException e) {
              JOptionPane.showMessageDialog(this, "Error al crear el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
              return;
          }
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                modeloProductos.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        listaProductos = new JList<>(modeloProductos);
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void cargarProductoSeleccionado() {
        String productoSeleccionado = listaProductos.getSelectedValue();
        
        if (productoSeleccionado != null) {
            String[] datosProducto = productoSeleccionado.split("\\|");
            String nombreProducto = datosProducto[0];
            String categoria = datosProducto[1];
            String caracteristicas = datosProducto[2];
            String especificaciones = datosProducto[3];
            
            txtNombreProducto.setText(nombreProducto);
            comboCategorias.setSelectedItem(categoria);
            
            cargarCaracteristicasYEspecificaciones(caracteristicas, especificaciones);
        }
    }
    
    private void cargarCaracteristicasYEspecificaciones(String caracteristicas, String especificaciones) {
        String archivoCaracteristicas = "caracteristicas.txt";
        File archivoCar = new File(archivoCaracteristicas);
        modeloCaracteristicas = new DefaultListModel<>();
        
        
        if (!archivoCar.exists()) {
            try {
                if (archivoCar.createNewFile()) {
                    JOptionPane.showMessageDialog(this, "El archivo de caracteristicas no existia, se ha creado uno nuevo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al crear el archivo de caracteristicas", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
        }
        
        try (BufferedReader readerCar = new BufferedReader(new FileReader(archivoCar))) {
            String lineaCar;
            while ((lineaCar = readerCar.readLine()) != null) {
                modeloCaracteristicas.addElement(lineaCar);
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, "Error al cargar las caracteristicas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        String archivoEspecificaciones = "especificaciones.txt";
        File archivoEsp = new File(archivoEspecificaciones);
        modeloEspecificaciones = new DefaultListModel<>();
        
        if (!archivoEsp.exists()) {
            try {
                if  (archivoEsp.createNewFile()) {
                    JOptionPane.showMessageDialog(this, "El archivo de especificaciones no exitia, se ha creado uno nuevo.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al crear el archivo de especificaciones.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        try (BufferedReader readerEsp = new BufferedReader(new FileReader(archivoEsp))) {
            String lineaEsp;
            while ((lineaEsp = readerEsp.readLine()) != null) {
                modeloEspecificaciones.addElement(lineaEsp);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las especificaciones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        listaCaracteristicas.setModel(modeloCaracteristicas);
        listaEspecificaciones.setModel(modeloEspecificaciones);
    }
    
    private void guardarProductoModificado() {
        String productoSeleccionado = listaProductos.getSelectedValue();
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nombreProductoModificado = txtNombreProducto.getText().trim();
        String categoriaModificada = comboCategorias.getSelectedItem().toString();
        List<String> caracteristicasModificadas = listaCaracteristicas.getSelectedValuesList();
        List<String> especificacionesModificadas = listaEspecificaciones.getSelectedValuesList();
        
        if (nombreProductoModificado.isEmpty() || caracteristicasModificadas.isEmpty() || especificacionesModificadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nuevaLineaProducto = nombreProductoModificado + "|" + categoriaModificada + "|" +
                String.join(",", caracteristicasModificadas) + "|" +
                String.join(",", especificacionesModificadas);
        
        actualizarArchivoProductos(productoSeleccionado, nuevaLineaProducto);
    }
    
    private void actualizarArchivoProductos(String productoAntiguo, String ProductoModificado) {
        String archivoProductos = "productos.txt";
        File archivo = new File(archivoProductos);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo productos no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            List<String> lineas = new BufferedReader(new FileReader(archivoProductos)).lines().toList();
            
            List<String> lineasActualizadas = lineas.stream()
                    .map(linea -> linea.equals(productoAntiguo) ? ProductoModificado : linea)
                    .toList();
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoProductos))) {
                for (String linea : lineasActualizadas) {
                    writer.write(linea);
                    writer.newLine();
                }
            }
            JOptionPane.showMessageDialog(this, "Producto Modificado exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarTiposDatosDesdeArchivo() {
        mapaTiposDatos = new HashMap<>();
        String archivoTiposDatos = "tipos_datos.txt";
        File archivo = new File(archivoTiposDatos);
        
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo tipos de datos no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (BufferedReader reader = new  BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 2) {
                    mapaTiposDatos.put(partes[0], partes[1]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los tipos de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarTiposDeDatos(Map<String, String> valoresIngresados) {
        for (Map.Entry<String, String> entrada : valoresIngresados.entrySet()) {
            String nombre = entrada.getKey();
            String valor = entrada.getValue();
            String tipoEsperado = mapaTiposDatos.get(nombre);
            
            if (tipoEsperado != null) {
                switch (tipoEsperado) {
                    case "int":
                        try {
                            Integer.parseInt(valor);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "El valor ingresado para" + nombre + "debe ser un entero.", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                        break;
                    case "double":
                        try {
                            Integer.parseInt(valor);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "El valor ingresado para" + nombre + "debe ser un numero decimal.", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                        break;
                    case "String":
                        if (valor.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "El valor ingresado" + nombre + "no puede estar vacio.", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Tipo de dato no soportado para " + nombre + ".", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                }
            }
        }
        return true;
    }
    
    public void inicializarComponentes() {
        // Crea combo para seleccionar productos que se eliminarán
        modeloProductosEliminar = new DefaultComboBoxModel<>();
        comboProductosEliminar = new JComboBox<>(modeloProductosEliminar);
        
        // Carga los productos en el combo
        cargarProductosParaEliminar();
        
        btnEliminarProducto = new JButton("Eliminar Producto");
        
        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProductoSeleccionado();
            }
        });
        
        JPanel panelEliminar = new JPanel(new GridLayout(2, 1));
        panelEliminar.add(new JLabel("Seleccione el producto para eliminar:"));
        panelEliminar.add(btnEliminarProducto);
        
        add(panelEliminar, BorderLayout.EAST);
        
    }
    
    private void cargarProductosParaEliminar(){
        String archivoProductos = "productos.txt";
        File archivo = new File(archivoProductos);
        
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo productos no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                modeloProductosEliminar.addElement(linea);
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, "Error al cargar los productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void eliminarProductoSeleccionado() {
        String productoSeleccionado = (String) comboProductosEliminar.getSelectedItem();
        
        if (productoSeleccionado != null) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estas seguro de eliminar el producto" + productoSeleccionado + "?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
            eliminarProductoDeArchivo(productoSeleccionado);
            JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.");
        }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void eliminarProductoDeArchivo(String producto) {
        File archivoOriginal = new File("productos.txt");
        File archivoTemporal = new File("productos_temp.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoOriginal));
                BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
            
            String linea;
            boolean productoEliminado = false;
            
            while ((linea = reader.readLine()) != null) {
                if (!linea.equals(producto)) {
                    writer.write(linea);
                    writer.newLine();
                } else {
                    productoEliminado = true;
                }
            }
            
            if (productoEliminado) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        archivoOriginal.delete();
        archivoTemporal.renameTo(archivoOriginal);
        
        modeloProductosEliminar.removeElement(producto);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CrearProductosScreen().setVisible(true);
            }
        });
    }
}
