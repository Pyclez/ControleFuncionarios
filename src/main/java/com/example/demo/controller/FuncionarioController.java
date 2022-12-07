package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Funcionario;
import com.example.demo.repository.FuncionarioRepository;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository repository;
	
	@GetMapping
	public List<Funcionario> findAll() {
		List<Funcionario> result = repository.findAll();
		return result;
	}
	
	@GetMapping(value = "/{id}")
	public Funcionario findById(@PathVariable Long id) {
		Funcionario result = repository.findById(id).get();
		return result;
	}
	
	@PostMapping
	public Funcionario insertFuncionario(@RequestBody Funcionario funcionario) {
		Funcionario result = repository.save(funcionario);
		return result;
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFuncionario(@PathVariable(value = "id") Long id){
        Optional<Funcionario> funcionario = repository.findById(id);
        if (!funcionario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario não encontrado.");
        }
        repository.delete(funcionario.get());
        return ResponseEntity.status(HttpStatus.OK).body("Funcionario foi deletado com sucesso!!!.");
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Object> updateFuncionario(@PathVariable(value = "id") Long id,
                                                    @RequestBody Funcionario funcionario){
        Optional<Funcionario> funcionarioOpt= repository.findById(id);
        if (!funcionarioOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario não encontrado.");
        }
        var funcionarioVar = new Funcionario();
        BeanUtils.copyProperties(funcionario, funcionarioVar);
        funcionarioVar.setId(funcionarioOpt.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(funcionarioVar));
    }
	
}
