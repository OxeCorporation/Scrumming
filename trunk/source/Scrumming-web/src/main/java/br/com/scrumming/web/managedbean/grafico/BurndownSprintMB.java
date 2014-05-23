package br.com.scrumming.web.managedbean.grafico;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Sprint;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.infra.bean.AbstractBean;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;


@ManagedBean
@ViewScoped
public class BurndownSprintMB extends AbstractBean {

	private static final long serialVersionUID = 1L;
	private CartesianChartModel graficoDeLinha;
	private SprintClientService sprintClientService;
	private Long totalDeHorasEstimadasDaSprint;
	private Sprint sprintSelecionada;
	
	@Override
    public void inicializar() {		
		setSprintClientService(new SprintClientService());
		sprintSelecionada = new Sprint();
		criarGrafico();		
	}
	
	private void criarGrafico() {
		setTotalDeHorasEstimadasDaSprint(sprintClientService.totalDeHorasEstimadasDaSprint(11));
		sprintSelecionada = sprintClientService.consultarSprint(11);
		
		graficoDeLinha = initLinearModel();	
	}
	
	private CartesianChartModel initLinearModel() {
		CartesianChartModel model = new CartesianChartModel();
 
        ChartSeries estimado = new ChartSeries();
        estimado.setLabel("Estimado");
        
        ChartSeries atual = new ChartSeries();
        atual.setLabel("Atual");
        
        DateTime data = sprintSelecionada.getDataInicio();
        int dias = Days.daysBetween(sprintSelecionada.getDataInicio(), sprintSelecionada.getDataFim()).getDays();
        float horasEstimadas = getTotalDeHorasEstimadasDaSprint();
        float horasRestantes = getTotalDeHorasEstimadasDaSprint();
        Long totalDeHorasRestantesDaSprintPorData;
        
        for (int i = 0; i < dias; i++) {
        	if (i == 0) {
        		estimado.set(data.toString("dd ") + data.monthOfYear().getAsShortText(), horasEstimadas);
        		atual.set(data.toString("dd ") + data.monthOfYear().getAsShortText(), horasEstimadas);
        	} else {
        		horasEstimadas = horasEstimadas - (getTotalDeHorasEstimadasDaSprint() / (dias-1));
        		totalDeHorasRestantesDaSprintPorData = sprintClientService.totalDeHorasRestantesDaSprintPorData(11, data.plusDays(i).toString("yyyy-MM-dd"));
        		if (totalDeHorasRestantesDaSprintPorData != null) {
        			horasRestantes = totalDeHorasRestantesDaSprintPorData;
        		}
        		estimado.set(data.plusDays(i).toString("dd ") + data.monthOfYear().getAsShortText(), horasEstimadas);
        		atual.set(data.plusDays(i).toString("dd ") + data.monthOfYear().getAsShortText(), horasRestantes);
        	}
		}        
 
        model.addSeries(estimado);
        model.addSeries(atual);
         
        return model;
    }

	public CartesianChartModel getGraficoDeLinha() {
		return graficoDeLinha;
	}
	
	public void setGraficoDeLinha(CartesianChartModel graficoDeLinha) {
		this.graficoDeLinha = graficoDeLinha;
	}

	public SprintClientService getSprintClientService() {
		return sprintClientService;
	}

	public void setSprintClientService(SprintClientService sprintClientService) {
		this.sprintClientService = sprintClientService;
	}

	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

	public void setSprintSelecionada(Sprint sprintSelecionada) {
		this.sprintSelecionada = sprintSelecionada;
	}

	public Long getTotalDeHorasEstimadasDaSprint() {
		return totalDeHorasEstimadasDaSprint;
	}

	public void setTotalDeHorasEstimadasDaSprint(
			Long totalDeHorasEstimadasDaSprint) {
		this.totalDeHorasEstimadasDaSprint = totalDeHorasEstimadasDaSprint;
	}

}
