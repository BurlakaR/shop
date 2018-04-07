


function checkForm(e){
    e.PreventDefault();

    var fio, login, password, checkpassword, date, email;

    fio= document.getElementById("fio").value;
    login=document.getElementById("usernamesignup").value;
    password=document.getElementById("passwordsignup").value;
    checkpassword=document.getElementById("passwordsignupcheck").value;
    date=document.getElementById("date").value;
    email=document.getElementById("emailsignup").value;

    var validate=true;


    if(/[\d\W]/.test(fio)){
        validate=false;
    }

    if(validate){
        var form=document.createElement("form");
        form.setAttribute("method","post");
        form.setAttribute("action","/registration");

        form.appendChild(fio);
        form.appendChild(login);
        form.appendChild(password);
        form.appendChild(checkpassword);
        form.appendChild(date);
        form.appendChild(email);

        document.body.appendChild(form);
        form.submit();
    }

/*    var fio, test;
    
    fio = document.getElementById("fio").value;
    
    if(/[\d\W]/.test(fio)){
        var xhr= new XMLHttpRequest();
        xhr.open('GET','/errorRegistr', true);
    }*/

    
}

