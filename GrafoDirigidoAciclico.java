import java.util.Random;   
import java.util.ArrayList;
import java.util.List;

public class GrafoDirigidoAciclico{
    private int vertice;
    private boolean letter;
    private List<List<Integer>> listaAdyacencia;
    private Random random;
    private int[][] matrizAdyacencia;

    public GrafoDirigidoAciclico(int n, boolean letter){
        this.vertice = n - 1;
        this.letter = letter;
        listaAdyacencia = new ArrayList<>();
        matrizAdyacencia = new int[this.vertice][this.vertice];
    }

    public GrafoDirigidoAciclico(int n){
        this.vertice = n;
        listaAdyacencia = new ArrayList<>();
        matrizAdyacencia = new int[this.vertice][this.vertice];
    }

    public GrafoDirigidoAciclico(){
        random = new Random();
        this.vertice = random.nextInt(5);
        listaAdyacencia = new ArrayList<>();
        matrizAdyacencia = new int[this.vertice][this.vertice];
    }

    public boolean insertarArista(int i, int j){
        try{
            if(i == j || matrizAdyacencia[i][j] == 1){
                return false;
            }else if(i > this.vertice || j > this.vertice){
                throw new IndexOutOfBoundsException("Indice i y j fuera del rango del grafo\n");
            }
            matrizAdyacencia[i][j] = 1;
            return true;
        }catch(IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
            return false;
        }
    
    }

    public boolean conectados(int i, int j){
        if (matrizAdyacencia[i][j] == 1 ){
            System.out.println("Conectados");
            return true;
        }else{
            return false;
        }
    }

    public String mostrarEstructura() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrizAdyacencia.length; i++) {
            for (int j = 0; j < matrizAdyacencia[i].length; j++) {
                sb.append(matrizAdyacencia[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        GrafoDirigidoAciclico g = new GrafoDirigidoAciclico(3);
        g.insertarArista(1, 2);
        g.insertarArista(0, 2);
        g.conectados(1, 2);
        System.out.println(g.mostrarEstructura());
    }
}
