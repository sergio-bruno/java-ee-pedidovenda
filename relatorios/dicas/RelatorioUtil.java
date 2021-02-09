package com.linkdesign.util.report;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.linkdesign.negocio.NegocioException;

public class RelatorioUtil implements Work, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	@Inject
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeRelatorioJasper;
	private String nomeRelatorioSaida;
	private JRExporter exportador;
	private String extensaoRelatorioExportado;

	private boolean relatorioGerado;

	public void gerarRelatorio(Map<String, Object> parametros,
			String nomeRelatorioJasper, String nomeRelatorioSaida,
			EnumRelatorioUtil tipo) throws NegocioException {
		this.parametros = parametros;
		this.nomeRelatorioJasper = nomeRelatorioJasper;
		this.nomeRelatorioSaida = nomeRelatorioSaida;
		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));

		this.configuraPeloTipo(tipo); // Configura o tipo

		this.initConnection(); // Inicia uma conexão
	}

	// Relatório padrão
	public void gerarRelatorio(Map<String, Object> parametros,
			String nomeRelatorioJasper, String nomeRelatorioSaida)
			throws NegocioException {
		// Default PDF
		gerarRelatorio(parametros, nomeRelatorioJasper, nomeRelatorioSaida,
				EnumRelatorioUtil.RELATORIO_PDF);
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		try {
			InputStream relatorioStream = this.getClass().getResourceAsStream("/relatorios/"+nomeRelatorioJasper);

			JasperPrint print = JasperFillManager.fillReport(relatorioStream,
					this.parametros, connection);

			this.relatorioGerado = print.getPages().size() > 0;

			if (this.relatorioGerado) {
				exportador.setParameter(JRExporterParameter.OUTPUT_STREAM,
						response.getOutputStream());
				exportador
						.setParameter(JRExporterParameter.JASPER_PRINT, print);

				response.setContentType("application/"
						+ this.extensaoRelatorioExportado);
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + this.nomeRelatorioSaida
								+ "." + this.extensaoRelatorioExportado + "\"");

				exportador.exportReport();
			}
		} catch (Exception e) {
			throw new SQLException("Erro ao executar relatório "
					+ ConstantReport.PATH_RESOURCE_JASPER
					+ this.nomeRelatorioJasper, e);
		}
	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}

	private void configuraPeloTipo(EnumRelatorioUtil tipo) {
		if (tipo.equals(EnumRelatorioUtil.RELATORIO_PDF)) {
			this.exportador = new JRPdfExporter();
			this.extensaoRelatorioExportado = "pdf";
		} else if (tipo.equals(EnumRelatorioUtil.RELATORIO_HTML)) {
			this.exportador = new JRHtmlExporter();
			this.extensaoRelatorioExportado = "html";
		} else if (tipo
				.equals(EnumRelatorioUtil.RELATORIO_PLANILHA_OPEN_OFFICE)) {
			this.exportador = new JROdsExporter();
			this.extensaoRelatorioExportado = "ods";
		} else if (tipo.equals(EnumRelatorioUtil.RELATORIO_EXCEL)) {
			this.exportador = new JRXlsExporter();
			this.extensaoRelatorioExportado = "xls";
		}
	}

	private void initConnection() {
		Session session = manager.unwrap(Session.class);
		session.doWork(this);
	}

}