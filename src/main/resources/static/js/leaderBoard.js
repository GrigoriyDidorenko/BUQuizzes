/**
 * Created by rondo104 on 12.01.2016.
 */
//http://flaviusmatis.github.io/simplePagination.js

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
            writePageNumber(testId,pageCount,page);
            $('#myTable').empty();
            $.each(getboard, function (index, nickMarks) {
                $.each(nickMarks, function (index, nickMark) {
                    $('#myTable').append('<tr><td>'+ nickMark[0]+'</td><td>'+ nickMark[1]+'</td></tr>');
                })
            })
        }
    })

}

function writePageNumber(testId, count, page){
    var viewPage=6;
    $('#pageNumber').empty();
    if (page > 1) $('#pageNumber').append('<button onclick="viewBoard('+testId+","+1+')">First</button>');
    if (page > 1) $('#pageNumber').append('<button onclick="viewBoard('+testId+","+(parseInt(page)-1)+')">Mines1</button>');
    for (var i = 1; i <= count ; i++) {
        if (count >= viewPage){
        if (i>=page-viewPage/2 && i<=page+viewPage/2){
        $('#pageNumber').append('<button onclick="viewBoard('+testId+","+i+')">'+ i +'</button>');}}
        else{ $('#pageNumber').append('<button onclick="viewBoard('+testId+","+i+')">'+ i +'</button>');}
    }
    if (page < count) $('#pageNumber').append('<button onclick="viewBoard('+testId+","+(parseInt(page)+1)+')">Plus1</button>');
    if (page < count) $('#pageNumber').append('<button onclick="viewBoard('+testId+","+count+')">End</button>');
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

$(document).ready(function() {
    var testId = GetURLParameter('testId');
    viewBoard(testId,1);
});

