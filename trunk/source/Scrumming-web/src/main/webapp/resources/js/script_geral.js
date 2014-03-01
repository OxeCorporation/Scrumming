/*Script Geral*/
$(document).ready(function(){
	// Accordion
	$(".accordion").accordion({ header: "h3",autoHeight: false, collapsible: true });
	$("td.semEspacamento .accordion.tabela").accordion({ header: "h3",autoHeight: true, collapsible: true, active: 1 });
	$(".calendario").datepicker({ showOn: "button", buttonImage: "images/calendar.png", buttonImageOnly: true });
	/* Brazilian initialisation for the jQuery UI date picker plugin. */
	/* Written by Leonildo Costa Silva (leocsilva@gmail.com). */
	jQuery(function($){
		$.datepicker.regional['pt-BR'] = {
			closeText: 'Fechar',
			prevText: '&#x3c;Anterior',
			nextText: 'Pr&oacute;ximo&#x3e;',
			currentText: 'Hoje',
			monthNames: ['Janeiro','Fevereiro','Mar&ccedil;o','Abril','Maio','Junho',
			'Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun',
			'Jul','Ago','Set','Out','Nov','Dez'],
			dayNames: ['Domingo','Segunda-feira','Ter&ccedil;a-feira','Quarta-feira','Quinta-feira','Sexta-feira','S&aacute;bado'],
			dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S&aacute;b'],
			dayNamesMin: ['Dom','Seg','Ter','Qua','Qui','Sex','S&aacute;b'],
			weekHeader: 'Sm',
			dateFormat: 'dd/mm/yy',
			firstDay: 0,
			isRTL: false,
			showMonthAfterYear: false,
			yearSuffix: ''};
		$.datepicker.setDefaults($.datepicker.regional['pt-BR']);
	});
	
	/*Modal de Consulta Perguntas*/
	$("#contenerPerguntas" ).dialog({
		resizable: false,
		width:800,
		autoOpen: false,
		modal: true,
		title:""
	});
	$( "#modalPerguntas" ).click(function() {
		$( "#contenerPerguntas" ).dialog( "open" );
		return false;
	});
	
	/*Mensagem de Sucesso*/
	var adrressBar = window.location.href;	
	var linkBack = adrressBar.split('?');
	tipoMensagem = linkBack[1];
	
	if(tipoMensagem == "confirmacaoInclusao"){
		$('.msg-sucesso').fadeIn('slow').prepend('<p><img src="images/confirmacao-sucesso.png" alt="Sucesso" />&nbsp;Inclusão realizada com Sucesso!</p>');
	}
	if(tipoMensagem == "confirmacaoAlteracao"){
		$('.msg-sucesso').fadeIn('slow').prepend('<p><img src="images/confirmacao-sucesso.png" alt="Sucesso" />&nbsp;Alteração realizada com Sucesso!</p>');
	}	
	if(tipoMensagem == "confirmacaoExclusao"){
		$('.msg-sucesso').fadeIn('slow').prepend('<p><img src="images/confirmacao-sucesso.png" alt="Sucesso" />&nbsp;Exclusão realizada com Sucesso!</p>');
	}	
	if(tipoMensagem == "confirmacaoFinalizacaoProva"){
		$('.msg-sucesso').fadeIn('slow').prepend('<p><img src="images/confirmacao-sucesso.png" alt="Sucesso" />&nbsp;Prova realizada com Sucesso!</p>');
	}
	
	/*Exemplo de mensagem de Erro*/
	$('#salvarInclusaoSelecao').submit(function(e){
		e.preventDefault();
		var campo1 = $('#proposito').val();
		var campo2 = $('#dataInicial').val();
		
		var msgErro = '<ul class="mensagem">';
		if(campo1 =='' || campo2 ==''){
			if(campo1 ==''){
				 msgErro +="<li class='ui-state-error clearfix'><p><img src='images/erro.png' alt='Erro' title='Erro' /></p><span>Campo Proposito é de preencimento.</span></li>";
				 $('#proposito').css('border', '1px solid #DF3C30').focus();
			}
			if(campo2 ==''){
				msgErro +="<li class='ui-state-error clearfix'><p><img src='images/erro.png' alt='Erro' title='Erro' /></p><span>Campo Data Inicial é de preencimento.</span></li>";
				 $('#dataInicial').css('border', '1px solid #DF3C30').focus();
			}
			msgErro +="</ul>";
			$(this).prepend(msgErro).fadeIn('slow');
			return false;
		}
		return true
	});
});