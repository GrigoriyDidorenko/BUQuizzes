/**
 * Created by rondo104 on 12.01.2016.
 */
//http://flaviusmatis.github.io/simplePagination.js

$(document).ready(function() {
    var testId = GetURLParameter('testId');
    viewBoard(testId,1);
});

function viewBoard(testId,currentPage){
    var getboard;
    var nickName = GetURLParameter('nickName');
    if (nickName != null) {
        var urll = "/guest/leaderBoard/userPosition/" + testId +"/"+nickName;
    }else urll = "/guest/leaderBoard/" + testId +"/"+currentPage;
    jQuery.ajax({
        type: "GET",
        url: urll,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            getboard = json;
            var itemOnPage = 10;
            $(function() {
                $('#light-pagination').pagination({
                    items: getboard.nickMarks.length,
                    itemsOnPage: itemOnPage,
                    cssStyle: 'light-theme',
                    pages: getboard.pageCount,
                    currentPage:currentPage,
                    onPageClick(pageNumber){
                    viewBoard(testId,pageNumber);
                }
            });
        });
    $('#myTable').empty();
    $.each(getboard, function (index, nickMarks) {
        $.each(nickMarks, function (index, nickMark) {
            $('#myTable').append('<tr><td style="width: 40px;">'+ (((currentPage-1)*itemOnPage)+index+1) +'</td><td>'+ nickMark[0]+'</td><td style="width: 40px;">'+ nickMark[1]+'%</td></tr>');
        })
    })
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

