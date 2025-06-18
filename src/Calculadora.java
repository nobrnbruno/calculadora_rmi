import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Calculadora  implements ICalculadora {

    private static final long serialVersionUID = 1L;

    private static int chamadas = 0;

    public int soma(int a, int b) throws RemoteException {
        System.out.println("Método soma chamado " + chamadas++);
        return a + b;
    }

    public int subtracao(int a, int b) throws RemoteException {
        System.out.println("Subtração chamada " + chamadas++);
        return a - b;
    }

    public int multiplicacao(int a, int b) throws RemoteException {
        System.out.println("Multiplicação chamada " + chamadas++);
        return a * b;
    }

    public int divisao(int a, int b) throws RemoteException {
        System.out.println("Divisão chamada " + chamadas++);
        if (b == 0) throw new ArithmeticException("Divisão por zero");
        return a / b;
    }

    public static void main(String[] args) throws Exception {
        Calculadora calc = new Calculadora();
        ICalculadora stub = (ICalculadora) UnicastRemoteObject.exportObject(calc, 0);

        Registry reg;
        try {
            reg = LocateRegistry.createRegistry(1099);
        } catch (Exception e) {
            reg = LocateRegistry.getRegistry(1099);
        }

        reg.rebind("calculadora", stub);
        System.out.println("Servidor pronto!");
    }
}