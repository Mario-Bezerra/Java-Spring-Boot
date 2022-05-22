package br.com.escola.Forum.controller.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.escola.Forum.modelo.Topico;
import br.com.escola.Forum.repository.TopicoRepository;

public class AtualizarTopicoForm {

	@NotBlank
	@Length(min = 5)
	private String titulo;
	@NotBlank
	@Length(min = 10)
	private String mensagem;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@SuppressWarnings("deprecation")
	public Topico atualizar(TopicoRepository topicoRepository, Long id) {
		
		Topico topico = topicoRepository.getById(id);
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		
		return topico;
	}

}
