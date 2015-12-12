$(document).ready(function ($) {

//owlCarousel
    $("#owl-example").owlCarousel({
        autoPlay: 3000
    });


//*owlCarousel
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
    var userok = GetURLParameter('user');
    var urltest = 'http://localhost:8080/student/tests/'+userok+'/pass/'+tech;
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
            function timeisUp() {
                $('#modal1').openModal();
            }

// 2.3 get each category of this article
            var qmass = $.each(testinfo.questions, function (i, questionone) {
                var div_q = $("<div style='margin: 0; padding: 0;'/>");
                $.each(questionone.answers, function (j, answersone) {
                    var span = $("<span style='margin: 0; padding: 0;'/>");
                    if (questionone.multichoice) {
                        span.html('<p style="margin: 0; padding: 0;"><label style="margin: 0; padding: 0;"><input type="checkbox" value="' + answersone.answerText + '" name="answer' + i + '" id ="'+answersone.id+'" style="margin: 0; padding: 0;">' +
                            answersone.answerText + '</label></p>')
                    } else {
                        span.html('<p style="margin: 0; padding: 0;"><label style="margin: 0; padding: 0;"><input type="radio" value="' + answersone.answerText + '" name="answer' + i + '" id ="'+answersone.id+'" style="margin: 0; padding: 0;">' +
                            answersone.answerText + '</label></p>')
                    }
                    div_q.append(span);
                });

                    if (!questionone.open) {
                        $("#tabs").append($('<div id="tabs-' + i + '" style="margin-bottom: 20px; padding: 0;" class="alldiv" />')
                                .append($('<div style="margin: 0; padding: 0;"/>').html(i + 1 + '. ' + '<span id="' + questionone.id + '" class="questionclass">' + questionone.question + '</span></div>'))
                                .append(div_q)
                        );
                    } else {
                        $("#tabs").append($('<div id="tabs-' + i + '" style="margin-bottom: 20px; padding: 0;" class="alldiv"/>')
                                .append($('<div style="margin: 0; padding: 0;"/>').html(i + 1 + '. ' + '<span id="' + questionone.id + '" class="questionclass">' + questionone.question + '</span></div>'))
                                .append($('<div style="margin: 0; padding: 0;"/>').html('<label for=".icon_prefix2">' + 'Відповідь:' + '</label>' +
                                    '<textarea class="materialize-textarea icon_prefix2" style="padding: 0; margin-bottom: 0;"></textarea>'))
                        );
                    }
            })
            var mytabs = [];
            mytabs.length = qmass.length
            for(i=0; i<mytabs.length; i++) {
                $('#demo').append('<li class="tab" style="display: inline"><a href="#tabs-' + i + '">' + i + ' ' + '</a></li>');

            }
            $( "#tabs" ).tabs();
        }
    });
    $(".modalAppear").click(function() {
        $('#modal2').openModal();
        $('#modal2').append('<div class="modal-content"><p>' + 'Після збереження відповіді будуть відправлені. Зберегти?' +
            '</p></div><div class="modal-footer"><a href="../pages/UserPage.html" class="modal-action modal-close waves-effect waves-green btn-flat" id="save">' +
            'Так' + '</a><a href="#!" class=" modal-action modal-close waves-effect waves-green btn-flat">' + 'Ні' + '</a></div>');
    });
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
        var exurl = 'http://localhost:8080/student/answers/' + tech;

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
