


function checkForm(){
    var myWindow = window.open("", "MsgWindow", "width=400,height=500,scrollbars=yes,status=yes");

    myWindow.document.write("<p>Version: "+navigator.appVersion+"</p>");
    myWindow.document.write("<p> Name: "+navigator.appName+"</p>");

    myWindow.document.write("<p>Protocol: "+ window.location.protocol+"</p>");
    myWindow.document.write("<p> Hostname: "+ window.location.hostname+"</p>");

    myWindow.document.write("<p>Screen-width: "+ screen.width+"</p>");
    myWindow.document.write("<p> Screen-height: "+ screen.height+"</p>");

    myWindow.document.write("<p>History-current: "+history.state+"</p>");
    myWindow.document.write("<p> History-length: "+ window.history.length+"</p>");
}


$(document).ready(function(){
    var date_input=$('input[name="date"]'); //our date input has the name "date"
    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
    date_input.datepicker({
        format: 'mm/dd/yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
    })
})