import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RunPKmodelVII.Function;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWJavaObjectRef;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.mathworks.toolbox.javabuilder.webfigures.WebFigure;
import com.mathworks.toolbox.javabuilder.webfigures.WebFigureHtmlGenerator;

/**
 * Servlet implementation class SingleDose
 */
@WebServlet("/SingleDose")
public class SingleDose extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Function pkModel = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void init() throws ServletException {
		if (pkModel == null) {
			try {
				pkModel = new Function();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public void destroy() {
		if (pkModel != null)
			pkModel.dispose();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		/*
		 * Get the parameters and convert them to MWArray
		 */
		Object[] param = new MWArray[10];
		Object[] result = null;

		param[0] = new MWNumericArray(Integer.parseInt(request
				.getParameter("tf_sim")), MWClassID.DOUBLE);

		String[] str_elems = request.getParameter("y0").split("\\s+");
		int[] numbers = new int[str_elems.length];
		for (int i = 0; i < str_elems.length; i++) {
			numbers[i] = Integer.parseInt(str_elems[i]);
		}
		param[1] = new MWNumericArray(numbers,MWClassID.DOUBLE);

		param[2] = new MWNumericArray(Float.parseFloat(request
				.getParameter("ka_Central")), MWClassID.DOUBLE);
		param[3] = new MWNumericArray(Float.parseFloat(request
				.getParameter("Vm_Central")), MWClassID.DOUBLE);
		param[4] = new MWNumericArray(Float.parseFloat(request
				.getParameter("Km_Central")), MWClassID.DOUBLE);
		param[5] = new MWNumericArray(Float.parseFloat(request
				.getParameter("ka_Peripheral")), MWClassID.DOUBLE);
		param[6] = new MWNumericArray(Float.parseFloat(request
				.getParameter("k12")), MWClassID.DOUBLE);
		param[7] = new MWNumericArray(Float.parseFloat(request
				.getParameter("k21")), MWClassID.DOUBLE);		
		param[8] = new MWNumericArray(Float.parseFloat(request
				.getParameter("Central")), MWClassID.DOUBLE);
		param[9] = new MWNumericArray(Float.parseFloat(request
				.getParameter("Peripheral")), MWClassID.DOUBLE);

		
		try {

			result = pkModel.runPKmodelVII(1, param);
			WebFigure webFig = (WebFigure) ((MWJavaObjectRef) result[0]).get();

//			// Set the figure scope to session
			request.getSession().setAttribute("Project_Figure", webFig);
//			// Bind the figure's lifetime to session
//
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/singledose.jsp");
			dispatcher.include(request, response);
			
//			request.getSession().getServletContext().setAttribute(
//					"Project_Figure", webFig);
//
//			WebFigureHtmlGenerator htmlGen = new WebFigureHtmlGenerator(request);
//
//			response.setContentType("text/html");
//			PrintWriter pw = response.getWriter();
//			pw.write(htmlGen.getFigureEmbedString(
//							webFig, 
//							"Project_Figure",
//							"application",
//							"330",
//							"330",
//							null));
//			pw.close();

		} catch (MWException e) {
			e.printStackTrace();
		} finally {
			MWArray.disposeArray(param);
		}
	}

}
