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

       var pageIndex =0;   //页索引
       var pageSize =3;    //每页显示的条数
       $("#Pagination").pagination( 7, {
                callback: PageCallback,
                prev_text: '上一页',
                next_text: '下一页',
                items_per_page: pageSize,
                num_display_entries: 5,
                current_page: pageIndex,
                num_edge_entries: 1
       });
       function PageCallback(index, jq) {
                Init(index);
       }

       function Init( index ) {
           $(bookList).empty();//移除所有的数据行
           $.ajax({
               type: "GET",
               dataType: "json",
               url:baseUrl + '/page/' + index + '/' + pageSize,
               success: function(data) {
                   if( data ){
                       var books= data.content;
                       books.forEach( function( book ){
                         var tr = createRow( book );
                         tableElements.push(tr);
                         $(bookList).append(tr);
                       });
                   }
               }
           });
       }

 };
