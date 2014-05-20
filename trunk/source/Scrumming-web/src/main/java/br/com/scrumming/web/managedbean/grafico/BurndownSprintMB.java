package br.com.scrumming.web.managedbean.grafico;

import javax.faces.bean.ManagedBean;

import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.infra.bean.AbstractBean;

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
	
	@Override
    public void inicializar() {		
		setSprintClientService(new SprintClientService());
		criarGrafico();
	}
	
	private void criarGrafico() {
		Long totalDeHorasEstimadasDaSprint = sprintClientService.totalDeHorasEstimadasDaSprint(10);
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
 
        estimado.set(1, 80);
        estimado.set(2, 60);
        estimado.set(3, 40);
        estimado.set(4, 20);
        estimado.set(5, 0);
 
        LineChartSeries atual = new LineChartSeries();
        atual.setLabel("Atual");
 
        atual.set(1, 80);
        atual.set(2, 70);
        atual.set(3, 75);
        atual.set(4, 50);
        atual.set(5, 30);
 
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

}
