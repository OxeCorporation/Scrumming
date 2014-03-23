package br.com.scrumming.domain;

import org.joda.time.DateTime;

public class EmpresaUtil {

	public static Empresa criar(String nome){
		Empresa empresa = new Empresa();
		empresa.setNome(nome);
		empresa.setAtivo(true);
		empresa.setDataCadastro(DateTime.now());
		return empresa;
	}
}
