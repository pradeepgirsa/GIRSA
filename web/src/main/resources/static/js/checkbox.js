$(':checkbox').change(function() {

	var hiddenField = $("#hiddenField");
	var delimiter = "_;_";
	var n = $("#hiddenField").val().length;

	if($(this).prop('checked') == true){

		if(n == 0 ) {
			hiddenField.val($(this).val()  );
			//alert("The hiddenfield value is: " + hiddenField.val() );
		} else if (n > 0) {
			hiddenField.val(hiddenField.val() + delimiter + $(this).val() );
			//alert("The hiddenfield value is: " + hiddenField.val() );
		}

	} else {

		if (hiddenField.val().includes(delimiter + $(this).val()) ){
			hiddenField.val(hiddenField.val().replace(delimiter + $(this).val(), ""));
			//alert("The hiddenfield value is: " + hiddenField.val() );
		} 	else if (hiddenField.val().includes($(this).val() + delimiter)){
			hiddenField.val(hiddenField.val().replace($(this).val() + delimiter, ""));
			//alert("The hiddenfield value is: " + hiddenField.val() );
		}	else if (hiddenField.val().includes($(this).val() ) ){
			hiddenField.val(hiddenField.val().replace($(this).val(), ""));
			//alert("The hiddenfield value is: " + hiddenField.val() );
		}
	}

});
