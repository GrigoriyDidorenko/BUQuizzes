/**
 * Created by c2413 on 09.02.2016.
 */
$(document).ready(function() {
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
                    $('#avaliableTests').append('<tr><td>' + myjs.testName + '</td><td>' +
                        myjs.duration + " хв" + '</td><td><a href=""><button class="start-test-btn">' +
                        "розпочати тест" + '</button></a></td></tr>');
            })
        }
    });
});
