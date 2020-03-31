package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Pais;
import service.PaisService;

/**
 * Servlet implementation class ManterPaisController
 */
@WebServlet("/ManterPais.do")
public class ManterPaisController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pPais = request.getParameter("pais");
		String pPopulacao = request.getParameter("populacao");
		String pArea = request.getParameter("area");
		long pPopulacao1 = Long.parseLong(pPopulacao);
		double pArea1 = Double.parseDouble(pArea);
		
		
		//instanciar o javabean
		Pais pais = new Pais();
		pais.setNomePais(pPais);
		pais.setPopulacaoPais(pPopulacao1);
		pais.setAreaPais(pArea1);
		
		
		//instanciar o service
		PaisService ps = new PaisService();
		ps.criar(pais);
		pais = ps.carregar(pais.getIdPais());
		
        //enviar para o jsp
        request.setAttribute("pais", pais);
        
        RequestDispatcher view = 
        request.getRequestDispatcher("Pais.jsp");
        view.forward(request, response);

	}

}
