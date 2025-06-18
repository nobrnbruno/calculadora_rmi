import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculadora extends Remote {
    int soma(int a, int b) throws RemoteException;
    int subtracao(int a, int b) throws RemoteException;
    int multiplicacao(int a, int b) throws RemoteException;
    int divisao(int a, int b) throws RemoteException;
}