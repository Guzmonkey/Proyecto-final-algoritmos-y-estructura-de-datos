package uabc.topologicalsort;

import java.util.ArrayList;
import java.util.Objects;

public class NodoGrafo<T>{
    private T info;
    private final ArrayList<NodoGrafo<T>> ant = new ArrayList<>();
    private final ArrayList<NodoGrafo<T>> sig = new ArrayList<>();

    public NodoGrafo(T info){
        this.info = info;
    }
    
    public ArrayList<NodoGrafo<T>> getSig() {
        return sig;
    }

    public void addSig(NodoGrafo<T> sig) {
        this.sig.add(sig);
    }

    public ArrayList<NodoGrafo<T>> getAnt() {
        return ant;
    }

    public void setAnt(NodoGrafo<T> ant) {
        this.ant.add(ant);
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "" + info;
    }
    
    @Override
    public boolean equals(Object obj) {
        NodoGrafo<?> nodo = (NodoGrafo<?>) obj;
        return Objects.equals(info, nodo.info);
    }
}
