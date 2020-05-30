function setCurrentTime(field) {
	/* sets the current time in HHMM format in the named field */
	var d = new Date();

	var curr_hour = d.getHours();
	curr_hour = curr_hour + "";
	if (curr_hour.length == 1) {
		curr_hour = "0" + curr_hour;
	}

	var curr_min = d.getMinutes();
	curr_min = curr_min + "";
	if (curr_min.length == 1) {
		curr_min = "0" + curr_min;
	}

	document.getElementById(field).value = curr_hour + curr_min;

}


function setCaseData(caseId) {
	var xmlHttp;

	try {
		// Firefox, Opera 8.0+, Safari
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		// Internet Explorer
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("Your browser does not support AJAX!");
				return false;
			}
		}
	}
	xmlHttp.onreadystatechange = function() {
		/* call back function which is called when something has happened */
		if (xmlHttp.readyState == 4) {
			/* state 4 means that response the response id fully read */
			data = xmlHttp.responseText;
			dataArray = data.split("\n");
			if (dataArray[1].length > 1) {
				document.getElementById("clientInput").value = dataArray[1]; // first line includes encodings, etc.	
				//alert("dataArray[1]: -" + dataArray[1] + "-" + dataArray[1].length);
			}
			if (dataArray[2].length > 1) {
				document.getElementById("caseNameInput").value = dataArray[2];
				//alert("length: " + (dataArray[1].length()));
			}
		}
	};
	
	try {
		xmlHttp.open("GET", "./CaseData.action?timeEntry.caseId=" + caseId, true);
	} catch (e) {
		alert("Problem getting case data! Exception: " + e);
		return false;
	}
	xmlHttp.send(null);
}

function resetForm() {
	document.getElementById('idInput').value="";
	document.getElementById('startTimeInput').value="";
	document.getElementById('endTimeInput').value="";
	document.getElementById('caseIdInput').value="";
	document.getElementById('caseNameInput').value="";
	document.getElementById('clientInput').value="";
	document.getElementById('rateInput').value="";
	document.getElementById('commentInput').value="";
	document.getElementById('isUpdateInput').value="";
}

function copyCase(name) {
	document.getElementById('caseIdInput').value=document.getElementById('caseId_' + name).innerHTML;
	document.getElementById('caseNameInput').value=document.getElementById('caseName_' + name).innerHTML;
	document.getElementById('clientInput').value=document.getElementById('client_' + name).innerHTML;
}

function autoSave(message) {
	var xmlHttp;
	try {
		// Firefox, Opera 8.0+, Safari
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		// Internet Explorer
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("Your browser does not support AJAX!");
				return false;
			}
		}
	}
	xmlHttp.onreadystatechange = function() {
		/* call back function which is called when something has happened */
		if (xmlHttp.readyState == 4) {
			/* state 4 means that response the response id fully read */
			var d = new Date();

			var curr_hour = d.getHours();
			curr_hour = curr_hour + "";
			if (curr_hour.length == 1) {
				curr_hour = "0" + curr_hour;
			}

			var curr_min = d.getMinutes();
			curr_min = curr_min + "";
			if (curr_min.length == 1) {
				curr_min = "0" + curr_min;
			}
			/* generate image url */
			imgUrl = document.getElementById('timeGraphImg').src;
			pos = imgUrl.indexOf("&tmpTime");
			if (pos > 0) {
				imgUrl = imgUrl.substring(0, pos);
			}
			tmpStart = "";
			if (document.getElementById('caseIdInput').value.length > 1) {
				tmpStart = document.getElementById('startTimeInput').value;
			}
			imgUrl = imgUrl + "&tmpTime=" + tmpStart;			
			imgUrl = imgUrl + "&" + (new Date()).getTime();
			document.getElementById('timeGraphImg').src = imgUrl; 
			msgB = false;
			msgB = message;
			if (message == true) {
				document.getElementById('message').innerHTML = "Autosaved form data at " + curr_hour + curr_min;
			}
		}
	}
	;
	
	try {
		xmlHttp.open("GET", "./Command?cmd=add_time_item" +
			"&id=" + document.getElementById('idInput').value + 
			"&the_date=" + document.getElementById('dateInput').value + 	
			"&start_time=" + document.getElementById('startTimeInput').value + 
			"&end_time=" + document.getElementById('endTimeInput').value + 
			"&case_id=" + document.getElementById('caseIdInput').value + 
			"&client=" + document.getElementById('clientInput').value + 
			"&case_name=" + document.getElementById('caseNameInput').value + 
			"&rate=" + document.getElementById('rateInput').value + 
			"&comment=" + document.getElementById('commentInput').value + 
			"&isUpdate=" + document.getElementById('isUpdateInput').value + 
			"&isAutoSave=1"
				, true);
	} catch (e) {
		alert("Problem! Exception: " + e);
		return false;
	}
	xmlHttp.send(null);
}

function addEntries() {

	// for all checkboxes
//	var dur = 0.0;
	var rev = 0;
	var i = 0;
	while (i < parseInt(document.getElementById('numEntries').innerHTML)) {
		if (document.getElementById('include' + i).checked) {
//			dur +=	parseFloat(document.getElementById('duration' + i).value);
			rev +=	parseInt(document.getElementById('revenue' + i).value);
//			dur +=	parseFloat(document.getElementById('duration' + i).innerHTML);
//			rev +=	parseInt(document.getElementById('revenue' + i).innerHTML);
		}
		i++;
	}
//	if (document.getElementById('newTimeEntry.include').checked) {
//		if (!isNaN(parseFloat(document.getElementById('newTimeEntry.duration').value))) {
//			dur +=	parseFloat(document.getElementById('newTimeEntry.duration').value);			
//		}
//		if (!isNaN(parseInt(document.getElementById('newTimeEntry.revenue').value))) {
//			rev +=	parseInt(document.getElementById('newTimeEntry.revenue').value);			
//		}
//	}
	
//	var tmp = Math.round(dur*10);
//	dur = tmp/10;

	document.getElementById('newAmount').innerHTML = rev;
	document.getElementById('newAmountValue').innerHTML = rev; 

//	document.getElementById('newDuration').innerHTML = dur;
//	document.getElementById('newDurationFormValue').innerHTML = dur; 

}

function setPunchTime() {
	/* sets the current time in HHMM format in the punch time field */
	var d = new Date();

	var curr_hour = d.getHours();
	curr_hour = curr_hour + "";
	if (curr_hour.length == 1) {
		curr_hour = "0" + curr_hour;
	}

	var curr_min = d.getMinutes();
	curr_min = curr_min + "";
	if (curr_min.length == 1) {
		curr_min = "0" + curr_min;
	}

	var curr_year = d.getFullYear();
	curr_year = curr_year + "";
	curr_year = curr_year.substring(2, 4);

	var curr_month = (d.getMonth() + 1);
	curr_month = curr_month + "";
	if (curr_month.length == 1) {
		curr_month = "0" + curr_month;
	}

	var curr_date = d.getDate();
	curr_date = curr_date + "";
	if (curr_date.length == 1) {
		curr_date = "0" + curr_date;
	}
	
	document.getElementById('formTime').value = curr_hour + curr_min;
	document.getElementById('originalTime').value = curr_hour + curr_min;
	document.getElementById('formDate').value = curr_year + curr_month + curr_date;	
}

