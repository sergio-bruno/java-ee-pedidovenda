package com.algaworks.pedidovenda.controller;

import java.math.BigDecimal;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.annotation.PostConstruct;

import org.primefaces.model.chart.PieChartModel;

import com.algaworks.pedidovenda.repository.Pedidos;
 
@Named
@RequestScoped
public class GraficoRankingVendedorBean {
 
    private PieChartModel pieModel;
     
    @Inject
    private Pedidos pedidos;
    
    @PostConstruct
    public void init() {
    	createPieModels();
    }
 
    public PieChartModel getPieModel() {
        return pieModel;
    }
     
    private void createPieModels() {
        pieModel = new PieChartModel();

        pieModel.setTitle("Ranking de vendedores");
        pieModel.setLegendPosition("e");
        pieModel.setFill(false);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(150);
         
        Map<String, BigDecimal> valoresPorVendedor = this.pedidos.valoresTotaisPorVendedor(null, null);
        for (Object key : valoresPorVendedor.keySet()) {
        	pieModel.set(key.toString(), valoresPorVendedor.get(key));
    	}        
    }    
	
}