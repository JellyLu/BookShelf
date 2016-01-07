 window.onload = function(){
       var tableElements = [];
       var bookList = document.querySelector('tbody');

       var deleteRow = function(isbn){
            var rows = bookList.getElementsByTagName('tr');
            for( var i = 0, len = rows.length; i < len; ++ i ){
                bookList.removeChild( rows[i] );
            }
       };

       var createRow = function( book ){
            var tr = document.createElement('tr');

            if( !book ){
                return tr;
            }

            for( var key in tableHeaderMapper ){
                var td = document.createElement('td');
                td.textContent = book[tableHeaderMapper[key]];
                td.setAttribute('class', 'col-' + tableHeaderMapper[key]);
                tr.appendChild(td);
            }

            var td = document.createElement('td');
            var editBtn = document.createElement('a');
            editBtn.textContent = '编辑';
            editBtn.setAttribute('class', 'button');
            editBtn.setAttribute('href', 'pages/book/editBook.html?isbn=' + book[tableHeaderMapper.ISBN]);
            td.appendChild( editBtn );

            var deleteBtn = document.createElement('a');
            deleteBtn.textContent = '删除';
            deleteBtn.setAttribute('class', 'button');
            deleteBtn.addEventListener('click', function(){
                $.ajax({
                    url: baseUrl + '/' + book[tableHeaderMapper.ISBN],
                    type:'DELETE',
                    success:function(){
                        deleteRow( book[tableHeaderMapper.ISBN] );
                    }
                });
            });
            td.appendChild( deleteBtn );
            td.setAttribute( 'class', 'col-operates' );
            tr.appendChild(td);
            return tr;
       };

       $.ajax({
            url:baseUrl,
            dataType:'json',
            success:function( books ){
                if( !books.length ){
                    bookList.appendChild( createRow() );
                }else{
                    books.forEach( function( book ){
                        var tr = createRow( book );
                        tableElements.push(tr);
                        bookList.appendChild(tr);
                    });
                }
            }
       });
 };
