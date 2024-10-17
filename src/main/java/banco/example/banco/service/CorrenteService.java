package banco.example.banco.service;


import banco.example.banco.exceptions.AccountInvalidException;
import banco.example.banco.exceptions.AccountNotFoundException;
import banco.example.banco.exceptions.ExistingAccountException;
import banco.example.banco.model.Corrente;
import banco.example.banco.model.request.RequestTransacao;
import banco.example.banco.model.response.ResponseExtrato;
import banco.example.banco.repository.CorrenteRepository;
import banco.example.banco.repository.PoupancaRepository;
import banco.example.banco.repository.SalarioRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CorrenteService extends ContaService {

    public CorrenteService(CorrenteRepository correnteRepository, SalarioRepository salarioRepository, PoupancaRepository poupancaRepository) {
        super(correnteRepository, salarioRepository, poupancaRepository);
    }

    public String salvar(Corrente corrente) throws ExistingAccountException {
        var correnteData = correnteRepository.findByNumeroDeConta(corrente.getNumeroDeConta());
        if (correnteData.isPresent())
            throw new ExistingAccountException();
        corrente.setCriacaoDeConta(LocalDateTime.now());
        corrente.setStatus(true);
        correnteRepository.save(corrente);
        return "Conta: " + corrente.getNumeroDeConta() + " salva com sucesso!";
    }

    public String deletarPorNumeroDeConta(RequestTransacao requestTransacao) throws AccountNotFoundException {
        var correnteData = correnteRepository.findByNumeroDeConta(requestTransacao.getNumeroDeConta());
        if (correnteData.isEmpty())
            throw new AccountNotFoundException();
        correnteRepository.deleteByNumeroDeConta(requestTransacao.getNumeroDeConta());
        return "Conta: " + requestTransacao.getNumeroDeConta() + " excluida com sucesso!";
    }

    public void deletarTodos() throws RuntimeException {
        if (correnteRepository.findAll().isEmpty())
            throw new RuntimeException();
        correnteRepository.deleteAll();
    }

    public String depositar(RequestTransacao requestTransacao) {
        var correnteData = this.validarContaCorrenteDataBase(requestTransacao.getNumeroDeConta());
        correnteData.setSaldo(correnteData.getSaldo() + requestTransacao.getValor());
        correnteRepository.save(correnteData);
        return "Deposito finalizado!";
    }

    public String sacar(RequestTransacao requestTransacao) throws RuntimeException {
        var correnteData = this.validarContaCorrenteDataBase(requestTransacao.getNumeroDeConta());
        if (requestTransacao.getValor() > correnteData.getSaldo())
            throw new RuntimeException("Saldo insuficiente: R$" + correnteData.getSaldo());
        correnteData.setSaldo(correnteData.getSaldo() - requestTransacao.getValor());
        correnteRepository.save(correnteData);
        return "Você sacou: R$" + requestTransacao.getValor();
    }

    public ResponseExtrato transferencia(RequestTransacao requestTransacao) throws RuntimeException {
        var correnteData = this.validarContaCorrenteDataBase(requestTransacao.getNumeroDeConta());
        if (requestTransacao.getValor() > correnteData.getSaldo())
            throw new RuntimeException("Saldo insuficiente: R$" + correnteData.getSaldo());
        if (requestTransacao.getContaDestino().equals(correnteData.getNumeroDeConta()))
            throw new AccountInvalidException("Conta destino inválida! " + requestTransacao.getNumeroDeConta());

        if (requestTransacao.getTipoDeConta() == 1) {
            var contaDestinoData = super.getCorrente(requestTransacao);
            contaDestinoData.setSaldo(contaDestinoData.getSaldo() + requestTransacao.getValor());
            correnteRepository.save(contaDestinoData);

        } else if (requestTransacao.getTipoDeConta() == 2) {
            var contaDestinoData = super.getPoupanca(requestTransacao);
            contaDestinoData.setSaldo(contaDestinoData.getSaldo() + requestTransacao.getValor());
            poupancaRepository.save(contaDestinoData);

        } else if (requestTransacao.getTipoDeConta() == 3) {
            var contaDestinoData = super.getSalario(requestTransacao);
            contaDestinoData.setSaldo(contaDestinoData.getSaldo() + requestTransacao.getValor());
            salarioRepository.save(contaDestinoData);
        }

        correnteData.setSaldo(correnteData.getSaldo() - requestTransacao.getValor());
        correnteRepository.save(correnteData);
        return new ResponseExtrato(requestTransacao.getValor(), requestTransacao.getNumeroDeConta(), requestTransacao.getContaDestino());
    }

    public String saldo(String numeroDaConta) {
        var correnteData = this.validarContaCorrenteDataBase(numeroDaConta);
        return "Saldo: R$" + correnteData.getSaldo();
    }

    private @NotNull Corrente validarContaCorrenteDataBase(String numeroDaConta) throws RuntimeException {
        var correnteData = correnteRepository.findByNumeroDeConta(numeroDaConta);
        if (correnteData.isEmpty())
            throw new AccountNotFoundException();
        if (!correnteData.get().getStatus())
            throw new RuntimeException("Conta: " + numeroDaConta + " desativada!");
        return correnteData.get();
    }
}
