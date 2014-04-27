$( "#slider" ).slider({
    value: 0,
    min: 0,
    max: 9,
    //range: "min",
    step: 1
})
.each(function() {
  
  var valorOption = $("#slider").slider("value");
  var valor = 0;
  var valorString;
  if(valorOption == 0){
	  valor= 0;
	  valorString= valor.toString();
	  $("#outValueSP").value= valorString;
	  //document.setElementById("myForm:totalCount").value=total;
	  
  }	else if(valorOption == 1){
	  valor= 1;
	  valorString= valor.toString();
	  $("#outValueSP").value= valorString;
	  
  }	else if(valorOption == 2){
	  valor= 2;
	  valorString= valor.toString();
	  $("#outValueSP").val(valorString);
	  
  }	else if(valorOption == 3){
	  valor= 1;
	  valorString= valor.toString();
	  $("#outValueSP").val(valorString);
	  
  }	else if(valorOption == 4){
	  valor= 5;
	  valorString= valor.toString();
	  $("#outValueSP").val(valorString);
	  
  }	else if(valorOption == 5){
	  valor= 8;
	  valorString= valor.toString();
	  $("#outValueSP").val(valorString);
	  
  }	else if(valorOption == 6){
	  valor= 13;
	  valorString= valor.toString();
	  $("#outValueSP").val(valorString);
	  
  }	else if(valorOption == 7){
	  valor= 20;
	  valorString= valor.toString();
	  $("#outValueSP").val(valorString);
	  
  }	else if(valorOption == 8){
	  valor= 40;
	  valorString= valor.toString();
	  $("#outValueSP").val(valorString);
	  
  } else if(valorOption == 9){
	  valor= 100;
	  valorString= valor.toString();
	  $("#outValueSP").val(valorString);
	  
  }
  
});
