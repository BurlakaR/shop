function addCategory (categories){
    var element = document.getElementById('category');

    for (var i=0; i<categories.length; i++){
        var divdrop = document.createElement('div');
        divdrop.setAttribute('class','dropdown');


        var divdropcontent = document.createElement('div');
        divdropcontent.setAttribute('class','dropdown-content');
        for(var j=0;j<categories[i].subcategories.length;j++){
            var linky = document.createElement('a');
            linky.setAttribute("href", "/"+categories[i].url+"/"+categories[i].subcategories[j]);
            linky.innerText = categories[i].subcategories[j];
            divdropcontent.appendChild(linky);
        }

        var link = document.createElement('a');
        link.setAttribute("href", "/"+categories[i].url+"/"+categories[i].url);
        link.setAttribute('class','dropbtn');
        link.innerText=categories[i].url;

        divdrop.appendChild(link);
        divdrop.appendChild(divdropcontent);
        element.appendChild(divdrop);
        element.appendChild(document.createElement('br'));
    }
}

function addBreadcrumb (){
    var breadcount = 2
    var count_i =0
    for (i=0; i<breadcount; i++){
        //if(count_i==(breadcount-1)){
        var element = document.getElementById('breadcrumb');
        var tli = document.createElement('li');
        var link = document.createElement('a');
        tli.appendChild(link)

        link.innerHTML='breadcrumb'+i;
        link.href='#bc'+i;
        link.id='bc_'+i;
        element.appendChild(tli);
        // }

        //}
        count_i=i;
    }
}

function addImgTxt (){
    var element = document.getElementById('imgtext');

    for(i=1; i<30; i++){
        var a = document.createElement('a');
        a.setAttribute("class", "imga");

        var figure = document.createElement('figure');
        var img = document.createElement('img');
        img.setAttribute("src", "images/sl"+i+".jpg");
        img.setAttribute("alt", "sl"+i);

        var figcaption = document.createElement('figcaption');
        //figcaption.innerHTML = "text under the picture "+i;

        var button = document.createElement('button');
        button.setAttribute("class", "btn-info");
        button.innerHTML = "Buy";

        var aname = document.createElement('a');
        var aprice = document.createElement('a');

        var name = "product ";

        aname.innerHTML = "Name: "+name+i+" ";

        aprice.innerHTML = "Price: "+i+" ";

        figcaption.appendChild(aname);
        //figcaption.appendChild(aprice);

        figure.appendChild(img);
        figure.appendChild(figcaption);


        a.appendChild(figure);

        a.appendChild(aprice);

        a.appendChild(button);

        element.appendChild(a);
    }

}



function addFilter(){

    var element = document.getElementById('filter');
    var btn = document.createElement('button');
    var divbtn = document.createElement('div');

    divbtn.appendChild(btn)

    divbtn.className ="row"

    btn.setAttribute("type", "button");
    btn.setAttribute("class", "btn");
    btn.setAttribute("class", "col-sm-2");
    btn.setAttribute("data-toggle", "collapse");
    btn.setAttribute("data-target", "#demo");

    btn.innerHTML='filter';
    btn.id = 'btnfilter';

    element.appendChild(divbtn);

}

function addProductPhoto (){
    var element = document.getElementById('product-photo');
    for(i=1; i<30; i++){
        var img = document.createElement('img');
        img.setAttribute("src", "images/sl"+i+".jpg");
        img.setAttribute("alt", "sl"+i);

        element.appendChild(img);

    }
}

function addSmallProdPhoto(){
    var element = document.getElementById('prod-photo-small');
    for(i=1; i<7; i++){

        var x =i-1;
        var li = document.createElement('li');
        li.setAttribute("class", "col-sm-12");

        var a = document.createElement('a');
        a.setAttribute("class", "thumbnail");
        a.id = "carousel-selector-"+x;

        var img = document.createElement('img');
        img.setAttribute("src", "images/sl"+i+".jpg");
        img.setAttribute("alt", "sl"+i);

        a.appendChild(img);
        li.appendChild(a);
        element.appendChild(li);
    }
}

function addLargeProgPhoto(){
    var element = document.getElementById('prod-photo-large');
    for(i=1; i<7; i++){
        var div = document.createElement('div');
        if(i==1){
            div.setAttribute("class", "active item");
            div.setAttribute("data-slide-number", i);
        } else{
            div.setAttribute("class", "item");
            div.setAttribute("data-slide-number", i);
        }
        var img = document.createElement('img');
        img.setAttribute("src", "images/sl"+i+".jpg");
        img.setAttribute("alt", "sl"+i);

        div.appendChild(img);
        element.appendChild(div);
    }
}

function addCarouselSlide(count){
    var element = document.getElementById('carousel-indicators');
    for (i=0; i<count; i++){
        var li = document.createElement('li');
        if(i==0){
            li.setAttribute("class", "active");
        } else{
            li.setAttribute("class", "");
        }
        li.setAttribute("data-target", "#myCarousel");
        li.setAttribute("data-slide-to", i);
        element.appendChild(li);
    }
}

function addCarouselPhoto(count){
    var element = document.getElementById('carousel-inner');
    for (i=0; i<count; i++){
        var div = document.createElement('div');
        if(i==0){
            div.setAttribute("class", "item active");
        } else{
            div.setAttribute("class", "item");
        }
        var img = document.createElement('img');
        img.setAttribute("src", "../static/img/sl"+i+".jpg");
        img.setAttribute("alt", "sl"+i);
        img.setAttribute("style", "width:100%;");
        
        div.appendChild(img);
        
        element.appendChild(div);
    }
}


function addDropdownMenu(user) {
    var element = document.getElementById('dropdown-menu');

    if (user === "guest") {
        var li_signup = document.createElement('li');
        var a_signup = document.createElement('a');

        a_signup.setAttribute("href", "/registration#toregistration");
        a_signup.innerHTML = "Sign Up";

        li_signup.appendChild(a_signup);
        element.appendChild(li_signup);

        var li_login = document.createElement('li');
        var a_login = document.createElement('a');

        a_login.setAttribute("href", "/registration#tologin");
        a_login.innerHTML = "Login";

        li_login.appendChild(a_login);
        element.appendChild(li_login);
    } else {
        var li_logout = document.createElement('li');
        var a_logout = document.createElement('a');

        a_logout.setAttribute("href", "logout");
        a_logout.innerHTML = "Logout";

        li_logout.appendChild(a_logout);
        element.appendChild(li_logout);
    }
}

function addCarouselInner(countElement) {
    var element = document.getElementById("carousel-inner");
    var div = document.createElement('div');
    div.setAttribute("class", "active item");

    var img = document.createElement('img');
    img.setAttribute("src", "../static/img/sl0.jpg");

    div.appendChild(img);
    element.appendChild(div);

    for(i=1; i<countElement; i++){

            var div = document.createElement('div');
            div.setAttribute("class", "item");

            var img = document.createElement('img');
            img.setAttribute("src", "../static/img/sl" + i + ".jpg");

            div.appendChild(img);
            element.appendChild(div);

    }
}

