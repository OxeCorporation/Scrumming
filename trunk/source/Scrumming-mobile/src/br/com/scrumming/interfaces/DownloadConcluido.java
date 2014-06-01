package br.com.scrumming.interfaces;

import java.util.List;

import br.com.scrumming.domain.TarefaDTO;

public interface DownloadConcluido {

	void concluiuDownload(List<TarefaDTO> listaDeTarefa);
}
