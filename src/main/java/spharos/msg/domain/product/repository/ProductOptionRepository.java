package spharos.msg.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.msg.domain.options.dto.OptionTypeDto;
import spharos.msg.domain.options.entity.Options;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductOption;

import java.util.List;
import java.util.Set;

public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {
    List<ProductOption> findByProduct(Product product);

    ProductOption findByOptions(Options options);

    @Query("select new spharos.msg.domain.options.dto.OptionTypeDto(o.optionLevel,o.optionType) from Options o where o= :options")
    OptionTypeDto findTypeByOptions(@Param("options")Options options);

    @Query("SELECT po.options.parent.id FROM ProductOption po WHERE po IN :productOptions AND po.options.parent IS NOT NULL")
    Set<Long> getParentOptionIds(@Param("productOptions") List<ProductOption> productOptions);

}
