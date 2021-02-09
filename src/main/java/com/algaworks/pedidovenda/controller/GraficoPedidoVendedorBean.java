package com.algaworks.pedidovenda.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.annotation.PostConstruct;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.security.UsuarioLogado;
import com.algaworks.pedidovenda.security.UsuarioSistema;
 
@Named
@RequestScoped
public class GraficoPedidoVendedorBean {
 
    private LineChartModel lineModel;
     
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
    
    @Inject
    private Pedidos pedidos;
    
	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioLogado;    
    
    @PostConstruct
    public void init() {
        createLineModels();
    }
 
    public LineChartModel getLineModel() {
        return lineModel;
    }
     
    private void createLineModels() {
        lineModel = initCategoryModel();
        lineModel.setTitle("Gráfico pedidos por dia");
        lineModel.setLegendPosition("e");
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Valores"));
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Dia");
    }
     
	private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
 
        Map<Date, BigDecimal> valoresPorData = this.pedidos.valoresTotaisPorData(15, null);
        ChartSeries todosPedido = new ChartSeries();
        todosPedido.setLabel("Todos os pedido");
		for (Date data : valoresPorData.keySet()) {
			todosPedido.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}
        
		Map<Date, BigDecimal> meusValores = this.pedidos.valoresTotaisPorData(15, usuarioLogado.getUsuario());
        ChartSeries meusPedido = new ChartSeries();
        meusPedido.setLabel("Meus Pedidos");
		for (Date data : meusValores.keySet()) {
			meusPedido.set(DATE_FORMAT.format(data), meusValores.get(data));
		}
 
        model.addSeries(todosPedido);
        model.addSeries(meusPedido);
         
        return model;
    }
	
}