package banco.example.banco.service;


import banco.example.banco.model.Corrente;
import banco.example.banco.model.TipoConta;
import banco.example.banco.model.request.Request;
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

    public String deletarPorNumeroDeConta(Request request) throws Exception {
        var correnteData = correnteRepository.findByNumeroDeConta(request.getNumeroDeConta());
        if (correnteData.isEmpty())
            throw new Exception("Conta não encontrada!");
        correnteRepository.deleteByNumeroDeConta(request.getNumeroDeConta());
        return "Conta: " + request.getNumeroDeConta() + " excluida com sucesso!";
    }

    public void deletarTodos() throws Exception {
        if (correnteRepository.findAll().isEmpty())
            throw new Exception("Conta já existente!");
        correnteRepository.deleteAll();
    }

    public String depositar(Request request) throws Exception {
        var correnteData = this.validarContaCorrente(request.getNumeroDeConta());
        correnteData.setSaldo(correnteData.getSaldo() + request.getValor());
        correnteRepository.save(correnteData);
        return "Deposito finalizado!";
    }

    public String sacar(Request request) throws Exception {
        var correnteData = this.validarContaCorrente(request.getNumeroDeConta());
        if (request.getValor() > correnteData.getSaldo())
            throw new Exception("Saldo insuficiente: R$" + correnteData.getSaldo());
        correnteData.setSaldo(correnteData.getSaldo() - request.getValor());
        correnteRepository.save(correnteData);
        return "Você sacou: R$" + request.getValor();
    }

    public String transferencia(Request request) throws Exception {
        var correnteData = this.validarContaCorrente(request.getNumeroDeConta());
        if (request.getValor() > correnteData.getSaldo())
            throw new Exception("Saldo insuficiente: R$" + correnteData.getSaldo());
        if (request.getConDestino().equals(correnteData.getNumeroDeConta()))
            throw new Exception("Conta destino inválida! " + request.getNumeroDeConta());

        if (request.getTipoDeConta() == 1) {
            var contaDestinoData = super.getCorrente(request);
            contaDestinoData.setSaldo(contaDestinoData.getSaldo() + request.getValor());
            correnteRepository.save(contaDestinoData);

        } else if (request.getTipoDeConta() == 2) {
            var contaDestinoData = super.getPoupanca(request);
            contaDestinoData.setSaldo(contaDestinoData.getSaldo() + request.getValor());
            poupancaRepository.save(contaDestinoData);

        } else if (request.getTipoDeConta() == 3) {
            var contaDestinoData = super.getSalario(request);
            contaDestinoData.setSaldo(contaDestinoData.getSaldo() + request.getValor());
            salarioRepository.save(contaDestinoData);
        }

        correnteData.setSaldo(correnteData.getSaldo() - request.getValor());
        correnteRepository.save(correnteData);
        return "Transferência realizada: R$" + request.getValor() + "\n" +
                "Conta envio:" + request.getNumeroDeConta() + "\n" +
                "Conta destinatário:" + request.getConDestino();
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
