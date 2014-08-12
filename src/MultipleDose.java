import java.io.IOException;

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

/**
 * Servlet implementation class MultiDose
 */
@WebServlet("/MultiDose")
public class MultipleDose extends HttpServlet {
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
		Object[] param = new MWArray[11];
		Object[] result = null;

		param[0] = new MWNumericArray(Integer.parseInt(request
				.getParameter("tfinal")), MWClassID.DOUBLE);

		String[] str_elems = request.getParameter("yini").split("\\s+");
		int[] numbers = new int[str_elems.length];
		for (int i = 0; i < str_elems.length; i++) {
			numbers[i] = Integer.parseInt(str_elems[i]);
		}
		param[1] = new MWNumericArray(numbers, MWClassID.DOUBLE);

		param[2] = new MWNumericArray(Integer.parseInt(request
				.getParameter("dose_number")), MWClassID.DOUBLE);
		param[3] = new MWNumericArray(Float.parseFloat(request
				.getParameter("ka_Central")), MWClassID.DOUBLE);
		param[4] = new MWNumericArray(Float.parseFloat(request
				.getParameter("Vm_Central")), MWClassID.DOUBLE);
		param[5] = new MWNumericArray(Float.parseFloat(request
				.getParameter("Km_Central")), MWClassID.DOUBLE);
		param[6] = new MWNumericArray(Float.parseFloat(request
				.getParameter("ka_Peripheral")), MWClassID.DOUBLE);
		param[7] = new MWNumericArray(Float.parseFloat(request
				.getParameter("k12")), MWClassID.DOUBLE);
		param[8] = new MWNumericArray(Float.parseFloat(request
				.getParameter("k21")), MWClassID.DOUBLE);
		param[9] = new MWNumericArray(Float.parseFloat(request
				.getParameter("Central")), MWClassID.DOUBLE);
		param[10] = new MWNumericArray(Float.parseFloat(request
				.getParameter("Peripheral")), MWClassID.DOUBLE);

		try {

			result = pkModel.RunPKmodelMultipledoseVI(1, param);
			WebFigure webFig = (WebFigure) ((MWJavaObjectRef) result[0]).get();

//			request.getSession().getServletContext().setAttribute("Project_Figure", webFig);

//			WebFigureHtmlGenerator htmlGen = new WebFigureHtmlGenerator(request);
//
//			response.setContentType("text/html");
//			PrintWriter pw = response.getWriter();
//			pw.write(htmlGen.getFigureEmbedString(
//							webFig, 
//							"Project_Figure",
//							"application",
//							"500",
//							"500",
//							null));
//			pw.close();
			
			// Set the figure scope to session
			request.getSession().setAttribute("Project_Figure", webFig);
			// Bind the figure's lifetime to session

			RequestDispatcher dispatcher = request.getRequestDispatcher("/multipledose.jsp");
			dispatcher.forward(request, response);

		} catch (MWException e) {
			e.printStackTrace();
		} finally {
			MWArray.disposeArray(param);
		}
	}

}
