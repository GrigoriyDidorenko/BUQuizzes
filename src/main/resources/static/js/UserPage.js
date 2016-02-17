$(document).ready(function ($) {
    var getuser;
    jQuery.ajax({
        type: "GET",
        url: "/profile/currentUser",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            getuser = json;
            $('#name').append(getuser.firstName + " " + getuser.lastName);

//getting tests
            var myjson;
            jQuery.ajax({
                type: "GET",
                url: '/student/tests',
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (json) {
                    myjson = json;
                    $.each(myjson, function (index, myjs) {
                        if(myjs.testDTO.submited){
                            if(myjs.testDTO.checked){
                                $('.resultsTests').append('<tr><td class="resultUser">' + myjs.testDTO.testName + '</td><td class="resultUser">' +
                                    myjs.testDTO.mark + '</td><td class="resultUser"><i class="fa fa-check fa-1x">' + '</i></td></tr>');
                            }else {
                                $('.resultsTests').append('<tr><td class="resultUser">' + myjs.testDTO.testName + '</td><td class="resultUser">' +
                                    myjs.testDTO.mark + '</td><td class="resultUser"><i class="fa fa-times fa-1x">' + '</i></td></tr>');
                            }
                        }else{
                            $('.avaliableTests').append('<tr><td class="resultUser">' + myjs.testDTO.testName + '</td><td class="resultUser">' +
                                myjs.testDTO.duration + " хв" + '</td><td class="resultUser"><a href="TestPage.html?resultId='+myjs.resultId+'"><button class="start-test-btn">' +
                                "розпочати тест" + '</button></a></td></tr>');
                        }
                    })
                }
            })
        }
    })
});