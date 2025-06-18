import java.rmi.RemoteException;

abstract class ExpressaoNode {
    abstract int avaliar(ICalculadora calc) throws RemoteException;
}

class NumeroNode extends ExpressaoNode {
    int valor;

    public NumeroNode(int valor) {
        this.valor = valor;
    }

    int avaliar(ICalculadora calc) {
        return valor;
    }
}

class OperadorNode extends ExpressaoNode {
    String operador;
    ExpressaoNode esquerdo, direito;

    public OperadorNode(String operador, ExpressaoNode esquerdo, ExpressaoNode direito) {
        this.operador = operador;
        this.esquerdo = esquerdo;
        this.direito = direito;
    }

    int avaliar(ICalculadora calc) throws RemoteException {
        int a = esquerdo.avaliar(calc);
        int b = direito.avaliar(calc);
        return switch (operador) {
            case "+" -> calc.soma(a, b);
            case "-" -> calc.subtracao(a, b);
            case "*" -> calc.multiplicacao(a, b);
            case "/" -> calc.divisao(a, b);
            default -> throw new RuntimeException("Operador inválido: " + operador);
        };
    }
}
