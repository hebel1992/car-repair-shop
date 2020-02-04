$(function () {
    let addButton = $("#add-part");
    let counter = 1;
    addButton.on('click', function (event) {
        event.preventDefault();
        if($(".nextPartInput").length<10){
            let nextPart = $("<p class='nextPartInput'>Name: <input name='partName'> Price: " +
                "<input name='partPrice' type='number' step='0.01'> Quantity: <input name='partQuantity' type='number' step='1'> <button id='add-part' class='minus'>-</button></p>")
            $("#last").before(nextPart);
        }
    })

    let parts = $("#parts")

    parts.on('click', 'button.minus', function (event) {
        event.preventDefault();
        $(this).parent('p').remove();
    })
})