package br.com.scrumming.web.managedbean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;

@ManagedBean
@ViewScoped
public class SprintBean extends AbstractBean {
	
	private Sprint sprint;
	private List<ItemBacklog> sprintBacklog;
	private List<ItemBacklog> availableBacklog;
	private SprintDTO sprintDTO;
	
	@Override
	public void inicializar() {
		sprint = new Sprint();
		sprintBacklog = new ArrayList<>();
		availableBacklog = new ArrayList<>();
		sprintDTO = new SprintDTO();
	}
}