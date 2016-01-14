/**
 * Created by rondo104 on 12.01.2016.
 */

function viewBoard(testId,page){
    var getboard;
    var pageCount;
    var urll = "/guest/leaderBoard/" + testId +"/"+page;
    jQuery.ajax({
        type: "GET",
        url: urll,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            getboard = json;
            pageCount= Number(getboard.pageCount);
            writePageNumber(testId,pageCount);
            $('#myTable').empty();
            $.each(getboard, function (index, nickMarks) {
                $.each(nickMarks, function (index, nickMark) {
                    $('#myTable').append('<tr><td>'+ nickMark[0]+'</td><td>'+ nickMark[1]+'</td></tr>');
                })
            })
        }
    })

}

function writePageNumber(testId, count){
    $('#pageNumber').empty();
    for (var i = 1; i <= count ; i++) {
        $('#pageNumber').append('<button onclick="viewBoard('+testId+","+i+')">'+ i +'</button>'+' ');
    }
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

function calls(){
    var testId = GetURLParameter('testId');
    viewBoard(testId,1);
}


