$(document).ready(function () {
	//alert("aaa");
	//
	var id_index = 0;
	var input_count=0;
	var output_count=0;
	var text_count=0;
	var integer_count=0;
	var select_count=0;
	var fixed_count=0;

	$("#add").on('click',function(){
		id_index++;
		

		var selectitem=$("#item").val();
		$("#one").append($('<fieldset id="fieldset_'+id_index+'" ><legend>iteam</legend></fieldset>'));
		$("#fieldset_"+id_index).append($('<div id="div_'+id_index+'" ></div>'));



		if(selectitem==0){
			//input
			input_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Input:";
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">IO:</label>'));
			
			//$("#div_"+id_index).append($('<select name="io" id="io"><option value="0" selected>input</option><option value="1">output</option></select><br />'));
			var pp = $('<select name="io" id="io"><option value="0" selected>input</option><option value="1">output</option></select><br />');
			
			pp.on('change', function(){
				opchange(pp);

			});
			$("#div_"+id_index).append(pp);
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="input_label_'+input_count+'" id="input_label_'+input_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of io<br />'));
			
			
			//var p = $('<input class="btn btn-info btn-xs" style="width:100px;" type="button" value="delete"><br />');
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		}
		
		/*else if(selectitem==1){
			//output
			output_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Output:";
			
			//$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Id:</label>'));
			//$("#div_"+id_index).append($('<input type="text" name="output_id_'+output_count+'" id="output_id_'+output_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//Id of output<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="output_label_'+output_count+'" id="output_label_'+output_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of output<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		}*/
		
		else if(selectitem==2){
			//text
			text_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Text:";
			
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="text_label_'+text_count+'" id="text_label_'+text_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of text<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		}else if(selectitem==3){
			//integer
			integer_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Integer:";
			
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="integer_label_'+integer_count+'" id="integer_label_'+integer_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of integer<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Size: </label>'));
			$("#div_"+id_index).append($('<input type="text" name="integer_size_'+integer_count+'" id="integer_size_'+integer_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//width of integer<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);


		}else if(selectitem==4){
			//select
			select_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Select:";
			
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="select_label_'+select_count+'" id="select_label_'+select_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of select<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Values:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="select_value_'+select_count+'" id="select_value_'+select_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//values of select,separate multiple values with a semicolon<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Texts: </label>'));
			$("#div_"+id_index).append($('<input type="text" name="select_text_'+select_count+'" id="select_text_'+select_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//texts of select,separate multiple texts with a semicolon<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		}else if(selectitem==5){
			//fixed
			fixed_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Fixed:";
			
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Value: </label>'));
			$("#div_"+id_index).append($('<input type="text" name="fixed_value_'+fixed_count+'" id="fixed_value_'+fixed_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//value of fixed<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);
			
		}
	
	});

	function delFunc(a) {
		a.parent().parent().remove();
	}
	function opchange(a) {
		var iosle=a.val();
		if(iosle==0){
			a.parent().parent().find("legend")[0].innerHTML="Input:";
		}else{
			a.parent().parent().find("legend")[0].innerHTML="Output:";
		}
		
	}
	
	
});