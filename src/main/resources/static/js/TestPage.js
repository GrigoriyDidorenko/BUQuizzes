$(document).ready(function ($) {
//getting test-info
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
    var tech = GetURLParameter('resultId');
    var urltest = '/student/tests/pass/'+tech;
    var testinfo;
    var globalVariable;
    jQuery.ajax({
        type: "GET",
        url: urltest,
        contentType: 'application/json; charset=utf-8',
        success: function (info) {
            testinfo = info;
            $('.test-info').append('<div>' + "Назва тесту: " + testinfo.testName + '</div>' +
                '<div>' + "Тривалість: " + testinfo.duration + " хв" + '</div>');

            $("#hms_timer").countdowntimer({
                hours:0,
                minutes: testinfo.duration,
                size: "lg",
                timeUp : timeisUp
            });


// 2.3 get each category of this article
            var qmass = $.each(testinfo.questions, function (i, questionone) {
                var div_q = $("<div class='positions'/>");
                $.each(questionone.answers, function (j, answersone) {
                    var span = $("<span class='positions'/>");
                    if (questionone.multichoice) {
                        span.html('<p class="positions"><input type="checkbox" value="' + answersone.answerText + '" name="answertabs-' + i + '" id ="'+answersone.id+'" class="positions inp filled-in"><label class="positions" for="'+answersone.id+'">' +
                            answersone.answerText + '</label></p>')
                    } else {
                        span.html('<p class="positions"><input type="radio" value="' + answersone.answerText + '" name="answertabs-' + i + '" id ="'+answersone.id+'" class="positions inp with-gap"><label class="positions" for="'+answersone.id+'">' +
                            answersone.answerText + '</label></p>')
                    }
                    div_q.append(span);
                });

                if (!questionone.open) {
                    $("#tabs").append($('<div id="tabs-' + i + '" style="margin-bottom: 20px; padding: 0;" class="alldiv tabs'+i+'" />')
                        .append($('<div class="positions"/>').html(i + 1 + '. ' + '<span id="' + questionone.id + '" class="questionclass">' + questionone.question + '</span></div>'))
                        .append(div_q)
                    );
                } else {
                    $("#tabs").append($('<div id="tabs-' + i + '" style="margin-bottom: 20px; padding: 0;" class="alldiv tabs'+i+'" />')
                        .append($('<div class="positions"/>').html(i + 1 + '. ' + '<span id="' + questionone.id + '" class="questionclass">' + questionone.question + '</span></div>'))
                        .append($('<div class="positions"/>').html('<label for=".icon_prefix2">' + 'Відповідь:' + '</label>' +
                            '<textarea class="icon_prefix2"></textarea>'))
                    );
                }
            });
            var mytabs = [];
            mytabs.length = qmass.length;
            for(i=0; i<mytabs.length; i++) {
                $('#demo').append('<li class="tab" style="display: inline"><a href="#tabs-'+i+'"><span class="tab_span act" id="mytabs-'+i+'">' + (i+1)+ '</span></a>' + ' | ' + '</li>');
                var $tabs = $( "#tabs" ).tabs({
                    activate: function( event, ui ) {
                        alert(ui.index());
                    }
                });

                $('.tabs' + i + '').change(function () {
                    var id = this.id;
                    var answ = 'answer'+id;

                    if ($('input[name=' + answ + ']').is(':checked')) {
                        var c = this.checked ? '#2dadf0' : '#2dadf0';
                        $('#my' + id + '').addClass('mother');
                    }
                    else{
                        var b = this.checked ? '#6d6e71' : '#6d6e71';
                        $('#my' + id + '').removeClass('mother')
                    }
                });
                $('.act').removeClass('act');
                $('#mytabs-0').addClass('act');
                $('#mytabs-'+i+'').bind('click', function() {
                    // remove the active class from all elements with active class
                    $('.act').removeClass('act');
                    // add active class to clicked element
                    $(this).addClass('act');
                });
            }
        }
    });

    $(".modalAppear").click(function() {
        $('#modal2').openModal();
    });
    $('.modal-trigger').leanModal({
        dismissible: false
    });
    $('#modal2').append('<div class="modal-content"><p>' + 'Після збереження відповіді будуть відправлені. Зберегти?' +
        '</p></div><div class="modal-footer"><a href="../pages/PageOfConfirm.html?resultId='+tech+'" class="modal-action modal-close waves-effect btn-flat" id="save">' +
        'Так' + '</a><a href="#!" class=" modal-action modal-close waves-effect btn-flat">' + 'Ні' + '</a></div>');
    function timeisUp() {
        document.location.href = "../pages/PageOfConfirmTimer.html?resultId=" + tech;
        //$('#modal1').openModal();
        //$('#modal1').append('<div class="modal-content"><p>' + 'Час тестування вийшов. Ваші результати збережено.' +
        //    '</p></div><div class="modal-footer"><a href="../pages/PageOfConfirm.html?resultId='+tech+'" class="modal-action modal-close waves-effect btn-flat" id="save">' +
        //    'Так' + '</a></div>');

            var answerId=[];
            var answerText=[];
            var eventArray = [];

            $(".alldiv").each(function () {
                var event={
                    questionId: "",
                    answerId: "",
                    answerText:""
                };
                event.questionId=+($(this).find(".questionclass").attr("id"));
                var inp=$(this).find("input:checked");
                var ansId =[];
                $(inp).each(function (){
                    ansId.push(+($(this).attr("id")));
                });
                event.answerId = ansId;
                if ($(this).find(".icon_prefix2").val()) {
                    event.answerText = ($(this).find(".icon_prefix2").val());
                } else {
                    event.answerText = "";
                }

                //var str = JSON.stringify(event);
                eventArray.push(event);
            });

            globalVariable=JSON.stringify(eventArray);
            console.log(globalVariable);
            var exurl = '/student/answers/' + tech;

            $.ajax({
                url:exurl,
                type:'POST',
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data:globalVariable,
                success: function() {
                }
            });
    }


    $("#save").click(function() {
        var answerId=[];
        var answerText=[];
        var eventArray = [];

        $(".alldiv").each(function () {
            var event={
                questionId: "",
                answerId: "",
                answerText:""
            };
            event.questionId=+($(this).find(".questionclass").attr("id"));
            var inp=$(this).find("input:checked");
            var ansId =[];
            $(inp).each(function (){
                ansId.push(+($(this).attr("id")));
            });
            event.answerId = ansId;
            if ($(this).find(".icon_prefix2").val()) {
                event.answerText = ($(this).find(".icon_prefix2").val());
            } else {
                event.answerText = "";
            }

            //var str = JSON.stringify(event);
            eventArray.push(event);
        });

        globalVariable=JSON.stringify(eventArray);
        console.log(globalVariable);
        var exurl = '/student/answers/' + tech;

        $.ajax({
            url:exurl,
            type:'POST',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data:globalVariable,
            success: function() {
            }
        });
    })
//*getting test-info
});
