import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CalculadoraCliente {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            ICalculadora calc = (ICalculadora) reg.lookup("calculadora");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite a expressão matemática (ex: (2+3)*4): ");
            String expressao = scanner.nextLine();

            ExpressaoNode raiz = ParserExpressao.parse(expressao);
            int resultado = raiz.avaliar(calc);
            System.out.println("Resultado: " + resultado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
