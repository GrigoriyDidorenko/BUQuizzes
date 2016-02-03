/**
 * Created by c2413 on 29.01.2016.
 */
$(document).ready(function () {
    $("#addQuestion").click(function () {
        addQuestion();
    });
    $("#addAnswer").click(function () {
        addAnswer();
    });
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
            $('#selectCategoryTestName').append('<option selected id="selected">'+categoryTestName+'</option>');

                $.each(myJson.questions, function (index, quest) {
                    var mu = ''+(index+1)+'';
                    var my = 'question-'+(index+1)+'';
                    $('.collapsible').append('<li id="question-'+(index+1)+'" class="question" name="'+(index+1)+'"><div class="collapsible-header">'+
                        '<input type="text" id="questioninput-'+(index+1)+'" placeholder="Question" name="questioninput" style="width: 85%" value="'+quest.question+'">'+
                        '<i id="question-'+(index+1)+'_d" class="fa fa-times closeicon" style="cursor: pointer"></i>'+
                        '<button id="addAnswer" name="'+(index+1)+'"><i class="fa fa-plus fa-1x yesicon" style="cursor: pointer"></i>add answer</button></div></li>');
                    $( '#question-'+(index+1)+'_d' ).click(function() {
                            $('#question-'+(index+1)+'').remove();
                    });
                    $.each(quest.answers, function (index, answer) {
                        $('#'+my+'').append('<div style="display: block" class="collapsible-body" id="question-'+mu+'_answer'+(index+1)+'">'+
                            '<input id="question-'+mu+'_a'+(index+1)+'" type="text" class="mur-'+mu+'" name="'+(index+1)+'" placeholder="Answer" value="'+answer.answerText+'">'+
                            '<input id="question-'+mu+'_m'+(index+1)+'" type="number" class="mark-question-'+mu+'" placeholder="mark" value="'+answer.mark+'">'+
                            '<i class="fa fa-times fa-1x closeicon" name="'+(index+1)+'" id="question-'+mu+'_delAnswer'+(index+1)+'" style="cursor: pointer"></i>'+
                            '<i class="fa fa-plus fa-1x yesicon '+mu+'" name="'+mu+'" id="question-'+mu+'_addAnswer'+(index+1)+'" style="cursor: pointer"></i></div>');
                        $('#question-'+mu+'_delAnswer'+(index+1)+'').click(function() {
                            $('#question-'+mu+'_answer'+(index+1)+'').remove();
                        });
                    });
                $('.collapsible').collapsible({
                    accordion: false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
                });
            });
            $(".yesicon").click(function () {
                var myau = $(this).attr('name');
                alert('pur='+myau+'');
                katya(myau);
            });
            function katya (myau) {
                alert('name='+myau+'');
                var kio = $('.mur-'+myau+':last').attr('id');
                alert(kio);
                var kiss = $('.mur-'+myau+':last').attr('name');
                alert(kiss);
                var kissAdd = (+kiss+1);
                alert(kissAdd);
                alert('#question-'+myau+'_answer'+kiss+'');
                $('#question-'+myau+'_answer'+kiss+'').after($('<div style="display: block" class="collapsible-body" id="question-'+myau+'_answer'+kissAdd+'">'+
                    '<input id="question-'+myau+'_a'+kissAdd+'" class="mur-'+myau+'" name="'+kissAdd+'" type="text" placeholder="Answer">'+
                    '<input id="question-'+myau+'_m'+kissAdd+'" type="number" class="mark-question-'+myau+'" placeholder="mark">'+
                    '<i class="fa fa-times fa-1x closeicon" name="'+kissAdd+'" id="question-'+myau+'_delAnswer'+kissAdd+'" style="cursor: pointer"></i>'+
                    '<i class="fa fa-plus fa-1x yesicon '+myau+'" name="'+myau+'" id="question-'+myau+'_addAnswer'+kissAdd+'" style="cursor: pointer"></i></div>'));

                $('.closeicon').click(function () {
                    var miq = $(this).attr('name');
                    $('#question-' + myau + '_answer' +miq+ '').remove();
                });
                $(".yesicon").click(function () {
                    var myau = $(this).attr('name');
                    alert('pur='+myau+'');
                    katya(myau);
                });
            }
        }
    });

    $('#importTest').click(function () {
        var mi = $('#questioninput-1').val();
        alert(mi);
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
function addQuestion() {
    var kissname = $('.question:last').attr('name');
    alert(kissname);
    var kissnameAdd = (+kissname+1);
    alert(kissnameAdd);
    var kissm = $('.question:last').attr('id');
    alert(kissm);
    $('#'+kissm+'').after($('<li id="question-'+kissnameAdd+'" class="question" name="'+kissnameAdd+'"><div class="collapsible-header">'+
        '<input type="text" id="questioninput-'+kissnameAdd+'" placeholder="Question" name="questioninput" style="width: 85%">'+
        '<i id="question-'+kissnameAdd+'_d" class="fa fa-times closeicon" style="cursor: pointer"></i></div>'+
        '<div style="display: block" class="collapsible-body" id="question-'+kissnameAdd+'_answer1">'+
        '<input id="question-'+kissnameAdd+'_a1" class="mur-'+kissnameAdd+'" name="1" type="text" placeholder="Answer">'+
        '<input id="question-'+kissnameAdd+'_m1" type="number" class="mark-question-'+kissnameAdd+'" placeholder="mark">'+
        '<i class="fa fa-times fa-1x closeicon" name="'+kissnameAdd+'" id="question-'+kissnameAdd+'_delAnswer1" style="cursor: pointer"></i>'+
        '<i class="fa fa-plus fa-1x yesicon '+kissnameAdd+'" name="'+kissnameAdd+'" id="question-'+kissnameAdd+'_addAnswer1" style="cursor: pointer"></i></div></li>'));
    $( '#question-'+kissnameAdd+'_d' ).click(function() {
        $('#question-'+kissnameAdd+'').remove();
    });
    $('.closeicon').click(function () {
        var miq = $(this).attr('name');
        $('#question-' + kissnameAdd + '_answer' +miq+ '').remove();
    });
    $(".yesicon").click(function () {
        var myau = $(this).attr('name');
        alert('pur='+myau+'');
        katya(myau);
    });
    $('.collapsible').collapsible({
        accordion: false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });
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
    $.each( $('.question') , function( indexQ, questionLi ) {
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