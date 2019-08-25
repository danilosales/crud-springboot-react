package br.com.danilosales.crud.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.danilosales.crud.dto.ClienteDTO;
import br.com.danilosales.crud.model.Cliente;
import br.com.danilosales.crud.repository.ClienteRepository;
import br.com.danilosales.crud.service.exception.ClienteJaCadastrado;
import br.com.danilosales.crud.service.exception.EntidadeInexistente;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public void salvar(ClienteDTO cliente) {
		Optional<Cliente> clienteExistente = this.clienteRepository.findByCpf(cliente.getCpf());

		if (clienteExistente.isPresent() && cliente.getId() == null) {
			throw new ClienteJaCadastrado("CPF já existe na base de dados");
		}

		Cliente clienteEntity = (Cliente) this.modelMapper.map(cliente, Cliente.class);

		clienteEntity.getTelefones().forEach(tel -> tel.setCliente(clienteEntity));

		this.clienteRepository.save(clienteEntity);
		cliente.setId(clienteEntity.getId());
	}

	public List<ClienteDTO> buscarTodosOsClientes() {
		List<Cliente> listaClientes = this.clienteRepository.findAllByOrderByIdAsc();

		return listaClientes.stream()
				.map(cliente -> (ClienteDTO) this.modelMapper.map(cliente, ClienteDTO.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public void excluir(Long id) {
		Optional<Cliente> cliente = this.clienteRepository.findById(id);

		if (!cliente.isPresent()) {
			throw new EntidadeInexistente("O id informado não existe");
		}

		this.clienteRepository.deleteById(id);
	}

	public Optional<ClienteDTO> buscarClientePorId(Long id) {
		Optional<Cliente> cliente = this.clienteRepository.findById(id);

		return cliente.isPresent() 
				? Optional.of((ClienteDTO) this.modelMapper.map(cliente.get(), ClienteDTO.class))
				: Optional.empty();
	}
}
