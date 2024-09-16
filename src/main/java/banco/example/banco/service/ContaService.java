package banco.example.banco.service;

import banco.example.banco.model.Corrente;
import banco.example.banco.model.Poupanca;
import banco.example.banco.model.Salario;
import banco.example.banco.model.request.RequestTransacao;
import banco.example.banco.repository.CorrenteRepository;
import banco.example.banco.repository.PoupancaRepository;
import banco.example.banco.repository.SalarioRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContaService {

    CorrenteRepository correnteRepository;
    SalarioRepository salarioRepository;
    PoupancaRepository poupancaRepository;

    public Corrente getCorrente(@NotNull RequestTransacao requestTransacao) throws Exception {
        var correnteData = correnteRepository.findByNumeroDeConta(requestTransacao.getContaDestino());
        if(correnteData.isEmpty())
            throw new Exception("Conta não encontrada!");
        if (!correnteData.get().getStatus())
            throw new Exception("Conta: " + correnteData.get().getNumeroDeConta() + " desativada!");
        return correnteData.get();
    }

    public Poupanca getPoupanca(@NotNull RequestTransacao requestTransacao) throws Exception {
        var poupancaData = poupancaRepository.findByNumeroDeConta(requestTransacao.getContaDestino());
        if(poupancaData.isEmpty())
            throw new Exception("Conta não encontrada!");
        if(!poupancaData.get().getStatus())
            throw new Exception("Conta: " + poupancaData.get().getNumeroDeConta() + " desativada!");
        return poupancaData.get();
    }

    public Salario getSalario(@NotNull RequestTransacao requestTransacao) throws Exception {
        var salarioData = salarioRepository.findByNumeroDeConta(requestTransacao.getContaDestino());
        if(salarioData.isEmpty())
            throw new Exception("Conta não encontrada!");
        if (!salarioData.get().getStatus())
            throw new Exception("Conta: " + salarioData.get().getNumeroDeConta() + " desativada!");
        return salarioData.get();
    }
}
