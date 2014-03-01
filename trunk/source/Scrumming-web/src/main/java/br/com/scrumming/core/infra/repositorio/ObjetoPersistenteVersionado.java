package br.com.scrumming.core.infra.repositorio;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import br.com.scrumming.core.infra.util.JodaDateTimeJsonDeserializer;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@MappedSuperclass
public abstract class ObjetoPersistenteVersionado<Chave extends Serializable>
		extends ObjetoPersistente<Chave> {

	/**
	 * Serial Verson
	 */
	private static final long serialVersionUID = 1L;

	@Version
	@Type(type = HibernateTypes.JODA_DATE_TIME)
	private DateTime ultimaAtualizacao;

	@JsonSerialize(using = JodaDateTimeJsonSerializer.class)
	@JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
	public DateTime getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(DateTime ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
}
