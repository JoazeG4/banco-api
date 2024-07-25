package banco.example.banco.service;

import banco.example.banco.model.Poupanca;
import banco.example.banco.model.request.Request;
import banco.example.banco.repository.PoupancaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PoupancaService {

    private PoupancaRepository poupancaRepository;

    public String salvar(Poupanca poupanca) throws Exception {
        var contaData = poupancaRepository.findByNumeroDeConta(poupanca.getNumeroDeConta());
        if (contaData.isPresent())
            throw new Exception("Conta já existente!");
        poupanca.setCriacaoDeConta(LocalDateTime.now());
        poupancaRepository.save(poupanca);
        return "Conta salva com sucesso!";
    }

    public String deletarPorNumeroDeConta(Request request) throws Exception {
        var contaData = poupancaRepository.findByNumeroDeConta(request.getNumeroDeConta());
        if (contaData.isEmpty())
            throw new Exception("Conta não encontrada!");
        poupancaRepository.deleteByNumeroDeConta(request.getNumeroDeConta());
        return "Conta: " + request.getNumeroDeConta() + " excluida com sucesso!";
    }

    public void deletarTodos() throws Exception {
        if (poupancaRepository.findAll().isEmpty())
            throw new Exception("Conta já existente!");
        poupancaRepository.deleteAll();
    }
}


