package banco.example.banco.service;


import banco.example.banco.model.Corrente;
import banco.example.banco.model.request.RequestTransacao;
import banco.example.banco.repository.CorrenteRepository;
import banco.example.banco.repository.PoupancaRepository;
import banco.example.banco.repository.SalarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CorrenteService extends ContaService {

    public CorrenteService(CorrenteRepository correnteRepository, SalarioRepository salarioRepository, PoupancaRepository poupancaRepository) {
        super(correnteRepository, salarioRepository, poupancaRepository);
    }

    public String salvar(Corrente corrente) throws Exception {
        var correnteData = correnteRepository.findByNumeroDeConta(corrente.getNumeroDeConta());
        if (correnteData.isPresent())
            throw new Exception("Conta já existente!");
        corrente.setCriacaoDeConta(LocalDateTime.now());
        correnteRepository.save(corrente);
        return "Conta: " + corrente.getNumeroDeConta() + " salva com sucesso!";
    }

    public String deletarPorNumeroDeConta(RequestTransacao requestTransacao) throws Exception {
        var correnteData = correnteRepository.findByNumeroDeConta(requestTransacao.getNumeroDeConta());
        if (correnteData.isEmpty())
            throw new Exception("Conta não encontrada!");
        correnteRepository.deleteByNumeroDeConta(requestTransacao.getNumeroDeConta());
        return "Conta: " + requestTransacao.getNumeroDeConta() + " excluida com sucesso!";
    }

    public void deletarTodos() throws Exception {
        if (correnteRepository.findAll().isEmpty())
            throw new Exception("Conta já existente!");
        correnteRepository.deleteAll();
    }

    public String depositar(RequestTransacao requestTransacao) throws Exception {
        var correnteData = this.validarContaCorrente(requestTransacao.getNumeroDeConta());
        correnteData.setSaldo(correnteData.getSaldo() + requestTransacao.getValor());
        correnteRepository.save(correnteData);
        return "Deposito finalizado!";
    }

    public String sacar(RequestTransacao requestTransacao) throws Exception {
        var correnteData = this.validarContaCorrente(requestTransacao.getNumeroDeConta());
        if (requestTransacao.getValor() > correnteData.getSaldo())
            throw new Exception("Saldo insuficiente: R$" + correnteData.getSaldo());
        correnteData.setSaldo(correnteData.getSaldo() - requestTransacao.getValor());
        correnteRepository.save(correnteData);
        return "Você sacou: R$" + requestTransacao.getValor();
    }

    public String transferencia(RequestTransacao requestTransacao) throws Exception {
        var correnteData = this.validarContaCorrente(requestTransacao.getNumeroDeConta());
        if (requestTransacao.getValor() > correnteData.getSaldo())
            throw new Exception("Saldo insuficiente: R$" + correnteData.getSaldo());
        if (requestTransacao.getConDestino().equals(correnteData.getNumeroDeConta()))
            throw new Exception("Conta destino inválida! " + requestTransacao.getNumeroDeConta());

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
        return "Transferência realizada: R$" + requestTransacao.getValor() + "\n" +
                "Conta envio:" + requestTransacao.getNumeroDeConta() + "\n" +
                "Conta destinatário:" + requestTransacao.getConDestino();
    }

    public String saldo(String numeroDaConta) throws Exception {
        var correnteData = this.validarContaCorrente(numeroDaConta);
        return "Saldo: R$" + correnteData.getSaldo();
    }

    private Corrente validarContaCorrente(String numeroDaConta) throws Exception {
        var correnteData = correnteRepository.findByNumeroDeConta(numeroDaConta);
        if (correnteData.isEmpty())
            throw new Exception("Conta inválida!" + numeroDaConta);
        if (!correnteData.get().getStatus())
            throw new Exception("Conta: " + numeroDaConta + " desativada!");
        return correnteData.get();
    }
}
