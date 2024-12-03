package org.Proyecto;
// Importamos clases necesarias
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GrafoDirigidoAciclico<T> {
        // Declaracion de atributos
        private ArrayList<NodoGrafo<T>> nodos = new ArrayList<>();
        private int[][] matrizDeAdyacencia;
        /*
         * Constructor vacio
         */
        public GrafoDirigidoAciclico() {

        }
        /*
         * Constructor que recibe de parametro x
         */
        public GrafoDirigidoAciclico(int x) {
            for (int i = 0; i < x; i++) {
                NodoGrafo<Integer> nodo = new NodoGrafo<>(i);
                nodos.add((NodoGrafo<T>) nodo);
            }
            // Inicializar la matriz de adyacencia al crear el grafo.
            hacerMatrizDeAdyacencia();
        }
        /*
         * Constructor que recibe de parametros si se crean numeros aleatorios
         */
        public GrafoDirigidoAciclico(boolean valoresAleatorios) {
            if (valoresAleatorios) {
                Random random = new Random();
                for (int i = 0; i < 4; i++) {
                    NodoGrafo<Integer> nodo = new NodoGrafo<>(random.nextInt(100));
                    nodos.add((NodoGrafo<T>) nodo);
                }
                // Inicializar la matriz de adyacencia con nodos aleatorios.
                hacerMatrizDeAdyacencia();
            }
        }
        /*
         * Metodo Getter que obtiene la lista de nodos
         */
        public List<NodoGrafo<T>> getNodos() {
            return nodos;
        }
        /*
         * Metodo que permite agregar un nuevo nodo
         */
        public void agregarNodo(NodoGrafo<T> nodo) {
            nodos.add(nodo);
            // Rehacer la matriz de adyacencia al agregar un nuevo nodo.
            hacerMatrizDeAdyacencia();
        }
        /*
         * Metodo que permite crear una matriz de adyacencia y muestra las conexiones entre nodos
         */
        public void hacerMatrizDeAdyacencia() {
            int n = nodos.size();
            if (n == 0) {
                matrizDeAdyacencia = new int[0][0];
                return;
            }
            matrizDeAdyacencia = new int[n][n];
            for (int i = 0; i < nodos.size(); i++) {
                NodoGrafo<T> nodo = nodos.get(i);
                for (NodoGrafo<T> nodoSiguiente : nodo.getNodosSiguientes()) {
                    for (int j = 0; j < nodos.size(); j++) {
                        if (nodoSiguiente.getInfo().equals(nodos.get(j).getInfo())) {
                            matrizDeAdyacencia[i][j] = 1;
                        }
                    }
                }
            }
        }
        /*
         * Metodo Getter que obtiene la matriz de adyacencia
         */
        public int[][] getMatrizDeAdyacencia() {
            return matrizDeAdyacencia;
        }
        /*
         * Metodo que calcula el grado de entrada de un nodo
         */
        public int gradoDeEntrada(int x) {
            int gradoDeEntrada = -1;
            int numNodos = nodos.size();
            if (x < 0 || x >= nodos.size()) {
                System.out.println("Indice fuera de rango");
            } else {
                gradoDeEntrada = 0;
                for (int i = 0; i < numNodos; i++) {
                    NodoGrafo<T> nodo = nodos.get(i);
                    for (NodoGrafo<T> nodoAux : nodo.getNodosSiguientes()) {
                        if (nodoAux.getInfo().equals(nodos.get(x).getInfo())) {
                            gradoDeEntrada++;
                        }
                    }
                }
            }
            return gradoDeEntrada;
        }
        /*
         * Metodo que calcula el grado de salida de un nodo
         */
        public int gradoDeSalida(int x) {
            return nodos.get(x).getNodosSiguientes().size();
        }
        /*
         * Metodo que calcula la cantidad de aristas totales que hay en un grafo
         */
        public int cuantasAristasHay() {
            int numeroDeAristas = 0;
            for (int i = 0; i < nodos.size(); i++) {
                numeroDeAristas += nodos.get(i).getNodosSiguientes().size();
            }
            return numeroDeAristas;
        }
        /*
         * Metodo que verifica si dos nodos estan conectados
         */
        public boolean adyacente(int x, int y) {
            NodoGrafo<T> nodoX = nodos.get(x);
            for (NodoGrafo<T> nodoAux : nodoX.getNodosSiguientes()) {
                if (nodoAux.getInfo().equals(nodos.get(y).getInfo())) {
                    return true;
                }
            }
            return false;
        }
        /*
         * Metodo que verifica si dos nodos estan conectados de forma directa
         */
        public boolean conectados(int i, int j) {
            boolean[] visitados;
            if (i < 0 || i >= nodos.size() || j < 0 || j >= nodos.size()) {
                System.out.println("Indices no permitidos");
            }
            visitados = new boolean[nodos.size()];
            return dfs(i, j, visitados);
        }
        /*
         * Metodo que utiliza el metodo DFS para verificar si hay conexion entre dos nodos
         */
        private boolean dfs(int actual, int destino, boolean[] visitados) {
            int siguienteIndex;
            if (actual == destino) {
                return true;
            }
            visitados[actual] = true;
            NodoGrafo<T> nodoActual = nodos.get(actual);
            for (NodoGrafo<T> nodoSiguiente : nodoActual.getNodosSiguientes()) {
                siguienteIndex = nodos.indexOf(nodoSiguiente);
                if (!visitados[siguienteIndex]) {
                    if (dfs(siguienteIndex, destino, visitados)) {
                        return true;
                    }
                }
            }
            return false;
        }
        /*
         * Metodo que permite eliminar todas las aristas
         */
        public void eliminarAristas() {
            int numNodos = nodos.size();
            for (NodoGrafo<T> nodo : nodos) {
                nodo.getNodosSiguientes().clear();
            }
            if (matrizDeAdyacencia == null || matrizDeAdyacencia.length != numNodos) {
                matrizDeAdyacencia = new int[numNodos][numNodos];
            } else {
                for (int i = 0; i < numNodos; i++) {
                    for (int j = 0; j < numNodos; j++) {
                        matrizDeAdyacencia[i][j] = 0;
                    }
                }
            }
        }
        /*
         * Metodo que permite insertar una arista entre dos nodos
         */
        public boolean insertarArista(int x, int y) {
            if (x < 0 || x >= nodos.size() || y < 0 || y >= nodos.size()) {
                System.out.println("Esos nodos no existen");
                return false;
            }
            if (x == y) {
                return false;
            }
            NodoGrafo<T> nodoX = nodos.get(x);
            NodoGrafo<T> nodoY = nodos.get(y);
            nodoX.getNodosSiguientes().add(nodoY);
            if (tieneCiclos()) {
                nodoX.getNodosSiguientes().remove(nodoY);
                return false;
            }
            // Rehacer la matriz de adyacencia al agregar la arista.
            hacerMatrizDeAdyacencia();
            return true;
        }
        /*
         * Metodo que permite mostrar la lista de adyacencia
         */
        public String mostrarListaDeAdyacencia() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodos.size(); i++) {
                NodoGrafo<T> nodo = nodos.get(i);
                sb.append("Nodo ").append(nodo.getInfo()).append(" -> ");
                ArrayList<NodoGrafo<T>> nodosSiguientes = nodo.getNodosSiguientes();
                if (nodosSiguientes.isEmpty()) {
                    sb.append("No tiene nodos adyacentes.\n");
                } else {
                    for (NodoGrafo<T> nodoSiguiente : nodosSiguientes) {
                        sb.append(nodoSiguiente.getInfo()).append(" ");
                    }
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
        /*
         * Metodo que verifica si dos nodos tienen ciclos
         */
        public boolean tieneCiclos() {
            int numNodos = nodos.size();
            boolean[] visitado = new boolean[numNodos];
            boolean[] enPila = new boolean[numNodos];
            for (int i = 0; i < numNodos; i++) {
                if (!visitado[i]) {
                    if (tieneCicloDFS(i, visitado, enPila)) {
                        return true;
                    }
                }
            }
            return false;
        }
        /*
         * Metodo que verifica si dos nodos tiene ciclos utilizando DFS
         */
        private boolean tieneCicloDFS(int i, boolean[] visitado, boolean[] enPila) {
            if (enPila[i]) {
                return true;
            }
            if (visitado[i]) {
                return false;
            }
            visitado[i] = true;
            enPila[i] = true;
            NodoGrafo<T> nodo = nodos.get(i);
            for (NodoGrafo<T> vecino : nodo.getNodosSiguientes()) {
                int indexVecino = nodos.indexOf(vecino);
                if (tieneCicloDFS(indexVecino, visitado, enPila)) {
                    return true;
                }
            }
            enPila[i] = false;
            return false;
        }
        /*
         * Metodo que utiliza el metodo topologicalSort
         */
        public String topologicalSort() {
            int numNodos;
            // Verificamos si hay ciclos antes de proceder
            if (tieneCiclos()) {
                return "Ciclo detectado";
            }
            numNodos = nodos.size();
            boolean[] visitado = new boolean[numNodos];
            Stack<NodoGrafo<T>> stack = new Stack<>();
            // Realizamos un DFS para llenar la pila
            for (int i = 0; i < numNodos; i++) {
                if (!visitado[i]) {
                    topologicalSortDFS(i, visitado, stack);
                }
            }
            // Convertimos la pila a un string
            StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty()) {
                sb.append(stack.pop().getInfo());
                if (!stack.isEmpty()) {
                    sb.append(" - ");
                }
            }
            return sb.toString();
        }
        /*
         * Metodo que guarda los nodos creados en un archivo de texto
         */
        public void guardarEnArchivo(String nombreArchivo) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
                // Formato para la fecha y hora actual
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fechaHoraActual = LocalDateTime.now().format(formatter);

                // Escribir la fecha y hora en el archivo
                writer.write("\n--- Se guardo el dia y hora:" + fechaHoraActual + " ---\n");

                for (int i = 0; i < nodos.size(); i++) {
                    NodoGrafo<T> nodo = nodos.get(i);
                    writer.write("Nodo " + nodo.getInfo() + " -> ");
                    ArrayList<NodoGrafo<T>> nodosSiguientes = nodo.getNodosSiguientes();
                    if (nodosSiguientes.isEmpty()) {
                        writer.write("No tiene nodos adyacentes.\n");
                    } else {
                        for (NodoGrafo<T> nodoSiguiente : nodosSiguientes) {
                            writer.write(nodoSiguiente.getInfo() + " ");
                        }
                        writer.write("\n");
                    }
                }
                System.out.println("Grafo guardado exitosamente en " + nombreArchivo);
            } catch (IOException e) {
                System.out.println("Error al guardar el grafo en el archivo: " + e.getMessage());
            }
        }
        /*
         * Metodo privado que aplica el metodo topologicalSort
         */
        private void topologicalSortDFS(int i, boolean[] visitado, Stack<NodoGrafo<T>> stack) {
            visitado[i] = true;
            NodoGrafo<T> nodo = nodos.get(i);
            for (NodoGrafo<T> vecino : nodo.getNodosSiguientes()) {
                int indexVecino = nodos.indexOf(vecino);
                if (!visitado[indexVecino]) {
                    topologicalSortDFS(indexVecino, visitado, stack);
                }
            }
            stack.push(nodo);
        }

}
