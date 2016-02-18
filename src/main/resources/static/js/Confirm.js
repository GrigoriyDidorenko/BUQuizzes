$(document).ready(function ($) {
    var getuser;
    jQuery.ajax({
        type: "GET",
        url: "/profile/currentUser",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            getuser = json;
            $('#name').append(getuser.firstName + " " + getuser.lastName);

//getting tests
            var userId=getuser.id;
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
            var resultid = GetURLParameter('resultId');
            var myjson;
            jQuery.ajax({
                type: "GET",
                url: '/student/tests',
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (json) {
                    myjson = json;
                    $.each(myjson, function (index, myjs) {
                        if(myjs.resultId==resultid){
                            $('#mark').append(myjs.testDTO.mark);
                            if(myjs.testDTO.checked){

                            }else
                                $('#testid').append("В тесті наявні відкриті відповіді, які потребують перевірки.");
                        }
                    })
                }
            })
        }
    })
});