package banco.example.banco.service;

import banco.example.banco.model.Corrente;
import banco.example.banco.model.Poupanca;
import banco.example.banco.model.Salario;
import banco.example.banco.model.request.Request;
import banco.example.banco.repository.CorrenteRepository;
import banco.example.banco.repository.PoupancaRepository;
import banco.example.banco.repository.SalarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContaService {

    CorrenteRepository correnteRepository;
    SalarioRepository salarioRepository;
    PoupancaRepository poupancaRepository;

    public Corrente getCorrente(Request request) throws Exception {
        var correnteData = correnteRepository.findByNumeroDeConta(request.getConDestino());
        if(correnteData.isEmpty())
            throw new Exception("Conta não encontrada!");
        if (!correnteData.get().getStatus())
            throw new Exception("Conta: " + correnteData.get().getNumeroDeConta() + " desativada!");
        return correnteData.get();
    }

    public Poupanca getPoupanca(Request request) throws Exception {
        var poupancaData = poupancaRepository.findByNumeroDeConta(request.getConDestino());
        if(poupancaData.isEmpty())
            throw new Exception("Conta não encontrada!");
        if(!poupancaData.get().getStatus())
            throw new Exception("Conta: " + poupancaData.get().getNumeroDeConta() + " desativada!");
        return poupancaData.get();
    }

    public Salario getSalario(Request request) throws Exception {
        var salarioData = salarioRepository.findByNumeroDeConta(request.getConDestino());
        if(salarioData.isEmpty())
            throw new Exception("Conta não encontrada!");
        if (!salarioData.get().getStatus())
            throw new Exception("Conta: " + salarioData.get().getNumeroDeConta() + " desativada!");
        return salarioData.get();
    }
}
