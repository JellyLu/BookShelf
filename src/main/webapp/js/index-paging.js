 var page_indexPaging = {
     bookList: document.querySelector('.indexBody'),
     pageCount:0,
     pageIndex:0,   //页索引
     pageSize:3,    //每页显示的条数
     firstQuest:true,
     deleteRow: function(isbn){
            var rows = page_indexPaging.bookList.getElementsByTagName('tr');
            for( var i = 0, len = rows.length; i < len; ++ i ){
                if( $(rows[i].querySelector('.col-isbn')).textContent === isbn ){
                      page_indexPaging.bookList.removeChild( rows[i] );
                      page_indexPaging.pageCount--;
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
                        page_indexPaging.deleteRow( book[tableHeaderMapper.ISBN] );
                        page_indexPaging.refresh();
                    }
                });
            });
            td.appendChild( deleteBtn );
            td.setAttribute( 'class', 'col-operates' );
            tr.appendChild(td);
            return tr;
     },
     refresh:function(){

         page_indexPaging.page = $("#pagination").paging( page_indexPaging.pageCount, {
                                	format: '[< ncnnn >]',
	                                perpage: page_indexPaging.pageSize,
	                                page:page_indexPaging.pageIndex,
                                	onSelect: function (page) {
                                		page_indexPaging.init( page );
                                	},
                                	onFormat: function (type) {
                                		switch (type) {
                                		case 'block': // n and c
                                		    if (!this.active){
                                        	    return '<a class="disabled">' + this.value + '</a>';
                                            }else if (this.value != this.page)
                                                return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
                     						else{
                     						    return '<a class="current">' + this.value + '</a>';
                     						}
                                		case 'next': // >
                                			if (this.active) {
                                            	return '<a href="#' + this.value + '" class="next">&raquo;</a>';
                                            }else{
            								    return '<a class="disabled">&raquo;</a>';
            								}
                                		case 'prev': // <
                                			if (this.active) {
                                            	return '<a href="#' + this.value + '" class="prev">&laquo;</a>';
                							}else{
                                                return '<a class="disabled">&laquo;</a>';
                                            }
                                		case 'first': // [
                                			if (this.active) {
                                                return '<a href="#' + this.value + '" class="first">|&lt;</a>';
                							}else{
                                                return '<a class="disabled">|&lt;</a>';
                                            }
                                		case 'last': // ]
                                			if (this.active) {
                                                return '<a href="#' + this.value + '" class="prev">&gt;|</a>';
                                            }else{
                                                return '<a class="disabled">&gt;|</a>';
                                            }
                                		}
                                	},
                                	onRefresh:function(){
                                        page_indexPaging.page.setNumber( page_indexPaging.pageCount );
                                	}
                                });
     },
     init:function( index ) {
           if( !page_indexPaging.firstQuest && index > 0 ){
                index --;
           }
           $.ajax({
               type: "GET",
               dataType: "json",
               url:baseUrl + '/page/' + index + '/' + page_indexPaging.pageSize,
               success: function(data) {
                   if( data ){
                       page_indexPaging.pageCount = data.totalElements;
                       if( page_indexPaging.firstQuest ){
                          page_indexPaging.refresh();
                          page_indexPaging.firstQuest = false;
                       }else{
                           var books= data.content;
                           $(page_indexPaging.bookList).empty();
                           books.forEach( function( book ){
                                var tr = page_indexPaging.createRow( book );
                                 page_indexPaging.bookList.appendChild(tr);
                           });
                       }
                   }
               }

           });
     }

 };

 window.onload = function(){
    page_indexPaging.init(0);
 };

