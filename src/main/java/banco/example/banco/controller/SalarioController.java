package banco.example.banco.controller;

import banco.example.banco.model.Poupanca;
import banco.example.banco.model.Salario;
import banco.example.banco.model.request.Request;
import banco.example.banco.service.SalarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("conta-salario")
@AllArgsConstructor
@RestController
public class SalarioController {

    private SalarioService salarioService;

    @PostMapping("/salvar")
    public ResponseEntity<String> salvar(@RequestBody Salario salario) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(salarioService.salvar(salario));
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<String> deletarPorNumeroDeConta(@RequestBody Request request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(salarioService.deletarPorNumeroDeConta(request));
    }

    @DeleteMapping("/deletar-todos")
    public ResponseEntity<String> deletarTodos() throws Exception {
        salarioService.deletarTodos();
        return ResponseEntity.status(HttpStatus.OK).body("Todas as contas excluidas com sucesso!");
    }
}
