package bookshelf.repository;

import bookshelf.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, String> {

     @Query( "select b from Book b where b.title like %?1%" )
     Iterable<Book> findByTitle(String title);


     Iterable<Book> findByCategoryCode( String code );

     Page<Book> findAll(Pageable pageable);
}

