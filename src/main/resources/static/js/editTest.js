/**
 * Created by c2413 on 29.01.2016.
 */
$(document).ready(function () {
    //get Test
    jQuery.ajax({
        type: "GET",
        url:"http://localhost:8080/guest/tests/1?email=atia29@mail.ru&nickName=katya&name=kate",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            var myJson=json;
            $('#testName').val(myJson.testName);
            $('#duration').val(myJson.duration);
            var oneTime = true;
            if(oneTime){
                $('#oneTime').prop('checked', true);
            }
            else{
                $('#oneTime').prop("checked", false);
            }
            var categoryTestName = "java";
            $('#selectCategoryTestName').append('<option disabled selected id="selected">'+categoryTestName+'</option>');

                $.each(myJson.questions, function (index, quest) {
                    var mu = ''+(index+1)+'';
                    var my = 'question-'+(index+1)+'';
                    $('.collapsible').append('<li id="question-'+(index+1)+'" class="question"><div class="collapsible-header">'+
                        '<input type="text" id="questioninput-'+(index+1)+'" placeholder="Question" name="questioninput" style="width: 85%" value="'+quest.question+'">'+
                        '<i id="question-'+(index+1)+'_d" class="fa fa-times closeicon"></i></div></li>');
                    $.each(quest.answers, function (index, answer) {
                        $('#'+my+'').append('<div style="display: block" class="collapsible-body" id="question-'+mu+'_answer'+(index+1)+'">'+
                            '<input id="question-'+mu+'_a'+(index+1)+'" type="text" placeholder="Answer" value="'+answer.answerText+'">'+
                            '<input id="question-'+mu+'_m'+(index+1)+'" type="number" class="mark-question-'+mu+'" placeholder="mark">'+
                            '<i class="fa fa-times fa-1x closeicon" id="question-'+mu+'_delAnswer'+(index+1)+'"></i>'+
                            '<i class="fa fa-plus fa-1x yesicon" id="question-'+mu+'_addAnswer'+(index+1)+'"></i></div>');
                    });
                $('.collapsible').collapsible({
                    accordion: false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
                });
            });
        }
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


});
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
            alert(json);
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