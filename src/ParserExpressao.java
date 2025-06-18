import java.util.*;

class ParserExpressao {

    public static ExpressaoNode parse(String input) {
        List<String> tokens = tokenizar(input);
        List<String> postfix = paraPosfixa(tokens);
        return construirArvore(postfix);
    }

    private static List<String> tokenizar(String entrada) {
        List<String> tokens = new ArrayList<>();
        StringBuilder num = new StringBuilder();

        for (char c : entrada.toCharArray()) {
            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c)) {
                num.append(c);
            } else {
                if (num.length() > 0) {
                    tokens.add(num.toString());
                    num.setLength(0);
                }
                if ("()+-*/".indexOf(c) >= 0) {
                    tokens.add(Character.toString(c));
                } else {
                    throw new RuntimeException("Caractere inválido: " + c);
                }
            }
        }

        if (num.length() > 0) tokens.add(num.toString());
        return tokens;
    }

    private static int precedencia(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }

    private static List<String> paraPosfixa(List<String> tokens) {
        List<String> saida = new ArrayList<>();
        Stack<String> pilha = new Stack<>();

        for (String token : tokens) {
            if (token.matches("\\d+")) {
                saida.add(token);
            } else if ("(".equals(token)) {
                pilha.push(token);
            } else if (")".equals(token)) {
                while (!pilha.isEmpty() && !"(".equals(pilha.peek())) {
                    saida.add(pilha.pop());
                }
                pilha.pop(); // remove "("
            } else {
                while (!pilha.isEmpty() && precedencia(pilha.peek()) >= precedencia(token)) {
                    saida.add(pilha.pop());
                }
                pilha.push(token);
            }
        }

        while (!pilha.isEmpty()) saida.add(pilha.pop());
        return saida;
    }

    private static ExpressaoNode construirArvore(List<String> postfix) {
        Stack<ExpressaoNode> pilha = new Stack<>();

        for (String token : postfix) {
            if (token.matches("\\d+")) {
                pilha.push(new NumeroNode(Integer.parseInt(token)));
            } else {
                ExpressaoNode b = pilha.pop();
                ExpressaoNode a = pilha.pop();
                pilha.push(new OperadorNode(token, a, b));
            }
        }

        return pilha.pop();
    }
}
