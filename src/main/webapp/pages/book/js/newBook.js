window.onload = function () {
    var isbn;
    var form = document.querySelector('.form');
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        var formElements = e.target.elements;
        var book = {};
        for (var i = 0; i < formElements.length - 1; ++i) {
            book[tableHeaderMapper[formElements[i].name]] = formElements[i].value;
        }
        if (isbn) {
            /*用jquery 实现PUT方式调用api请求*/
             $.ajax({
                type: "POST",
                url: baseUrl,
                data: JSON.stringify(book),
                contentType: "application/json; charset=utf-8",
                success: function () {
                      location.href = '/index.html';
                }
              });
        } else {
            $.ajax({
                type: "POST",
                url: baseUrl,
                data: JSON.stringify(book),
                contentType: "application/json; charset=utf-8",
                success: function () {
                    location.href = '/index.html';
                }
            });
        }
    });
};
