






$(document).on("change paste keyup",'#nalezeno', function () {
   // alert($(this).val());

    $.ajax({
        type: "GET",
        minLength:2,
        contentType: "application/json",
        url: "/NaseptavacProdukty",
        data:{nalezeno:$(this).val()},
        dataType: "html",
        cache: false,
        timeout: 600000,


        success: function (data) {
          //  alert('Uspech'+data);
           console.log(data);

         //   var el = $('#vysledky');
          //  el.empty();
var output =document.getElementById('vysledky');
output.innerHTML=data;



          //  el.append('<h3>' +data.val() + '</h3>');
       //      $( "#vysledky" ).val(data)
        //  el.autocomplete({source: data});
          /*  $( "#nalezenoa" ).autocomplete({
                source: data
            });*/

        },
        error: function (xhr, ajaxOptions, thrownError) {
          //  alert(xhr.status);
         //   alert(thrownError);
               }
    });
});

/*
function getProdukty(data) {
    let pokus =data;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/NaseptavacProdukty",
        dataType: 'json',
        cache: false,
        timeout: 600000,


        success: function (data) {
            $( "#a" ).autocomplete({
                source: data
            });

        },
        error: function (e) {
           // alert(e);
        }
    });
}*/
