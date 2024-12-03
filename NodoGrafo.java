package org.Proyecto;
import java.util.ArrayList;
public class NodoGrafo<T> {
    private T info; // Información del nodo

    // ArrayList que almacena los nodos siguientes y anteriores
    private ArrayList<NodoGrafo<T>> nodosSiguientes = new ArrayList<>();

    // Grado que se toma en cuenta en el ordenamiento topológico
    private int gradoDeEntrada = 0;

    // Constructor del nodo
    public NodoGrafo(T info) {
        this.info = info;
    }

    // Getters y setters de info
    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    // Getters y setters de gradoDeEntrada
    public int getGradoDeEntrada() {
        return gradoDeEntrada;
    }

    public void setGradoDeEntrada(int gradoDeEntrada) {
        this.gradoDeEntrada = gradoDeEntrada;
    }

    // Método para agregar un nuevo nodo a la lista de nodos siguientes
    public void agregarNodo(NodoGrafo<T> nodo) {
        nodosSiguientes.add(nodo);
        gradoDeEntrada++;
    }

    // Getter de la lista de nodos siguientes
    public ArrayList<NodoGrafo<T>> getNodosSiguientes() {
        return nodosSiguientes;
    }
}
