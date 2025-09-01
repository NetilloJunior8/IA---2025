class Nodo {
    int valor;
    Nodo izq, der;

    Nodo(int v) { valor = v; }
}

class Arbol {
    Nodo raiz;

    boolean vacio() { return raiz == null; }

    void insertar(int v) { raiz = insRec(raiz, v); }
    Nodo insRec(Nodo n, int v) {
        if (n == null) return new Nodo(v);
        if (v < n.valor) n.izq = insRec(n.izq, v);
        else if (v > n.valor) n.der = insRec(n.der, v);
        return n;
    }

    Nodo buscarNodo(int v) { return busRec(raiz, v); }
    Nodo busRec(Nodo n, int v) {
        if (n == null || n.valor == v) return n;
        return (v < n.valor) ? busRec(n.izq, v) : busRec(n.der, v);
    }

    void imprimirArbol() { inOrden(raiz); System.out.println(); }
    void inOrden(Nodo n) {
        if (n != null) {
            inOrden(n.izq);
            System.out.print(n.valor + " ");
            inOrden(n.der);
        }
    }

    public static void main(String[] args) {
        Arbol a = new Arbol();
        a.insertar(50); a.insertar(30); a.insertar(70);
        a.insertar(20); a.insertar(40); a.insertar(60); a.insertar(80);

        System.out.println("Árbol en inorden:");
        a.imprimirArbol();

        System.out.println("¿Árbol vacío?: " + a.vacio());
        System.out.println("Buscar 40: " + (a.buscarNodo(40) != null ? "Encontrado" : "No encontrado"));
    }
}
