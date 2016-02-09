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
        url:"http://localhost:8080/guest/tests/2?email=atia29@mail.ru&nickName=katya&name=kate",
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
            //get groups
            $.each(myJson.questions, function (index, quest) {
                $('.myGroup').append('<div class="ui-widget groupdiv" id="group-'+index+'" name="'+index+'" style="margin-top: 5px; margin-left: 5px; float: left;border-bottom: 1px solid gainsboro;">'+
                   '<span style="margin-right:5px;font-size: 14px;">Group: </span><input id="tags-'+index+'" type="text" class="tags" style="font-size: 14px;" value="'+quest.question+'">'+
                   '<span style="margin-right:5px; font-size: 14px;">Begin: </span><input type="text" id="datepicker-'+index+'" class="begin" style="font-size: 14px;" value="'+quest.question+'">'+
                    '<span style="margin-right:5px;font-size: 14px;">End: </span><input id="end-'+index+'" type="text" class="end" style="font-size: 14px;" value="'+quest.question+'"></div>');
            });
            $(function() {
                $( ".begin" ).datepicker();
                $( ".end" ).datepicker();
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
                }
            });
            $.each(myJson.questions, function (index, quest) {
                var mu = ''+(index+1)+'';
                var my = 'question-'+(index+1)+'';
                $('.collapsible').append('<li id="question-'+(index+1)+'" class="question" name="'+(index+1)+'"><div class="collapsible-header">'+
                    '<input type="text" id="questioninput-'+(index+1)+'" placeholder="Question" name="questioninput" style="width: 85%" value="'+quest.question+'">'+
                    '<i id="question-'+(index+1)+'_d" class="fa fa-times closeicon" style="cursor: pointer"></i></div></li>');
                $( '#question-'+(index+1)+'_d' ).click(function() {
                    $('#question-'+(index+1)+'').remove();
                });
                $.each(quest.answers, function (index, answer) {
                    $('#'+my+'').append('<div style="display: block; margin: 1px 0 -10px 10px;" class="collapsible-body" id="question-'+mu+'_answer'+(index+1)+'">'+
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
                var kio = $('.mur-'+myau+':last').attr('id');
                var kiss = $('.mur-'+myau+':last').attr('name');
                var kissAdd = (+kiss+1);
                $('#question-'+myau+'_answer'+kiss+'').after($('<div style="display: block; margin: 1px 0 -10px 10px;" class="collapsible-body" id="question-'+myau+'_answer'+kissAdd+'">'+
                    '<input id="question-'+myau+'_a'+kissAdd+'" class="mur-'+myau+'" name="'+kissAdd+'" type="text" placeholder="Answer">'+
                    '<input id="question-'+myau+'_m'+kissAdd+'" type="number" class="mark-question-'+myau+'" placeholder="mark">'+
                    '<i class="fa fa-times fa-1x closeicon" name="'+kissAdd+'" id="question-'+myau+'_delAnswer'+kissAdd+'" style="cursor: pointer"></i>'+
                    '<i class="yesicon '+myau+'" name="'+myau+'" id="question-'+myau+'_addAnswer'+kissAdd+'"></i></div>'));

                $('.closeicon').click(function () {
                    var miq = $(this).attr('name');
                    $('#question-' + myau + '_answer' +miq+ '').remove();
                });
            });
        }
    });

    $('#importTest').click(function () {
        var mi = $('#questioninput-1').val();
        importTest();
    });


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
                $('#'+katya+'').after($('<div class="ui-widget groupdiv" id="group-'+katyaAdd+'" name="'+katyaAdd+'" style="margin-top: 5px; margin-left: 5px; float: left;border-bottom: 1px solid gainsboro;">'+
                    '<span style="margin-right:5px;font-size: 14px;">Group: </span><input id="tags-'+katyaAdd+'" type="text" class="tags" style="font-size: 14px;">'+
                    '<span style="margin-right:5px; font-size: 14px;">Begin: </span><input type="text" id="datepicker-'+katyaAdd+'" class="begin" style="font-size: 14px;">'+
                    '<span style="margin-right:5px;font-size: 14px;">End: </span><input id="end-'+katyaAdd+'" type="text" class="end" style="font-size: 14px;"></div>'));
                $(function() {
                    $( ".begin" ).datepicker();
                    $( ".end" ).datepicker();
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
    var kissname = $('.question:last').attr('name');
    var kissnameAdd = (+kissname+1);
    var kissm = $('.question:last').attr('id');
    $('#'+kissm+'').after($('<li id="question-'+kissnameAdd+'" class="question" name="'+kissnameAdd+'"><div class="collapsible-header">'+
        '<input type="text" id="questioninput-'+kissnameAdd+'" placeholder="Question" name="questioninput" style="width: 85%">'+
        '<i id="question-'+kissnameAdd+'_d" class="fa fa-times closeicon" style="cursor: pointer"></i></div>'+
        '<div style="display: block; margin: 1px 0 -10px 10px;" class="collapsible-body" id="question-'+kissnameAdd+'_answer1">'+
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
        var kio = $('.mur-'+myau+':last').attr('id');
        var kiss = $('.mur-'+myau+':last').attr('name');
        var kissAdd = (+kiss+1);
        $('#question-'+myau+'_answer'+kiss+'').after($('<div style="display: block;  margin: 1px 0 -10px 10px;" class="collapsible-body" id="question-'+myau+'_answer'+kissAdd+'">'+
            '<input id="question-'+myau+'_a'+kissAdd+'" class="mur-'+myau+'" name="'+kissAdd+'" type="text" placeholder="Answer">'+
            '<input id="question-'+myau+'_m'+kissAdd+'" type="number" class="mark-question-'+myau+'" placeholder="mark">'+
            '<i class="fa fa-times fa-1x closeicon" name="'+kissAdd+'" id="question-'+myau+'_delAnswer'+kissAdd+'" style="cursor: pointer"></i>'+
            '<i class="yesicon '+myau+'" name="'+myau+'" id="question-'+myau+'_addAnswer'+kissAdd+'"></i></div>'));

        $('.closeicon').click(function () {
            var miq = $(this).attr('name');
            $('#question-' + myau + '_answer' +miq+ '').remove();
        });
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
    var groupName;
    var beginTime;
    var endTime;
    var group;
    var testToGroups = [];
    $('.groupdiv').each(function (index) {
        groupName = $.trim($('#tags-'+index+'').val());
        beginTime = $.trim($('#datepicker-'+index+'').val());
        endTime = $.trim($('#end-'+index+'').val());
        group = new Group(groupName, beginTime, endTime);
        testToGroups.push(group);
    });
    console.log(testToGroups);
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
    var test = new Test(testName, duration, oneTime, categoryTestName, testToGroups, questions);
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

function Test(testName, duration, oneTime, categoryTestName, testToGroups, questions) {
    this.testName = testName;
    this.duration = duration;
    this.oneTime = oneTime;
    this.categoryTestName = categoryTestName;
    this.testToGroups = testToGroups;
    this.questions = questions;
}
function Group(groupName, beginTime, endTime) {
    this.groupName = groupName;
    this.beginTime = beginTime;
    this.endTime = endTime;
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