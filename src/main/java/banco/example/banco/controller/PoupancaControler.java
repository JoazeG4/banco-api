package banco.example.banco.controller;

import banco.example.banco.model.Corrente;
import banco.example.banco.model.Poupanca;
import banco.example.banco.model.request.Request;
import banco.example.banco.service.PoupancaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("conta-poupanca")
public class PoupancaControler {

    private PoupancaService poupancaService;

    @PostMapping("/salvar")
    public ResponseEntity<String> salvar(@RequestBody Poupanca poupanca) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(poupancaService.salvar(poupanca));
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<String> deletarPorNumeroDeConta(@RequestBody Request request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(poupancaService.deletarPorNumeroDeConta(request));
    }

    @DeleteMapping("/deletar-todos")
    public ResponseEntity<String> deletarTodos() throws Exception {
        poupancaService.deletarTodos();
        return ResponseEntity.status(HttpStatus.OK).body("Todas as contas excluidas com sucesso!");
    }
}
