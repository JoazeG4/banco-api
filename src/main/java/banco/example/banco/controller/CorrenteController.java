package banco.example.banco.controller;


import banco.example.banco.model.Corrente;
import banco.example.banco.model.request.RequestTransacao;
import banco.example.banco.model.response.ResponseExtrato;
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
    public ResponseEntity<String> deletarPorNumeroDeConta(@RequestBody RequestTransacao requestTransacao) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.deletarPorNumeroDeConta(requestTransacao));
    }

    @DeleteMapping("/deletar-todos")
    public void deletarTodos() throws Exception {
        correnteService.deletarTodos();
        ResponseEntity.status(HttpStatus.OK).body("Contas excluidas com sucesso!");
    }

    @PostMapping("/depositar")
    public ResponseEntity<String> depositar(@RequestBody RequestTransacao requestTransacao) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.depositar(requestTransacao));
    }

    @PostMapping("/sacar")
    public ResponseEntity<String> sacar(@RequestBody RequestTransacao requestTransacao) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.sacar(requestTransacao));
    }

    @PostMapping("/transferencia")
    public ResponseEntity<ResponseExtrato> transferencia(@RequestBody RequestTransacao requestTransacao) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.transferencia(requestTransacao));
    }

    @GetMapping("/saldo/{numeroDaConta}")
    public ResponseEntity<String> saldo(@PathVariable String numeroDaConta) throws  Exception{
        return ResponseEntity.status(HttpStatus.OK).body(correnteService.saldo(numeroDaConta));
    }
}