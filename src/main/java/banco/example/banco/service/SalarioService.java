package banco.example.banco.service;

import banco.example.banco.model.Salario;
import banco.example.banco.model.request.Request;
import banco.example.banco.repository.SalarioRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class SalarioService {

    private SalarioRepository salarioRepository;

    public String salvar(Salario salario) throws Exception {
        var correnteData = salarioRepository.findByNumeroDeConta(salario.getNumeroDeConta());
        if (correnteData.isPresent())
            throw new Exception("Conta já existente!");
        salario.setCriacaoDeConta(LocalDateTime.now());
        salarioRepository.save(salario);
        return "Conta: " + salario.getNumeroDeConta() + " salva com sucesso!";
    }

    public String deletarPorNumeroDeConta(Request request) throws Exception {
        var correnteData = salarioRepository.findByNumeroDeConta(request.getNumeroDeConta());
        if (correnteData.isEmpty())
            throw new Exception("Conta não encontrada!");
        salarioRepository.deleteByNumeroDeConta(request.getNumeroDeConta());
        return "Conta: " + request.getNumeroDeConta() + " excluida com sucesso!";
    }

    public void deletarTodos() throws Exception {
        if (salarioRepository.findAll().isEmpty())
            throw new Exception("Conta já existente!");
        salarioRepository.deleteAll();
    }
}
