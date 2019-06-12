
    
    var opcoesNomes = ["Barras", "Fatias", "Linhas", "Colunas", "Area"];
    var opcoesValores = [ "bar", "doughnut","line", "column", "area"];
    
    
    var div = document.getElementById("mainDiv");
    div.style.backgroundColor = "gray";
    
    var comboBoxTitle = document.createElement("span");
    comboBoxTitle.innerHTML = "Selecione o tipo de gráfico:\n";

    var comboOpcoes = document.createElement('select');
    
    var cont = 0;

    var selected = document.body.getAttribute("selectedIndex");
    
    opcoesNomes.forEach( nome => {
            
            var elem = document.createElement('option');
            elem.text = nome;
            elem.value = opcoesValores[cont];

            comboOpcoes.appendChild(elem);
    
            if(opcoesValores[cont] == selected){
                comboOpcoes.selectedIndex = cont;
            }

            cont++;
        }
    );
    
    comboOpcoes.onchange = () => {
        var redirectPath = "relatorios.html?type=" + opcoesValores[comboOpcoes.selectedIndex];
        window.location.replace(redirectPath, true);
    };
    
    div.appendChild(comboBoxTitle);
    div.appendChild(comboOpcoes);
