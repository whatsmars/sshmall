var Ajax = {
	createXHR:function () {
		if (XMLHttpRequest) return new XMLHttpRequest();
		else if (ActiveXObject) return new ActiveXObject("Microsoft.XMLHTTP");
	}, 
	sendRequest:function (method, url, data, callback) {
		var xhr = this.createXHR();
		xhr.open(method, url);
		if (method == "GET") {
			xhr.send(null);
		} else if (method == "POST") {
			xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xhr.send(escape(data));//escape --> URL encode
		}
		xhr.onreadystatechange = function () {
			if (xhr.readyState == 4) {
			    if (xhr.status == 200) {
					var p = {text:xhr.responseText, xml:xhr.responseXML};
					callback(p);
				}
			}
		}
	}
};

