<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script>
function isAlphaNumeric(str) {
	var code, i, len;
	for (i = 0, len = str.length; i < len; i++) {
		code = str.charCodeAt(i);
		if (!(code > 47 && code < 58) && // numeric (0-9)
			!(code > 64 && code < 91) && // upper alpha (A-Z)
	        !(code > 96 && code < 123) && // lower alpha (a-z)
	        !(code == 32)){ // space 
			return false;
		}
	}
		return true;
};

function isAlphabetical(str) {
	var code, i, len;
	for (i = 0, len = str.length; i < len; i++) {
		code = str.charCodeAt(i);
		if (!(code > 64 && code < 91) && // upper alpha (A-Z)
	        !(code > 96 && code < 123) && // lower alpha (a-z)
	        !(code == 32)){ // space 
			return false;
		}
	}
		return true;
};

function resetForm() {
	document.getElementById("age_error").innerHTML = "";
	document.getElementById("ssn_error").innerHTML = "";
	document.getElementById("address_error").innerHTML = "";
	document.getElementById("name_error").innerHTML = "";
}

function validateForm() {
	resetForm();
	var regEx = /^[a-z][a-z\s]*$/;
	let ssn = document.forms["createForm"]["ssn"].value;
	let ssn_text;
	let name = document.forms["createForm"]["name"].value;
	let name_text;
	let age = document.forms["createForm"]["age"].value;
	let age_text;
	let addr = document.forms["createForm"]["address"].value;
	let addr_text;
	var letters = /^[A-Za-z]+$/;
	
	errorExists = false;
	
	// <!-- fields cannot be null -->
	if (ssn == '' || name == '' || age == '' || addr == '') {
		alert("Fields must not be empty!");
	    errorExists = true;
	} else {
		//<!-- ssn must be 9 digit numeric -->
		if (isNaN(ssn) || ssn.length < 9 || ssn.length > 9) {
			ssn_text = "SSN must be a number of length 9";
			document.getElementById("ssn_error").innerHTML = ssn_text;
			errorExists = true;
		} 
		// <!-- age must be 18+ -->
		if (age < 18 || isNaN(age)) {
			age_text = "Age must be number, 18+";
			document.getElementById("age_error").innerHTML = age_text;
			errorExists = true;
		}
		// <!-- name must be alphabetical -->
		if (!isAlphabetical(name)) {
			name_text = "Name must be alphabetical";
			document.getElementById("name_error").innerHTML = name_text;
			errorExists = true;
		}
		// <!-- address must be alpha-numeric --> 
		if (!isAlphaNumeric(addr)){
			addr_text = "Address must be alpha-numeric only!";
			document.getElementById("address_error").innerHTML = addr_text;
	        errorExists = true;
		}
	}
	if (errorExists){
		return false;
	} else {
		return true;
	}
	
}
</script>
<style>
/* http://meyerweb.com/eric/tools/css/reset/ 
   v2.0 | 20110126
   License: none (public domain)
*/
html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p,
	blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn,
	em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var,
	b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend,
	table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas,
	details, embed, figure, figcaption, footer, header, hgroup, menu, nav,
	output, ruby, section, summary, time, mark, audio, video {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%;
	font: inherit;
	vertical-align: baseline;
}
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure, footer, header, hgroup,
	menu, nav, section {
	display: block;
}

body {
	line-height: 1;
}

ol, ul {
	list-style: none;
}

blockquote, q {
	quotes: none;
}

blockquote:before, blockquote:after, q:before, q:after {
	content: '';
	content: none;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}

/* ------------------------------------------------------------------------------------------------------------------ */
:root {
	--main-bg-color: #000000;
	--secondary-bg-color: #FFDE00;
	--bg-grey: #282828;
	--white-font: #ffffff;
}

p {
	font-family: Arial, Helvetica, sans-serif;
}

a, h3, td, th, h1, h2 {
	font-family: Arial, Helvetica, sans-serif;
}

html {
	min-height: 100vh;
}

body {
	display: flex;
	flex-direction: column;
	justify-content:space-between;

	min-height: 100vh;
}

.bg-black {
	background-color: var(--main-bg-color);
}

.bg-secondary {
	background-color: var(--secondary-bg-color);
}

.font-secondary-color {
	color: var(--secondary-bg-color);
}

.header {
	display: flex;
	flex-direction: column;
	justify-content: space-between;	
	
	min-height: 20vh;
	padding: 0% 10vw;
}

.white {
	color: white;
}

.title {
	display: flex;
	gap: 0.5em;
	
	margin-top: 0.75em; 
}

.title-font {
	font-size: 2.5em;
	font-weight: 900;
}

.nav-font {
	font-size: 1em;
	font-weight: 700;
}

.footer-title-font {
	font-size: 1.5em;
	font-weight: 600;
}

.footer {
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	
	min-height: 30vh;
	border-top: 0.4em var(--secondary-bg-color) solid;
	
}

.nav-bar {
	display: flex;
	align-items: center;

	height: 3em;
	padding: 0.3em 1.5em;

}

.nav-bar a {
	text-decoration: none;
	color: black;
}

.link{
	display: flex;
	align-items: center;
	position: relative;

	margin-right: 2em;
}

.link > .fa {
	position: relative;
	bottom: 0.25em;
	
	padding: 0px;
	padding-left: 3px;
}

.link:hover {
	cursor: pointer;
}

.information-wrap {
	display: flex;
	justify-content: space-between;
	gap: 4em;
	
	padding: 0% 10vw;
	margin-top: 1em;
}

.information-wrap h3 {
	margin-bottom: 0.3em;
}

.information-wrap p {
	font-size: 0.8em;
	font-weight: 600;
	color: #696969;
}

