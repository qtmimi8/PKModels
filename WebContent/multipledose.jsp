<%@ page import="RunPKmodelVII.Function"%>
<%@ page import="com.mathworks.toolbox.javabuilder.MWException"%>
<%@ taglib prefix="wf"
	uri="http://www.mathworks.com/builderja/webfigures.tld"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="dosestyle.css" />
<title>Single Dose Model</title>

<%
	String tfinalStr = request.getParameter("tfinal");
	int tfinal = 100;
	if (tfinalStr != null && tfinalStr.length() > 0) {
		tfinal = Integer.parseInt(tfinalStr);
	}

	String yiniStr = request.getParameter("yini");
	String yini = "10 0 0 0";
	if (yiniStr != null && yiniStr.length() > 0) {
		yini = yiniStr;
	}

	String dose_numberStr = request.getParameter("dose_number");
	int dose_number = 9;
	if (dose_numberStr != null && dose_numberStr.length() > 0) {
		dose_number = Integer.parseInt(dose_numberStr);
	}

	String ka_CentralStr = request.getParameter("ka_Central");
	double ka_Central = 0.5;
	if (ka_CentralStr != null && ka_CentralStr.length() > 0) {
		ka_Central = Float.parseFloat(ka_CentralStr);
	}

	String Vm_CentralStr = request.getParameter("Vm_Central");
	double Vm_Central = 30;
	if (Vm_CentralStr != null && Vm_CentralStr.length() > 0) {
		Vm_Central = Float.parseFloat(Vm_CentralStr);
	}

	String Km_CentralStr = request.getParameter("Km_Central");
	double Km_Central = 1;
	if (Km_CentralStr != null && Km_CentralStr.length() > 0) {
		Km_Central = Float.parseFloat(Km_CentralStr);
	}

	String ka_PeripheralStr = request.getParameter("ka_Peripheral");
	float ka_Peripheral = 0.1f;
	if (ka_PeripheralStr != null && ka_PeripheralStr.length() > 0) {
		ka_Peripheral = Float.parseFloat(ka_PeripheralStr);
	}

	String k12Str = request.getParameter("k12");
	double k12 = 1;
	if (k12Str != null && k12Str.length() > 0) {
		k12 = Float.parseFloat(k12Str);
	}

	String k21Str = request.getParameter("k21");
	double k21 = 1;
	if (k21Str != null && k21Str.length() > 0) {
		k21 = Float.parseFloat(k21Str);
	}

	String CentralStr = request.getParameter("Central");
	double Central = 1;
	if (CentralStr != null && CentralStr.length() > 0) {
		Central = Float.parseFloat(CentralStr);
	}

	String PeripheralStr = request.getParameter("Peripheral");
	double Peripheral = 1;
	if (PeripheralStr != null && PeripheralStr.length() > 0) {
		Peripheral = Float.parseFloat(PeripheralStr);
	}
%>

</head>

<body>
	<!--
       <form action="http://localhost:8080/multipledose.jsp">
    -->
	<form method="GET" action="MultipleDose" id="searchform">
		<div id="container">

			<div id="header1">
				<a href="singledose.jsp" id="singledose">Single Dose</a> &nbsp;
				&nbsp; <a href="index.html" id="index">Home</a>
			</div>

			<div id="header2">
				<h2>Multiple Dose Model</h2>
			</div>

			<div id="order">
				<p>
					Please fill in the <font id="orderfont">YELLOW</font> boxes and
					click Simulate
				</p>
			</div>

			<div id="content">

				<div id="picture">
					<div id="img">
						<img alt="PK Diagram" src="PKdiagram1.jpg">
					</div>
					<div id="parameter">
						<input type="text" name="ka_Central" class="ka_Central"
							id="ka_Central" value="<%=ka_Central%>" /> <input type="text"
							name="k12" class="k12" id="k12" value="<%=k12%>" /> <input
							type="text" name="k21" id="k21" class="k21" value="<%=k21%>" />
						<input type="text" name="ka_Peripheral" class="ka_Peripheral"
							id="ka_Peripheral" value="<%=ka_Peripheral%>" /> <input
							type="text" name="Km_Central" class="Km_Central" id="Km_Central"
							value="<%=Km_Central%>" /> <input type="text" name="Vm_Central"
							class="Vm_Central" id="Vm_Central" value="<%=Vm_Central%>" /> <input
							type="text" name="Central" class="Central" id="Central"
							value="<%=Central%>" /> <input type="text" name="Peripheral"
							class="Peripheral" id="Peripheral" value="<%=Peripheral%>" />
					</div>
					<div id="tSimulate">
						<table>
							<tbody>
								<tr>
									<th align="center" colspan="2" class="th">Simulation</th>
								<tr align="left">
									<td class="td"><br>Time<br></td>
									<td class="td"><br> <input type="text" class="tfinal"
										name="tfinal" id="tfinal" value="<%=tfinal%>" /><br></td>
								</tr>
								<tr align="left">
									<td class="td">Initial Condition</td>
									<td class="td"><input type="text" name="yini" class="yini"
										id="yini" value="<%=yini%>" /><br></td>
								</tr>
								<tr align="left">
									<td class="td">Dose Number<br></td>
									<td class="td"><input type="text" class="dose_number"
										name="dose_number" id="dose_number" value="<%=dose_number%>" /><br></td>
								</tr>
								<tr>
									<td align="center" colspan="2"><br>
										<div id="submit_div">
											<input type="submit" value="Simulate" name="DoPLot"
												id="DoPLot" />
										</div>
								</tr>
						</table>
					</div>
				</div>

				<div id="webfigure">
					<jsp:include page="dose.jsp"></jsp:include>
				</div>

			</div>
			<div>
				<table id="tInfo">
					<tr>
						<th scope="col" width="25%">Absorption</th>
						<th scope="col" width="25%">Elimination</th>
						<th scope="col" width="25%">Volume of Distribution</th>
						<th scope="col" width="25%">Incompartmental Clearance</th>
					</tr>
					<tbody>
						<tr align="center">
							<td>ka_Central<br></td>
							<td>Km_Central<br></td>
							<td>Central<br></td>
							<td>k12<br></td>
						</tr>
						<tr align="center">
							<td>ka_Peripheral<br></td>
							<td>Vm_Central<br></td>
							<td>Peripheral<br></td>
							<td>k21<br></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="footer">
				<b>Copyright &copy; 2014 Zetty Zakaria, University College
					London</b>
			</div>

		</div>
	</form>
</body>

</html>