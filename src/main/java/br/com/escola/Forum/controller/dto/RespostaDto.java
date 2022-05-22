package br.com.escola.Forum.controller.dto;

import java.time.LocalDateTime;

import br.com.escola.Forum.modelo.Resposta;

public class RespostaDto {

	private Long id;
	private String mensagem;
	private LocalDateTime data;
	private String nomeDoAutor;

	public RespostaDto(Resposta resposta) {
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.data = resposta.getDataCriacao();
		this.nomeDoAutor = resposta.getAutor().getNome();
	}

	public Long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getData() {
		return data;
	}

	public String getNomeDoAutor() {
		return nomeDoAutor;
	}

}
