 var page_index = {
     bookList: document.querySelector('.indexBody'),
     pageCount:0,
     pageIndex:0,   //页索引
     pageSize:3,    //每页显示的条数
     firstQuest:true,
     deleteRow: function(isbn){
            var rows = page_index.bookList.getElementsByTagName('tr');
            for( var i = 0, len = rows.length; i < len; ++ i ){
                if( $(rows[i].querySelector('.col-isbn')).textContent === isbn ){
                      page_index.bookList.removeChild( rows[i] );
                      page_index.pageCount--;
                      page_index.refresh();
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
            editBtn.addEventListener('click',function(){
               window.location = 'pages/book/editBook.html?isbn=' + book[tableHeaderMapper.ISBN];;
            });
            td.appendChild( editBtn );

            var deleteBtn = document.createElement('a');
            deleteBtn.textContent = '删除';
            deleteBtn.setAttribute('class', 'button');
            deleteBtn.addEventListener('click', function(){
                $.ajax({
                    url: baseUrl + '/' + book[tableHeaderMapper.ISBN],
                    type:'DELETE',
                    success:function(){
                        page_index.deleteRow( book[tableHeaderMapper.ISBN] );
                        page_index.refresh();
                    }
                });
            });
            td.appendChild( deleteBtn );
            td.setAttribute( 'class', 'col-operates' );
            tr.appendChild(td);
            return tr;
     },
     refresh:function(){

         page_index.page = $("#Pagination").pagination( page_index.pageCount, {
                     callback: pageCallback,
                     prev_text: '上一页',
                     next_text: '下一页',
                     items_per_page: page_index.pageSize,
                     num_display_entries: 5,
                     current_page: page_index.pageIndex,
                     num_edge_entries: 1
         });

         function pageCallback(index, jq) {
              page_index.init(index);
         }

     },
     init:function( index ) {
           $.ajax({
               type: "GET",
               dataType: "json",
               url:baseUrl + '/page/' + index + '/' + page_index.pageSize,
               success: function(data) {
                   if( data ){
                       page_index.pageCount = data.totalElements;

                       var books= data.content;
                       $(page_index.bookList).empty();
                       books.forEach( function( book ){
                            var tr = page_index.createRow( book );
                             page_index.bookList.appendChild(tr);
                       });
                       if( page_index.firstQuest ){
                           page_index.refresh();
                           page_index.firstQuest = false;
                       }
                   }
               }

           });
     }

 };

 window.onload = function(){
    page_index.init(0);

 };

