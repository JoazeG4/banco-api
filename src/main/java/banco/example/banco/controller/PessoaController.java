package banco.example.banco.controller;

import banco.example.banco.controller.httpresponse.RestUtil;
import banco.example.banco.exceptions.ExistingPersonException;
import banco.example.banco.exceptions.NoPersonsFoundException;
import banco.example.banco.exceptions.PersonNotFoundException;
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
    public ResponseEntity<Pessoa> savePerson(@RequestBody @Valid RequestPessoa requestPessoa) throws ExistingPersonException {
        return RestUtil.ifComflict409(pessoaService.savePerson(requestPessoa));
    }

    @GetMapping("/")
    public ResponseEntity<List<Pessoa>> listAllPeople() throws NoPersonsFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listAllPeople());
    }

    @GetMapping("/cpf")
    public  ResponseEntity<Pessoa> findByCpf(@RequestBody @Valid RequestCpf requestCpf) throws PersonNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findByCpf(requestCpf));
    }

    @DeleteMapping("/cpf")
    public ResponseEntity<String> deleteByCpf(@RequestBody @Valid RequestCpf requestCpf) throws PersonNotFoundException {
        pessoaService.deleteByCpf(requestCpf);
        return ResponseEntity.status(HttpStatus.OK).body("Excluida com sucesso!");
    }

}
