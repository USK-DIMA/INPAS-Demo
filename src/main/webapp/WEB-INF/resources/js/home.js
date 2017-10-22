$(document).ready(function() {
    setDataTableLanguage();
    initHistoryTable();
})

function clearHistory() {
    $.ajax({
        url: getContextPath() + '/history',
        method: 'DELETE'
    }).done(function(){
        clearTable();
    }).fail(function(){
        alert('На сервере произошла ошибка');
    })
}


function clearTable() {
    $('#historyTable').DataTable().clear().draw();
}

function setDataTableLanguage() {
    $.extend( true, $.fn.dataTable.defaults, {
        "language": {
            "lengthMenu": "Показать _MENU_ строк",
            "zeroRecords": "Не найдено",
            "info": "Страница _PAGE_ из _PAGES_",
            "search": "Поиск",
            "emptyTable": "",
            "infoEmpty": "Нет доступных данных",
            "paginate": {
              "previous": "Предыдущая",
              "next": "Следующая"
            }
        }
    });
}

function getContextPath() {
    return window.location.pathname;
}

function initHistoryTable(){
    $('#historyTable').DataTable({
        ajax: getContextPath() +'/history',
        sAjaxDataProp: "",
        bInfo: false,
        searching: false,
        paging: false,
        columns: [
            {
                data: "index"
            },
            {
                data: "input"
            },
            {
                data: "output"
            }
        ]
    });
}

/**
    Считывает данные из input, валидирует, отправляет запрос для расчётов на сервер и отображает результат на экране
*/
function calculateText() {
    var inputDataStr = $('#inputArray').val();
    var inputDataInt = parseAndValidate(inputDataStr);
    if(inputDataInt == undefined) {
        alert('Ошибка входных данных');
        return;
    }
    $('#inputArray').val(inputDataInt);

   calculate(ajaxCalculateText, inputDataInt)

}

/**
    Отправляет файл с информацией о расчётах на сервер и отображает результат на экране
*/
function calculateFile() {
    var file = $('#inputFile').prop('files')[0]
    var formData = new FormData();
    formData.append("file", file);

    calculate(ajaxCalculateFile, formData);
}

function ajaxCalculateFile(data) {
    return $.ajax({
        url: getContextPath() + '/calculateFile',
        data: data,
        dataType: 'json',
        processData: false,
        contentType: false,
        type: 'POST'
    });
}

function ajaxCalculateText(inputArray) {
    return $.ajax({
        url: getContextPath() + '/calculateText',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(inputArray),
        method: 'POST'
    })
}


/**
    Общая часть при расчётах при вводе руками и при загрузке файла
*/
function calculate(ajaxRequestExecutor, requestData){
    ajaxRequestExecutor(requestData).done(function(outData) {
        updateAnswer(outData.output);
        insertRow(outData);
    }).fail(function(data) {
        alert('На сервере произошла ошибка. Проверье правильность входных данных.');
    })
}


/**
    Преобразует массив, записанный в виде строки в input в нормальный массив целых чисел.
    @return undefined, если введена невалидная строка.
*/
function parseAndValidate(inputDataString) {
    var re = /\s*[,|;]\s*/
    var inputArrayString = inputDataString.split(re);
    var ans = [];
    inputArrayString.forEach(function(item){
        var intValue = parseInt(item);
        if(isNaN(intValue)){
            ans = undefined;
            return;
        }
        ans.push(intValue);
    })
    return ans;
}

/**
    Отрисовывает 'answer' на экране
*/
function updateAnswer(answer) {
    $("#answerContainer").show();
    $("#currentAnswer").text(answer);
}


/**
    Добавляет в таблицу строку с данными
*/
function insertRow(outputData){
    var dt = $('#historyTable').DataTable();
    $('#historyTable').DataTable().row.add({
        index: outputData.index,
        input: outputData.input,
        output: outputData.output
    }).draw();
}