package br.com.scrumming.web.managedbean.grafico;

import javax.faces.bean.ManagedBean;

import br.com.scrumming.domain.Sprint;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.infra.bean.AbstractBean;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
public class BurndownSprintMB extends AbstractBean {

	private static final long serialVersionUID = 1L;
	private LineChartModel graficoDeLinha;
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
		totalDeHorasEstimadasDaSprint = sprintClientService.totalDeHorasEstimadasDaSprint(10);
		sprintSelecionada = sprintClientService.consultarSprint(10);
		
		graficoDeLinha = initLinearModel();
		graficoDeLinha.setTitle("Burndown da Sprint");
		graficoDeLinha.setLegendPosition("e");
		graficoDeLinha.setShowPointLabels(true);
		graficoDeLinha.getAxes().put(AxisType.X, new CategoryAxis("Dias"));
        Axis yAxis = graficoDeLinha.getAxis(AxisType.Y);
        yAxis.setLabel("Horas");
        yAxis.setMin(0);
        yAxis.setMax(totalDeHorasEstimadasDaSprint);		
	}
	
	private LineChartModel initLinearModel() {
		LineChartModel model = new LineChartModel();
 
        LineChartSeries estimado = new LineChartSeries();
        estimado.setLabel("Estimado");
        
        LineChartSeries atual = new LineChartSeries();
        atual.setLabel("Atual");
        
        int dias = Days.daysBetween(sprintSelecionada.getDataInicio(), sprintSelecionada.getDataFim()).getDays();
        float horasEstimadas = totalDeHorasEstimadasDaSprint;
        DateTime data = sprintSelecionada.getDataInicio();
        
        for (int i = 0; i < dias; i++) {
        	if (i == 0) {
        		estimado.set(data.toString("dd ") + data.monthOfYear().getAsShortText(), totalDeHorasEstimadasDaSprint);
        		atual.set(data.toString("dd ") + data.monthOfYear().getAsShortText(), 80);
        	} else {
        		horasEstimadas = horasEstimadas - (totalDeHorasEstimadasDaSprint / (dias-1));
        		estimado.set(data.plusDays(i).toString("dd ") + data.monthOfYear().getAsShortText(), horasEstimadas);
        		atual.set(data.plusDays(i).toString("dd ") + data.monthOfYear().getAsShortText(), 80);
        	}
		}        
 
        model.addSeries(estimado);
        model.addSeries(atual);
         
        return model;
    }

	public LineChartModel getGraficoDeLinha() {
		return graficoDeLinha;
	}
	
	public void setGraficoDeLinha(LineChartModel graficoDeLinha) {
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

}
