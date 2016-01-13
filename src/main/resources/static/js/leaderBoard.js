/**
 * Created by rondo104 on 12.01.2016.
 */

function viewBoard(page){
    var getboard;
    var pageCount;
    var urll = "/guest/leaderBoard/1/"+page;
    jQuery.ajax({
        type: "GET",
        url: urll,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            getboard = json;
            pageCount= Number(getboard.pageCount);
            writePageNumber(pageCount);
            $('#myTable').empty();
            $.each(getboard, function (index, nickMarks) {
                $.each(nickMarks, function (index, nickMark) {
                    $('#myTable').append('<tr><td>'+ nickMark[0]+'</td><td>'+ nickMark[1]+'</td></tr>');
                })
            })
        }
    })

}

function writePageNumber(count){
    $('#pageNumber').empty();
    for (var i = 1; i <= count ; i++) {
        $('#pageNumber').append('<button onclick="viewBoard('+i+')">'+ i +'</button>'+' ');
    }
}

function calls(){
    viewBoard(1);
}


