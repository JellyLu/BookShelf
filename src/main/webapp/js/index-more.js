 var page_indexMore = {
     bookList: document.querySelector('.moreBody'),
     pageCount:0,
     pageIndex:0,   //页索引
     pageSize:3,    //每页显示的条数
     currentPageCount:0,
     deleteRow: function(isbn){
           var rows = page_indexMore.bookList.getElementsByTagName('tr');
           for( var i = 0, len = rows.length; i < len; ++ i ){
              if( $(rows[i]).find('.col-isbn').text() === isbn ){
                   $(rows[i]).remove();
              }
           }
     },
     createRow: function( book ){
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
                        page_indexMore.deleteRow( book[tableHeaderMapper.ISBN] );
                    }
                });
            });
            td.appendChild( deleteBtn );
            td.setAttribute( 'class', 'col-operates' );
            tr.appendChild(td);
            return tr;
     },
     refresh:function(){

         $.ajax({
             type: "GET",
             dataType: "json",
             url:baseUrl + '/page/' + page_indexMore.pageIndex + '/' + page_indexMore.pageSize,
             success: function(data) {
                 if( data ){
                     page_indexMore.pageCount = data.totalElements;
                     var books= data.content;
                     books.forEach( function( book ){
                         var tr = page_indexMore.createRow( book );
                         page_indexMore.bookList.appendChild(tr);
                         page_indexMore.currentPageCount ++;
                     });
                 }
             }
         });

     },
     more:function(){
        if( page_indexMore.currentPageCount < page_indexMore.pageCount ){
                 page_indexMore.pageIndex ++;
                 page_indexMore.refresh();
        }
     }

 };

 window.onload = function(){
    page_indexMore.refresh();
 };

