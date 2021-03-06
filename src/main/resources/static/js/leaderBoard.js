/**
 * Created by rondo104 on 12.01.2016.
 */
//http://flaviusmatis.github.io/simplePagination.js

$(document).ready(function() {
    var testId = GetURLParameter('testId');
    var page = GetURLParameter('page');
    if (page == undefined ) {
        page = 1;
    }
    viewBoard(testId,page);
});

function viewBoard(testId,currentPage){
    var getboard;
    var nickName = GetURLParameter('nickName');
    urll = "/guest/leaderBoard/" + testId +"/"+currentPage;
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
                    pages: getboard.pageNumber,
                    currentPage:getboard.currentPageNumber,
                    onPageClick(pageNumber){
                    viewBoard(testId,pageNumber);
                }
            });
        });

    $('#myTable').empty();
    $.each(getboard, function (index, nickMarks) {
        $.each(nickMarks, function (index, nickMark) {
            if (nickName == nickMark[0]) {
            $('#myTable').append('<tr style="color:#2dadf0;font-weight: bold;"><td style="width: 40px;">' + (((currentPage - 1) * itemOnPage) + index + 1) + '</td><td>' + nickMark[0] + '</td><td style="width: 40px;">' + nickMark[1] + '%</td></tr>');
        }else { $('#myTable').append('<tr><td style="width: 40px;">'+ (((currentPage-1)*itemOnPage)+index+1) +'</td><td>'+ nickMark[0]+'</td><td style="width: 40px;">'+ nickMark[1]+'%</td></tr>');
            }
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
