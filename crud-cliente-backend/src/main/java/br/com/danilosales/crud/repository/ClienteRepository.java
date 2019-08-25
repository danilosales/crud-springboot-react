package br.com.danilosales.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.danilosales.crud.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Optional<Cliente> findByCpf(String cpf);
	
	@EntityGraph(attributePaths = {"telefones"})
	List<Cliente> findAllByOrderByIdAsc();
	  
	@EntityGraph(attributePaths = {"telefones"})
	Optional<Cliente> findById(Long id);
	
}
