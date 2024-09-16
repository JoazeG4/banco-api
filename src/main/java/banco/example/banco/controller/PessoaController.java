package banco.example.banco.controller;

import banco.example.banco.model.Pessoa;
import banco.example.banco.model.request.RequestCpf;
import banco.example.banco.model.request.RequestPessoa;
import banco.example.banco.service.PessoaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pessoa")
public class PessoaController {

    private PessoaService pessoaService;

    @PostMapping("/salvar")
    public ResponseEntity<Pessoa> salvarPessoa(@RequestBody @Valid RequestPessoa requestPessoa) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.salvarPessoa(requestPessoa));
    }

    @GetMapping("/")
    public ResponseEntity<List<Pessoa>> listaTodasPessoa() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listarTodasPessoas());
    }

    @GetMapping("/cpf")
    public  ResponseEntity<Pessoa> findByCpf(@RequestBody @Valid RequestCpf requestCpf) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findByCpf(requestCpf));
    }

    @DeleteMapping("/cpf")
    public ResponseEntity<String> deletarPorCpf(@RequestBody @Valid RequestCpf requestCpf) throws Exception {
        pessoaService.deletarPorCpf(requestCpf);
        return ResponseEntity.status(HttpStatus.OK).body("Excluida com sucesso!");
    }

}
