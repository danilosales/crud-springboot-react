package br.com.danilosales.crud.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.danilosales.crud.dto.ClienteDTO;
import br.com.danilosales.crud.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Clientes")
@RestController
@RequestMapping({ "/api/clientes" })
@CrossOrigin(origins = { "http://localhost:3000", "http://crud-cliente-frontend:3000" })
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;

	@ApiOperation("Retorna uma lista de clientes")
	@GetMapping
	public List<ClienteDTO> buscarTodosOsClientes() {
		return this.clienteService.buscarTodosOsClientes();
	}

	@ApiOperation("Cadastrar um usuário")
	@PostMapping
	public ResponseEntity<ClienteDTO> inserirCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
		
		this.clienteService.salvar(clienteDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(new Object[] { clienteDTO.getId() }).toUri();

		return ResponseEntity.created(location).body(clienteDTO);
	}

	@ApiOperation("Atualizar um cliente da base")
	@PutMapping({ "/{codigo}" })
	public ResponseEntity<ClienteDTO> atualizarCliente(@NotNull @PathVariable("codigo") Long id,
			@Valid @RequestBody ClienteDTO clienteDTO) {
		
		clienteDTO.setId(id);
		this.clienteService.salvar(clienteDTO);

		return ResponseEntity.ok(clienteDTO);
	}

	@ApiOperation("Excluir um cliente da base")
	@DeleteMapping({ "/{codigo}" })
	public ResponseEntity<?> excluirClientePorId(@NotNull @PathVariable("codigo") Long id) {
		this.clienteService.excluir(id);

		return ResponseEntity.ok().build();
	}

	@ApiOperation("Buscar um cliente por id")
	@GetMapping({ "/{codigo}" })
	public ResponseEntity<ClienteDTO> buscarCientePorId(@NotNull @PathVariable("codigo") Long id) {
		Optional<ClienteDTO> cliente = this.clienteService.buscarClientePorId(id);

		return cliente.isPresent() 
				? ResponseEntity.ok((ClienteDTO) cliente.get()) 
				: ResponseEntity.noContent().build();
	}
}
