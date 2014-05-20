package br.com.scrumming.web.managedbean.grafico;

import javax.faces.bean.ManagedBean;

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
	
	@Override
    public void inicializar() {		
		criarGrafico();
	}
	
	private void criarGrafico() {
		graficoDeLinha = initLinearModel();
		graficoDeLinha.setTitle("Burndown da Sprint");
		graficoDeLinha.setLegendPosition("e");
		graficoDeLinha.setShowPointLabels(true);
		graficoDeLinha.getAxes().put(AxisType.X, new CategoryAxis("Dias"));
        Axis yAxis = graficoDeLinha.getAxis(AxisType.Y);
        yAxis.setLabel("Horas");
        yAxis.setMin(0);
        yAxis.setMax(10);		
	}
	
	private LineChartModel initLinearModel() {
		LineChartModel model = new LineChartModel();
 
        LineChartSeries estimado = new LineChartSeries();
        estimado.setLabel("Estimado");
 
        estimado.set(1, 2);
        estimado.set(2, 1);
        estimado.set(3, 3);
        estimado.set(4, 6);
        estimado.set(5, 8);
 
        LineChartSeries atual = new LineChartSeries();
        atual.setLabel("Atual");
 
        atual.set(1, 6);
        atual.set(2, 3);
        atual.set(3, 2);
        atual.set(4, 7);
        atual.set(5, 9);
 
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

}
