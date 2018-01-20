

$( document ).ready(function() {
    console.log( "document loaded" );
    $( "#target" ).click(function() {
        alert( "Handler for .click() called." );
    });
});

$( window ).on( "load", function() {
    console.log( "window loaded" );
});