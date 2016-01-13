/**
 * Created by BrightestSirius on 10.12.2015.
 */
$(document).ready(function ($) {
//getting tests
    var myjs;
    jQuery.ajax({
        type: "GET",
        url: "/trainer/testToUser",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            myjs = json;
                $.each(myjs.tests, function (index, mytests) {
                    $.each(mytests, function (index, mytestsinner) {
                        $('#addTest').append(mytestsinner)
                    })
                })
            $.each(myjs.users, function (index, mytests) {
                $.each(mytests, function (index, mytestsinner) {
                    $('#addUser').append(mytestsinner)
                })
            })
        }
    })
});