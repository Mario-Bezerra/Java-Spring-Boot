package br.com.escola.Forum.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.escola.Forum.modelo.Curso;

@SpringBootTest
@ActiveProfiles("prod")
class CursoRepositoryTest {

	@Autowired
	private CursoRepository cursoRepository;
	
	@Test
	public void cursoDeveriaSerEncontradoComNome() {
		String nomeCurso = "Spring Boot";
		Curso curso = cursoRepository.findByNome(nomeCurso);
		Assertions.assertNotNull(curso);
		Assertions.assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	public void cursoNãoDeveriaSerEncontradoQuandoPassadoNomeErrado() {
		String nomeCurso = "Zacarias Boot";
		Curso curso = cursoRepository.findByNome(nomeCurso);
		Assertions.assertNull(curso);
	}

}
