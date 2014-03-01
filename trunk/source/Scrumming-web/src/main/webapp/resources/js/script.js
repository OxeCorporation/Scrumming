// ********************************************************************************
// Custom Scripts
// ********************************************************************************
var isTeclaPressionada;

function isNumber(e) {
	var result = false;
	var code = (e.keyCode ? e.keyCode : e.which);
	if (code > 47 && code < 58) {
		result = true;
	} else if (code == 8 ||
			code == 9) {
			result = true;
	} else {
		result = false;
	}
	return result;
}


function recoverKey(event) {
	return (event.which) ? event.which : event.keyCode;
}

function campoDecimal_onKeyPress(campo, event) {
	campoDecimal_onKeyPressLimite(campo, event, -1);
}

function minusSymbolPressed(keycode){
	return keycode == 109 || keycode == 173;
}

function campoDecimal_onKeyPressLimite(campo, event, quantidadeDigitos) {
	var whichCode = recoverKey(event);
	var vr = removerMascara(campo.value);
	
	if(isNegativo(campo)){
		if(minusSymbolPressed(whichCode))
		{
			return false;
		}
	}else if(vr.length > 0 && minusSymbolPressed(whichCode)){
		return false;
	}
	
	if (whichCode == 13 || whichCode == 8 || whichCode == 9 || whichCode == 0 || whichCode == 37 || whichCode == 39) {
		return true;
	} else {
		
		if(isTeclaPressionada == true){
			return false;
		}else{
			isTeclaPressionada = true;
		}
		
		if((quantidadeDigitos > -1) && (vr.length >= quantidadeDigitos)){
			return false;
		}
		
		
		if (minusSymbolPressed(whichCode) || (whichCode > 47 && whichCode < 58 || whichCode > 95 && whichCode < 106)) {
			return true;
		}else{
			return false;
		}
	}
}

function campoDecimal_onKeyUp(campo, event, decimais) {
	isTeclaPressionada = false;
	var tecla = recoverKey(event);
	if (tecla != 0 && tecla != 9 && tecla != 16 && tecla != 37 && tecla != 39){
		var vr = removerMascara(campo.value);
		var tam = vr.length; 
		var retorno = '';
		var separador_decimal= ',';
		var separador_unidade= '.';
		var negativo = isNegativo(campo);
		
		if ( tam <= decimais ){
			retorno = vr ;
		}
		
		if ((tam > decimais) && (tam <= decimais + 3) ){
			retorno = vr.substr( 0, tam - decimais ) + separador_decimal + vr.substr( tam - decimais, tam ) ;
		}
		
		if ( (tam >= decimais + 4) && (tam <= decimais + 6) ){
			retorno = vr.substr( 0, tam - (decimais + 3) ) + separador_unidade + vr.substr( tam - (decimais + 3), 3 ) + separador_decimal + vr.substr( tam - decimais, tam ) ;
		}
		
		if ( (tam >= decimais + 7) && (tam <= decimais + 9) ){
			retorno = vr.substr( 0, tam - (decimais + 6) ) + separador_unidade + vr.substr( tam - (decimais + 6), 3 ) + separador_unidade + vr.substr( tam - (decimais + 3), 3 ) + separador_decimal + vr.substr( tam - decimais, tam ) ;
		}
		
		if ( (tam >= decimais + 10) && (tam <= decimais + 12) ){
			retorno = vr.substr( 0, tam - (decimais + 9) ) + separador_unidade + vr.substr( tam - (decimais + 9), 3 ) + separador_unidade + vr.substr( tam - (decimais + 6), 3 ) + separador_unidade + vr.substr( tam - (decimais + 3), 3 ) +
			separador_decimal + vr.substr( tam - decimais, tam ) ;
		}
		
		if ( (tam >= decimais + 13) && (tam <= decimais + 16) ){
			retorno = vr.substr( 0, tam - (decimais + 12) ) + separador_unidade + vr.substr( tam - (decimais + 12), 3 ) + separador_unidade + vr.substr( tam - (decimais + 9), 3 ) + separador_unidade + vr.substr( tam - (decimais + 6), 3 ) +
			separador_unidade + vr.substr( tam - (decimais + 3), 3 ) + separador_decimal + vr.substr( tam - decimais, tam ) ;
		}
		
		if(negativo){
			retorno = '-'+retorno;
		}
		
		campo.value = retorno;
	}
}

