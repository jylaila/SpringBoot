package edu.fatec.cliente.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.fatec.cliente.api.dao.ClienteDao;
import edu.fatec.cliente.api.model.Cliente;

@RequestMapping("/cliente")
@RestController
@SpringBootApplication
@CrossOrigin (origins = "*")
public class ClienteController {
	
	  private ClienteDao repository;

	   ClienteController(ClienteDao clientedao) {
	       this.repository = clientedao;
	   }
	   
	   @GetMapping
	   public List findAll(){
	      return repository.findAll();
	   }
	   
	   @GetMapping(path = {"/{id}"})
	   public ResponseEntity findById(@PathVariable int id){
	      return repository.findById(id)
	              .map(record -> ResponseEntity.ok().body(record))
	              .orElse(ResponseEntity.notFound().build());
	   }
	   
	   @PostMapping
	   public Cliente create(@RequestBody Cliente cliente){
	      return repository.save(cliente);
	   }
	   	   	   
	   @PutMapping(value="/{id}")
	   public ResponseEntity update(@PathVariable("id") int id,
	                                         @RequestBody Cliente cliente) {
	      return repository.findById(id)
	              .map(record -> {
	                  record.setNome(cliente.getNome());
	                  record.setEndereco(cliente.getEndereco());
	                  record.setLimiteCredito(cliente.getLimiteCredito());
	                  Cliente updated = repository.save(record);
	                  return ResponseEntity.ok().body(updated);
	              }).orElse(ResponseEntity.notFound().build());
	   }
	   
	   @DeleteMapping(path ={"/{id}"})
	   public ResponseEntity <?> delete(@PathVariable int id) {
	      return repository.findById((int)id)
	              .map(record -> {
	                  repository.deleteById((int) id);
	                  return ResponseEntity.ok().build();
	              }).orElse(ResponseEntity.notFound().build());
	   }
}
