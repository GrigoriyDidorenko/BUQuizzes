/**
 * Created by c2413 on 09.02.2016.
 */
$(document).ready(function() {
    jQuery.ajax({
        type: "GET",
        url: '/guest/oneTimeTests',
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            var rols = json;
            var mas = [];
            $('select').material_select(
                $.each(rols, function (index, rolsone) {
                    var uniqu = true;
                    for (var i = 0; i < mas.length; i++) {
                        if (mas[i] == rolsone.categoryTestName) {
                            uniqu = false;
                        }
                    }
                    if (uniqu) {
                        mas.push(rolsone.categoryTestName)
                        $('select').append('<option value="' + rolsone.categoryTestName + '">' + rolsone.categoryTestName + '</option>')
                    }
                }))

            $( "select" ).change(function() {
                $("#testTable").empty();
                var str = "";
                $("#roles option:selected").each(function () {
                    str += $(this).text() + " ";
                });
                $.each(rols, function (index, rolsone) {
                    if ($.trim(str) == rolsone.categoryTestName) {

                        $("#testTable").append("<tr><td>" + rolsone.testName + "</td><td>" + rolsone.duration + " хв" + "</td><td><input class='submit save-btn-test test' type='submit' value='Редагувати' id='"+rolsone.id+"'></td></tr>");
                        //TODO
                        $(".test").click(function () {
                            var id=this.id;
                            window.location = 'http://localhost:8080/pages/editTest.html?test='+id+'';

                        });
                    }
                });

            });
        }
    });
});
