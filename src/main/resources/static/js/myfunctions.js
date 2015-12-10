$(document).ready(function ($) {

//owlCarousel
    $("#owl-example").owlCarousel({
        autoPlay: 3000
    });


//*owlCarousel
//getting tests
    var myjson;
    jQuery.ajax({
        type: "GET",
        url: "http://localhost:8080/student/tests/1",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            myjson = json;
            $.each(myjson, function (index, myjs) {
                    $('.avaliableTests').append('<tr><td>' + myjs.testDTO.testName + '</td><td>' +
                        myjs.testDTO.duration + " хв" + '</td><td><a href="TestPage.html?resultId='+myjs.resultId+'"><button class="start-test-btn">' +
                        "розпочати тест" + '</button></a></td></tr>')
            })
        }
    })

//*getting tests
//getting results
    jQuery.ajax({
        type: "GET",
        url: "http://localhost:8080/superAdmin/addUser",
        contentType: 'application/json; charset=utf-8',
        success: function (jsonrols) {
            var rols;
            rols = jsonrols;
            $.each(rols, function (index, rolsone) {
                   $('#roles').append('<option value="role' + index + '" disabled>' + rolsone + '</option>')
            })
        }
    });
//*getting results
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
    var urltest = 'http://localhost:8080/student/tests/1/pass/' + tech;
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
                                    span.html('<p style="margin: 0; padding: 0;"><label style="margin: 0; padding: 0;"><input type="checkbox" class="filled-in" value="' + answersone.answerText + '" name="answer' + i + '" id ="'+answersone.id+'" style="margin: 0; padding: 0;">' +
                                        answersone.answerText + '</label></p>')
                                } else {
                                    span.html('<p style="margin: 0; padding: 0;"><label style="margin: 0; padding: 0;"><input class="with-gap" type="radio" value="' + answersone.answerText + '" name="answer' + i + '" id ="'+answersone.id+'" style="margin: 0; padding: 0;">' +
                                        answersone.answerText + '</label></p>')
                                }
                            div_q.append(span);
                        });
                    if (!questionone.open) {
                        $("#tabs").append($('<div id="tabs-' + i + '" style="margin-bottom: 20px; padding: 0;" class="alldiv" />')
                            .append($('<div style="margin: 0; padding: 0;"/>').html(i+1 + '. ' + '<span id="'+questionone.id+'" class="questionclass">' + questionone.question + '</span></div>'))
                            .append(div_q)
                        );
                    } else {
                        $("#tabs").append($('<div id="tabs-' + i + '" style="margin-bottom: 20px; padding: 0;" class="alldiv"/>')
                            .append($('<div style="margin: 0; padding: 0;"/>').html(i+1 + '. ' + '<span id="'+questionone.id+'" class="questionclass">' + questionone.question + '</span></div>'))
                            .append($('<div style="margin: 0; padding: 0;"/>').html('<label for=".icon_prefix2">' + 'Відповідь:' + '</label>'+
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

            $("#save").click(function() {
                $('#modal2').openModal();
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
                        alert("yes");
                    },
                    error: function (e) {
                        alert(e.message);
                    }
                });
            })
//*getting test-info
});

//IMPORT TEST

$(document).ready(function() {

    var dropZone = $('#dropZone'),
        maxFileSize = 1000000; // максимальный размер фалйа - 1 мб.

    // Проверка поддержки браузером
    if (typeof(window.FileReader) == 'undefined') {
        dropZone.text('Не поддерживается браузером!');
        dropZone.addClass('error');
    }

    // Добавляем класс hover при наведении
    dropZone[0].ondragover = function() {
        dropZone.addClass('hover');
        return false;
    };

    // Убираем класс hover
    dropZone[0].ondragleave = function() {
        dropZone.removeClass('hover');
        return false;
    };

    // Обрабатываем событие Drop
    dropZone[0].ondrop = function(event) {
        event.preventDefault();
        dropZone.removeClass('hover');
        dropZone.addClass('drop');

        var file = event.dataTransfer.files[0];

        // Проверяем размер файла
        if (file.size > maxFileSize) {
            dropZone.text('Файл слишком большой!');
            dropZone.addClass('error');
            return false;
        }

        // Создаем запрос

        var boundary = String(Math.random()).slice(2);
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener('progress', uploadProgress, false);


        xhr.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);
        xhr.open('POST', '/trainer/import');

        xhr.send(file);
    };

    // Показываем процент загрузки
    function uploadProgress(event) {
        var percent = parseInt(event.loaded / event.total * 100);
        dropZone.text('Загрузка: ' + percent + '%');
    }

    // Пост обрабочик
    function stateChange(event) {
        if (event.target.readyState == 4) {
            if (event.target.status == 200) {
                dropZone.text('Загрузка успешно завершена!');
            } else {
                dropZone.text('Произошла ошибка!');
                dropZone.addClass('error');
            }
        }
    }

});
//*IMPORT TEST
