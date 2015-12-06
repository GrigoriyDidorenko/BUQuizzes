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
        url: "http://localhost:8080/student/tests/2",
        contentType: 'application/json; charset=utf-8',
        success: function (json) {
            myjson = json;
            $.each(myjson, function (index, myjs) {
                $.each(myjs.testDTO, function (j, testDT) {
                    $('.avaliableTests').append('<tr><td>' + testDT.testName + '</td><td>' +
                        testDT.duration + " хв" + '</td><td><a href="TestPage.htm"><button class="start-test-btn">' +
                        "розпочати тест" + '</button></a></td></tr>')
                })
            })
        }
    });

//*getting tests
//getting results
    jQuery.ajax({
        type: "GET",
        url: "http://localhost:8080/student/tests/1",
        contentType: 'application/json; charset=utf-8',
        success: function (jsonres) {
            myresult = jsonres;
            $.each(myresult, function (index, myres) {
                $.each(myres.testDTO, function (j, testDTOone) {
                });
            });
        }
    });
//*getting results
//getting test-info
    var testinfo;
    jQuery.ajax({
        type: "GET",
        url: "http://localhost:8080/student/tests/1/pass/1",
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
                                    span.html('<p style="margin: 0; padding: 0;"><label style="margin: 0; padding: 0;"><input type="checkbox" class="filled-in" value="' + answersone.answerText + '" name="answer' + i + '" style="margin: 0; padding: 0;">' +
                                        answersone.answerText + '</label></p>')
                                } else {
                                    span.html('<p style="margin: 0; padding: 0;"><label style="margin: 0; padding: 0;"><input class="with-gap" type="radio" value="' + answersone.answerText + '" name="answer' + i + '" style="margin: 0; padding: 0;">' +
                                        answersone.answerText + '</label></p>')
                                }
                            div_q.append(span);
                        });
                    if (!questionone.open) {
                        $(".test").append($('<div id="question' + i + '" style="margin-bottom: 20px; padding: 0;"/>')
                            .append($('<div style="margin: 0; padding: 0;"/>').html(i+1 + '. ' + questionone.question + '</div>'))
                            .append(div_q)
                        );
                    } else {
                        $(".test").append($('<div id="question' + i + '" style="margin-bottom: 20px; padding: 0;"/>')
                            .append($('<div style="margin: 0; padding: 0;"/>').html(i+1 + '. ' + questionone.question + '</div>'))
                            .append($('<div style="margin: 0; padding: 0;"/>').html('<label for="icon_prefix2">' + 'Відповідь:' + '</label>'+
                                '<textarea id="icon_prefix2" class="materialize-textarea" style="padding: 0; margin-bottom: 0;"></textarea>'))
                    );
                    }
                })
            var mytabs = [];
            mytabs.length = qmass.length
            for(i=0; i<=mytabs.length; i++) {
                    $('#demo').append('<li style="display: inline"><a href="#question' + i + '">' + i + ' ' + '</a></li>');

            }

            $("#button").click(function() {
                    var answertext = [];
                        $.each(testinfo.questions, function (i, questionone) {
                            $.each($('input[name="answer' + i + '"]:checked'), function () {
                                answertext.push($(this).val());
                        });
                    });

                    event = {
                        answerText: answertext
                    };
                    alert(JSON.stringify(event));
                })

        }
    });
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
