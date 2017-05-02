/**
 * Created by Lemm on 05.06.14.
 */
$(document).ready(function(){

    $('.phone').each(function(){
        var phone = $(this);

        phone.mask('(999)999-9999');
    });


});