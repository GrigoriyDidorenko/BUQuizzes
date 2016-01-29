/**
 * Created by rondo104 on 23.01.2016.
 */

$(document).ready(function() {
    jQuery.ajax({
        type: "GET",
        url: "/trainer/getAllСategoryTestName",
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            creatTestsTable(JSON);
        },
        error: function (http) {
            alert(http.responseText);
        }
    });


    $('.collapsible').collapsible({
        accordion: false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });


});

function creatTestsTable(JSON){
    $.each( answerDiv.querySelectorAll("input"), function( indexI, test ) {
        addOneTest(test, indexI);
    });
}

function addOneTest(test, indexI){
    var countTest  = indexI;
    ++countTest;
    var childLI = document.createElement('li');
    childLI.id = 'test-' + countTest;
    var childDiv = document.createElement('div');
    childDiv.setAttribute("class", "collapsible-header");


    var childButton = document.createElement('button');
    childButton.id = childLI.id + "_d";
    childButton.appendChild(document.createTextNode("Перевірити"));
    childButton.addEventListener("click", function(){
        //ToDO unique identification
        window.location = "auditOpenQuestions.html?" +  questionID;
    });

    childDiv.appendChild(childButton);
    childLI.appendChild(childDiv);
    document.getElementById('tests').appendChild(childLI);
}