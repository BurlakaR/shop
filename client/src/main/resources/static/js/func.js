function checkFIO(){
    var fio, test;
    
    fio = document.getElementById("fio").value;
    
    if(/[\d\W]/.test(fio)){
        alert ("Input not valid");
    }
    else {
        alert ("OK");
    }
    
}

f