/**
 * Created by rondo104 on 23.01.2016.
 */

$(document).ready(function() {
    var testss = [];
    jQuery.ajax({
        type: "GET",
        url: "/trainer/uncheckedTests",
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            $.each( json, function( indexI, test ) {
                var obj = new tests(test[0],test[1],test[2],test[3])
                testss.push(obj);
            });
            creatTestsTable(testss);
        },
        error: function (http) {
            alert(http.responseText);
        }
    });


    $('.collapsible').collapsible({
        accordion: false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });

});

function creatTestsTable(testss){
    var flag;
    var index;
    var testNames = [];
    $.each( testss, function( indexI, test ) {
        index = -1;
        $.each( testNames, function( indexII, testName ) {
          if (test.testname == testName)  {
              index = indexII;
              flag=false;
          }
        });
        if (index == -1){
            testNames.push(test.testname);
            index = testNames.length-1;
            flag = true;
        }
        addOneTest(test, index,flag);
    });
}

function addOneTest(test, indexI,flag){
    var index=indexI+1;
    var childLI;
    if (flag) {
    childLI = document.createElement('li');
    childLI.id = 'test-' + index;
    var childDiv = document.createElement('div');
    childDiv.setAttribute("class", "collapsible-header");
    childDiv.appendChild(document.createTextNode(test.testname));
    childLI.appendChild(childDiv);
    } else {
        childLI = document.getElementById('test-' + index );
    }
    var childDiv2 = document.createElement('div');
        childDiv2.setAttribute("class", "collapsible-body");
        childDiv2.appendChild(document.createTextNode(test.question));
    var childButton = document.createElement('button');
    childButton.id = childLI.id + "_d";
    childButton.appendChild(document.createTextNode("Перевірити"));
    childButton.addEventListener("click", function(){
        window.location = "auditOpenQuestions.html?" + "question=" + test.questionId;
    });
    childDiv2.appendChild(childButton);
    childDiv2.appendChild(document.createTextNode(test.numberOfUncheckedAnswers));
    childLI.appendChild(childDiv2);
    document.getElementById('tests').appendChild(childLI);
    $('.collapsible').collapsible(document.getElementById('tests'));
}

function tests(testname, questionId, question, numberOfUncheckedAnswers){
    this.testname = testname;
    this.questionId = questionId;
    this.question = question;
    this.numberOfUncheckedAnswers = numberOfUncheckedAnswers;
}