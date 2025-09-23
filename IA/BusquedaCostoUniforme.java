package IA;

import java.util.*;

public class BusquedaCostoUniforme {

    // Clase interna para guardar el estado en la cola de prioridad
    private static class Estado implements Comparable<Estado> {
        Nodo nodo;
        double costo;

        Estado(Nodo nodo, double costo) {
            this.nodo = nodo;
            this.costo = costo;
        }

        @Override
        public int compareTo(Estado otro) {
            return Double.compare(this.costo, otro.costo);
        }
    }

    // El método que tu Main está buscando
    public static void buscar(Grafo grafo, Nodo inicio, Nodo objetivo) {
        System.out.println("\n--- Búsqueda de Costo Uniforme (UCS) ---");
        PriorityQueue<Estado> pq = new PriorityQueue<>();
        Map<Nodo, Nodo> predecesores = new HashMap<>();
        Map<Nodo, Double> costos = new HashMap<>();

        pq.add(new Estado(inicio, 0));
        costos.put(inicio, 0.0);

        while (!pq.isEmpty()) {
            Estado estadoActual = pq.poll();
            Nodo nodoActual = estadoActual.nodo;

            if (costos.get(nodoActual) < estadoActual.costo) {
                continue;
            }

            if (nodoActual.equals(objetivo)) {
                System.out.println("¡Objetivo encontrado: " + objetivo.nombre + "!");
                System.out.println("Costo total: " + estadoActual.costo);
                imprimirCamino(predecesores, inicio, objetivo);
                return;
            }

            for (Arista arista : grafo.getVecinos(nodoActual)) {
                Nodo vecino = arista.destino;
                double nuevoCosto = estadoActual.costo + arista.costo;

                if (!costos.containsKey(vecino) || nuevoCosto < costos.get(vecino)) {
                    costos.put(vecino, nuevoCosto);
                    predecesores.put(vecino, nodoActual);
                    pq.add(new Estado(vecino, nuevoCosto));
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