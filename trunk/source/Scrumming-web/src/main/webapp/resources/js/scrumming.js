function fib(n) {
        if (n == 0) {
            return n = 0;
        }else if(n == 1){
        	return n = 1;
        }else if(n == 2){
        	return n = 2;
        }else if(n == 3){
        	return n = 3;
        }else if(n == 4){
        	return n = 5;
        }else if(n == 5){
        	return n = 8;
        }else if(n == 6){
        	return n = 13;
        }else if(n == 7){
        	return n = 20;
        }else if(n == 8){
        	return n = 40;
        }else if(n == 9){
        	return n = 100;
        }
}
$(function(){
    $("#slider").slider({
        value: 0,
        min: 0,
        max: 9,
        change: function(event,ui){
             $("#valor").html(fib(ui.value));        
        }
    });       
});
