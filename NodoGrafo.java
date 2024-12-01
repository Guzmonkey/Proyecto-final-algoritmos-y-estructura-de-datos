import java.util.ArrayList;
public class NodoGrafo<T>{
    private T info;
    private ArrayList<T> ant;
    private ArrayList<T> sig;

    public NodoGrafo(){}

    public NodoGrafo(T info, ArrayList ant, ArrayList sig){

    }
    
    public ArrayList<T> getSig() {
        return sig;
    }

    public void setSig(ArrayList<T> sig) {
        this.sig = sig;
    }

    public ArrayList<T> getAnt() {
        return ant;
    }

    public void setAnt(ArrayList<T> ant) {
        this.ant = ant;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "" + info + "" + ant + "" + sig;
    }
}
