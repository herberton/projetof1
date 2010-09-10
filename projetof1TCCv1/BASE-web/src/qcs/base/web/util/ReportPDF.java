package qcs.base.web.util;

import java.io.File;
import java.sql.Statement;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReportPDF {
	protected static Log log = LogFactory.getLog(ReportPDF.class);
	public ReportPDF() {
	}

	public static void viewReportPDF(String reportName, Map<String, Object> reportParameters,int idAcesso, Statement stmt) {
		try{     
			FacesContext fc = FacesContext.getCurrentInstance();

			//HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();   

			//InputStream reportStream = fc.getExternalContext().getResourceAsStream("/reports/FirstReport.jasper");

			//          ApplicationModule am = Util.getApplicationModule(fc);
			//          DBTransaction tr = (DBTransaction)am.getTransaction();

			ServletContext context = (ServletContext)fc.getExternalContext().getContext();

			String realPath = context.getRealPath("");

			byte[] err = {'E','r','r','o'};
			byte[] pdf = err;

			try{
				reportParameters.put("BaseDir", realPath+"/jasperReports/compilados/");
				File reportFile = new File(realPath+"/jasperReports/compilados/"+reportName);                     
				File outPutPDF = new File (realPath+"/jasperReports/pdf/relatorioTEMP"+idAcesso+".pdf");
				
				//File outPutRTF = new File (realPath+"/jasperReports/pdf/relatorioTEMP"+idAcesso+".rtf");     

				pdf = JasperRunManager.runReportToPdf(reportFile.getPath(), reportParameters, stmt.getConnection());
				log.debug("pdf.length :"+pdf.length);

				reportFile = verificarRelatorioVazio(realPath, reportFile, reportName, reportParameters, pdf.length);

				JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), reportParameters, stmt.getConnection());
				JasperExportManager.exportReportToPdfFile(jasperPrint, outPutPDF.getPath()); 
				
				Map<String,Object> sessionMap = fc.getExternalContext().getSessionMap();
				log.debug("realpath: "+realPath);
				sessionMap.remove("path");
				sessionMap.put("path", realPath+"\\jasperReports\\pdf\\relatorioTEMP1.pdf");

			}catch(Exception e){
				e.printStackTrace();

				byte[] errReport = {'E','r','r','o',' ','a','o',' ','g','e','r','a','r',' ','r','e','l','a','t','o','r','i','o'};
				pdf = errReport;

			} 
		
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}
	} 

	public static File verificarRelatorioVazio(String realPath, File repotFileAntigo, String reportName,Map<String,Object> reportParameters,int pdfLenght){
		//Se for relatório de servidores ou estagiário
		if(reportName.equals("novo_frequencias_servidores.jasper") || reportName.equals("novo_frequencias_estagiarios.jasper")){     
			int idTipoProc = Integer.parseInt(reportParameters.get("ID_TIPO_PROC").toString());
			if(idTipoProc == 2 && pdfLenght==797){
				return new File(realPath+"/jasperReports/compilados/sem_registro.jasper");
				//reportParameters.put("BaseDir", realPath);
			}else if(idTipoProc == 1 && pdfLenght==796){
				return new File(realPath+"/jasperReports/compilados/sem_registro.jasper");
			}else if(idTipoProc == 1 && pdfLenght==811){
				return new File(realPath+"/jasperReports/compilados/sem_registro.jasper");
			}else if(idTipoProc == 1 && pdfLenght==803){
				return new File(realPath+"/jasperReports/compilados/sem_registro.jasper");
			}else if(idTipoProc == 1 && pdfLenght==812){
				return new File(realPath+"/jasperReports/compilados/sem_registro.jasper");
			}
			return repotFileAntigo;
			//Se for relatório de servidores por cargo
		}else{           
			if(pdfLenght == 802 || pdfLenght == 805){
				return new File(realPath+"/jasperReports/compilados/sem_registro.jasper");
			}
			return repotFileAntigo;
		}
	}
}
