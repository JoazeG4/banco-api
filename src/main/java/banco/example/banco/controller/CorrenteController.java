package banco.example.banco.controller;


import banco.example.banco.model.Corrente;
import banco.example.banco.model.request.Request;
import banco.example.banco.service.CorrenteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/conta-corrente")
public class CorrenteController {

    private CorrenteService correnteService;

    @PostMapping("/salvar")
    public ResponseEntity<String> salvar(@RequestBody Corrente corrente) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(correnteService.salvar(corrente));
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<String> deletarPorNumeroDeConta(@RequestBody Request request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.deletarPorNumeroDeConta(request));
    }

    @DeleteMapping("/deletar-todos")
    public void deletarTodos() throws Exception {
        correnteService.deletarTodos();
        ResponseEntity.status(HttpStatus.OK).body("Contas excluidas com sucesso!");
    }

    @PostMapping("/depositar")
    public ResponseEntity<String> depositar(@RequestBody Request request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.depositar(request));
    }

    @PostMapping("/sacar")
    public ResponseEntity<String> sacar(@RequestBody Request request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.sacar(request));
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> transferencia(@RequestBody Request request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.transferencia(request));
    }

    @GetMapping("/saldo/{numeroDaConta}")
    public ResponseEntity<String> saldo(@PathVariable String numeroDaConta) throws  Exception{
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.saldo(numeroDaConta));
    }
}