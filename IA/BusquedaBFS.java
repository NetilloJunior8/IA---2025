package IA;

import java.util.*;

public class BusquedaBFS {

    public static void buscar(Grafo grafo, Nodo inicio, Nodo objetivo) {
        System.out.println("--- Búsqueda Primero en Anchura (BFS) ---");
        Queue<Nodo> cola = new LinkedList<>();
        Set<Nodo> visitados = new HashSet<>();
        Map<Nodo, Nodo> predecesores = new HashMap<>();

        cola.add(inicio);
        visitados.add(inicio);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            System.out.println("Visitando: " + actual.nombre);

            if (actual.equals(objetivo)) {
                System.out.println("¡Objetivo encontrado: " + objetivo.nombre + "!");
                imprimirCamino(predecesores, inicio, objetivo);
                return;
            }

            for (Arista arista : grafo.getVecinos(actual)) {
                Nodo vecino = arista.destino;
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    predecesores.put(vecino, actual);
                    cola.add(vecino);
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