function removerMascara(value) {
	var vr = '';
	var numero;
	
	for (var i = 0; i < value.length ; i++) {
		numero = value.substring(i,i + 1);
		
		if (numero != '/' && numero != '.' && numero != '-'  && numero != ',' ){
			vr += numero;
		}
	}
	return vr;
}

function isNegativo(campo){
	if(campo.value.length > 0 && campo.value[0] == '-'){
		return true;
	}else{
		return false;
	}
}

function numeroDecreto_onKeyPressLimite(campo, event, quantidadeDigitos) {
	var whichCode = recoverKey(event);
	var vr = removerMascara(campo.value);
	
	if (whichCode == 13 || whichCode == 8 || whichCode == 9 || whichCode == 0 || whichCode == 37 || whichCode == 39) {
		return true;
	} else {
		
		if((quantidadeDigitos > -1) && (vr.length >= quantidadeDigitos)){
			return false;
		}
	
		if(whichCode > 47 && whichCode < 58 || whichCode > 95 && whichCode < 106){
			return true;
		}else{
			return false;
		}
	}
}

function numeroDecreto_onKeyUp(campo, event){
	var tecla = recoverKey(event);
	
	if (tecla != 0 && tecla != 9 && tecla != 16 && tecla != 37 && tecla != 39){
		var vr = removerMascara(campo.value);
		var tam = vr.length; 
		var retorno = '';
		var separador_unidade= '.';
		
		if ( tam <= 3 ){
			retorno = vr ;
		}
		
		if ((tam > 3) && (tam <= 6) ){
			retorno = vr.substr( 0, tam - 3 ) + separador_unidade + vr.substr( tam - 3, tam ) ;
		}
		
		if ( (tam >= 7) && (tam <= 9) ){
			retorno = vr.substr( 0, tam - 6 ) + separador_unidade + vr.substr( tam - 6, 3 ) + separador_unidade + vr.substr( tam - 3, tam );
		}
		
		if ( (tam >= 10) && (tam <= 12) ){
			retorno = vr.substr( 0, tam - 9 ) + separador_unidade + vr.substr( tam - 9, 3 ) + separador_unidade + vr.substr( tam - 6, 3 ) +
			separador_unidade + vr.substr( tam - 3, tam );
		}
		
		campo.value = retorno;
	}
}



function validateTextAreaLength(element, maxLength)
{
	var value = element.value;
	value = value.replace(/(\r\n)/gm, "    ");
	value = value.replace(/(\n|\r)/gm, "  ");
	var numCaracteres = maxLength - value.length;
	if(numCaracteres<0)
	{
		element.value = element.value.substring(0,element.value.length + numCaracteres);
		numCaracteres=0;
		return false;
	}
	
	return true;
}

$(document).ready(function(){
	$(".ui-icon-button").hover(
		function() {
			$( this ).addClass( "ui-state-hover" );
		},
		function() {
			$( this ).removeClass( "ui-state-hover" );
		}
	);
	$(".accordion .ui-accordion-header").addClass("ui-accordion-icons");
	$(".ui-messages ul li").addClass("clearfix").css({width:"76%"});
	
	$("#content").ajaxComplete(function(){
		$(".ui-messages ul li").addClass("clearfix").css({width:"80%"});
		$(".ui-messages ul").css({width:"90%"});
	});
});

$(document).ajaxComplete(function(){
	$(".ui-messages ul li").addClass("clearfix").css({width:"80%"});
	$(".ui-messages ul").css({width:"90%"});
});

function desabilitar(id, component, cfg){
	$('button[id="'+id+'"]').removeClass("ui-state-hover ui-state-focus ui-state-active").addClass("ui-state-disabled").attr("disabled", "disabled");
}

jsf.ajax.addOnEvent(function(data) {
	if (data.source.type != "submit") {
        return;
    }

    switch (data.status) {
        case "begin":
            data.source.disabled = true;
            break;
        case "complete":
            data.source.disabled = false;
            break;
    }   
});

function openPdfLink() {
	var link = document.getElementById('pdfReportLink');
	if (link.getAttribute('onclick') != null) {
		link.onclick();
	} else {
		document.location.href = link;
	}
}