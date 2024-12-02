package uabc.topologicalsort;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class GrafoDirigidoAciclico {
    
    private ArrayList<NodoGrafo> vertices = new ArrayList<>();
    private int [][] matrizAdyacencia;
    private Scanner scanner = new Scanner(System.in);
    private int orden;
    
    public GrafoDirigidoAciclico(int n){
        Random random = new Random();
        NodoGrafo vertice;
        matrizAdyacencia = new int[n][n];
        this.orden = n;
        System.out.println("[1] Numeros ordenados");
        System.out.println("[2] Numeros aleatorios");
        System.out.println("[3] Letras aleatorias");
        int eleccion;
        do{
            eleccion = scanner.nextInt();
        }while (eleccion<1 || eleccion>3);
        switch (eleccion){
            case 1 -> {
                for (int i=0; i<n; i++){
                    vertice = new NodoGrafo(i);
                    vertices.add(vertice);
                }
            }
            case 2 -> {
                for (int i=0; i<n; i++){
                    do{
                       int numero = random.nextInt(100);
                       vertice = new NodoGrafo(numero+1); 
                    }while (vertices.contains(vertice) == true);
                    vertices.add(vertice);
                }
            }
            case 3 -> {
                for (int i=0; i<n; i++){
                    do{
                        int numLetra = random.nextInt(26);
                        char letra = (char) (numLetra+65);
                        vertice = new NodoGrafo(letra);
                    }while (vertices.contains(vertice) == true);
                    vertices.add(vertice);
                }
            }
        }
    }
    
    public GrafoDirigidoAciclico(){
        Random random = new Random();
        NodoGrafo vertice;
        matrizAdyacencia = new int[4][4];
        this.orden = 4;
        for (int i=0; i<4; i++){
            do{
                int numero = random.nextInt(100);
                vertice = new NodoGrafo(numero+1); 
            }while (vertices.contains(vertice) == true);
            vertices.add(vertice);
        }
    }

    public int getOrden() {
        return orden;
    }
    
    public int gradoDeEntrada(int i){
        if (i >= vertices.size() || i < 0){
            throw new IllegalArgumentException("Indice fuera de rango.");
        }else{
            return vertices.get(i).getAnt().size();
        }
    }
    
    public int gradoDeSalida(int i){
        if (i >= vertices.size() || i < 0){
            throw new IllegalArgumentException("Indice fuera de rango.");
        }else{
            return vertices.get(i).getSig().size();
        }
    }
    
    public int cuantasAristasHay(){
        int aristas = 0;
        for (NodoGrafo nodo : vertices){
            aristas += nodo.getSig().size();
        }
        return aristas;
    }
    
    public boolean conectados(int i, int j){
        boolean flag = false;
        if (i>vertices.size() || i<0 || j>vertices.size() || j<0){
            throw new IllegalArgumentException("Indice fuera de rango");
        }else{
            if (matrizAdyacencia[i][j]==1 || matrizAdyacencia[j][i]==1){
                flag = true;
            }
        }
        return flag;
    }
    
    public String mostrarEstructura(){
        StringBuilder matrix = new StringBuilder();
        int orden = vertices.size();
        matrix.append("  "); // Espacio para los bordes de la matriz
        for (int i = 0; i < orden; i++) {
            matrix.append("  ").append(vertices.get(i).getInfo()).append("  ");
        }
        for (int i=0; i<orden; i++){
            matrix.append("\n").append(vertices.get(i).getInfo()).append("[");
            for (int j=0; j<orden; j++){
                matrix.append("| ").append(matrizAdyacencia[i][j]).append(" |");
            }
            matrix.append("]");
        }
        return matrix.toString();
    }
    
    public boolean insertarArista(int i, int j){
        boolean flag = false;
        if (i>vertices.size() || i<0 || j>vertices.size() || j<0){
            throw new IllegalArgumentException("Indice fuera de rango");
        }else{
            vertices.get(i).getSig().add(vertices.get(j));
            vertices.get(j).getAnt().add(vertices.get(i));
            matrizAdyacencia[i][j] = 1;
            flag = true;
        }
        return flag;
    }
    
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        for (NodoGrafo nodo : vertices){
            string.append(nodo.toString()).append(", ");
        }
        return string.toString();
    }
    
}
