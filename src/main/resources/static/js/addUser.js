/**
 * Created by c2413 on 10.12.2015.
 */
//getting results
jQuery.ajax({
    type: "GET",
    url: "http://localhost:8080/superAdmin/addUser",
    contentType: 'application/json; charset=utf-8',
    success: function (jsonrols) {
        var rols;
        rols = jsonrols;
        $.each(rols, function (index, rolsone) {
            $('#roles').append('<option>' + rolsone + '</option>')
        })
    }
});
//*getting results
