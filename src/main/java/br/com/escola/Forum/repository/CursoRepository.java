package br.com.escola.Forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escola.Forum.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	Curso findByNome(String nomeCurso);

}
