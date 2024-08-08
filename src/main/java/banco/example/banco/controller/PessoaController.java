package banco.example.banco.controller;

import banco.example.banco.model.Pessoa;
import banco.example.banco.service.PessoaService;
import banco.example.banco.service.PoupancaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/pessoa")
public class PessoaController {

    private PessoaService pessoaService;

    @PostMapping("/salvar")
    public void salvarPessoa(@RequestBody Pessoa pessoa) throws Exception {
        pessoaService.salvarPessoa(pessoa);
    }
}
