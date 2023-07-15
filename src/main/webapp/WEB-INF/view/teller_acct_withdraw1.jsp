<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script>
function validateForm() {
	let val = document.forms["withdrawForm"]["val"].value;
	errorExists = false;
	// <!-- field cannot be null -->
	if (val=='') {
		alert("Please enter a value!");
	    errorExists = true;
	}
	if (!errorExists) {
		if (isNaN(val)) {
			alert("Withdraw amount must be a number!");
			errorExists = true;
		} else if (val < 1) {
			alert("Withdraw amount must be a number greater than zero!");
			errorExists = true;
		}
	}
	// if error exists, do not continue
	if (errorExists){
		return false;
	} else {
		return true;
	}
}
</script>
	<meta charset="ISO-8859-1">
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

a, h3, td, th, h1 {
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
	width: 70%
	
}

.table-create td, .table-create th {
	padding: 0.5em;
}

.submit-button {
	height: 2em;
	width: 7em;
	
	font-size: 1em;
	font-weight: 600;
	
	background-color: black;
	color: var(--secondary-bg-color);
}

.submit-button:hover {
	cursor: pointer;
}

tbody {
	display: flex;
	flex-direction: column;
	align-items: center;
}
</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<title>Bank</title>
</head>
<body>
	<div class="header bg-black">
		<div class="title"><p class="title-font font-secondary-color">FedChoice</p><p class="title-font white">Bank</p></div>
		<div class="nav-bar bg-secondary">
			<a class="link nav-font" href="teller-home.html">Home</a>
			<a class="link nav-font" href="home.html">Logout</a>
		</div>	
	</div>
	
	<div class="main-content">
		<h1 class="page-title" align="center">Withdraw Amount</h1>
		<br>
		<form class="form" action="accountWithdraw2.html" style="width:70%" name="withdrawForm" onsubmit="return validateForm()">
		<input type='hidden' name='accID' value='${account1.accountId}'>
		<table class="table-create">
			<tr>
				<td>Customer ID: </td>
				<td>${account1.customerId}</td>
			</tr>
			<tr>
				<td>Account ID: </td>
				<td>${account1.accountId}</td>
			</tr>
			<tr>
				<td>Account Type: </td>
				<td>${account1.accountType}</td>
			</tr>
			<tr>
				<td>Balance: </td>
				<td>${account1.balance}</td>
			</tr>
			<tr>
				<td>Withdraw Amount: </td>
				<td><input type='text' name='val'></td>
			</tr>
			<c:if test="${not empty MSG}">
				<tr>
					<td colspan='2'>${MSG}</td>
				</tr>
			</c:if>
			<tr>
				<td colspan='2'><input class="submit-button" type='submit' name='submit' value='Submit'></td>
			</tr>
		</table>
		</form>
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