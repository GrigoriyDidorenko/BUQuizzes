/**
 * Created by rondo104 on 14.01.2016.
 */

$(document).ready(function () {
    $('.opentest').hide();
    $(function() {
        $( ".begin" ).datepicker({
            dateFormat: "yy-mm-dd"
        });
        $( ".end" ).datepicker({
            dateFormat: "yy-mm-dd"
        });
    });
    addQuestion();
    //ToDo
    //Add new Question
    $("#addQuestion").click(function () {
        addQuestion();
    });
    $('#importTest').click(function () {
        $('.opentest').hide();
        $.validator.setDefaults({
            submitHandler: function() {
                $(".question").each(function (index) {
                    var iM = (index + 1);
                    $('#question-' + iM + '_error').hide();
                    $('#question-' + iM + '_error2').hide();
                    $('#question-' + iM + '_error3').hide();
                    var value = $.trim($('#questioninput-' + iM + '').val());
                    if (value.length > 0) {
                        $('#question-' + iM + '_error').hide();
                    }
                    else {
                        $('#question-' + iM + '_error').show();
                    }
                    $('.mark-question-' + iM + '').each(function (index) {
                        var questV = '#question-' + iM + '_a'+(index+1)+'';
                        var value=$.trim($(questV).val());
                        if(value.length > 0){
                            var mu = $(this).attr('id');
                            var val = $.trim($('#question-' + iM + '_m' + (index + 1) + '').val());
                            if (val.length > 0) {
                            }
                            else {
                                $('#question-' + iM + '_error2').show();
                            }
                        }
                    });
                    $('#question-' + iM + '_m' + (index + 1) + '').each(function (index) {
                        var questV = '#question-' + iM + '_m' + (index + 1) + '';
                        var value=$.trim($(questV).val());
                        if(value.length > 0){
                            var val = $.trim($('#question-' + iM + '_a'+(index+1)+'').val());
                            if (val.length > 0) {
                            }
                            else {
                                $('#question-' + iM + '_error3').show();
                            }
                        }
                    });
                });
                var groupval;
                if ($('#oneTime').is(':checked')) {
                    $(".tags").each(function (index) {
                        $('#tags-' + index + '').each(function (index) {
                            groupval = $.trim($(this).val());
                        });
                    });
                    if (groupval.length > 0) {
                        $('.opentest').show();
                    }
                    else {
                    }
                }
                if($('.mama').is(":visible") || $('.papa').is(":visible")|| $('.child').is(":visible")){
                }
                else {
                    importTest();
                }

            }
        });
        $().ready(function() {
            var catVal=$.trim($('#categoryTestName').val());
            var mamula=$.trim($('#selectCategoryTestName').val());
            if(catVal.length>0){
                $("#selectCategoryTestName").prop('required',false);
            }
            else {
                $("#selectCategoryTestName").prop('required',true);
            }
            // validate the comment form when it is submitted
            $("#commentForm").validate({
                messages: {
                    testName: {
                        required: "Введіть, будь ласка, назву тесту"
                    },
                    selectCategory: {
                        required: "Будь ласка, оберіть категорію тесту чи створіть нову"
                    }
                }
            });
        });

    });

    //ToDo ADD category TestName
    jQuery.ajax({
        type: "GET",
        url: "/trainer/getAllCategoryTestName",
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            var rols = json;

            var availableTags = []; // create array here

            $.each(rols, function (index, rolsone) {
                $('#selectCategoryTestName').append('<option>'+rolsone+'</option>');
            });
        },
        error: function (http) {
            return http.responseText;
        }
    });

    $('.collapsible').collapsible({
        accordion: false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });
    jQuery.ajax({
        type: "GET",
        url: "/trainer/getAllGroups",
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            var manson = json;
            var availableGroups = []; // create array here
            $.each(manson, function (index, myjs) {
                availableGroups.push(myjs[1]); //push values here
            });
            var unique=availableGroups.filter(function(itm,i,availableGroups){
                return i==availableGroups.indexOf(itm);
            });
            $( ".tags" ).autocomplete({
                source: unique
            });
            $("#addGroup").click(function () {
                var katya = $('.groupdiv:last').attr('id');
                var katenka = $('.groupdiv:last').attr('name');
                var katyaAdd = (+katenka+1);
                $('#'+katya+'').after($('<div class="ui-widget groupdiv" id="group-'+katyaAdd+'" name="'+katyaAdd+'" style="margin-top: 5px; margin-left: 5px; float: left;padding-top: 10px;">'+
                        '<fieldset><legend>Група-'+(katyaAdd+1)+'</legend>'+
                    '<span style="margin-right:5px;font-size: 14px;">Назва групи: </span><input id="tags-'+katyaAdd+'" type="text" class="tags" style="font-size: 14px;">'+
                    '<span style="margin-right:5px; font-size: 14px;">Початок: </span><input type="text" id="datepicker-'+katyaAdd+'" class="begin" style="font-size: 14px;">'+
                    '<span style="margin-right:5px;font-size: 14px;">Кінець: </span><input id="end-'+katyaAdd+'" type="text" class="end" style="font-size: 14px;"></fieldset></div>'));
                $(function() {
                    $( ".begin" ).datepicker({
                        dateFormat: "yy-mm-dd"
                    });
                    $( ".end" ).datepicker({
                        dateFormat: "yy-mm-dd"
                    });
                });
                $( ".tags" ).autocomplete({
                    source: unique
                });
            });
        },
        error: function (http) {
            return http.responseText;
        }
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
    childLI.setAttribute("class", "question");
    var childDiv = document.createElement('div');
    childDiv.setAttribute("class", "collapsible-header");
    var childInp = document.createElement('input');
    childInp.type = "text";
    childInp.id = 'questioninput-' + +countQuestion;
    childInp.setAttribute("class", "questioninput");
    childInp.placeholder = "Питання";
    childInp.name = "questioninput";
    childInp.style = 'width:85%;';
    var childDelButton = document.createElement('i');
    childDelButton.id = childLI.id + "_d";
    childDelButton.setAttribute("class", "fa fa-times closeicon");
    childDelButton.addEventListener("click", function(){
        deleteQuestion(childLI.id);
    });
    var childError = document.createElement('i');
    childError.id = childLI.id + "_error";
    childError.textContent = "Будь ласка, введіть питання";
    childError.style = 'display: block; font-size: 14px; width: 50%; margin: -25px 0 -15px 0; padding: 0px; text-align: left;font-style:normal;';
    childError.setAttribute("class", "mama");
    var childError2 = document.createElement('i');
    childError2.id = childLI.id + "_error2";
    childError2.textContent = "Будь ласка, введіть оцінку";
    childError2.style = 'display: block; font-size: 14px; width: 50%; margin: -15px 0px -10px 0px; text-align:left; font-style:normal;';
    childError2.setAttribute("class", "papa");
    var childError3 = document.createElement('i');
    childError3.id = childLI.id + "_error3";
    childError3.textContent = "Будь ласка, введіть відповідь";
    childError3.style = 'display: block; font-size: 14px; width: 50%; margin: -15px 0px -10px 0px; text-align:left; font-style:normal;';
    childError3.setAttribute("class", "child");
    childDiv.appendChild(childInp);
    childDiv.appendChild(childDelButton);
    childDiv.appendChild(childError);
    childDiv.appendChild(childError2);
    childDiv.appendChild(childError3);
    childLI.appendChild(childDiv);
    document.getElementById('questions').appendChild(childLI);
    addAnswer(childLI.id);
    $('.mama').hide();
    $('.papa').hide();
    $('.child').hide();
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
    childInp1.placeholder = "Відповідь";
    var childInp2 = document.createElement('input');
    childInp2.id = questionId + "_m" + countAnswer;
    childInp2.type = "number";
    childInp2.setAttribute("class", "mark-"+questionId+"");
    childInp2.placeholder = "оцінка";
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
    var groupName;
    var accessBegin;
    var accessEnd;
    var group;
    var testsToGroups = [];
    $('.groupdiv').each(function (index) {
        groupName = $.trim($('#tags-'+index+'').val());
        accessBegin = $.trim($('#datepicker-'+index+'').val());
        accessEnd = $.trim($('#end-'+index+'').val());
        group = new Group(groupName, accessBegin, accessEnd);
        testsToGroups.push(group);
    });
    console.log(testsToGroups);
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
    var test = new Test(testName, duration, oneTime, categoryTestName, testsToGroups, questions);
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

function Test(testName, duration, oneTime, categoryTestName, testsToGroups, questions) {
    this.testName = testName;
    this.duration = duration;
    this.oneTime = oneTime;
    this.categoryTestName = categoryTestName;
    this.testsToGroups = testsToGroups;
    this.questions = questions;
}
function Group(groupName, accessBegin, accessEnd) {
    this.groupName = groupName;
    this.accessBegin = accessBegin;
    this.accessEnd = accessEnd;
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
    });
}
