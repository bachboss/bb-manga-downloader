function doLog(e) {
    chrome.tabs.getSelected(null, function(tab) {
        sendServiceRequest(tab.url);
    });
}
			
function sendServiceRequest(url) {
    var jax = new XMLHttpRequest();
    jax.open('POST','http://localhost:25560/extension/',true);
    jax.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    jax.send('url='+url);
}

document.addEventListener('DOMContentLoaded', 
    function () {
        document.querySelector('button').addEventListener('click', doLog);
    }
    );