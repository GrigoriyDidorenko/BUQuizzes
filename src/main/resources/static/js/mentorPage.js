/**
 * Created by c2413 on 09.02.2016.
 */
$(document).ready(function() {
    $('#check-open').hide();
    var getuser;
    jQuery.ajax({
        type: "GET",
        url: "/profile/currentUser",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            getuser = json;
            $('#name').append(getuser.firstName + " " + getuser.lastName);
        }
    });
    jQuery.ajax({
        type: "GET",
        url: '/trainer/availableTestsNames',
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            myjson = json;
            $.each(myjson, function (index, myjs) {
                    $('#avaliableTests').append('<tr style="padding: 5px 0 5px 0"><td>' + myjs.testName + '</td><td>' +
                        myjs.duration + " хв" + '</td><td><a href="../pages/editTest.html?id='+myjs.id+'"><i class="fa fa-pencil-square-o"></i></a></td></tr>');
            })
        }
    });
    jQuery.ajax({
        type: "GET",
        url: "/trainer/uncheckedTests",
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            mtjs=json;
            $.each( mtjs, function( index, test ) {
                console.log(test[0]);
                if(test[0]){
                    $('#check-open').show();
                }
            });
        },
        error: function (http) {
            alert(http.responseText);
        }
    });
});
