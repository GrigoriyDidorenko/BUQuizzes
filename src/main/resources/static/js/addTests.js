/**
 * Created by rondo104 on 14.01.2016.
 */

$(document).ready(function () {


    $('#categoryTestName').hide();
    //ToDo
    //Add categoryTestName
    $("#buttonCategoryTestName").click(function(){
        $('#buttonCategoryTestName').hide();
        $('#selectCategoryTestName').hide();
        $('#categoryTestName').show();
    });
    //Add new Question
    $("#addQuestion").click(function(){
        $('#selectCategoryTestName').append(addQuestion());
    });
    var urllForGetTestId = "/guest/getAvaliableOneTestCategoryName/";
    var JSON = getJSON(urllForGetTestId);
    $.each(JSON, function (index, categoryTestName) {
        $('#selectCategoryTestName').append('<option>categoryTestName</option>');
    });

    $('.collapsible').collapsible({
        accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });


});


function addQuestion(){
    var listQuestion = document.getElementById('questions');
    var countQuestion = listQuestion.getElementsByTagName("LI").length;
    var childLI = document.createElement('li');
        childLI.id = 'question' + ++countQuestion;
    var childDiv = document.createElement('div');
        childDiv.class = "collapsible-header";
    var childInp = document.createElement('input');
        childInp.type = "email";
        childInp.value = "Нове запитання";
    var childDelButton = document.createElement('button');
        childDelButton.id =  childLI.id + "_d";
        childDelButton.appendChild(document.createTextNode("del"));
        childDelButton.addEventListener("click", deleteQuestion);
    childDiv.appendChild(childInp);
    childDiv.appendChild(childDelButton);
    childLI.appendChild(childDiv);
    document.getElementById('questions').appendChild(childLI);
}


function deleteQuestion(){
    var idQuestion=this.id.slice(0,this.id.length-2);
    var delEle = document.getElementById(idQuestion);
    document.getElementById('questions').removeChild(delEle);
}

function Test(testName, duration, oneTime, categoryTestName, questions) {
    this.testName = testName;
    this.duration = duration;
    this.oneTime = oneTime;
    this.categoryTestName = categoryTestName;
    this.questions = questions;
}

function Question(question, answers) {
    this.question = question;
    this.answers = answers;
}

function Answers(answerText, mark) {
    this.answerText = answerText;
    this.mark = mark;
}

function getJSON(urll) {
    jQuery.ajax({
        type: "GET",
        url: urll,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            return json;
        },
        error: function (http) {
            return http.responseText;
        }
    })
}