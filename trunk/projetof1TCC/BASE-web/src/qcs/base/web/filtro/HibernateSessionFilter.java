package qcs.base.web.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import qcs.persistence.util.SimpleHibernateUtil;

public class HibernateSessionFilter implements Filter {

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Session session = null;
		Transaction tx = null;
		try{
			session = SimpleHibernateUtil.getSession();
			tx = session.beginTransaction();
			((HttpServletRequest) request).setAttribute("hibernateSession", session);
			chain.doFilter(request, response);
			tx.commit();
		}catch(InvalidStateException e){
			if(tx != null && tx.isActive())
				tx.rollback();
			e.printStackTrace();
			StringBuilder strBuilder = new StringBuilder();
			for(InvalidValue value: e.getInvalidValues()){
				strBuilder.append("Entidade: "+value.getBean()+">> Propriedade: "+value.getPropertyName()+" mensagem: "+value.getMessage());
			}
			throw new ServletException("Erro de validação ao submeter informações >> "+strBuilder.toString());
//			FacesContext.getCurrentInstance().addMessage(null, 
//					new FacesMessage("Erro de validação ao submeter informações", strBuilder.toString()));
		}catch(Exception e){
			if(tx != null && tx.isActive())
				tx.rollback();
			e.printStackTrace();
			throw new ServletException("Erro ao submeter informações >> "+e.getMessage());
//			FacesContext.getCurrentInstance().addMessage(null, 
//					new FacesMessage("Erro ao submeter informações para o banco de dados", e.getMessage()));
		}finally{
			if(session!= null){
				if(session.isOpen())session.close();
			}
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}