package br.com.isaccanedo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/api/funcionario")
public class FuncionarioRest
{
    private static Log logger = LogFactory.getLog(FuncionarioRest.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @ApiOperation(notes = "Retorna todos os funcionários", value = "Retorna todos os funcionários", nickname = "todosFuncionarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = List.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping(path="/funcionarios")
    public List<Funcionario> todosFuncionarios()
    {
        return funcionarioRepository.findAll();
    }

    @ApiOperation(notes = "Encontre um funcionário", value = "Retorne um determinado funcionário usando {funcionarioId}", nickname = "encontrarEmpregado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Funcionario.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping(path="/funcionarios/{funcionarioId}")
    public Funcionario encontrarFuncionario (@PathVariable Long funcionarioId)
    {
        Funcionario func = funcionarioRepository.findOne(funcionarioId);


        return func;
    }

    @ApiOperation(notes = "Criar um novo empregado", value = "Criar um novo empregado", nickname = "criarFuncionario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Criado", response = ResponseEntity.class),
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 403, message = "Proibido"),
            @ApiResponse(code = 404, message = "Não encontrado"),
            @ApiResponse(code = 500, message = "Falha")})
    @PostMapping(path="/funcs")
    public ResponseEntity<Void> criarFuncionario(@RequestBody Funcionario funcionario)
    {
        Funcionario emp = funcionarioRepository.save(funcionario);

        // Build the location URI of the new item
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{funcionarioId}")
                .buildAndExpand(emp.getId())
                .toUri();

        // Explicitly create a 201 Created response
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(notes = "Atualizar um funcionário existente", value = "Atualizar um funcionário existente", nickname = "atualizarFuncionario")
    @PutMapping(path="/funcs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarFuncionario(@RequestBody Funcionario funcionario)
    {
        funcionarioRepository.save(funcionario);
    }

    @ApiOperation(notes = "Excluir um funcionário", value = "Excluir um funcionário usando {funcionarioId}", nickname = "excluirFuncionario")
    @DeleteMapping(path="/funcs/{funcionarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirFuncionario(@PathVariable Long funcionarioId)
    {
        Funcionario func = funcionarioRepository.findOne(funcionarioId);
        funcionarioRepository.delete(func);
        logger.info("Funcionário com id: " + funcionarioId + " excluído com sucesso!...");
    }

}
