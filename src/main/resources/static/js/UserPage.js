$(document).ready(function ($) {
    var getuser;
    jQuery.ajax({
        type: "GET",
        url: "http://localhost:8080/profile/currentUser",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            getuser = json;
                $('#name').append(getuser.firstName + " " + getuser.lastName);

//getting tests
    var userId=getuser.id;
    var myjson;
    jQuery.ajax({
        type: "GET",
        url: 'http://localhost:8080/student/tests/'+userId,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            myjson = json;
            var mymark;
            $.each(myjson, function (index, myjs) {
                if(myjs.resultId=='0'){
                    if(myjs.testDTO.checked){
                        $('.resultsTests').append('<tr><td>' + myjs.testDTO.testName + '</td><td>' +
                            myjs.testDTO.mark + '</td><td><i class="fa fa-check fa-1x">' + '</i></td></tr>');
                    }else {
                        $('.resultsTests').append('<tr><td>' + myjs.testDTO.testName + '</td><td>' +
                            myjs.testDTO.mark + '</td><td><i class="fa fa-times fa-1x">' + '</i></td></tr>');
                    }
                }else{
                    $('.avaliableTests').append('<tr><td>' + myjs.testDTO.testName + '</td><td>' +
                        myjs.testDTO.duration + " хв" + '</td><td><a href="TestPage.html?resultId='+myjs.resultId+'&user='+userId+'"><button class="start-test-btn">' +
                        "розпочати тест" + '</button></a></td></tr>');
                }
            })
        }
    })
        }
    })
});