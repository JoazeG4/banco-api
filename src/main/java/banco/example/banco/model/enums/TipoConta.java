package banco.example.banco.model.enums;

public enum TipoConta {

    CORRENTE,
    POUPANCA,
    SALARIO;


    public static int getTipoConta(String valor){
        if (valor.equalsIgnoreCase("CORRENTE")){
            return 1;
        } else if (valor.equalsIgnoreCase("POUPANCA")){
            return 2;
        } else if (valor.equalsIgnoreCase("SALARIO")){
            return 3;
        }
        return 0;
    }
}
