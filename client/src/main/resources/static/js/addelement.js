function addCategory (categories){
    var element = document.getElementById('category');

    for (var i=0; i<categories.length; i++){
        var divdrop = document.createElement('div');
        divdrop.setAttribute('class','dropdown');


        var divdropcontent = document.createElement('div');
        divdropcontent.setAttribute('class','dropdown-content');
        for(var j=0;j<categories[i].subcategories.length;j++){
            var linky = document.createElement('a');
            linky.innerText = categories[i].subcategories[j];
            divdropcontent.appendChild(linky);
        }

        var link = document.createElement('a');
        link.setAttribute('class','dropbtn');
        link.innerText=categories[i].url;

        divdrop.appendChild(link);
        divdrop.appendChild(divdropcontent);
        element.appendChild(divdrop);
        element.appendChild(document.createElement('br'));
    }
}



