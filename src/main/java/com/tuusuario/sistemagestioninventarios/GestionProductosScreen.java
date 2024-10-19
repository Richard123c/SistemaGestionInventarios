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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class GestionProductosScreen extends JFrame{
    private JList<String> listaCategorias;
    private DefaultListModel<String> modeloCategorias;
    private JButton btnGuardarCategoria;
    private ArrayList<Producto> listaProductos;
    
    private static final int ITEMS_PER_PAGE = 20; // Número de productos por página
    private int currentPage = 1;
    private DefaultListModel<Producto> productoListModel = new DefaultListModel<>();
    private JList<Producto> productoList = new JList<>(productoListModel);
    private List<Producto> productos = obtenerProductosDesdeArchivo();
    private int paginaActual = 1;
    private int totalPaginas = 10;
    
    public GestionProductosScreen(){
        // Inicializa la interfaz gráfica
        configurarUI();
        mostrarProductos(currentPage); // Muestra la primera página al cargar la ventana

    }
        
        private void configurarUI(){
        //Configura el JFrame
        setTitle("Gestion de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(productoList);
        add(scrollPane, BorderLayout.CENTER);
        
        // Agregar botones de navegación
        JButton btnSiguiente = new JButton("Siguiente");
        JButton btnAnterior = new JButton("Anterior");
        btnSiguiente.addActionListener(e -> mostrarProductos(currentPage + 1));
        btnAnterior.addActionListener(e -> mostrarProductos(currentPage - 1));
        
        JPanel panelNavegacion = new JPanel();
        panelNavegacion.add(btnAnterior);
        panelNavegacion.add(btnSiguiente);

      // Añadir el panel a la ventana principal
       add(panelNavegacion, BorderLayout.SOUTH);
        
        //Panel de Categorias
        JPanel panelCategorias = new JPanel();
        panelCategorias.setLayout(new BorderLayout());
        panelCategorias.setBorder(BorderFactory.createTitledBorder("Categorías de Productos"));

        // Lista de categorías
        modeloCategorias = new DefaultListModel<>();
        listaCategorias = new JList<>(modeloCategorias);
        listaProductos = new ArrayList<> ();
        JScrollPane scrollCategorias = new JScrollPane(listaCategorias);
        panelCategorias.add(scrollCategorias, BorderLayout.CENTER);
        
        // Botones de categorías
        JPanel panelBotonesCategorias = new JPanel();
        panelBotonesCategorias.setLayout(new GridLayout(1, 3, 10, 0));

        JButton btnAgregarCategoria = new JButton("Agregar");
        JButton btnEliminarCategoria = new JButton("Eliminar");
        JButton btnModificarCategoria = new JButton("Modificar");
        JButton btnCaracteristicas = new JButton("Caracteristicas de ProductoS");
        btnGuardarCategoria = new JButton("Guardar");
        
        panelBotonesCategorias.add(btnAgregarCategoria);
        panelBotonesCategorias.add(btnEliminarCategoria);
        panelBotonesCategorias.add(btnModificarCategoria);
        panelBotonesCategorias.add(btnGuardarCategoria);
        panelBotonesCategorias.add(btnCaracteristicas);

        panelCategorias.add(panelBotonesCategorias, BorderLayout.SOUTH);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createTitledBorder("Detalles del Producto"));

        // Aquí agregarás componentes adicionales para alta, baja, modificación, características y especificaciones

        // Añadir paneles al JFrame
        add(panelCategorias, BorderLayout.WEST);
        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotonesCategorias, BorderLayout.NORTH);
        
        // Agregar acciones a los botones
        btnAgregarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaCategoria = JOptionPane.showInputDialog("Ingrese el nombre de la nueva categoría:");
                if (nuevaCategoria != null && !nuevaCategoria.trim().isEmpty()) {
                    modeloCategorias.addElement(nuevaCategoria.trim());
                }
            }
        });

        btnEliminarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaCategorias.getSelectedIndex();
                if (selectedIndex != -1) {
                    modeloCategorias.remove(selectedIndex);
                    eliminarCategoria();
                }
            }
        });
        
        btnModificarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaCategorias.getSelectedIndex();
                if (selectedIndex != -1) {
                    String nuevaCategoria = JOptionPane.showInputDialog("Modificar categoría:", modeloCategorias.get(selectedIndex));
                    if (nuevaCategoria != null && !nuevaCategoria.trim().isEmpty()) {
                        modeloCategorias.set(selectedIndex, nuevaCategoria.trim());
                    }
                }
            }
        });
        
        btnGuardarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCategoriasEnArchivo();
            }
        });
        
        btnCaracteristicas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCaracteristicasProductos();
            }
        });
        
        //Simula cargar productos
        cargarProductosSimulados();
    }
    
    private void agregarCategoria() {
        JTextField nombreField = new JTextField(10);
        JTextArea descripcionArea = new JTextArea(5, 20);
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Nombre de la categoría:"), BorderLayout.NORTH);
        panel.add(nombreField, BorderLayout.CENTER);
        panel.add(new JLabel("Descripción (opcional):"), BorderLayout.SOUTH);
        panel.add(new JScrollPane(descripcionArea), BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar nueva categoría", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        String nombre = nombreField.getText().trim();
        String descripcion = descripcionArea.getText().trim();

        // Validar que el nombre no esté vacío
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la categoría no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el nombre no exista ya en la lista de categorías
        for (int i = 0; i < modeloCategorias.getSize(); i++) {
            String categoriaExistente = modeloCategorias.get(i).split(" - ")[0];
            if (categoriaExistente.equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(this, "Ya existe una categoría con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Agregar la nueva categoría
        modeloCategorias.addElement(nombre + (descripcion.isEmpty() ? "" : " - " + descripcion));
        
        
    }
}
    
    private void eliminarCategoria(){
        int selectedIndex = listaCategorias.getSelectedIndex();
        if  (selectedIndex != -1) {
            String categoriaAEliminar = modeloCategorias.get(selectedIndex);
            //Muestra cuadro de dialogo de confirmacion
            int confirm = JOptionPane.showConfirmDialog(this, 
      "¿Estas seguro de que deseas eliminar esta categoria?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
            
            //Si el usuario confirma la eliminacion
            if (confirm == JOptionPane.YES_OPTION) {
                
                modeloCategorias.remove(selectedIndex);
                //Guardar cambios en el archivo despues de eliminar
                eliminarAsociacionesDeProductos(categoriaAEliminar);
                
                guardarCategoriasEnArchivo();
            }
        } else {
            //Mostrar mensaje de advertencia si no hay una categoria seleccionada
            JOptionPane.showMessageDialog(this, 
      "Por favor, selecciona una categoria para eliminar.", "Eliminar categoria", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void eliminarCategoriaDelArchivo(String categoriaAEliminar){
        File archivo = new File("categorias.txt");
        File archivoTemporal = new File("categorias_temp.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.split("\\|")[0].trim().equals(categoriaAEliminar.split(" - ")[0].trim())){
                    writer.write(linea);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la categoria del archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        //Reemplazar el archivo original con el archivo temporal actualizado
        if (archivo.delete()) {
            archivoTemporal.renameTo(archivo);
        } else {
            JOptionPane.showMessageDialog( this, "Error al actualizar el archivo de categorias,", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarCategoria(){
        int selectedIndex = listaCategorias.getSelectedIndex();
        if (selectedIndex != -1) {
            String categoriaSeleccionada = modeloCategorias.get(selectedIndex);
            String[] partes = categoriaSeleccionada.split(" - ");
            String nombreActual = partes[0];
            String descripcionActual = partes.length > 1 ? partes[1] : "";
            
            JTextField nombreField = new JTextField(nombreActual, 10);
            JTextArea descripcionArea = new JTextArea(descripcionActual, 5, 20);
            descripcionArea.setLineWrap(true);
            descripcionArea.setWrapStyleWord(true);
            
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel("Nombre de la categoria:"), BorderLayout.NORTH);
            panel.add(nombreField, BorderLayout.CENTER);
            panel.add(new JLabel("Descripcion (opcional):"), BorderLayout.SOUTH);
            panel.add(new JScrollPane(descripcionArea), BorderLayout.SOUTH);
            
            int result = JOptionPane.showConfirmDialog(null, panel, "Modificar categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String nuevoNombre = nombreField.getText().trim();
                String nuevaDescripcion = descripcionArea.getText () .trim(); 
                
                //Validar que el nuevo nombre no este vacio
                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre de la categoria no puede estar vacio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar que el nuevo nombre no exista ya en la lista de categorías
                for (int i = 0; i < modeloCategorias.getSize(); i++) {
                    if (i == selectedIndex) continue;
                    String categoriaExistente = modeloCategorias.get(i).split(" - ")[0];
                    if (categoriaExistente.equalsIgnoreCase(nuevoNombre)) {
                        JOptionPane.showMessageDialog(this, "Ya existe una categoría con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
            }
                
                // Modificar la categoría
                modeloCategorias.set(selectedIndex, nuevoNombre + (nuevaDescripcion.isEmpty() ? "" : " - " + nuevaDescripcion));

                // Guardar las categorías actualizadas en el archivo
                guardarCategoriasActualizadasEnArchivo();  
            }
        }
    }
            
            
    private void guardarCategoriasEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("categorias.txt", true))) {
            for (int i = 0; i < modeloCategorias.getSize(); i++) {
                String categoria = modeloCategorias.get(i);
                String[] partes = categoria.split(" - ");
                String nombre = partes[0];
                String descripcion = partes.length > 1 ? partes[1] : "";
                writer.write(nombre + "|" + descripcion);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Categorías guardadas exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar las categorías en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Metodo para verificar si una categoria ya existe en el modelo
    private boolean categoriaYaExiste(String nombreCategoria) {
        for (int i = 0; i < modeloCategorias.getSize(); i++) {
            String categoriaExistente = modeloCategorias.get(i).split(" - ")[0];
            if (categoriaExistente.equalsIgnoreCase(nombreCategoria)) {
                return true;
            }
        }
        return false;
    }
    
    private void guardarCategoriasActualizadasEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("categorias.txt",true))) {
            for (int i = 0; i < modeloCategorias.getSize(); i++) {
                String categoria = modeloCategorias.get(i);
                String[] partes = categoria.split(" - ");
                String nombre = partes[0];
                String descripcion = partes.length > 1 ? partes[1] : "";
                writer.write(nombre + "|" + descripcion);
                writer.newLine();  
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la categorias en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
           
        }
    }
    
    private void cargarCategoriasDesdeArchivo(){
        try (BufferedReader reader = new BufferedReader(new FileReader("categorias.txt"))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split("\\|");
            String nombre = partes[0];
            String descripcion = partes.length > 1 ? partes[1] : "";
            modeloCategorias.addElement(nombre + (descripcion.isEmpty() ? "" : " - " + descripcion));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las categorias desde el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarAsociacionesDeProductos(String categoriaAEliminar) {
        Iterator<Producto> iterator = listaProductos.iterator();
        
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCategoria(). equals(categoriaAEliminar)) {
                producto.setCategoria("Sin categoria");
                
            }
        }
        
        guardarProductosEnArchivo();
    }
    
    private void guardarProductosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Producto.txt",true))) {
            for (int i = 0; i < listaProductos.size(); i++) {
                Producto producto = listaProductos.get(i);
                String nombre = producto.getNombre();
                String descripcion = producto.getDescripcion();
                
                writer.write(nombre + "|" + descripcion);
                writer.newLine();  
            }
         JOptionPane.showMessageDialog(this, "Productos guardadas exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los productos en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarProductosSimulados(){
        //Simulacion de carga de productos
        listaProductos.add(new Producto("Producto 1", "Descripcion 1", "categoria 1"));
        listaProductos.add(new Producto("Producto 2", "Descripcion 2", "Categoria 2"));
        listaProductos.add(new Producto("Producto 3", "Descripcion 3", "Categoria 1" ));
    }
    
    //Maneja los productos y sus categorias
    class Producto {
        private String nombre;
        private String descripcion;
        private String categoria;
        
        public Producto(String nombre, String descripcion, String categoria) {
            this.nombre=  nombre;
            this.descripcion = descripcion;
            this.categoria = categoria;
        }
        
        public String getDescripcion(){
            return descripcion;
        }
        
        public void setDescripcion(String descripcion){
            this.descripcion = descripcion;
        }
        
        public String getNombre(){
            return nombre;
        }
        
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public String getCategoria(){
            return categoria;
        }
        
        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }
    }
    
    private void abrirCaracteristicasProductos() {
        //Crea una nueva ventana o panel para gestionar las caracteristicas
        CaracteristicasProductosScreen caracteristicasScreen = new  CaracteristicasProductosScreen();
        caracteristicasScreen.setVisible(true);
    }

    public void mostrarProductos(int page) {
    int start = (page - 1) * ITEMS_PER_PAGE;
    int end = Math.min(start + ITEMS_PER_PAGE, productos.size());

    // Limpiar la pantalla anterior
    productoListModel.clear();

    for (int i = start; i < end; i++) {
        productoListModel.addElement(productos.get(i));
    }

    
}
    
    private List<Producto> obtenerProductosDesdeArchivo() {
    List<Producto> productos = new ArrayList<>();
    String archivo = "Users/Lenovo/SistemaGestionInventarios/SistemaGestionInventarios/productos.txt";  // Asegúrate de que esta ruta sea correcta

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split("\\|");  // Dividir la línea por el símbolo "|"
            if (partes.length == 5) {  // Asegúrate de que haya exactamente 5 partes
                String id = partes[0];
                String nombre = partes[1];
                String descripcion = partes[2];
                double precio = Double.parseDouble(partes[3]);
                int stock = Integer.parseInt(partes[4]);
                
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        // Manejar el error si es necesario
    }
    return productos;
}
    

public void cambiarPagina(int nuevaPagina) {
    if (nuevaPagina >= 1 && nuevaPagina <= totalPaginas) {
        paginaActual = nuevaPagina;
        
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionProductosScreen().setVisible(true);
            }
        });
    }
    
}
