package IA;

import java.util.*;

public class BusquedaDFS {

    public static void buscar(Grafo grafo, Nodo inicio, Nodo objetivo) {
        System.out.println("\n--- Búsqueda Primero en Profundidad (DFS) ---");
        Stack<Nodo> pila = new Stack<>();
        Set<Nodo> visitados = new HashSet<>();
        Map<Nodo, Nodo> predecesores = new HashMap<>();

        pila.push(inicio);

        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();

            if (!visitados.contains(actual)) {
                visitados.add(actual);
                System.out.println("Visitando: " + actual.nombre);

                if (actual.equals(objetivo)) {
                    System.out.println("¡Objetivo encontrado: " + objetivo.nombre + "!");
                    imprimirCamino(predecesores, inicio, objetivo);
                    return;
                }
                
                List<Arista> vecinos = grafo.getVecinos(actual);
                Collections.reverse(vecinos);
                for (Arista arista : vecinos) {
                    Nodo vecino = arista.destino;
                    if (!visitados.contains(vecino)) {
                        predecesores.put(vecino, actual);
                        pila.push(vecino);
                    }
                }
            }
        }
        System.out.println("El objetivo no se encontró.");
    }
    
    private static void imprimirCamino(Map<Nodo, Nodo> predecesores, Nodo inicio, Nodo objetivo) {
        List<Nodo> camino = new ArrayList<>();
        Nodo actual = objetivo;
        while (actual != null) {
            camino.add(actual);
            actual = predecesores.get(actual);
        }
        Collections.reverse(camino);
        System.out.println("Camino: " + camino);
    }
}