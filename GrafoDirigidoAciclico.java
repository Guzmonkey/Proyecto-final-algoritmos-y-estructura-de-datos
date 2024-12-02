package uabc.topologicalsort;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GrafoDirigidoAciclico<T> {
    private ArrayList<NodoGrafo<T>> nodos = new ArrayList<>();
    private int[][] matrizDeAdyacencia;

    public GrafoDirigidoAciclico() {
        
    }

    public GrafoDirigidoAciclico(int x){
        for (int i = 0; i < x; i++) {
            NodoGrafo<Integer> nodo = new NodoGrafo<>(i);
            nodos.add((NodoGrafo<T>) nodo);
        }
    }

    public GrafoDirigidoAciclico( boolean valoresAleatorios) {
        if (valoresAleatorios) {
            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                NodoGrafo<Integer> nodo = new NodoGrafo<>(random.nextInt(100));
                nodos.add((NodoGrafo<T>) nodo);
            }
        }
    }

    public void agregarNodo(NodoGrafo<T> nodo) {
        nodos.add(nodo);
    }

    public void hacerMatrizDeAdyacencia() {
        //Tomamos el size de la lista de nodos
        int n = nodos.size();
        //Creamos la matriz de la lista de nodos
        matrizDeAdyacencia = new int[n][n];
        
        //Recorremos la la lista de los nodos
        for (int i = 0; i < nodos.size(); i++) {
            //Tomamos un nodo i
            NodoGrafo<T> nodo = nodos.get(i);
            //Recorremos la lista de los nodos siguientes del nodo
            for (NodoGrafo<T> nodoSiguiente : nodo.getNodosSiguientes()) {
                //Recorremos nuevamente la lista de los nodos
                for (int j = 0; j < nodos.size(); j++) {
                    //Si la info del nodo siguiente es igual al nodo es que hay una conexion
                    if (nodoSiguiente.getInfo().equals(nodos.get(j).getInfo())) {
                        matrizDeAdyacencia[i][j] = 1;
                    }
                }
            }
        }
    }

    public int[][] getMatrizDeAdyacencia() {
        return matrizDeAdyacencia;
    }

    public int gradoDeEntrada(int x){
        int gradoDeEntrada = -1;
        int numNodos = nodos.size();

        if (x < 0 || x >= nodos.size()) {
            System.out.println("Indice fuera de rango");
        } else {
            gradoDeEntrada = 0;
            for (int i = 0; i < numNodos; i++) {
                NodoGrafo<T> nodo = nodos.get(i);
                for (int j = 0; j < nodo.getNodosSiguientes().size(); j++) {
                    NodoGrafo<T> nodoAux = nodo.getNodosSiguientes().get(j);
                    if (nodoAux.getInfo().equals(nodos.get(x).getInfo())){
                        gradoDeEntrada++;
                    }
                }
            }
        }
        return gradoDeEntrada;
    }

    public int gradoDeSalida(int x){
        return nodos.get(x).getNodosSiguientes().size();
    }

    public int cuantasAristasHay(){
        int numeroDeAristas = 0;
        for (int i = 0; i < nodos.size(); i++) {
            numeroDeAristas += nodos.get(i).getNodosSiguientes().size();
        }
        return numeroDeAristas;
    }

    public boolean adyacente(int x, int y){
        NodoGrafo<T> nodoX = nodos.get(x);

        for (int i = 0; i < nodoX.getNodosSiguientes().size(); i++) {
            NodoGrafo<T> nodoAux = nodoX.getNodosSiguientes().get(i);
            if (nodoAux.getInfo().equals(nodos.get(y).getInfo())){
                return true;
            }
        }

        return false;
    }

    //Codigo visto en clase
    public boolean conectados(int i, int j) {
        if (i < 0 || i >= nodos.size() || j < 0 || j >= nodos.size()) {
            System.out.println("Indices no permitidos");
        }
        boolean[] visitados = new boolean[nodos.size()];
        return dfs(i, j, visitados);
    }
    
    private boolean dfs(int actual, int destino, boolean[] visitados) {
        if (actual == destino) {
            return true;
        }
        visitados[actual] = true;
        NodoGrafo<T> nodoActual = nodos.get(actual);
        for (NodoGrafo<T> nodoSiguiente : nodoActual.getNodosSiguientes()) {
            int siguienteIndex = nodos.indexOf(nodoSiguiente);
            if (!visitados[siguienteIndex]) {
                if (dfs(siguienteIndex, destino, visitados)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void mostrarListaDeAdyacencia() {
        for (int i = 0; i < nodos.size(); i++) {
            NodoGrafo<T> nodo = nodos.get(i);
            System.out.print("Nodo " + nodo.getInfo() + " -> ");
            ArrayList<NodoGrafo<T>> nodosSiguientes = nodo.getNodosSiguientes();
            if (nodosSiguientes.isEmpty()) {
                System.out.println("No tiene nodos adyacentes.");
            } else {
                for (NodoGrafo<T> nodoSiguiente : nodosSiguientes) {
                    System.out.print(nodoSiguiente.getInfo() + " ");
                }
                System.out.println(); 
            }
        }
    }  

    public void eliminarAristas(){
        for (NodoGrafo<T> nodo : nodos) {
            nodo.getNodosSiguientes().clear();
        }
        matrizDeAdyacencia = null;
    }

    public boolean insertarArista(int x, int y){
        if (x < 0 || x >= nodos.size() || y < 0 || y >= nodos.size()) {
            System.out.println("Esos nodos no existen");
        }

        if (x == y){
            return false;
        }

        NodoGrafo<T> nodoX = nodos.get(x);
        NodoGrafo<T> nodoY = nodos.get(y);

        nodoX.getNodosSiguientes().add(nodoY);
        if (tieneCiclos()){
            nodoX.getNodosSiguientes().remove(nodoY);
            return false;
        }

        return true;
    }

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
    
    public String topologicalSort() {
        // Verificamos si hay ciclos antes de proceder
        if (tieneCiclos()) {
            return "Ciclo detectado";
        }

        // Variables para el ordenamiento topológico
        int numNodos = nodos.size();
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
