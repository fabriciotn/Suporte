package com.Reports;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import com.model.User;

public class ImpressaoLaudo {
	private Connection connection;
	Calendar data = Calendar.getInstance();

	/**
	 * Metodo resposável pela impressao do Laudo
	 * @param usuario
	 * @param id_laudo
	 * @return arrayDeBytes
	 */
	public byte[] imprimeRelatorio(User usuario, int id_laudo) {
		byte[] relatorio = null;
		try {
			URL arquivo = getClass().getResource("HelpDesk.jasper");
			JasperReport arquivoJasper = (JasperReport) JRLoader
					.loadObject(arquivo);
			this.connection = getConection();
			
			String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			//map.put("SUBREPORT_DIR", path + "/WEB-INF/classes/com/Reports/");
			//map.put("usuario_logado", usuarioLogado.getName());
			map.put("ID_LAUDO", id_laudo);
			map.put("CAMINHO_IMAGEM", path + "images/logo.png");
			map.put("assinatura", path + "assinaturas/" + usuario.getLogin()+ ".png");
			map.put("assinatura_responsavel", path + "assinaturas/responsavel.png");
			relatorio = JasperRunManager.runReportToPdf(arquivoJasper, map,
					this.connection);
			//JasperPrint jp = JasperFillManager.fillReport(arquivoJasper, map, this.connection);

            //JasperViewer.viewReport(jp, false);
            //JasperPrintManager.printReport(jp,true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return relatorio;
	}

	/**
	 * Realiza a conexão com o banco exclusivamente para o relatório
	 * @return Connection
	 */
	public Connection getConection() {
		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		String senhaBanco = bundle.getString("senhaBanco");
		String urlBanco = bundle.getString("url");
		
		String url = urlBanco;
		String usuario = "root";
		String senha = senhaBanco;
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, usuario, senha);

			// a conexão foi obtida com sucesso?
			if (conn != null) {
				//System.out.println("conexao ok");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
