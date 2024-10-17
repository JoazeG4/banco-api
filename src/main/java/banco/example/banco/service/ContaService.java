package banco.example.banco.service;

import banco.example.banco.exceptions.AccountNotFoundException;
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

    public Corrente getCorrente(@NotNull RequestTransacao requestTransacao) throws RuntimeException {
        var correnteData = correnteRepository.findByNumeroDeConta(requestTransacao.getContaDestino());
        if(correnteData.isEmpty())
            throw new AccountNotFoundException();
        if (!correnteData.get().getStatus())
            throw new RuntimeException("Conta: " + correnteData.get().getNumeroDeConta() + " desativada!");
        return correnteData.get();
    }

    public Poupanca getPoupanca(@NotNull RequestTransacao requestTransacao) throws RuntimeException {
        var poupancaData = poupancaRepository.findByNumeroDeConta(requestTransacao.getContaDestino());
        if(poupancaData.isEmpty())
            throw new AccountNotFoundException();
        if(!poupancaData.get().getStatus())
            throw new RuntimeException("Conta: " + poupancaData.get().getNumeroDeConta() + " desativada!");
        return poupancaData.get();
    }

    public Salario getSalario(@NotNull RequestTransacao requestTransacao) throws RuntimeException {
        var salarioData = salarioRepository.findByNumeroDeConta(requestTransacao.getContaDestino());
        if(salarioData.isEmpty())
            throw new AccountNotFoundException();
        if (!salarioData.get().getStatus())
            throw new RuntimeException("Conta: " + salarioData.get().getNumeroDeConta() + " desativada!");
        return salarioData.get();
    }
}
