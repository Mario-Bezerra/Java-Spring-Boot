package br.com.escola.Forum.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.escola.Forum.controller.dto.DetalhesDoTopicoDto;
import br.com.escola.Forum.controller.dto.TopicoDto;
import br.com.escola.Forum.controller.form.AtualizarTopicoForm;
import br.com.escola.Forum.controller.form.TopicoForm;
import br.com.escola.Forum.modelo.Topico;
import br.com.escola.Forum.repository.CursoRepository;
import br.com.escola.Forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(){
		
		List<Topico> topicos = topicoRepository.findAll();
		return TopicoDto.converter(topicos);
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
	
		Topico topico = form.toTopico(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public DetalhesDoTopicoDto detalhar(@PathVariable("id") Long id) {
		
		@SuppressWarnings("deprecation")
		Topico topico = topicoRepository.getById(id);
		return new DetalhesDoTopicoDto(topico);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@RequestBody @Valid AtualizarTopicoForm form, @PathVariable("id") Long id) {
		
		Topico topico = form.atualizar(topicoRepository, id);
		
		return ResponseEntity.ok(new TopicoDto(topico));
	}
}
