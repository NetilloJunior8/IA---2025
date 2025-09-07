public class Arbol {
    private Nodo raiz;

    public Arbol() {
        this.raiz = null;
    }

    public boolean vacio() {
        return raiz == null;
    }

    public void insertar(String nombre) {
        raiz = insertarRec(raiz, nombre);
    }

    private Nodo insertarRec(Nodo actual, String nombre) {
        if (actual == null) {
            return new Nodo(nombre);
        }
        if (nombre.compareToIgnoreCase(actual.nombre) < 0) {
            actual.izquierda = insertarRec(actual.izquierda, nombre);
        } else if (nombre.compareToIgnoreCase(actual.nombre) > 0) {
            actual.derecha = insertarRec(actual.derecha, nombre);
        }
        return actual;
    }

    public Nodo buscarNodo(String nombre) {
        return buscarRec(raiz, nombre);
    }

    private Nodo buscarRec(Nodo actual, String nombre) {
        if (actual == null || actual.nombre.equalsIgnoreCase(nombre)) {
            return actual;
        }
        if (nombre.compareToIgnoreCase(actual.nombre) < 0) {
            return buscarRec(actual.izquierda, nombre);
        } else {
            return buscarRec(actual.derecha, nombre);
        }
    }

    public void imprimirArbol() {
        imprimirInOrden(raiz);
        System.out.println();
    }

    private void imprimirInOrden(Nodo nodo) {
        if (nodo != null) {
            imprimirInOrden(nodo.izquierda);
            System.out.print(nodo.nombre + " ");
            imprimirInOrden(nodo.derecha);
        }
    }
}
