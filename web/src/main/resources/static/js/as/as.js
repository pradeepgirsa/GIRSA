function lepoCheckboxChange() {
    $('#checkMe1').on('change', function() {
                if(this.checked) {
                    $('#equity').slideDown(400);
                } else {
                    $('#equity').slideUp(400);
                    $('#localEquity').val(0.0);
                    $('#foreignEquity').val(0.0);
                }
            });
            $('#checkMe2').click(function() {
                if(this.checked) {
                    $('#property').slideDown(400);
                } else {
                    $('#property').slideUp(400);
                    $('#localProperty').val(0.0);
                    $('#foreignProperty').val(0.0);
                }
            });
            $('#checkMe3').click(function() {
                if(this.checked) {
                    $('#bonds').slideDown(400);
                } else {
                    $('#bonds').slideUp(400);
                    $('#localBonds').val(0.0);
                    $('#foreignBonds').val(0.0);
                }
            });
            $('#checkMe4').click(function() {
                if(this.checked) {
                    $('#cash').slideDown(400);
                } else {
                    $('#cash').slideUp(400);
                    $('#localCash').val(0.0);
                    $('#foreignCash').val(0.0);
                }
            });
            $('#checkMe5').click(function() {
                if(this.checked) {
                    $('#hedgeTop').slideDown(400);
                } else {
                    $('#hedgeTop').slideUp(400);
                    $('#hedge').val(0.0);
                }
            });
            $('#checkMe6').click(function() {
                if(this.checked) {
                    $('#totalOtherTop').slideDown(400);
                } else {
                    $('#totalOtherTop').slideUp(400);
                    $('#totalOther').val(0.0);

                }
            });
}


function lepoCheckOpen() {

    if($('#localEquity').val() > 0.0 || $('#foreignEquity').val() > 0.0){
                $('#checkMe1').prop('checked', true);
                $('#equity').slideDown(50);
            } else {
                $('#checkMe1').prop('checked', false);
            }

            if($('#localProperty').val() > 0.0 || $('#foreignProperty').val() > 0.0){
                $('#checkMe2').prop('checked', true);
                $('#property').slideDown(50);
            } else {
                $('#checkMe2').prop('checked', false);
            }

            if($('#foreignBonds').val() > 0.0 || $('#localBonds').val() > 0.0){
                $('#checkMe3').prop('checked', true);
                $('#bonds').slideDown(50);
            } else {
                $('#checkMe3').prop('checked', false);
            }

            if($('#localCash').val() > 0.0 || $('#foreignCash').val() > 0.0){
                $('#checkMe4').prop('checked', true);
                $('#cash').slideDown(50);
            } else {
                $('#checkMe4').prop('checked', false);
            }

            if($('#hedge').val() > 0.0){
                $('#checkMe5').prop('checked', true);
                $('#hedgeTop').slideDown(50);
            } else {
                $('#checkMe5').prop('checked', false);
            }

            if($('#totalOther').val() > 0.0){
                $('#checkMe6').prop('checked', true);
                $('#totalOtherTop').slideDown(50);
            } else {
                $('#checkMe6').prop('checked', false);
            }

}

function lepoTotals() {

            $('#save-fund').css('background-color', '#9e9e9e');
            $('#save-fund').attr('disabled', true);
            $('#convertToPercent').css('display', 'none');

            $("input").change(function(){

                var total = 0;

                total += parseFloat($('#localEquity').val());
                total += parseFloat($('#localProperty').val());
                total += parseFloat($('#localBonds').val());
                total += parseFloat($('#localCash').val());
                total += parseFloat($('#foreignEquity').val());
                total += parseFloat($('#foreignProperty').val());
                total += parseFloat($('#foreignBonds').val());
                total += parseFloat($('#foreignCash').val());
                total += parseFloat($('#hedge').val());
                total += parseFloat($('#totalOther').val());

                toString($('#total').val(total.toFixed(10)));

                if(total != 100) {
                    $('#save-fund').css('background-color', '#9e9e9e');
                    $('#save-fund').attr('disabled', true);
                } else {
                    $('#save-fund').css('background-color', '#357ca5');
                    $('#save-fund').removeAttr('disabled');
                }

                if(total === 1.000) {
                    $('#convertToPercent').css('display', 'inline-block');
                } else {
                    $('#convertToPercent').css('display', 'none');
                }

            });

            $('#convertToPercent').click(function() {

                $('#localEquity').val($('#localEquity').val() * 100);
                $('#localProperty').val($('#localProperty').val() * 100);
                $('#localBonds').val($('#localBonds').val() * 100);
                $('#localCash').val($('#localCash').val() * 100);
                $('#foreignEquity').val($('#foreignEquity').val() * 100);
                $('#foreignProperty').val($('#foreignProperty').val() * 100);
                $('#foreignBonds').val($('#foreignBonds').val() * 100);
                $('#foreignCash').val($('#foreignCash').val() * 100);
                $('#hedge').val($('#hedge').val() * 100);
                $('#totalOther').val($('#totalOther').val() * 100);

                var total = 0;

                total += parseFloat($('#localEquity').val());
                total += parseFloat($('#localProperty').val());
                total += parseFloat($('#localBonds').val());
                total += parseFloat($('#localCash').val());
                total += parseFloat($('#foreignEquity').val());
                total += parseFloat($('#foreignProperty').val());
                total += parseFloat($('#foreignBonds').val());
                total += parseFloat($('#foreignCash').val());
                total += parseFloat($('#hedge').val());
                total += parseFloat($('#totalOther').val());

                toString($('#total').val(total.toFixed(10)));

                $('#save-fund').css('background-color', '#357ca5');
                $('#save-fund').removeAttr('disabled');
                $('#convertToPercent').css('display', 'none');

            });

}