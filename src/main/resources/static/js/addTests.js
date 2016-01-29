/**
 * Created by rondo104 on 14.01.2016.
 */

$(document).ready(function () {
    addQuestion();
    //$('#newCategory').hide();
    //ToDo
    //$("#addCategory").click(function () {
    //    $('#newCategory').show();
    //});
    //Add new Question
    $("#addQuestion").click(function () {
        addQuestion();
    });
    $('#importTest').click(function () {
        importTest();
    });

    //ToDo ADD category TestName
    jQuery.ajax({
        type: "GET",
        url: "/trainer/getAllСategoryTestName",
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            var rols = json;
            $.each(rols, function (index, rolsone) {
                $('#selectCategoryTestName').append('<option>'+rolsone+'</option>');
            });
        },
        error: function (http) {
            return http.responseText;
        }
    })

    $('.collapsible').collapsible({
        accordion: false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });


});

function addQuestion() {
    var listQuestion = document.getElementById('questions');
    var Li = listQuestion.getElementsByTagName('li');
    var countQuestion = 0;
    if  (Li.length != 0) {
        countQuestion = Li[Li.length - 1].id.split("-")[1];
    }
   // var listQuestion = document.getElementById('questions');
   // var countQuestion = listQuestion.getElementsByTagName('li').length-1;
    var childLI = document.createElement('li');
    childLI.id = 'question-' + ++countQuestion;
    var childDiv = document.createElement('div');
    childDiv.setAttribute("class", "collapsible-header");
    var childInp = document.createElement('input');
    childInp.type = "text";
    childInp.placeholder = "Question";
    var childDelButton = document.createElement('i');
    childDelButton.id = childLI.id + "_d";
    childDelButton.setAttribute("class", "fa fa-times closeicon");
    childDelButton.addEventListener("click", function(){
        deleteQuestion(childLI.id);
    });
    childDiv.appendChild(childInp);
    childDiv.appendChild(childDelButton);
    childLI.appendChild(childDiv);
    document.getElementById('questions').appendChild(childLI);
    addAnswer(childLI.id);
}

function deleteQuestion(idQuestion) {
    var delEle = document.getElementById(idQuestion);
    document.getElementById('questions').removeChild(delEle);
}

function addAnswer(questionId) {
    var listAnswer = document.getElementById(questionId);
    var countAnswer = listAnswer.querySelectorAll('div [class="collapsible-body"]').length;
    var childDivAnswer = document.createElement('div');
    childDivAnswer.setAttribute("class", "collapsible-body");
    ++countAnswer;
    childDivAnswer.id = questionId + "_answer" + countAnswer;
    //inp field
    var childInp1 = document.createElement('input');
    childInp1.id = questionId + "_a" + countAnswer;
    childInp1.type = "text";
    childInp1.placeholder = "Answer";
    var childInp2 = document.createElement('input');
    childInp2.id = questionId + "_m" + countAnswer;
    childInp2.type = "number";
    childInp2.placeholder = "mark";
    //button
    var childDelButton = document.createElement('i');
    //Add id
    childDelButton.setAttribute("class", "fa fa-times fa-1x closeicon");
    childDelButton.id = questionId + "_delAnswer" + countAnswer;
    childDelButton.type = "button";
    childDelButton.addEventListener("click", function () {
        removeAnswer(childDivAnswer.id)
    });
    var childAddButton = document.createElement('i');
    childAddButton.setAttribute("class", "fa fa-plus fa-1x yesicon");
    childAddButton.id = questionId + "_addAnswer" + countAnswer;
    childAddButton.type = "button";
    childAddButton.addEventListener("click", function () {
        addAnswer(questionId)
    });
    childDivAnswer.appendChild(childInp1);
    childDivAnswer.appendChild(childInp2);
    childDivAnswer.appendChild(childDelButton);
    childDivAnswer.appendChild(childAddButton);
    document.getElementById(questionId).appendChild(childDivAnswer);
    $('.collapsible').collapsible(document.getElementById(questionId));
}

function removeAnswer(question_answer){
    var del = question_answer.split("_");
    var delEle = document.getElementById(question_answer);
    document.getElementById(del[0]).removeChild(delEle);
}
function importTest() {
    var value=$.trim($("#categoryTestName").val());

    if(value.length>0)
    {
        var categoryTestName= $('#categoryTestName').val();
        console.log(categoryTestName)
    }
    else {
        var categoryTestName= $('#selectCategoryTestName').val();
        console.log(categoryTestName)
    }
    var testName = $('#testName').val();
    var duration = $('#duration').val();
    var oneTime=$('#oneTime').prop("checked");
    var questions = [];
    $.each( $('#questions li') , function( indexQ, questionLi ) {
      var questionD;
      var answers =[];
      var question;
        $.each( questionLi.querySelectorAll("div"), function( indexA, answerDiv ) {
            if (answerDiv.className == 'collapsible-header' || answerDiv.className == 'collapsible-header active' ){
                questionD = answerDiv.querySelector('input').value;
            }else {
                var answer;
                var answerText;
                var mark;
                $.each( answerDiv.querySelectorAll("input"), function( indexI, answerInput ) {
                    if(answerInput.id == questionLi.id + "_a" + indexA ) {
                        answerText = answerInput.value ;
                    }
                    if (answerInput.id == questionLi.id + "_m" + indexA){
                        mark= answerInput.value;
                    }
                });
            answer = new Answer(answerText, mark);
            answers.push(answer);
            }
            question = new Question(questionD, answers);
        });
         questions.push(question);
    });
    var test = new Test(testName, duration, oneTime, categoryTestName, questions);
    console.log(test);
    var json = JSON.stringify(test);
    console.log(json);
    jQuery.ajax({
        url: "/trainer/addNewTest",
        type: "POST",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: json,
        success: function (json) {
           //alert(json);
        },
        error: function (http) {
            $('#exeption').empty();
            $('#exeption').append(http.responseText);
            return http.responseText;
        }
    })
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

function Answer(answerText, mark) {
    this.answerText = answerText;
    this.mark = mark;
}

function getRequest(urll, data) {
    jQuery.ajax({
        type: "GET",
        url: urll,
        data:data,
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
