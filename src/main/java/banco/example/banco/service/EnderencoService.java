package banco.example.banco.service;


import banco.example.banco.model.Pessoa;
import banco.example.banco.model.request.RequestPessoa;
import banco.example.banco.model.response.ResponseEndereco;
import banco.example.banco.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnderencoService {

    private PessoaRepository pessoaRepository;

    //private EnderecoFeign enderecoFeign;
    //public ResponseEndereco getViaCep(RequestPessoa requestPessoa){
      //  return enderecoFeign.buscaEnderencoCep(requestPessoa.getCep());
    //}

}
