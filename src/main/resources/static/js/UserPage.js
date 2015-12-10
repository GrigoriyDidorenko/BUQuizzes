$(document).ready(function ($) {
//getting tests
    var myjson;
    jQuery.ajax({
        type: "GET",
        url: "http://localhost:8080/student/tests/1",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            myjson = json;
            $.each(myjson, function (index, myjs) {
                if(myjs.resultId=='0'){
                    $('.resultsTests').append('<tr><td>' + myjs.testDTO.testName + '</td><td>' +
                        myjs.testDTO.duration + " хв" + '</td></tr>')
                }else{
                    $('.avaliableTests').append('<tr><td>' + myjs.testDTO.testName + '</td><td>' +
                        myjs.testDTO.duration + " хв" + '</td><td><a href="TestPage.html?resultId='+myjs.resultId+'"><button class="start-test-btn">' +
                        "розпочати тест" + '</button></a></td></tr>')
                }
            })
        }
    })
});