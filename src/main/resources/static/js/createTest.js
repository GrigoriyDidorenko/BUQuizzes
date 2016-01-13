/**
 * Created by c2411 on 13.01.2016.
 */
var counter = 1;
function addTestInput(divName){
    var newdiv = document.createElement('div');
    newdiv.innerHTML = "Entry " + (counter + 1) + "<br><input type='text' id='testInput"+counter+"'>"+
    "<br><div id='questionAndAnswer'><div id='dynamicInputQuestion'><input type='text' id='questionInput-"+counter+"'>" +
        "<input type='button' value='Add another question input' onClick='addQuestionInput(\"questionAndAnswer\")'></div>" +
        "<br><div id='dynamicInputAnswer'><input type='text' id='answerInput-"+counter+"'>" +
        "<input type='button' value='Add another answer input' onClick='addQuestionInput(\"dynamicInputAnswer\")'></div></div>"
    document.getElementById(divName).appendChild(newdiv);
    counter++;
}

function addQuestionInput(divName){
    var newdiv = document.createElement('div');
    newdiv.innerHTML = "Entry " + (counter + 1) + " <br><input type='text' name='myInputs[]' id='questionInput-"+counter+"'>";
    document.getElementById(divName).appendChild(newdiv);
    counter++;
}

function addAnswerInput(divName){
    var newdiv = document.createElement('div');
    newdiv.innerHTML = "Entry " + (counter + 1) + " <br><input type='text' name='myInputs[]' id='answerInput-"+counter+"'>";

    document.getElementById(divName).appendChild(newdiv);
    counter++;
}