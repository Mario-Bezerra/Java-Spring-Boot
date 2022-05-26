package br.com.escola.Forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.escola.Forum.config.security.TokenServiceForum;
import br.com.escola.Forum.controller.dto.DetalhesDoTopicoDto;
import br.com.escola.Forum.controller.dto.TopicoDto;
import br.com.escola.Forum.controller.form.AtualizarTopicoForm;
import br.com.escola.Forum.controller.form.TopicoForm;
import br.com.escola.Forum.modelo.Topico;
import br.com.escola.Forum.modelo.Usuario;
import br.com.escola.Forum.repository.CursoRepository;
import br.com.escola.Forum.repository.TopicoRepository;
import br.com.escola.Forum.repository.UsuarioRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TokenServiceForum tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeTopicos")
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
								 @PageableDefault(page = 0, size = 10, sort = "dataCriacao", direction = Direction.ASC) Pageable paginacao)
	{
		if(nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDto.converter(topicos);
		} else {
			Page<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
			return TopicoDto.converter(topicos);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict (value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder, String token) {
		Long usuarioId = tokenService.getIdUsuario(token);
		Topico topico = form.toTopico(cursoRepository);
		Usuario usuario = usuarioRepository.findById(usuarioId).get();
		topico.setAutor(usuario);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable("id") Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if(topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict (value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@RequestBody @Valid AtualizarTopicoForm form, @PathVariable("id") Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if(optional.isPresent()) {
			Topico topico = form.atualizar(topicoRepository, id);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict (value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable("id") Long id){
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if(optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