.information1 {
	flex: 5 1 0;
}

.information2{
	flex: 2 1 0;
}

.information3 {
	flex: 3 1 0;
}

.socials-wrap {
	display: flex;
	justify-content: space-between;
	align-items: center;
	
	background-color: var(--bg-grey);
	
	padding: 1em 10vw;
}

.icons {
	display: flex;
	align-items: center;
	gap: 0.5em;
}

.fa {
	padding: 7px;
	width: 10px;
	height: 10px;
	border-radius: 7px;
	text-align: center;
	text-decoration: none;
	background: #ffffff;
	color: black;
}

.highlight {
	font-size: 0.8em;
	font-weight: 700;
}

.main-content {
	display: flex;
	flex-direction: column;
	justify-content: space-around;
	align-items: center;
	
	min-height: 50vh;
}

.page-title {
	font-size: 2.5em;
	font-weight: 600;
}

.form {
	display: flex;
	flex-direction: column;
	align-items: center;
	
}

.button {
	width: 5em;
	height: 2em;
	
	background-color: #282828;
	color: #f0f0f0;
	border: 0px solid black;
	border-radius: 5px;
}

.padded {
	padding: 1em;"
}

table p {
	font-weight: 600;
}

.table-create {
	display: flex;
	justify-content: center;
	align-self: center;
	
	gap: 1em;
	width: 80%
	
}

.table-create td, .table-create th {
	padding: 0.5em;
}

.submit-button {
	height: 2em;
	width: 6em;
	
	font-size: 1em;
	font-weight: 600;
	
	background-color: black;
	color: var(--secondary-bg-color);
}

.submit-button:hover {
	cursor: pointer;
}
</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta charset="ISO-8859-1">
<title>Bank</title>
</head>
<body>
	<div class="header bg-black">
		<div class="title"><p class="title-font font-secondary-color">FedChoice</p><p class="title-font white">Bank</p></div>
		<div class="nav-bar bg-secondary">
			<a class="link nav-font" href="exec-splash.html">Home</a>
			<a class="link nav-font" href="list.html">List Customers<i class="fa fa-caret-down " style="font-size:24px; background-color: #FFDE00"></i></a>
			<a class="link nav-font" href="create-customer-page.html">Create Customer<i class="fa fa-caret-down " style="font-size:24px; background-color: #FFDE00"></i></a>
			<a class="link nav-font" href="searchLoad.html">Search Customer<i class="fa fa-caret-down " style="font-size:24px; background-color: #FFDE00"></i></a>
			<a class="link nav-font" href="allacclist.html">List Accounts<i class="fa fa-caret-down " style="font-size:24px; background-color: #FFDE00"></i></a>
			<a class="link nav-font" href="home.html">Log Out</a>
		</div>
	</div>
	
	<div class="main-content">
	<h2 class="page-title" align="center">Create Customer</h2>
	<c:if test="${not empty errorMsg}">
		<p align="center"><font color="red">${errorMsg }</font></p>
	</c:if>
	<FORM class="form" name="createForm" method="get" onsubmit="return validateForm()"
		action="create-customer.html" style="width:80%;">
		<TABLE class="table-create">
			<TR>
				<td><p>Customer SSN Id</p></td>
				<td><input type="text" name="ssn"></td>
				<td><span style="color: red" id="ssn_error"></span></td>
			</TR>
			<TR>
				<td><p>Customer Name</p></td>
				<td><input type="text" name="name"></td>
				<td><span style="color: red" id="name_error"></span></td>
			</TR>
			<TR>
				<td><p>Age</p></td>
				<td><input type="text" name="age"></td>
				<td><span style="color: red" id="age_error"></span></td>
			</TR>
			<TR>
				<td><p>Address</p></td>
				<td><input type="text" name="address"></td>
				<td><span style="color: red" id="address_error"></span></td>
			</TR>
			<TR>
				<td><input class="submit-button" type="submit" value="Submit"></td>
				<td><input class="submit-button" type="reset" value="Reset" onclick="resetForm();"></td>
			</TR>
		</TABLE>
	</FORM>
	
	</div>

	<div class="footer bg-black">
		<div class="information-wrap">
			<div class="information1">
				<h3 class="footer-title-font font-secondary-color">About Us</h3>
				<p>FedChoice Bank was founded on 14th June 2016 with the objective of providing with the detail services based on Retail Banking operations. The Retail Internet Banking of FedChoice Bank offers a plethora of products and services, to cater its customers by providing certain animus services with an easy volving offers and ways to do the required job without hurdling the process.</p>
			</div>
			<div class="information2">
				<h3 class="footer-title-font font-secondary-color">Services</h3>
				<p>Retail and Consumer Banking<br>Personal Internet Banking<br>Corporate Internet Banking<br>Debit and Credit Cards</p>
			</div>
			<div class="information3">
				<h3 class="footer-title-font font-secondary-color">Contact Us</h3>
				<p>Email:customer.service@fedchoice.com<br>Contact No:022-42406778, 022-54567890<br>Address:Corporate Office, Madame Cama Road,<br>Nariman Point, Mumbai, Maharashtra 400021</p>
			</div>
		</div>
	
		<div class="socials-wrap">
			<div class="copyright">
				<p class="highlight white">Copyright &#169 All rights reserved by <span class="font-secondary-color">FedChoice Bank</span></p>
			</div>
			<div class="socials">
				<div class="icons">
					<p class="highlight font-secondary-color">Contact Us</p>
					<a href="#" class="fa fa-facebook"></a>
      				<a href="#" class="fa fa-twitter"></a>
      				<a href="#" class="fa fa-google"></a>
      				<a href="#" class="fa fa-linkedin"></a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>