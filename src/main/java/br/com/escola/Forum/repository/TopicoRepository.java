package br.com.escola.Forum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escola.Forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{
	
	public Page<Topico> findByCurso_Nome(String nomeCurso, Pageable page);

}
