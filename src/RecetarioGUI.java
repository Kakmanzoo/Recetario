// Importación de librerías necesarias para la interfaz gráfica, manejo de eventos y archivos
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;

/**
 * RecetarioGUI
 * 
 * Clase que implementa una aplicación gráfica para gestionar los pasos de una receta
 * de huevos revueltos usando Java Swing.
 */
public class RecetarioGUI extends JFrame {
    // Lista que almacena los pasos de la receta
    private LinkedList<String> receta = new LinkedList<>();
    // Modelo para mostrar los pasos en un componente JList
    private DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private JList<String> listaPasos;     // Lista visual para mostrar pasos
    private JTextField campoPaso;         // Campo de texto para introducir o editar pasos

    /**
     * Constructor: Configura la interfaz gráfica de usuario.
     */
    public RecetarioGUI() {
        super("📋 Recetario: Huevos Revueltos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null); // Centra la ventana
        setLayout(new BorderLayout());

        // ---------- Encabezado ----------
        JLabel titulo = new JLabel("📋 RECETARIO: Huevos Revueltos", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(220, 240, 255));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // ---------- Panel central con campo de texto y botones ----------
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoPaso = new JTextField();
        campoPaso.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoPaso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panelCentral.add(new JLabel("Paso seleccionado o nuevo paso:"));
        panelCentral.add(campoPaso);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre campo y botones

        // ---------- Botones de acción ----------
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        String[] textos = {"Agregar", "Actualizar", "Eliminar", "Buscar"};

        for (String texto : textos) {
            JButton btn = new JButton(texto);
            btn.setBackground(new Color(200, 230, 255));
            btn.setFocusPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            panelBotones.add(btn);

            // Asociar cada botón con su acción correspondiente
            switch (texto) {
                case "Agregar":
                    btn.addActionListener(e -> agregarPaso());
                    break;
                case "Actualizar":
                    btn.addActionListener(e -> actualizarPaso());
                    break;
                case "Eliminar":
                    btn.addActionListener(e -> eliminarPaso());
                    break;
                case "Buscar":
                    btn.addActionListener(e -> buscarPaso());
                    break;
            }
        }
        panelCentral.add(panelBotones);
        add(panelCentral, BorderLayout.CENTER);

        // ---------- Lista visual de pasos ----------
        listaPasos = new JList<>(modeloLista);
        listaPasos.setFont(new Font("Monospaced", Font.PLAIN, 14));
        listaPasos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPasos.setBorder(BorderFactory.createTitledBorder("📃 Lista de Pasos"));

        // Cuando se selecciona un paso en la lista, se muestra en el campo de texto
        listaPasos.addListSelectionListener(e -> {
            int i = listaPasos.getSelectedIndex();
            if (i != -1) campoPaso.setText(modeloLista.getElementAt(i));
        });

        JScrollPane scroll = new JScrollPane(listaPasos);
        scroll.setPreferredSize(new Dimension(400, 200));
        add(scroll, BorderLayout.SOUTH);

        // Cargar receta desde archivo (o usar una receta por defecto)
        cargarReceta();
        setVisible(true);
    }

    // --------- MÉTODOS DE ACCIÓN ---------

    /**
     * Agrega un nuevo paso a la receta.
     */
    private void agregarPaso() {
        String paso = campoPaso.getText().trim();
        if (!paso.isEmpty()) {
            receta.add(paso);
            modeloLista.addElement(paso);
            campoPaso.setText("");
            guardarReceta();
        }
    }

    /**
     * Actualiza el paso seleccionado con el nuevo texto.
     */
    private void actualizarPaso() {
        int index = listaPasos.getSelectedIndex();
        String nuevo = campoPaso.getText().trim();
        if (index != -1 && !nuevo.isEmpty()) {
            receta.set(index, nuevo);
            modeloLista.set(index, nuevo);
            campoPaso.setText("");
            guardarReceta();
        }
    }

    /**
     * Elimina el paso actualmente seleccionado de la receta.
     */
    private void eliminarPaso() {
        int index = listaPasos.getSelectedIndex();
        if (index != -1) {
            receta.remove(index);
            modeloLista.remove(index);
            campoPaso.setText("");
            guardarReceta();
        }
    }

    /**
     * Busca un paso que contenga el texto ingresado y lo selecciona en la lista.
     */
    private void buscarPaso() {
        String texto = campoPaso.getText().toLowerCase().trim();
        if (!texto.isEmpty()) {
            for (int i = 0; i < receta.size(); i++) {
                if (receta.get(i).toLowerCase().contains(texto)) {
                    listaPasos.setSelectedIndex(i);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "❌ No se encontró ningún paso con ese texto.");
        }
    }

    // --------- MÉTODOS PARA ARCHIVOS ---------

    /**
     * Guarda la receta actual en un archivo de texto.
     */
    private void guardarReceta() {
        try (PrintWriter writer = new PrintWriter("src/recetas.txt")) {
            for (String paso : receta) {
                writer.println(paso);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar receta.");
        }
    }

    /**
     * Carga la receta desde un archivo. Si no existe, usa una receta por defecto.
     */
    private void cargarReceta() {
        File archivo = new File("src/recetas.txt");

        // Si no existe el archivo, usar receta por defecto
        if (!archivo.exists()) {
            receta.add("Romper 2 huevos en un recipiente.");
            receta.add("Batir los huevos con sal y pimienta.");
            receta.add("Calentar una sartén con mantequilla.");
            receta.add("Verter los huevos batidos.");
            receta.add("Revolver hasta que estén cocidos.");
            receta.add("Servir caliente.");
            for (String paso : receta) modeloLista.addElement(paso);
            guardarReceta();
            return;
        }

        // Cargar pasos desde archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                receta.add(linea);
                modeloLista.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al cargar receta.");
        }
    }

    /**
     * Método principal: ejecuta la aplicación.
     */
    public static void main(String[] args) {
        new RecetarioGUI();
    }
}

