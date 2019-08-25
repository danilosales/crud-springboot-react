package br.com.danilosales.crud.model.audit;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class })
public abstract class Auditable<T> {
	
	@CreatedBy
	@Column(name = "usuario_inclusao")
	protected T usuarioInclusao;
	
	@CreatedDate
	@Column(name = "data_criacao")
	protected LocalDateTime dataCriacao;
	
	@LastModifiedBy
	@Column(name = "usuario_atualizacao")
	protected T ultimoUsuarioAtualizcao;
	
	@LastModifiedDate
	protected LocalDateTime ultimaDataModificacao;

	public T getUsuarioInclusao() {
		return (T) this.usuarioInclusao;
	}

	public void setUsuarioInclusao(T usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	public LocalDateTime getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public T getUltimoUsuarioAtualizcao() {
		return (T) this.ultimoUsuarioAtualizcao;
	}

	public void setUltimoUsuarioAtualizcao(T ultimoUsuarioAtualizcao) {
		this.ultimoUsuarioAtualizcao = ultimoUsuarioAtualizcao;
	}

	public LocalDateTime getUltimaDataModificacao() {
		return this.ultimaDataModificacao;
	}

	public void setUltimaDataModificacao(LocalDateTime ultimaDataModificacao) {
		this.ultimaDataModificacao = ultimaDataModificacao;
	}
}
