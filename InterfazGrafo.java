package org.Proyecto;
// Importamos clases necesarias
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InterfazGrafo extends JFrame {
    // Declaracion de atributos
    private GrafoDirigidoAciclico<Integer> grafo;
    private JTextArea textoGrafo;
    private mxGraph jGraph;
    private JPanel panelGrafo;
    /*
     * Constructor de la clase interfazGrafo que contiene la logica para crear la GUI
     */
    public InterfazGrafo() {
        setTitle("Interfaz Grafo Dirigido Acíclico");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // Selección del tipo de grafo
        String[] opciones = {"Grafo con nodos aleatorios", "Grafo con nodos especificados", "Grafo vacio"};
        String seleccion = (String) JOptionPane.showInputDialog(null, "Elige el tipo de grafo:",
                "Seleccione el grafo", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion != null) {
            switch (seleccion) {
                case "Grafo con nodos aleatorios":
                    grafo = new GrafoDirigidoAciclico<>(true);
                    break;
                case "Grafo con nodos especificados":
                    String input = JOptionPane.showInputDialog("Ingrese la cantidad de nodos:");
                    if (input != null && !input.isEmpty()) {
                        try {
                            int cantidadNodos = Integer.parseInt(input);
                            grafo = new GrafoDirigidoAciclico<>(cantidadNodos);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Ingrese un numero valido.");
                            return;
                        }
                    }
                    break;
                case "Grafo vacio":
                    grafo = new GrafoDirigidoAciclico<>();
                    break;
            }
        } else {
            return;
        }

        // Inicialización de la interfaz gráfica
        inicializarComponentes();
        inicializarGrafoVisual();
    }
    /*
     * Metodo que inicializa los componentes que llevara la GUI
     */
    private void inicializarComponentes() {
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton mostrarListaButton = new JButton("Mostrar Lista de Adyacencia");
        JButton mostrarMatrizButton = new JButton("Mostrar Matriz de Adyacencia");
        JButton agregarNodoButton = new JButton("Agregar Nodo");
        JButton agregarAristaButton = new JButton("Agregar Arista");
        JButton gradoEntradaButton = new JButton("Grado de Entrada");
        JButton gradoSalidaButton = new JButton("Grado de Salida");
        JButton cuantasAristasHayButton = new JButton("Numero de aristas");
        JButton adyacenteButton = new JButton("Nodos adyacentes");
        JButton conectadosButton = new JButton("Nodos conectados");
        JButton eliminarAristasButton = new JButton("Eliminar aristas");
        JButton tieneCiclosButton = new JButton("Tiene ciclos");
        JButton topologicalSortButton = new JButton("Topological Sort");
        JButton guardarGrafoButton = new JButton("Guardar Grafo");
        JButton verGrafosButton = new JButton("Verificar Grafo");
        mostrarListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                textoGrafo.setText("Lista de Adyacencia:\n" + grafo.mostrarListaDeAdyacencia());
            }
        });
        mostrarMatrizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                int[][] matriz = grafo.getMatrizDeAdyacencia();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < matriz.length; i++) {
                    for (int j = 0; j < matriz[i].length; j++) {
                        sb.append(matriz[i][j]).append(" ");
                    }
                    sb.append("\n");
                }
                textoGrafo.setText("Matriz de Adyacencia:\n" + sb.toString());
            }
        });
        agregarNodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                String input = JOptionPane.showInputDialog("Ingrese el valor del nodo:");
                if (input != null && !input.isEmpty()) {
                    try {
                        int valor = Integer.parseInt(input);
                        grafo.agregarNodo(new NodoGrafo<>(valor));
                        JOptionPane.showMessageDialog(null, "Nodo agregado correctamente.");
                        textoGrafo.setText("Grafo actualizado:\n" + grafo.mostrarListaDeAdyacencia());
                        actualizarGrafoVisual();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese un numero valido.");
                    }
                }
            }
        });
        agregarAristaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                String input = JOptionPane.showInputDialog("Ingrese los índices de los nodos (x,y):");
                if (input != null && !input.isEmpty()) {
                    String[] indices = input.split(",");
                    if (indices.length == 2) {
                        try {
                            int x = Integer.parseInt(indices[0].trim());
                            int y = Integer.parseInt(indices[1].trim());
                            if (grafo.insertarArista(x, y)) {
                                JOptionPane.showMessageDialog(null, "Arista agregada correctamente.");
                                textoGrafo.setText("Grafo actualizado:\n" + grafo.mostrarListaDeAdyacencia());
                                actualizarGrafoVisual();
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo agregar la arista.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Ingrese indices validos.");
                        }
                    }
                }
            }
        });
        gradoEntradaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                String input = JOptionPane.showInputDialog("Ingrese el índice del nodo para calcular el grado de entrada:");
                try {
                    int x = Integer.parseInt(input);
                    int grado = grafo.gradoDeEntrada(x);
                    JOptionPane.showMessageDialog(null, "El grado de entrada del nodo " + x + " es: " + grado);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un numero valido.");
                }
            }
        });

        gradoSalidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                String input = JOptionPane.showInputDialog("Ingrese el indice del nodo para calcular el grado de salida:");
                try {
                    int x = Integer.parseInt(input);
                    int grado = grafo.gradoDeSalida(x);
                    JOptionPane.showMessageDialog(null, "El grado de salida del nodo " + x + " es: " + grado);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un numero válido.");
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Indice fuera de rango. Ingrese un numero valido.");
                }
            }
        });

        cuantasAristasHayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                int numeroDeAristas = grafo.cuantasAristasHay();
                JOptionPane.showMessageDialog(null, "El numero total de aristas en el grafo es: " + numeroDeAristas);
            }
        });

        adyacenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                int x = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el indice x:"));
                int y = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el indice y:"));
                boolean resultado = grafo.adyacente(x, y);
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Los nodos " + x + " y " + y + " son adyacentes.");
                } else {
                    JOptionPane.showMessageDialog(null, "Los nodos " + x + " y " + y + " no son adyacentes.");
                }
            }
        });

        conectadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                int i = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el indice i:"));
                int j = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el indice j:"));
                boolean resultado = grafo.conectados(i, j);
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Los nodos " + i + " y " + j + " estan conectados.");
                } else {
                    JOptionPane.showMessageDialog(null, "Los nodos " + i + " y " + j + " no estan conectados o los indice no son validos.");
                }
            }
        });

        eliminarAristasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                // Llama al metodo que elimina todas las aristas.
                grafo.eliminarAristas();
                // Muestra un mensaje de confirmación al usuario.
                JOptionPane.showMessageDialog(null, "Todas las aristas han sido eliminadas.");
                // Actualiza la visualización de los nodos y aristas.
                actualizarGrafoVisual();
            }
        });

        tieneCiclosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                // Llama al metodo que verifica si el grafo tiene ciclos.
                boolean ciclos = grafo.tieneCiclos();
                // Muestra un mensaje dependiendo de si se encontraron ciclos o no.
                if (ciclos) {
                    JOptionPane.showMessageDialog(null, "El grafo tiene ciclos.");
                } else {
                    JOptionPane.showMessageDialog(null, "El grafo no tiene ciclos.");
                }
            }
        });

        topologicalSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                // Llama al metodo que realiza el ordenamiento topológico.
                String resultado = grafo.topologicalSort();
                // Muestra el resultado del ordenamiento en un mensaje emergente.
                JOptionPane.showMessageDialog(null, resultado);
            }
        });

        guardarGrafoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //Metodo que provoca un evento
                String nombreArchivo = "grafos.txt";

                grafo.guardarEnArchivo(nombreArchivo);
            }
        });

        verGrafosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Metodo que provoca un evento
                File archivo = new File("grafos.txt");

                if (archivo.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                        StringBuilder contenido = new StringBuilder();
                        String linea;
                        while ((linea = reader.readLine()) != null) {
                            contenido.append(linea).append("\n");
                        }
                        JTextArea text = new JTextArea(contenido.toString());
                        new JFrame("Historial grafos");
                        add(text);

                        text.setWrapStyleWord(true);
                        text.setLineWrap(true);
                        text.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(text);
                        add(scrollPane);

                    } catch (IOException ex) {
                        System.out.println("Error al leer el archivo de grafo: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo 'grafos.txt' no se encontro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Se añaden los botones en el panel
        panelBotones.add(mostrarListaButton);
        panelBotones.add(mostrarMatrizButton);
        panelBotones.add(agregarNodoButton);
        panelBotones.add(agregarAristaButton);
        panelBotones.add(gradoEntradaButton);
        panelBotones.add(gradoSalidaButton);
        panelBotones.add(cuantasAristasHayButton);
        panelBotones.add(adyacenteButton);
        panelBotones.add(conectadosButton);
        panelBotones.add(eliminarAristasButton);
        panelBotones.add(tieneCiclosButton);
        panelBotones.add(topologicalSortButton);
        panelBotones.add(guardarGrafoButton);
        panelBotones.add(verGrafosButton);
        textoGrafo = new JTextArea();
        textoGrafo.setEditable(false);
        textoGrafo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textoGrafo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textoGrafo);

        panelGrafo = new JPanel(new BorderLayout());
        panelGrafo.setPreferredSize(new Dimension(1000, 500));

        add(panelBotones, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelGrafo, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }
    /*
     * Metodo que dibuja los grafos en la GUI
     */
    private void inicializarGrafoVisual() {
        jGraph = new mxGraph();
        Object parent = jGraph.getDefaultParent();
        jGraph.getModel().beginUpdate();
        try {
            Map<Integer, Object> vertexMap = new HashMap<>();
            for (NodoGrafo<Integer> nodo : grafo.getNodos()) {
                Object vertex = jGraph.insertVertex(parent, null, String.valueOf(nodo.getInfo()), 0, 0, 70, 50);
                vertexMap.put(nodo.getInfo(), vertex);
            }
            for (NodoGrafo<Integer> nodo : grafo.getNodos()) {
                for (NodoGrafo<Integer> adyacente : nodo.getNodosSiguientes()) {
                    Object sourceVertex = vertexMap.get(nodo.getInfo());
                    Object targetVertex = vertexMap.get(adyacente.getInfo());
                    if (sourceVertex != null && targetVertex != null) {
                        jGraph.insertEdge(parent, null, "", sourceVertex, targetVertex);
                    }
                }
            }
        } finally {
            jGraph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(jGraph);
        graphComponent.setPreferredSize(new Dimension(1000, 500));
        panelGrafo.add(graphComponent, BorderLayout.CENTER);
    }
    /*
     * Metodo que actualiza los grafos en la GUI
     */
    private void actualizarGrafoVisual() {
        panelGrafo.removeAll();
        inicializarGrafoVisual();
        revalidate();
        repaint();
    }
    /*
     * Metodo main
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazGrafo frame = new InterfazGrafo();
            frame.setVisible(true);
        });
    }
}
