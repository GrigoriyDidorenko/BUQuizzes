/**
 * Created by rondo104 on 16.12.2015.
 */
function myfun(){

    var url = "/trainer/addOpenTest/1";
    var text  = document.getElementById("cont").value;

    $.post(url,
        {
            question: text,
            isOpen: true
        },
        function(data,status){
           alert("Data: " + data + "\nStatus: " + status);
        });

}




