package br.com.escola.Forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escola.Forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{
	
	public List<Topico> findByCurso_Nome(String nomeCurso);

}
