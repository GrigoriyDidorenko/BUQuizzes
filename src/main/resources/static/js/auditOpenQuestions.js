/**
 * Created by rondo on 29.01.2016.
 */
$(document).ready(function() {

    $("#question").text(urldecode(GetURLParameter('question')));

    //var URL = "/trainer/uncheckedTests/" + GetURLParameter('questionId');
    //jQuery.ajax({
    //    type: "GET",
    //    url: "/trainer/uncheckedTests",
    //    dataType: "json",
    //    async: false,
    //    contentType: "application/json; charset=utf-8",
    //    success: function (json) {
    //        $.each( json, function( indexI, test ) {
    //            var obj = new tests(test[0],test[1],test[2],test[3])
    //            testss.push(obj);
    //        });
    //        creatTestsTable(testss);
    //    },
    //    error: function (http) {
    //        alert(http.responseText);
    //    }
    //});

    $( "#next, #preview" ).click(function() {
        setMark();
        //TO DO next answer
    });
    $( "#exit" ).click(function() {
        window.location = "allOpenQuestions.html"
    });

});


function setMark(){
    alert( "Handler for .click() called." );
}

function GetURLParameter(sParam) {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++)
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam)
        {
            return sParameterName[1];
        }
    }
}

function tests(testname, questionId, question, numberOfUncheckedAnswers){
    this.testname = testname;
    this.questionId = questionId;
    this.question = question;
    this.numberOfUncheckedAnswers = numberOfUncheckedAnswers;
}

function urldecode(str) {
    return decodeURIComponent((str+'').replace(/\+/g, '%20'));
}