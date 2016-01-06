package bookshelf.repository;

import bookshelf.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {

     @Query( "select b from Book b where b.title like %?1%" )
     Iterable<Book> findByTitle(String title);


     Iterable<Book> findByCategoryCode( String code );
}

