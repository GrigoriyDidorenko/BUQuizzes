/**
 * Created by rondo on 29.01.2016.
 */
$(document).ready(function() {
    $("#question").text(urldecode(GetURLParameter('question')));
    var onPageAnswer;
    var answers = [];
    var count;
    var checkedCount;
    var ans;
    var URL ="/trainer/uncheckedTests/"+ GetURLParameter('questionId')+"";
    jQuery.ajax({
        type: "GET",
        url: URL,
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            //console.log(json);
            //json = [ [ 5, 4, "1", 2 ], [ 7, 6, "2", 2 ]  ];
            $.each( json, function( indexI, ans ) {
                var Answer = new answer(ans[0],ans[1],ans[2],ans[3])
                answers.push(Answer);
            });
            count = answers.length;
            checkedCount = 0;
            onPageAnswer = 0;
            $("#stay").append(checkedCount);
            $("#all").append(count);
            //$("#count").text(checkedCount +'/'+ count);
            $("#answer").text(answers[0].answer);
        },
        error: function (http) {
            alert(http.responseText);
        }
    });

    $( "#next" ).click(function() {
        if (count > onPageAnswer)  ++onPageAnswer;
        $("#answer").text(answers[onPageAnswer-1].answer);
        $("#mark").empty();
    });

    $( "#preview" ).click(function() {
        if (onPageAnswer >= 1)  --onPageAnswer;
        $("#answer").text(answers[onPageAnswer].answer);
        $("#mark").empty();
    });

    $( "#saveMark" ).click(function() {
        ans=answers[onPageAnswer];
        setMark(ans, checkedCount, count);
    });

    $( "#exit" ).click(function() {
        window.location = "allOpenQuestions.html"
    });

});

function setMark(answer,checkedCount,count){
    var mark = Number($("#mark").val());
    var sent = new  sentJSON(answer.userAnswerId, answer.resultId, mark);
    var json = JSON.stringify(sent);
    console.log(json);
    jQuery.ajax({
        url: "/trainer/uncheckedTests/answers",
        type: "POST",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: json,
        success: function (json) {

            //$("#count").text(++checkedCount +'/'+ count);
            //alert(+checkedCount);
            $("#mark").empty();
            $("#stay").empty();
            $("#stay").append(+checkedCount+1);
        },
        error: function (http) {
        //TOdo
        }
    })

}

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

function answer(userAnswerId, resultId, answer, numberOfUncheckedAnswers){
    this.userAnswerId = userAnswerId;
    this.resultId = resultId;
    this.answer = answer;
    this.numberOfUncheckedAnswers = numberOfUncheckedAnswers;
}

function sentJSON(useranswerId, resultId, mark){
    this.id = useranswerId;
    this.resultId = resultId;
    this.mark = mark;
}

function urldecode(str) {
    return decodeURIComponent((str+'').replace(/\+/g, '%20'));
}