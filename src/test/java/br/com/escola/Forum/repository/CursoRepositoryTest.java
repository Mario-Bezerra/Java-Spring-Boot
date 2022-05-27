package br.com.escola.Forum.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.escola.Forum.modelo.Curso;

@SpringBootTest
class CursoRepositoryTest {

	@Autowired
	private CursoRepository cursoRepository;
	
	@Test
	public void cursoDeveriaSerEncontradoComNome() {
		String nomeCurso = "Spring Boot";
		Curso curso = cursoRepository.findByNome(nomeCurso);
		Assert.assertNotNull(curso);
		Assert.assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	public void cursoNãoDeveriaSerEncontradoQuandoPassadoNomeErrado() {
		String nomeCurso = "Zacarias Boot";
		Curso curso = cursoRepository.findByNome(nomeCurso);
		Assert.assertNull(curso);
	}

}
