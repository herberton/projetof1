package qcs.base.web.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthFilter implements Filter {
	protected static Log log = LogFactory.getLog(AuthFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.debug("doFilter");
		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rp = (HttpServletResponse) response;
		boolean auth = rq.getSession().getAttribute("user") != null;
		String loginPath = rq.getServletPath();

		log.debug("servletPath: "+rq.getServletPath());
			if (!auth && !loginPath.equals("/pages/login/login.xhtml")) {
				log.debug("Encaminhando");
				rp.sendRedirect(rq.getContextPath() + "/pages/login/login.xhtml");
			}else {
				log.debug("Mantido");
				try {
					chain.doFilter(request, response);
				} catch (Exception e) {
					throw new ServletException(e);
				}
			}
		
	}	

	public void destroy() {	}

	public void init(FilterConfig config) throws ServletException {	}
}
